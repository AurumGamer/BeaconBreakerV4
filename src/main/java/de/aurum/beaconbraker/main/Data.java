package de.aurum.beaconbraker.main;

import de.aurum.beaconbraker.util.ItemBuilder;
import de.aurum.beaconbraker.util.Utils;
import de.aurum.beaconbraker.util.shop.ShopItem;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Data {
    
    private static final BeaconBreaker plugin = BeaconBreaker.getPlugin();
    private static Config config, locationsConfig, itemConfig;
    private static String prefix, noPerm, usage, joinMessage, wrongSender;
    private static Location lobbySpawnLocation; ;

    public static void setupData() {
        locationsConfig = new Config("locations.yml");
        plugin.saveDefaultConfig();
        config = new Config("config.yml");
        itemConfig = new Config("itemConfig.yml");
        prefix = formatConfigString("chat.prefix");
        joinMessage = formatConfigString("chat.joinMessage");
        noPerm = formatConfigString("chat.noPerm");
        wrongSender = formatConfigString("chat.wrongSender");
        usage = formatConfigString("chat.usage");
        lobbySpawnLocation = getLocationsConfig().getLocation("locations.lobby.spawn");
    }
    
    
    private static @Nullable String formatConfigString(String path){
        String s = Data.getDefaultConfig().getString(path);
        if (s != null) {
            if (s.contains("&")) {
                s = s.replace("&", "§");
            }
            if (s.contains("%Prefix%")) {
                s = s.replace("%Prefix%", prefix);
            }
                return s;
            }
        return null;
    }

    public static Location roundLocation( @NotNull Location location){
        location.setX(Math.floor(location.getX())+0.5);
        location.setY(Math.floor(location.getY()));
        location.setZ(Math.floor(location.getZ())+0.5);
        location.setPitch(0);
        location.setYaw(Math.round(location.getYaw() / 90) * 90);
        return location;
    }

    public static void reloadResources(){
        plugin.reloadConfig();
        locationsConfig.reload();
        config.reload();
        prefix = formatConfigString("chat.prefix");
        joinMessage = formatConfigString("chat.joinMessage");
        noPerm = formatConfigString("chat.noPerm");
        wrongSender = formatConfigString("chat.wrongSender");
        usage = formatConfigString("chat.usage");
        lobbySpawnLocation = getLocationsConfig().getLocation("locations.lobby.spawn");
        Utils.sendOperatorMessage(Data.prefix + "Plugin Reloaded");
    }

    public static ItemStack getItemFromConfig(String path){
        FileConfiguration fileConfiguration = itemConfig.getConfiguration();
        String displayName = fileConfiguration.getString(path + ".diplayname");
        String type = fileConfiguration.getString(path + ".type");
        Material material = Material.getMaterial(type);
        if(material == null){
            String cause = type == null ? "type of item (material) is not specified in config" : path + ".type cannot be converted into item Material";
            Utils.sendErrorMessage("Failed loading Material of item from itemConfig", cause );
        }
        HashMap<Enchantment, Integer> enchantmentIntegerMap = new HashMap<Enchantment, Integer>();
        if(fileConfiguration.get(path + ".enchantments") != null){
            ArrayList<String> enchantments = (ArrayList<String>) fileConfiguration.getList(path + ".enchantments");
            if(enchantments != null){
                for(String enchanmentString : enchantments) {
                    String[] split = enchanmentString.split(":");
                    enchantmentIntegerMap.put(Enchantment.getByKey(NamespacedKey.minecraft(split[0])), Integer.parseInt(split[1]));
                }
            }

        }
        List<String> lore = (List<String>) fileConfiguration.getList(path + ".lore");
        ItemStack item = new ItemBuilder(material).setName(displayName).setLore(lore).build();
        item.addUnsafeEnchantments(enchantmentIntegerMap);
        return item;
    }


//<-----------Getters and Setters----------->
    public static String getPrefix() {
        return prefix;
    }

    public static String getNoPerm() {
        return noPerm;
    }

    public static String getUsage() {
        return usage;
    }

    public static String getJoinMessage() {
        return joinMessage;
    }
    public static String getWrongSender() {
        return wrongSender;
    }
    public static FileConfiguration getLocationsConfig() {
        return locationsConfig.getConfiguration();
    }
    public static FileConfiguration getItemConfig() {
        return itemConfig.getConfiguration();
    }

    public static FileConfiguration getDefaultConfig(){
        return config.getConfiguration();
    }

    public static Location getLobbySpawnLocation() {
        return lobbySpawnLocation;
    }

    public static void saveLocationsConfig(){
        locationsConfig.save();
    }
    public static void saveItemConfig(){
        itemConfig.save();
    }

    public static void saveConfig(){
        plugin.saveDefaultConfig();
    }

}
