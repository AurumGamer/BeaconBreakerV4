package de.aurum.beaconbraker.main;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Location;

import java.util.Objects;

public class Data {
    
    private static final BeaconBreaker plugin = BeaconBreaker.getPlugin();

    private static Config locationsConfig;
    private static String prefix;
    private static String noPerm;
    private static String usage;
    private static String joinMessage;
    private static String wrongSender;

    public static void setupData() {
        plugin.saveDefaultConfig();
        formatConfigStrings();
        locationsConfig = new Config("locations.yml", plugin.getDataFolder());
    }

    private static void formatConfigStrings(){
        prefix = formatString("Chat.Prefix");
        joinMessage = formatString("Chat.JoinMessage");
        noPerm = formatString("Chat.NoPerm").replace("&", "§");
        wrongSender = formatString("Chat.WrongSender");
        usage = formatString("Chat.Usage");
    }
    
    private static String formatString(String path){
        String s = plugin.getConfig().getString(path);
        if( s != null){
            if(s.contains("&")) s = s.replace("&", "§");
            if(s.contains("%Prefix%")) s = s.replace("%Prefix", prefix);
        }
        return s;
    }

    public static Location roundLocation(Location location){
        location.setX(Math.floor(location.getX())+0.5);
        location.setY(Math.floor(location.getY()));
        location.setZ(Math.floor(location.getZ())+0.5);
        location.setPitch(0);

        int yaw = Math.round(location.getYaw());
        int rem = 0;
        rem = yaw % 90;
        if(rem>45) {
            yaw = yaw+(90-rem);
        }else if(rem<-45) {
            yaw = yaw+(-90-rem);
        } else {
            yaw = yaw - rem;
        }
        location.setYaw(yaw);

        return location;
    }



//<-----------Getters and Setters----------->
    public static String getPrefix() {
        return prefix;
    }

    public static String getNoPerm() {
        return noPerm;
    }

    public static String getUsage(String command) {
        if(usage.contains("%Usage%")){
            usage.replace("%Usage%", plugin.getCommand(command).getUsage());
        }
        return usage;
    }

    public static String getJoinMessage() {
        return joinMessage;
    }
    public static String getWrongSender() {
        return wrongSender;
    }
    public static Config getLocationsConfig() {
        return locationsConfig;
    }

}
