package de.aurum.beaconbraker.util;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

public class RecipeManager {

    public static void registerRecipes() {
        ItemStack beaconBreaker = new ItemBuilder(Material.NETHERITE_PICKAXE, 1).setName("§6§lBeaconBreaker").addEnchant(Enchantment.LUCK, 0).addFlag(ItemFlag.HIDE_ENCHANTS).build();
        ShapedRecipe breakerRecepie = new ShapedRecipe(beaconBreaker);

        breakerRecepie.shape("NSD","OLO","OLO");
        breakerRecepie.setIngredient('N', Material.NETHERITE_INGOT);
        breakerRecepie.setIngredient('S', Material.NETHER_STAR);
        breakerRecepie.setIngredient('D', Material.DIAMOND_BLOCK);
        breakerRecepie.setIngredient('L', Material.BLAZE_ROD);
        breakerRecepie.setIngredient('O', Material.AIR);

        Bukkit.addRecipe(breakerRecepie);
    }


}
