package de.aurum.beaconbraker.util;

import org.bukkit.entity.Player;

import java.util.HashMap;

public class PlayerManager {
    private static HashMap<Player, Integer> playerBalances = new HashMap<Player, Integer>();


    //<-----------Getters and Setters----------->

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

    public static void addPlayerToBalanceList(Player player){
        playerBalances.put(player, 100);
    }
}
