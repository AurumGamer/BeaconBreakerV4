package de.aurum.beaconbraker.util;

import de.aurum.beaconbraker.main.BeaconBreaker;
import de.aurum.beaconbraker.main.Data;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.*;
import java.util.stream.Collectors;

public class EntityManager {

    private static final FileConfiguration locationsConfig = Data.getLocationsConfig();

    public static void spawnBoss(){
        //TODO Add boss spawn command
    }



    public static void spawnShops(){
        if (locationsConfig.getConfigurationSection("locations.teams") != null) {
            for (String teamKey : locationsConfig.getConfigurationSection("locations.teams").getKeys(false)) {
                if (locationsConfig.getConfigurationSection("locations.teams." + teamKey) != null) {
                    Set<String> sortetLocationKeys = locationsConfig.getConfigurationSection("locations.teams."+ teamKey).getKeys(false).stream()
                            .filter(value -> value.equals("shop") || value.equals("upgrades"))
                            .collect(Collectors.toSet());
                    for (String locationKey : sortetLocationKeys) {
                        Location spawnLocation = locationsConfig.getLocation("locations.teams."+ teamKey + "." + locationKey);
                        if(spawnLocation != null){
                            String entityName = ChatColor.BLACK + locationKey.substring(0, 1).toUpperCase() + locationKey.substring(1);
                            Optional<Villager> optionalVillager;
                            Villager shopVillager;
                            if (spawnLocation.getWorld().getEntitiesByClass(Villager.class).stream().anyMatch(entity -> entity.getMetadata("id").get(0).asString().equals(teamKey+locationKey))){
                                optionalVillager = spawnLocation.getWorld().getEntitiesByClass(Villager.class).stream()
                                        .filter(entity -> entity.getMetadata("id").get(0).asString().equals(teamKey+locationKey)).findFirst();
                                shopVillager = optionalVillager.map(villager -> (Villager) villager).orElseGet(() -> (Villager) spawnLocation.getWorld().spawnEntity(spawnLocation, EntityType.VILLAGER));
                            }else {
                                shopVillager = (Villager) spawnLocation.getWorld().spawnEntity(spawnLocation, EntityType.VILLAGER);
                            }
                            shopVillager.setCustomName(entityName);
                            shopVillager.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(0);
                            shopVillager.setCanPickupItems(false);
                            shopVillager.setCollidable(false);
                            shopVillager.setCustomNameVisible(true);
                            shopVillager.setProfession(Villager.Profession.ARMORER);
                            shopVillager.setVillagerLevel(2);
                            shopVillager.setMetadata("id", new FixedMetadataValue(BeaconBreaker.getPlugin(), teamKey+locationKey));
                        }
                    }
                }
            }
        }
    }

}
