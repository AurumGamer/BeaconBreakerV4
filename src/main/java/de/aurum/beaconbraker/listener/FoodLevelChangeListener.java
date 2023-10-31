package de.aurum.beaconbraker.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class FoodLevelChangeListener implements Listener {

    @EventHandler
    public static void onFoodLevelChange(FoodLevelChangeEvent e){

        e.setCancelled(true);

    }

}
