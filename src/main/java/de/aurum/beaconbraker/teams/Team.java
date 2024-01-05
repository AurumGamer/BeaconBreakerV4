package de.aurum.beaconbraker.teams;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;

public class Team {

    private final String name;
    private final ChatColor color;
    private final String prefix;
    private Location spawnPoint;
    private int balance;
    private final Set<Player> teamMembers = new HashSet<>();

    public Team(String name, ChatColor color, Location spawnPoint) {
        this.name = name;
        this.color = color;
        this.spawnPoint = spawnPoint;
        this.balance = 0;
        this.prefix ="[" + color + name+ "]";
    }


    //<-----------Getters and Setters----------->
    public String getName() {
        return name;
    }

    public ChatColor getColor() {
        return color;
    }

    public String getPrefix() {
        return prefix;
    }

    public Location getSpawnPoint() {
        return spawnPoint;
    }

    public int getBalance() {
        return balance;
    }
    public void addBalance(int amount){
        this.balance += amount;
    }
    public void removeBalance(int amount){
        if(this.balance >= amount){
            this.balance = 0;
            return;
        }this.balance -= amount;
    }
    public void setBalance(int amount){
        this.balance = amount;
    }

    public boolean addTeammember(Player p){
        return teamMembers.add(p);
    }

    public boolean removeTeammember(Player p){
        return teamMembers.remove(p);
    }

    public Set<Player> getTeammembers() {
        return teamMembers;
    }
}
