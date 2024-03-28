package de.aurum.beaconbraker.listener;

import de.aurum.beaconbraker.util.shop.ShopManager;
import de.aurum.beaconbraker.util.teams.Team;
import de.aurum.beaconbraker.util.teams.TeamManager;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class EntityInteractListener implements Listener {

    @EventHandler
    public void onEntityInteractEvent(PlayerInteractEntityEvent event) {
        Entity entity  = event.getRightClicked();
        Player player = event.getPlayer();
        Team playerTeam = TeamManager.getPlayerTeam(player);

        if(entity instanceof Villager){ //TODO: Add check for team != null
            if(!entity.getMetadata("id").isEmpty()){
                String metadata = entity.getMetadata("id").get(0).asString();
                //if(metadata.contains(playerTeam.getName())){
                    if(metadata.contains("shop")){
                        player.sendMessage("Shop");
                        ShopManager.openShopGui(player);
                    }
                    if(metadata.contains("upgrades")){
                        player.sendMessage("Upgrade");
                        ShopManager.openUpgradesGui(player);
                    }
                //}
            }
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onEntityDamageEvent(EntityDamageByEntityEvent event) {
        Entity entity  = event.getEntity();

        if(event.getDamager() instanceof Player && entity instanceof Villager){
            Player player = (Player) event.getDamager();
            Team playerTeam = TeamManager.getPlayerTeam(player);
            if(!entity.getMetadata("id").isEmpty()){ //TODO: Add check for team != null
                String metadata = entity.getMetadata("id").get(0).asString();
                //if(metadata.contains(playerTeam.getName())){
                    if(metadata.contains("shop")){
                        player.sendMessage("Shop");
                    }else if(metadata.contains("upgrades")){
                        player.sendMessage("Upgrade");
                    }else player.sendMessage("id corrupted");
                //}

            }else  player.sendMessage("No id");
            event.setCancelled(true);
        }

    }

}
