package de.aurum.beaconbraker.util;

import de.aurum.beaconbraker.util.data.DataManager;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
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

    public static void sendErrorMessage(String error, String cause){
        if(cause == null) cause = "No cause specified";
        String prefix = (DataManager.getPrefix() != null) ? DataManager.getPrefix() : "";
        TextComponent errorText = new TextComponent(prefix + ChatColor.RED + "Error: "+ error);
        Text causeText = new Text(ChatColor.RED + "Cause: " + cause);
        errorText.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, causeText));

        Bukkit.getConsoleSender().sendMessage( ChatColor.RED + errorText.getText() + "\nCause: " + cause);
        for(Player player : Bukkit.getOnlinePlayers()){
            if(player.isOp()) player.spigot().sendMessage(errorText);
        }
    }

    public static void sendUsage(Player player, Command cmd){
        String message = DataManager.getUsage().replaceAll("(?i)%Usage%", cmd.getUsage());
        player.sendMessage(message);
    }

    public static void sendUsage(Player player, String usage){
        String message = DataManager.getUsage().replaceAll("(?i)%Usage%", usage);
        player.sendMessage(message);
    }

    public static void sendOperatorMessage(String message){
        Bukkit.getConsoleSender().sendMessage(message);
        for(Player player : Bukkit.getOnlinePlayers()){
            if(player.isOp()) player.sendMessage(message);
        }
    }

    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }



}
