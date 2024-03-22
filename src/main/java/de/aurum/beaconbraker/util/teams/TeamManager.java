package de.aurum.beaconbraker.util.teams;

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
                Location spawnPoint = locationsConfig.getLocation("locations.teams." + key + ".spawn");
                Location beaconLocation = locationsConfig.getLocation("locations.teams." + key + ".beacon");
                Location shopLocation = locationsConfig.getLocation("locations.teams." + key + ".shop");
                Location upgradesLocation = locationsConfig.getLocation("locations.teams." + key + ".upgrades");
                try {
                    if(teams.containsKey(key)) throw new IllegalArgumentException("Cannot create two teams with the same name");
                    teams.put(key ,new Team(name, color, spawnPoint, beaconLocation, shopLocation, upgradesLocation));
                }catch (IllegalArgumentException e){
                    Utils.sendErrorMessage("Failed loading Team '"+ key + "' configuration (teams may not be Set-up correctly; this massage can be ignored)", TeamManager.class.getName() + "\n" + e.toString());
                }
            }
        }catch (NullPointerException e){
            Utils.sendErrorMessage("Failed loading Team configuration", TeamManager.class.getName() + "\n" + e.toString());
        }
    }

    //<-----------Getters and Setters----------->

    public static Team getTeam(String key){
        return teams.get(key.toLowerCase());
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


