package de.aurum.beaconbraker.main;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Data {
    
    private static final BeaconBreaker plugin = BeaconBreaker.getPlugin();
    private static Config config;
    private static Config locationsConfig;
    private static String prefix;
    private static String noPerm;
    private static String usage;
    private static String joinMessage;
    private static String wrongSender;

    public static void setupData() {
        plugin.saveDefaultConfig();
        config = new Config(plugin.getConfig());
        prefix = formatString("chat.prefix");
        joinMessage = formatString("chat.joinMessage");
        noPerm = formatString("chat.noPerm");
        wrongSender = formatString("chat.wrongSender");
        usage = formatString("chat.usage");
        locationsConfig = new Config("locations.yml");
    }
    
    
    private static @Nullable String formatString(String path){
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

    public static FileConfiguration getDefaultConfig(){
        return config.getConfiguration();
    }

    public static void saveLocationsConfig(){
        locationsConfig.save();
    }

    public static void saveConfig(){
        plugin.saveDefaultConfig();
    }

}
