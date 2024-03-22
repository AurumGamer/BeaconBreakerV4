package de.aurum.beaconbraker.util.shop;

import de.aurum.beaconbraker.main.Data;
import de.aurum.beaconbraker.util.GameManager;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import xyz.xenondevs.invui.item.ItemProvider;
import xyz.xenondevs.invui.item.builder.ItemBuilder;
import xyz.xenondevs.invui.item.impl.AbstractItem;

public class ShopItem extends AbstractItem {

    private ItemStack displayItem;
    private ItemStack boughtItem;
    private int price;

    private void buyItem(Player player){
        if (GameManager.getPlayerBalance(player) >= price){
            if(!player.getInventory().addItem(boughtItem).isEmpty()){
                GameManager.subtractPlayerBalance(player, price);
            }else player.sendMessage(Data.getPrefix() + "Du hast kein Platz im inventar");
        }else player.sendMessage(Data.getPrefix() + "Du hast nicht genügend Geld");
    }

    @Override
    public ItemProvider getItemProvider() {
        return (ItemProvider) displayItem;
    }

    @Override
    public void handleClick(@NotNull ClickType clickType, @NotNull Player player, @NotNull InventoryClickEvent inventoryClickEvent) {
        if(clickType.isLeftClick()) {
            buyItem(player);
        }
    }
}
