package de.aurum.beaconbraker.main;

import de.aurum.beaconbraker.util.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Data {
    
    private static final BeaconBreaker plugin = BeaconBreaker.getPlugin();
    private static Config config, locationsConfig;
    private static String prefix, noPerm, usage, joinMessage, wrongSender;
    private static Location lobbySpawnLocation; ;

    public static void setupData() {
        locationsConfig = new Config("locations.yml");
        plugin.saveDefaultConfig();
        config = new Config("config.yml");
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

    public static Location getLobbySpawnLocation() {
        return lobbySpawnLocation;
    }

    public static void saveLocationsConfig(){
        locationsConfig.save();
    }

    public static void saveConfig(){
        plugin.saveDefaultConfig();
    }

}
