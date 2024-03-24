package de.aurum.beaconbraker.util.shop;

import de.aurum.beaconbraker.main.Data;
import de.aurum.beaconbraker.util.PlayerManager;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import xyz.xenondevs.invui.item.ItemProvider;
import xyz.xenondevs.invui.item.builder.ItemBuilder;
import xyz.xenondevs.invui.item.impl.AbstractItem;

import java.util.*;

public class ShopItem extends AbstractItem {

    private ItemBuilder displayItem;
    private ItemBuilder boughtItem;
    private int price;

    public ShopItem(ItemBuilder displayItem, ItemBuilder boughtItem, int price) {
        this.displayItem = displayItem;
        this.boughtItem = boughtItem;
        this.price = price;
    }

    private void buyItem(Player player){
        if (PlayerManager.getPlayerBalance(player) >= price){
            HashMap<Integer, ItemStack> map = player.getInventory().addItem(boughtItem.get());
            if(map.isEmpty()){
                PlayerManager.subtractPlayerBalance(player, price);
                player.sendMessage(Data.getPrefix() + "Gekauft; geld übrig: " + PlayerManager.getPlayerBalance(player));
            }else {player.sendMessage(Data.getPrefix() + "Du hast kein Platz im inventar " + Arrays.asList(map));}
        }else player.sendMessage(Data.getPrefix() + "Du hast nicht genügend Geld");
    }

    @Override
    public ItemProvider getItemProvider() {
        return displayItem;
    }

    @Override
    public void handleClick(@NotNull ClickType clickType, @NotNull Player player, @NotNull InventoryClickEvent inventoryClickEvent) {
        if(clickType.isLeftClick()) {
            buyItem(player);
        }
    }
}
