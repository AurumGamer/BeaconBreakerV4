package de.aurum.beaconbraker.util;

import de.aurum.beaconbraker.main.BeaconBreaker;
import de.aurum.beaconbraker.main.Data;
import de.aurum.beaconbraker.util.teams.Team;
import de.aurum.beaconbraker.util.teams.TeamManager;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Villager;
import org.bukkit.metadata.FixedMetadataValue;

import javax.annotation.Nonnull;
import java.util.*;

public class EntityManager {

    private static Map<String, Villager> villagers = new HashMap<String,Villager>();

    private static final FileConfiguration locationsConfig = Data.getLocationsConfig();

    public static void spawnBoss(){
        //TODO Add boss spawn command
    }



    public static void spawnShops(){
        for (Team team : TeamManager.getTeams()) {
            villagers.put(team.getName()+"shop", createVillager(team.getShopLocation(), team, "shop"));
            villagers.put(team.getName()+"upgrades", createVillager(team.getUpgradesLocation(), team, "upgrades"));
        }
    }

    private static Villager createVillager(@Nonnull Location location, @Nonnull Team team, @Nonnull String type){
        String teamKey = team.getName();
        String entityName = team.getColor() + type.substring(0, 1).toUpperCase() + type.substring(1);
        Optional<Villager> optionalVillager;
        Villager shopVillager = null;
        if (!location.getWorld().getEntitiesByClass(Villager.class).isEmpty()){
            for (Villager villager : location.getWorld().getEntitiesByClass(Villager.class)){
                if((!villager.getMetadata("id").isEmpty()) &&villager.getMetadata("id").get(0).asString().equals(teamKey+type)){
                    shopVillager = villager;
                }
            }
        }
        if(shopVillager == null) shopVillager = (Villager) location.getWorld().spawnEntity(location, EntityType.VILLAGER);

        shopVillager.setCustomName(entityName);
        shopVillager.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(0);
        shopVillager.setCanPickupItems(false);
        shopVillager.setCollidable(false);
        shopVillager.setCustomNameVisible(true);
        shopVillager.setProfession(Villager.Profession.ARMORER);
        shopVillager.setVillagerLevel(2);
        shopVillager.setMetadata("id", new FixedMetadataValue(BeaconBreaker.getPlugin(), teamKey+type));
        shopVillager.teleport(location);

        return shopVillager;
    }
}
