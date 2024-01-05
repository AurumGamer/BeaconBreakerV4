package de.aurum.beaconbraker.commands;

import de.aurum.beaconbraker.main.Data;
import de.aurum.beaconbraker.util.Utils;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class SetLobbySpawnCommand implements CommandExecutor {

    private static FileConfiguration cfg = Data.getLocationsConfig();


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if(sender instanceof Player){
            Player player = (Player) sender;
            if(Utils.userHasPermission(cmd, sender, "setLobbySpawn")){
                if(args.length == 0){
                    Location location = Data.roundLocation(player.getLocation());
                    cfg.set("locations.lobby.spawn", location);
                    Data.saveLocationsConfig();
                    player.sendMessage(Data.getPrefix() + "§cDer Spawn wurde erfolgreich gesetzt!");
                }else player.sendMessage(Data.getUsage() + cmd.getUsage());
            }else player.sendMessage(Data.getNoPerm());
        }else sender.sendMessage(Data.getWrongSender());
        return false;
    }

}
