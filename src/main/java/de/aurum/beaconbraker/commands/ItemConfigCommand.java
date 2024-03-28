package de.aurum.beaconbraker.commands;

import de.aurum.beaconbraker.util.data.DataManager;
import de.aurum.beaconbraker.util.Utils;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class ItemConfigCommand implements CommandExecutor, TabCompleter {

    private static FileConfiguration itemConfig = DataManager.getItemConfig();
    private static final List<String> validOptions = Arrays.asList("add", "remove");
    private static final List<String> validTypes = Collections.singletonList("item");

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {

        if(sender instanceof Player) {
            Player player = (Player) sender;
            if (Utils.userHasPermission(cmd, sender, "beaconbreaker.admin")) {
                if (args[0].equalsIgnoreCase("add")) {
                    if(args.length == 4){
                        if(args[1].equalsIgnoreCase("item")){
                            ItemStack itemStack = player.getInventory().getItemInMainHand();
                            ItemMeta itemMeta = itemStack.getItemMeta();
                            if(itemMeta != null && itemStack.getType() != Material.AIR){
                                itemConfig.set(args[2].toLowerCase() + ".diplayname", itemMeta.getDisplayName());
                                itemConfig.set(args[2].toLowerCase() + ".type", itemStack.getType().name());
                                if(itemMeta.hasEnchants()) {
                                    ArrayList<String> ens = new ArrayList<String>();
                                    for (Enchantment enchantment : itemStack.getEnchantments().keySet()) {
                                        ens.add(enchantment.getKey().getKey() + ":" + itemStack.getEnchantments().get(enchantment));
                                    }
                                    itemConfig.set(args[2].toLowerCase() + ".enchantments", ens);
                                }
                                itemConfig.set(args[2].toLowerCase() + ".lore", itemMeta.getLore());
                                itemConfig.set(args[2].toLowerCase() + ".price", Integer.valueOf(args[3]));
                                DataManager.saveItemConfig();
                            }else player.sendMessage(DataManager.getPrefix() + "§cdas item in deiner Hand kann nicht hinzugefügt werden");
                        }else Utils.sendUsage(player, cmd);
                    }else Utils.sendUsage(player, cmd);
                } else if (args[0].equalsIgnoreCase("remove")) {
                    if (args.length == 2){
                        if(itemConfig.getConfigurationSection(args[1].toLowerCase()) != null){
                            itemConfig.set(args[1].toLowerCase(), null);
                            DataManager.saveItemConfig();
                            player.sendMessage(DataManager.getPrefix() + "§6"+ args[1].toLowerCase() + " §bwurde aus der config entfernt");
                        }else player.sendMessage(DataManager.getPrefix() + "§6"+ args[1].toLowerCase() + " §cexistiert nicht");
                    }else Utils.sendUsage(player, "/itemconfig remove [path] " + args.length);
                }else Utils.sendUsage(player, "/itemconfig remove [path] ");
            }else player.sendMessage(DataManager.getNoPerm());
        }else sender.sendMessage(DataManager.getWrongSender());

        return true;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if(args.length == 1){
          return validOptions;
        }else if(args.length == 2){
            if(args[0].equalsIgnoreCase("remove")){
                return new ArrayList<String>(itemConfig.getKeys(true));
            }else if(args[0].equalsIgnoreCase("add")){
                return validTypes;
            }
            return new ArrayList<>();
        }else if(args.length == 3){
            if(args[0].equalsIgnoreCase("add")){
                return new ArrayList<String>(itemConfig.getKeys(true));
            }
        return new ArrayList<>();
        }
        return new ArrayList<>();
    }
}
