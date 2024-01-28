package de.aurum.beaconbraker.util.shop;

import org.bukkit.Material;
import xyz.xenondevs.invui.gui.Gui;
import xyz.xenondevs.invui.gui.TabGui;
import xyz.xenondevs.invui.gui.structure.Markers;
import xyz.xenondevs.invui.item.Item;
import xyz.xenondevs.invui.item.builder.ItemBuilder;
import xyz.xenondevs.invui.item.impl.SimpleItem;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ShopManager {
    List<Item> items = Arrays.stream(Material.values())
            .filter(material -> !material.isAir() && material.isItem())
            .map(material -> new SimpleItem(new ItemBuilder(material)))
            .collect(Collectors.toList());

    Gui gui1 = Gui.empty(9, 2);
    Gui gui2 = Gui.empty(9, 2);

    Gui shopGui = TabGui.normal().setStructure(
            "# 0 # 1 # 2 # 3 #",
            "# x x x x x x x #",
            "# x x x x x x x #",
            "# # # # # # # # #")
            .addIngredient('x', Markers.CONTENT_LIST_SLOT_VERTICAL)
            .addIngredient('#', new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setDisplayName("§r"))
            .addIngredient('0', new ShopTabItem(0))
            .addIngredient('1', new ShopTabItem(1))
            .setTabs(Arrays.asList(gui1, gui2))
            .build();
    public void setupShopGUI() {
        gui1.(new SimpleItem(new ItemBuilder(Material.DIRT)), true);
        gui2.fill(new SimpleItem(new ItemBuilder(Material.DIAMOND)), true);
    }
}
