package de.aurum.beaconbraker.listener;

import de.aurum.beaconbraker.main.Data;
import de.aurum.beaconbraker.util.GameManager;
import org.bukkit.GameMode;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e){

        Player p = e.getPlayer();

        String JoinMessage = Data.getJoinMessage().replace("%Player%", p.getName());
        e.setJoinMessage(JoinMessage);
        e.getPlayer().setGameMode(GameMode.ADVENTURE);

        
//        switch (GameManager.getGameState()){
//            case LOBBY:
//                e.getPlayer().getInventory().clear();
//                e.getPlayer().getInventory().setArmorContents(null);
//                e.getPlayer().updateInventory();
//                e.getPlayer().setExp(0);
//                e.getPlayer().setLevel(0);
//                e.getPlayer().setFoodLevel(20);
//                e.getPlayer().setHealth(20);
//                e.getPlayer().getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(20);
//                e.getPlayer().setAllowFlight(false);
//                //TeleportManager.teleportToLobbySpawn(p);
//                break;
//            case INGAME:
//                break;
//            default:
//                e.getPlayer().sendMessage("DEFAULT");
//                break;
//        }




    }

}

