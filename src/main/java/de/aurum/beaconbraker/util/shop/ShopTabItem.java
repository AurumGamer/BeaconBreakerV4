package de.aurum.beaconbraker.util.shop;

import org.bukkit.Material;
import xyz.xenondevs.invui.gui.TabGui;
import xyz.xenondevs.invui.item.ItemProvider;
import xyz.xenondevs.invui.item.builder.ItemBuilder;
import xyz.xenondevs.invui.item.impl.controlitem.TabItem;

public class ShopTabItem extends TabItem {

    private final int tab;

    public ShopTabItem(int tab) {
        super(tab);
        this.tab = tab;
    }

    @Override
    public ItemProvider getItemProvider(TabGui tabGui) {
        if (tabGui.getCurrentTab() == tab) {
            return new ItemBuilder(Material.GLOWSTONE_DUST)
                    .setDisplayName("Tab " + tab + " (selected)");
        } else {
            return new ItemBuilder(Material.GUNPOWDER)
                    .setDisplayName("Tab " + tab + " (not selected)");
        }
    }
}
}
