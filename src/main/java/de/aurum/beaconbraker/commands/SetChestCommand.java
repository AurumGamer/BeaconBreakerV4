package de.aurum.beaconbraker.commands;

import de.aurum.beaconbraker.util.data.DataManager;
import de.aurum.beaconbraker.util.ChestManager;
import de.aurum.beaconbraker.util.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class SetChestCommand implements CommandExecutor {

    private static final FileConfiguration locationsConfig = DataManager.getLocationsConfig();
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(sender instanceof Player) {
            Player player = (Player) sender;
            if (Utils.userHasPermission(cmd, sender, "beaconbreaker.admin")) {
                if (args.length == 1 && Utils.isNumeric(args[0])) {
                    locationsConfig.set("locations.chests." + args[0], player.getLocation());
                    DataManager.saveLocationsConfig();
                    ChestManager.placeChests();
                    player.sendMessage(DataManager.getPrefix() + "Chest " + args[0] + " wurde platziert");
                }else Utils.sendUsage(player, cmd);
            }else player.sendMessage(DataManager.getNoPerm());
        }else sender.sendMessage(DataManager.getWrongSender());
        return true;
    }

}
