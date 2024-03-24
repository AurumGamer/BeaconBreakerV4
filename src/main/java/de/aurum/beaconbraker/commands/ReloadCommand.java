package de.aurum.beaconbraker.commands;

import de.aurum.beaconbraker.main.Data;
import de.aurum.beaconbraker.util.Utils;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ReloadCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if(Utils.userHasPermission(cmd, sender, "beaconbreaker.admin")){
            if(args.length == 0){
                Data.reloadResources();
            }else sender.sendMessage("§cUsage: " + cmd.getUsage());
        }else sender.sendMessage(Data.getNoPerm());

        return true;
    }
}

