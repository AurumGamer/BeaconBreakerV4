package de.aurum.beaconbraker.util;

import de.aurum.beaconbraker.main.Data;
import de.aurum.beaconbraker.util.teams.TeamManager;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class TeleportManager {

    private static final FileConfiguration locationsConfig = Data.getLocationsConfig();

    public static void teleportToLobbySpawn(Player player, boolean playSound){
        Location lobbySpawn = Data.getLobbySpawnLocation();
        if(lobbySpawn != null){
            player.teleport(lobbySpawn);
            player.sendMessage(Data.getPrefix() + "Du wurdest zum §cLobby-Spawn §bTeleportiert");//TODO: Entfernen wenn fertig
            if(playSound)player.playSound(lobbySpawn, Sound.ENTITY_PLAYER_LEVELUP, 1 , 2);
        }else {
            player.sendMessage(Data.getPrefix() + "Du konntest nicht Teleportiert werden");
            Utils.sendErrorMessage("Could not load Spawn Location from locations config", TeleportManager.class.getName());
        }
    }

    public static void teleportToTeamSpawn(Player player, boolean playSound){
        try {
            Location teamSpawn = TeamManager.getPlayerTeam(player).getSpawnPoint();
            player.teleport(teamSpawn);
            player.sendMessage(Data.getPrefix() + "Du wurdest zum §cLobby-Spawn $bTeleportiert");//TODO: Entfernen wenn fertig
            if(playSound)player.playSound(teamSpawn, Sound.ENTITY_PLAYER_LEVELUP, 1 , 2);
        }catch (NullPointerException e){
            player.sendMessage(Data.getPrefix() + "Du konntest nicht Teleportiert werden");
            Utils.sendErrorMessage("Could not teleport player §l" +player.getName()+ "§c to teamSpawn", TeleportManager.class.getName());
        }
    }


}
