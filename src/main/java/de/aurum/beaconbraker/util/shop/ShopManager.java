package de.aurum.beaconbraker.util.shop;

import de.aurum.beaconbraker.util.data.DataManager;
import de.aurum.beaconbraker.util.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import xyz.xenondevs.invui.gui.Gui;
import xyz.xenondevs.invui.gui.TabGui;
import xyz.xenondevs.invui.gui.structure.Markers;
import xyz.xenondevs.invui.gui.structure.Structure;
import xyz.xenondevs.invui.item.builder.ItemBuilder;
import xyz.xenondevs.invui.item.impl.SimpleItem;
import xyz.xenondevs.invui.window.Window;

import java.util.*;

public class ShopManager {
    private static ItemStack testItemStack = DataManager.getItemFromConfig("shop.weapons.swords.wood");
    private static FileConfiguration itemConfig = DataManager.getItemConfig();
    private static SimpleItem defaultGlass = new SimpleItem(new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setDisplayName(""));
    //private static ArrayList<Gui> tabGuis = new ArrayList<>();
    private static ShopItem testItem = itemStackToShopItem(testItemStack, 12);

    private static Gui shopGui;
    private static Gui upgradesGui;

    public static void setupShop() {

        if(itemConfig.getConfigurationSection("shop") != null) shopGui = createTabGui("shop");
        if(itemConfig.getConfigurationSection("upgrades") != null) upgradesGui = createTabGui("upgrades");

    }

    public static void openShopGui(Player player){
        if(shopGui != null){
            Window window = Window.single()
                    .setViewer(player)
                    .setTitle("§6Shop")
                    .setGui(shopGui)
                    .build();
            window.open();
        }
    }
    public static void openUpgradesGui(Player player){
        if(upgradesGui != null){
            Window window = Window.single()
                    .setViewer(player)
                    .setTitle("§6Upgrades")
                    .setGui(upgradesGui)
                    .build();
            window.open();
        }
    }

    private static ShopItem itemStackToShopItem(ItemStack itemStack, int price){

        ItemMeta itemMeta = itemStack.getItemMeta();
        Material material = itemStack.getType();
        String name = "§b" + ((itemMeta != null ) ? itemMeta.getDisplayName() : itemStack.getType().toString());
        List<String> lore = new ArrayList<>();
        Map<Enchantment, Integer> enchantments = itemStack.getEnchantments();
        if(itemMeta != null && itemMeta.getLore() != null) itemMeta.getLore().forEach(string -> lore.add("§5"+string));
        ItemBuilder displayItemBuilder = new ItemBuilder(material).setDisplayName(name).setLegacyLore(lore).addLoreLines(String.valueOf(price));
        ItemBuilder buyItemBuilder = new ItemBuilder(material).setDisplayName(name).setLegacyLore(lore);
        if(!enchantments.isEmpty()){
            for(Map.Entry<Enchantment, Integer> entry : enchantments.entrySet()){
                displayItemBuilder.addEnchantment(entry.getKey(), entry.getValue(), true);
                buyItemBuilder.addEnchantment(entry.getKey(), entry.getValue(), true);
            }
        }
        return new ShopItem(displayItemBuilder, buyItemBuilder, price);
    }

    private static TabGui createTabGui(String path){

        List<String> tabs = new ArrayList<>(itemConfig.getConfigurationSection(path).getKeys(false));
        Bukkit.getConsoleSender().sendMessage(path + tabs);
        List<Gui> tabGuis = new ArrayList<>();
        StringBuilder tabString = new StringBuilder("######### ......... ......... ......... ......... #########");
        int i = 0;

        for(String str: tabs) {
           tabString.replace((9/tabs.size()) +i, (9/tabs.size())+i+1, String.valueOf(i));
           i++;
        }
        Bukkit.getConsoleSender().sendMessage(tabString.toString());
        Structure structure = new Structure(9, 6, tabString.toString());
        TabGui.Builder shopGui = TabGui.normal().setStructure(structure).addIngredient('#', defaultGlass).addIngredient('.', Markers.CONTENT_LIST_SLOT_VERTICAL);

        for(i=0; i < tabs.size(); i++) {
                shopGui.addIngredient((char)(i + '0'), new ShopTabItem(i, new ItemBuilder(Material.PAPER).setDisplayName(tabs.get(i))));
            Bukkit.getConsoleSender().sendMessage(tabString.toString() + (char)(i + '0'));
        }
        String tab;
        if(tabs.size() > 4)Utils.sendOperatorMessage("§cRegistered more than 4 tabs for §6" + path);
        for (i = 0; (i < 4) && i < tabs.size(); i++){
            tabGuis.add(i, Gui.empty(9, 4));
            tab = tabs.get(i);
            List<String> itemTypes = new ArrayList<>(itemConfig.getConfigurationSection(path + "." + tab).getKeys(false));
            String itemType;

            if(itemTypes.size() > 4)Utils.sendOperatorMessage("§cRegistered more than 9 Item-rows for tab §6" + tab + " §cin §6" + path);
            for (int j = 0; (j < 9) && j < itemTypes.size(); j++){
                itemType = itemTypes.get(j);
                List<String> configItems = new ArrayList<>(itemConfig.getConfigurationSection(path + "." + tab + "." + itemType).getKeys(false));
                String configItem;
                if(configItems.size() > 4)Utils.sendOperatorMessage("§cRegistered more than 4 items for §6" + itemType + " §cof tab §6" + tab + " §cin §6" + path);
                for (int g = 0; (g < 4) && g < configItems.size(); g++){
                    configItem = configItems.get(g);
                    tabGuis.get(i).setItem(j*4+g,itemStackToShopItem(DataManager.getItemFromConfig(path + "." + tab + "." + itemType + "." + configItem), itemConfig.getInt(path + "." + tab + "." + itemType + "." + configItem + ".price")));
                }
            }
        }
        return tabGuis.isEmpty() ? null : shopGui.setTabs(tabGuis).build();
    }

}