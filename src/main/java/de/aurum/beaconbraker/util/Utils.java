package de.aurum.beaconbraker.util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Utils {

    public static boolean userHasPermission(Command cmd, CommandSender sender, String permission) {
        final String cmdPermission = cmd.getPermission();
        boolean temp = cmdPermission != null && sender.hasPermission(cmdPermission);
        return temp || sender.hasPermission(permission);
    }

    public static boolean userHasPermission(Command cmd, CommandSender sender) {
        final String cmdPermission = cmd.getPermission();
        return cmdPermission != null && sender.hasPermission(cmdPermission);
    }

    public static void sendErrorMessage(String text){
        Bukkit.getConsoleSender().sendMessage( ChatColor.RED + "Error: "+ text);
        for(Player p : Bukkit.getOnlinePlayers()){
            if(p.isOp()) p.sendMessage(ChatColor.RED + "Error: "+ text);
        }
    }


}
