package de.aurum.beaconbraker.main;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class BeaconBreaker extends JavaPlugin {

    private static BeaconBreaker plugin;

    @Override
    public void onEnable() {
        // Plugin startup logic
        plugin = this;
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private void registerCommands(PluginManager pluginManager) {

    }
    private void registerEvents(PluginManager pluginManager) {

    }

    public void startupMessage() {
        Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA +"################################################################################\n" +
                "#" + ChatColor.GOLD + "\t ____                             ____                 _             \t" + ChatColor.AQUA + "#\n" +
                ChatColor.AQUA + "#" + ChatColor.GOLD + "\t|  _ \\                           |  _ \\               | |            \t" + ChatColor.AQUA + "#\n" +
                ChatColor.AQUA + "#" + ChatColor.GOLD + "\t| |_) | ___  __ _  ___ ___  _ __ | |_) |_ __ ___  __ _| | _____ _ __ \t" + ChatColor.AQUA + "#\n" +
                ChatColor.AQUA + "#" + ChatColor.GOLD + "\t|  _ < / _ \\/ _` |/ __/ _ \\| '_ \\|  _ <| '__/ _ \\/ _` | |/ / _ \\ '__|\t" + ChatColor.AQUA + "#\n" +
                ChatColor.AQUA + "#" + ChatColor.GOLD + "\t| |_) |  __/ (_| | (_| (_) | | | | |_) | | |  __/ (_| |   <  __/ |   \t" + ChatColor.AQUA + "#\n" +
                ChatColor.AQUA + "#" + ChatColor.GOLD + "\t|____/ \\___|\\__,_|\\___\\___/|_| |_|____/|_|  \\___|\\__,_|_|\\_\\___|_|   \t" + ChatColor.AQUA + "#\n" +
                ChatColor.AQUA + "#" + ChatColor.GOLD + "\t                                                                      \t" + ChatColor.AQUA + "#\n" +
                ChatColor.AQUA + "################################################################################\n");
    } //TODO: Improve this method

    //<-----------Getters and Setters----------->
    public static BeaconBreaker getPlugin(){
        return plugin;
    }
    public static void setPlugin(BeaconBreaker plugin) {
        BeaconBreaker.plugin = plugin;
    }
}
