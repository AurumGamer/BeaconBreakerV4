package de.aurum.beaconbraker.main;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class Config {
    private File file;
    private FileConfiguration fileConfiguration;

    public Config(String name, File path) {
        this.file = new File(path, name);

        if(!file.exists()){
            path.mkdir();
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        fileConfiguration = new YamlConfiguration();
        try {
            fileConfiguration.load(file);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }
    public void save(){
        try {
            fileConfiguration.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void reload(){
        try {
            fileConfiguration.load(file);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidConfigurationException e) {
            throw new RuntimeException(e);
        }
    }
    //<-----------Getters and Setters----------->
    public File getFile() {
        return file;
    }
    public FileConfiguration getFileConfiguration() {
        return fileConfiguration;
    }
}
