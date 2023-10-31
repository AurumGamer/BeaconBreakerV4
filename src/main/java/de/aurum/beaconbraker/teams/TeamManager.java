package de.aurum.beaconbraker.teams;

import de.aurum.beaconbraker.main.Data;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashSet;
import java.util.Set;

public class TeamManager {

    private static final FileConfiguration config = Data.getLocationsConfig().getFileConfiguration();
    Set<Team> teams = new HashSet<>();

    public void setupTeams() {
        for(String key : config.getConfigurationSection("Game.Teams").getKeys(false)){
            String name = config.getString("Game.Teams." + key + ".Name");
            config.set("Game.Teams." + key + ".Color", Color.RED);
            ChatColor color = ChatColor.valueOf(config.getString("Game.Teams." + key + ".Color"));
            Location spawnPoint = config.getLocation("Game.Teams." + key + ".Spawnpoint");
            teams.add(new Team(name, color, spawnPoint));
        }
    }

}
