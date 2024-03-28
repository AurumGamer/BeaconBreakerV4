package de.aurum.beaconbraker.commands;

import de.aurum.beaconbraker.util.data.DataManager;
import de.aurum.beaconbraker.util.Utils;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class SetLobbySpawnCommand implements CommandExecutor {

    private static final FileConfiguration cfg = DataManager.getLocationsConfig();


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if(sender instanceof Player){
            Player player = (Player) sender;
            if(Utils.userHasPermission(cmd, sender, "beaconbreaker.admin")){
                if(args.length == 0){
                    Location location = DataManager.roundLocation(player.getLocation());
                    cfg.set("locations.lobby.spawn", location);
                    DataManager.saveLocationsConfig();
                    player.sendMessage(DataManager.getPrefix() + "§cDer Spawn wurde erfolgreich gesetzt!");
                }else Utils.sendUsage(player, cmd);
            }else player.sendMessage(DataManager.getNoPerm());
        }else sender.sendMessage(DataManager.getWrongSender());
        return true;
    }

}
