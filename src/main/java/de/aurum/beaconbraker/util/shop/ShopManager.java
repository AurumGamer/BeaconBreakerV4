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
    private static Gui tabGui2 = Gui.empty(9, 4);
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
        if(itemConfig.getConfigurationSection("shop") != null) {
            HashSet<String> guiTypes = (HashSet<String>) itemConfig.getConfigurationSection("shop").getKeys(false);
            Iterator<String> guiTypesIterator = guiTypes.iterator();
                for(Gui currentGui : tabGuis){
                    if(!guiTypesIterator.hasNext()) break;
                    String guiType = guiTypesIterator.next();
                    Bukkit.getConsoleSender().sendMessage("shop." + guiType);
                    for(String itemType : itemConfig.getConfigurationSection("shop." + guiType).getKeys(false)){
                        HashSet<String> configItems = (HashSet<String>) itemConfig.getConfigurationSection("shop." + guiType + "." + itemType).getKeys(false);
                        Iterator<String> configItemsIterator = configItems.iterator();
                        if(configItemsIterator.hasNext()) {
                            int i = 0;
                            String currentItem;
                            while(configItemsIterator.hasNext() && i < 4) {
                                currentItem = configItemsIterator.next();
                                currentGui.addItems(itemStackToShopItem(Data.getItemFromConfig("shop." + guiType + "." + itemType + "." + currentItem), itemConfig.getInt("shop." + guiType + "." + itemType + "." + currentItem + ".price")));
                                i++;
                            }
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
        StringBuilder nameBuilder = new StringBuilder(itemMeta.getDisplayName());
        List<String> lore = new ArrayList<>();
        itemMeta.getLore().forEach(string -> lore.add("§5"+string));
        Map<Enchantment, Integer> enchantments = itemStack.getEnchantments();
        if(!enchantments.isEmpty()) nameBuilder.insert(0, "§b");
        HashMap<Enchantment, Pair<Integer, Boolean>> builderEnchantments = new HashMap<Enchantment, Pair<Integer, Boolean>>();
        ItemBuilder displayItemBuilder = new ItemBuilder(material).setDisplayName(nameBuilder.toString()).setLegacyLore(lore).addLoreLines(String.valueOf(price));
        ItemBuilder buyItemBuilder = new ItemBuilder(material).setDisplayName(nameBuilder.toString()).setLegacyLore(lore);
        if(!enchantments.isEmpty()){
            for(Map.Entry<Enchantment, Integer> entry : enchantments.entrySet()){
                displayItemBuilder.addEnchantment(entry.getKey(), entry.getValue(), true);
                buyItemBuilder.addEnchantment(entry.getKey(), entry.getValue(), true);
            }
        }
        return new ShopItem(displayItemBuilder, buyItemBuilder, price);
    }
}