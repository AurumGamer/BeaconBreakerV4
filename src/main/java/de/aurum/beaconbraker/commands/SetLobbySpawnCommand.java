package de.aurum.beaconbraker.commands;

import de.aurum.beaconbraker.main.Data;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class SetLobbySpawnCommand implements CommandExecutor {

    static FileConfiguration cfg = Data.getLocationsConfig().getFileConfiguration();


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(sender instanceof Player){
            Player p = (Player) sender;
            if(cmd.getName().equalsIgnoreCase("setlobbyspawn")) {
                if(args.length == 0) {
                    if(p.hasPermission("game.setup")) {
                        Location location = Data.roundLocation(p.getLocation());
                        cfg.set("Lobby.Spawn", location);
                        Data.getLocationsConfig().save();
                        p.sendMessage(Data.getPrefix() + "§cDer Spawn wurde erfolgreich gesetzt!");
                    } else p.sendMessage(Data.getNoPerm());
                } else p.sendMessage(Data.getUsage("setlobbyspawn") + " /SetSpawn");
            } else p.sendMessage(Data.getUsage(label));
        }else sender.sendMessage(Data.getWrongSender());
        return false;
    }

}
