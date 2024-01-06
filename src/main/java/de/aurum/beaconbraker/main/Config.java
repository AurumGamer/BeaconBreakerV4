package de.aurum.beaconbraker.main;

import de.aurum.beaconbraker.util.Utils;
import org.bukkit.ChatColor;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class Config {

    private static final BeaconBreaker plugin = BeaconBreaker.getPlugin();
    private final File file;
    private final String name;
    private final FileConfiguration fileConfiguration;

    public Config(String name) {
        this.name = name;
        this.file = new File(plugin.getDataFolder(), name);
        if (!this.file.exists()) {
            this.file.getParentFile().mkdirs();
        }

        this.fileConfiguration = new YamlConfiguration();


        try {
            this.fileConfiguration.load(file);
        } catch (IOException | InvalidConfigurationException e) {
            Utils.sendErrorMessage("Failed loading " + this.name + " configuration", this.getClass().getName() + "\n" + e.toString() + ChatColor.BOLD + "\n Ignore on first startup");
        }
    }

    public Config(FileConfiguration fileConfiguration){
        this.fileConfiguration = fileConfiguration;
        this.file = null;
        this.name = fileConfiguration.getName();
    }

    public void save(){
        try {
            fileConfiguration.save(file);
        } catch (IOException e) {
            Utils.sendErrorMessage("Failed saving " + name + " configuration", this.getClass().getName());
        }
    }
    public void reload(){
        try {
            fileConfiguration.load(file);
        } catch (IOException | InvalidConfigurationException e) {
            Utils.sendErrorMessage("Failed loading " + name + " configuration", this.getClass().getName());
        }
    }
    //<-----------Getters and Setters----------->

    public FileConfiguration getConfiguration() {
        return fileConfiguration;
    }
}
