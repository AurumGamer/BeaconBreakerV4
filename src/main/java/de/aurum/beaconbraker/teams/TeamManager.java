package de.aurum.beaconbraker.teams;

import de.aurum.beaconbraker.main.Data;
import de.aurum.beaconbraker.util.Utils;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class TeamManager {

    private static final FileConfiguration defaultConfig = Data.getDefaultConfig();
    private static final FileConfiguration locationsConfig = Data.getLocationsConfig();
    private static final Map<String, Team> teams = new HashMap<>();

    public void setupTeams() {

        try {
            for(String key : defaultConfig.getConfigurationSection("game.teams").getKeys(false)){
                String name = defaultConfig.getString("game.teams." + key + ".name");
                ChatColor color = ChatColor.valueOf(defaultConfig.getString("game.teams." + key + ".color"));
                Location spawnPoint = locationsConfig.getLocation("locations.teams." + key + ".spawnpoint");
                if(name == null || color == null || spawnPoint == null) throw new NullPointerException();
                teams.put(key ,new Team(name, color, spawnPoint));
            }
        }catch (NullPointerException e){
            Utils.sendErrorMessage("Failed loading Team configuration");
        }
    }

    //<-----------Getters and Setters----------->

    public Team getTeam(String key){
        return teams.get(key);
    }

    public Set<String> getTeamKeys(){
        return teams.keySet();
    }

}


