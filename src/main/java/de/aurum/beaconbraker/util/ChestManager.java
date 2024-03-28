package de.aurum.beaconbraker.util;

import de.aurum.beaconbraker.util.data.DataManager;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.Inventory;

import java.util.Random;

public class ChestManager {

    private static FileConfiguration cfg = DataManager.getLocationsConfig();

    private static final Material[] materials = {Material.GOLD_INGOT, Material.DIAMOND, Material.NETHERITE_SCRAP, Material.BLAZE_ROD};

    public static void fillChests() {
        if (cfg.getConfigurationSection("locations.chests") != null) {
            for (String key : cfg.getConfigurationSection("locations.chests").getKeys(false)) {
                Location location = cfg.getLocation("locations.chests." + key);
                if (location != null) {
                    if (location.getBlock().getType() == Material.CHEST) {
                        Chest chest = (Chest) location.getBlock().getState();
                        Inventory inv = chest.getInventory();
                        inv.clear();
                        Random rand = new Random();

                        int randomPercentage = rand.nextInt(100);
                        int randomAmount;

                        if (randomPercentage <= 40) {
                            randomAmount = 1;
                        } else if (randomPercentage <= 75) {
                            randomAmount = 2;
                        } else if (randomPercentage <= 90) {
                            randomAmount = 3;
                        } else {
                            randomAmount = 4;
                        }

                        for (int i = 0; i < randomAmount; i++) {
                            int randomSlot = rand.nextInt(26);
                            int randomMaterial = rand.nextInt(4);
                            inv.setItem(randomSlot, new ItemBuilder(materials[randomMaterial], 1).build());
                        }
                    }
                }
            }
        }
    }

    public static void placeChests() {
        if (cfg.getConfigurationSection("locations.chests") != null) {
            for (String key : cfg.getConfigurationSection("locations.chests").getKeys(false)) {
                Location location = cfg.getLocation("locations.chests." + key);
                if (location != null) {
                    Block block = location.getBlock();
                    if(block.getType() != Material.CHEST){
                        block.setType(Material.CHEST);
                    }
                }
            }
        }
    }
}
