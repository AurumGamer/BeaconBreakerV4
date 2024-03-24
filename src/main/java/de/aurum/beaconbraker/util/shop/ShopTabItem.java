package de.aurum.beaconbraker.util.shop;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import xyz.xenondevs.invui.gui.TabGui;
import xyz.xenondevs.invui.item.ItemProvider;
import xyz.xenondevs.invui.item.builder.ItemBuilder;
import xyz.xenondevs.invui.item.impl.controlitem.TabItem;

public class ShopTabItem extends TabItem {

    private final int tab;
    private final ItemBuilder displayItem;


    public ShopTabItem(int tab, ItemBuilder displayItem) {
        super(tab);
        this.tab = tab;
        this.displayItem = displayItem;
    }

    @Override
    public ItemProvider getItemProvider(TabGui tabGui) {
        if (tabGui.getCurrentTab() == tab) {
            return displayItem
                    .addEnchantment(Enchantment.LUCK, 1, false)
                    .addItemFlags(ItemFlag.HIDE_ENCHANTS);
        } else {
            return displayItem.clearEnchantments();
        }
    }
}
