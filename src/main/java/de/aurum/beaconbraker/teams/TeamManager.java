package de.aurum.beaconbraker.teams;

import de.aurum.beaconbraker.main.Data;
import de.aurum.beaconbraker.util.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class TeamManager {

    private static final FileConfiguration defaultConfig = Data.getDefaultConfig();
    private static final FileConfiguration locationsConfig = Data.getLocationsConfig();
    private static final Map<String, Team> teams = new HashMap<>();

    public static void setupTeams() {
        try {
            for(String key : defaultConfig.getConfigurationSection("game.teams").getKeys(false)){
                String name = defaultConfig.getString("game.teams." + key + ".name");
                ChatColor color = ChatColor.valueOf(defaultConfig.getString("game.teams." + key + ".color"));
                Location spawnPoint = locationsConfig.getLocation("locations.teams." + key + ".spawnpoint");
                Location beaconLocation = locationsConfig.getLocation("locations.teams." + key + ".beacon");
                if(name == null || color == null) throw new NullPointerException();
                teams.put(key ,new Team(name, color, spawnPoint, beaconLocation));
            }
        }catch (NullPointerException e){
            Utils.sendErrorMessage("Failed loading Team configuration", TeamManager.class.getName() + "\n" + e.toString());
        }
        Bukkit.broadcastMessage(teams.toString());
    }

    //<-----------Getters and Setters----------->

    public static Team getTeam(String key){
        return teams.get(key);
    }

    public static Set<String> getTeamKeys(){
        return teams.keySet();
    }
    public static Set<Team> getTeams(){
        return new HashSet<Team>(teams.values());
    }

    @Nullable
    public static Team getPlayerTeam(Player player){
        for(Team team : teams.values()){
           if(team.getTeammembers().contains(player)){
               return team;
           }
        }
        return null;
    }

}


