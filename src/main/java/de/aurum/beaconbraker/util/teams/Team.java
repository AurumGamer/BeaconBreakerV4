package de.aurum.beaconbraker.util.teams;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;
import java.util.HashSet;
import java.util.Set;

public class Team {

    private final String name;
    private final ChatColor color;
    private final String prefix;
    private final Location spawnPoint;
    private final Location shopLocation;
    private final Location upgradesLocation;
    private final Location beaconLocation;
    private final boolean baconAlive;
    private int balance;
    private final Set<Player> teamMembers = new HashSet<>();

    public Team (@Nonnull String name, @Nonnull ChatColor color, @Nonnull Location spawnPoint, @Nonnull Location beaconLocation, @Nonnull Location shopLocation, @Nonnull Location upgradesLocation) {
        this.name = name;
        this.color = color;
        this.spawnPoint = spawnPoint;
        this.beaconLocation = beaconLocation;
        this.shopLocation = shopLocation;
        this.upgradesLocation = upgradesLocation;
        this.balance = 0;
        this.baconAlive = true;
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

    public Location getBeaconLocation() {
        return beaconLocation;
    }

    public boolean isBaconAlive() {
        return baconAlive;
    }

    public Location getShopLocation() {
        return shopLocation;
    }

    public Location getUpgradesLocation() {
        return upgradesLocation;
    }
}
