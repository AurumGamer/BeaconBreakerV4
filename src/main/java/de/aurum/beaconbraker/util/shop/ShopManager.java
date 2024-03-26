package de.aurum.beaconbraker.util.shop;

import de.aurum.beaconbraker.main.Data;
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
import xyz.xenondevs.invui.item.Item;
import xyz.xenondevs.invui.item.builder.ItemBuilder;
import xyz.xenondevs.invui.item.impl.SimpleItem;
import xyz.xenondevs.invui.util.Pair;
import xyz.xenondevs.invui.window.Window;

import java.util.*;
import java.util.stream.Collectors;

public class ShopManager {
    private static ItemStack testItemStack = Data.getItemFromConfig("shop.weapons.swords.wood");
    private static FileConfiguration itemConfig = Data.getItemConfig();
    private static SimpleItem defaultGlass = new SimpleItem(new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setDisplayName(""));
    private static Gui tabGui1 = Gui.empty(9, 4);
    private static Gui tabGui3 = Gui.empty(9, 4);
    private static Gui tabGui2 = Gui.empty(9, 3);
    private static Gui tabGui4 = Gui.empty(9, 4);
    private static ArrayList<Gui> tabGuis = new ArrayList<>(Arrays.asList(tabGui1, tabGui2, tabGui3, tabGui4));
    private static ShopItem testItem = itemStackToShopItem(testItemStack, 12);

    private static Gui gui = TabGui.normal().setStructure(
                    "# # 0 1 2 3 # # #",
                    ". . . . . . . . .",
                    ". . . . . . . . .",
                    ". . . . . . . . .",
                    ". . . . . . . . .",
                    "# # # # # # # # #")
            .addIngredient('#', defaultGlass)
            .addIngredient('.', Markers.CONTENT_LIST_SLOT_VERTICAL)
            .addIngredient('0', new ShopTabItem(0, new ItemBuilder(Material.IRON_SWORD).setDisplayName("Kampf")))
            .addIngredient('1', new ShopTabItem(1, new ItemBuilder(Material.STONE_PICKAXE).setDisplayName("Tools")))
            .addIngredient('2', new ShopTabItem(2, new ItemBuilder(Material.POTION).setDisplayName("Tränke")))
            .addIngredient('3', new ShopTabItem(3, new ItemBuilder(Material.TNT).setDisplayName("Extras")))
            .setTabs(tabGuis)
            .build();

    public static void setupShop() {

        List<String> shops = new ArrayList<String>();
        if(itemConfig.getConfigurationSection("shop") != null) shops.add("shop");
        if(itemConfig.getConfigurationSection("upgrades") != null) shops.add("upgrades");

        for(String shop : shops){

            List<String> tabs = new ArrayList<>(itemConfig.getConfigurationSection(shop).getKeys(false));
            String tab;
            if(tabs.size() > 4)Utils.sendOperatorMessage("§cRegistered more than 4 tabs for §6" + shop);
            for (int i = 0; (i < 4) && i < tabs.size(); i++){
                tab = tabs.get(i);
                List<String> itemTypes = new ArrayList<>(itemConfig.getConfigurationSection(shop + "." + tab).getKeys(false));
                String itemType;
                if(itemTypes.size() > 4)Utils.sendOperatorMessage("§cRegistered more than 9 Item-rows for tab §6" + tab + " §cin §6" + shop);
                for (int j = 0; (j < 9) && j < itemTypes.size(); j++){
                    itemType = itemTypes.get(j);
                    List<String> configItems = new ArrayList<>(itemConfig.getConfigurationSection(shop + "." + tab + "." + itemType).getKeys(false));
                    String configItem;
                    if(configItems.size() > 4)Utils.sendOperatorMessage("§cRegistered more than 4 items for §6" + itemType + " §cof tab §6" + tab + " §cin §6" + shop);
                    for (int g = 0; (g < 4) && g < configItems.size(); g++){
                        configItem = configItems.get(g);
                        tabGuis.get(i).setItem(j*4+g,itemStackToShopItem(Data.getItemFromConfig(shop + "." + tab + "." + itemType + "." + configItem), itemConfig.getInt(shop + "." + tab + "." + itemType + "." + configItem + ".price")));
                    }
                }
            }
        }
    }

    public static void openToPlayer(Player player){
        Window window = Window.single()
                .setViewer(player)
                .setTitle("InvUI")
                .setGui(gui)
                .build();
        window.open();
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
}