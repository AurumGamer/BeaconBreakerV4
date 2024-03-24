package de.aurum.beaconbraker.main;

import de.aurum.beaconbraker.commands.*;
import de.aurum.beaconbraker.listener.EntityInteractListener;
import de.aurum.beaconbraker.listener.FoodLevelChangeListener;
import de.aurum.beaconbraker.listener.PlayerJoinListener;
import de.aurum.beaconbraker.util.GameManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Server;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.awt.*;

public final class BeaconBreaker extends JavaPlugin {

    private static BeaconBreaker plugin;
    public static boolean debugMode = true;

    @Override
    public void onEnable() {
        // Plugin startup logic
        plugin = this;
        Data.setupData();
        GameManager.setUp();
        startupMessage();
        registerCommands();
        registerEvents();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private void registerCommands() {
        getCommand("setlobbyspawn").setExecutor(new SetLobbySpawnCommand());
        getCommand("setteamposition").setExecutor(new SetTeamPositionsCommand());
        getCommand("setteamposition").setTabCompleter(new SetTeamPositionsCommand());
        getCommand("setchest").setExecutor(new SetChestCommand());
        getCommand("bbreload").setExecutor(new ReloadCommand());
        getCommand("itemConfig").setExecutor(new ItemConfigCommand());
    }
    private void registerEvents() {
        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new PlayerJoinListener(), this);
        pluginManager.registerEvents(new FoodLevelChangeListener(), this);
        pluginManager.registerEvents(new EntityInteractListener(), this);
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

}
