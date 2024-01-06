package de.aurum.beaconbraker.commands;

import de.aurum.beaconbraker.main.BeaconBreaker;
import de.aurum.beaconbraker.main.Data;
import de.aurum.beaconbraker.util.ChestManager;
import de.aurum.beaconbraker.util.Utils;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.Objects;

public class SetChestCommand implements CommandExecutor {

    private static final FileConfiguration locationsConfig = Data.getLocationsConfig();
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(sender instanceof Player) {
            Player player = (Player) sender;
            if (Utils.userHasPermission(cmd, sender, "setLobbySpawn")) {
                if (args.length == 1 && Utils.isNumeric(args[0])) {
                    locationsConfig.set("locations.chests." + args[0], player.getLocation());
                    Data.saveLocationsConfig();
                    ChestManager.placeChests();
                    player.sendMessage(Data.getPrefix() + "Chest " + args[0] + " wurde platziert");
                }else player.sendMessage(Data.getUsage() + cmd.getUsage());
            }else player.sendMessage(Data.getNoPerm());
        }else sender.sendMessage(Data.getWrongSender());
        return true;
    }

}
