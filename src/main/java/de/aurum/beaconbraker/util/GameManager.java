package de.aurum.beaconbraker.util;

import de.aurum.beaconbraker.util.teams.Team;
import de.aurum.beaconbraker.util.teams.TeamManager;
import org.bukkit.Bukkit;
import org.bukkit.GameRule;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Set;

public class GameManager {

    private static HashMap<Player, Integer> playerBalances = new HashMap<Player, Integer>();

    private static GameState gameState;
    private static final World world = Bukkit.getServer().getWorlds().get(0);
    public static void setUp(){
        gameState = GameState.LOBBY;
        world.setPVP(false);
        world.setTime(6000);
        world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
        world.setGameRule(GameRule.ANNOUNCE_ADVANCEMENTS, false);
        world.setGameRule(GameRule.DO_MOB_LOOT, false);
        world.setGameRule(GameRule.DO_MOB_SPAWNING, false);
        world.setGameRule(GameRule.DO_WEATHER_CYCLE, false);
        world.setGameRule(GameRule.DO_IMMEDIATE_RESPAWN, true);
        world.setGameRule(GameRule.KEEP_INVENTORY, true);
        ChestManager.placeChests();
        ChestManager.fillChests();
        TeamManager.setupTeams();
        placeTeamBeacons();
        EntityManager.spawnShops();
    }

    public void setGameState(GameState gameState) {

        if (GameManager.gameState != GameState.INGAME && gameState != GameState.STARTING) return; //Mann kann ein aktives spiel nicht wieder in den Startmodus versetzen (Wechsle erst zu Lobby)

        GameManager.gameState = gameState;
        switch (gameState) {
            case LOBBY:
                break;
            case STARTING:
                break;
            case INGAME:
                break;
            case WON:
                break;
        }
    }

    private static void placeTeamBeacons(){
        for(Team team : TeamManager.getTeams()){
            if(team.getBeaconLocation() != null){
                team.getBeaconLocation().getBlock().setType(Material.BEACON);
            }else Utils.sendErrorMessage("Failed loading team '" + team.getName() + "' beacon location", GameManager.class.getName() + " Location equals null");
        }
    }

    //<-----------Getters and Setters----------->

    public static GameState getGameState(){
        return gameState;
    }

    public static int getPlayerBalance(Player player){
        return playerBalances.get(player);
    }

    public static int setPlayerBalance(Player player, int amount){
        playerBalances.put(player, amount);
        return getPlayerBalance(player);
    }

    public static int addPlayerBalance(Player player, int amount){
        playerBalances.put(player, getPlayerBalance(player) + amount);
        return getPlayerBalance(player);
    }

    public static int subtractPlayerBalance(Player player, int amount){
        if(getPlayerBalance(player) >= amount){
            playerBalances.put(player, getPlayerBalance(player) - amount);
            return getPlayerBalance(player);
        }
        return -1;
    }
}

