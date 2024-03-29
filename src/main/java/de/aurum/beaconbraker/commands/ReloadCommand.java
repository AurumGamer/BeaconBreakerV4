package de.aurum.beaconbraker.commands;

import de.aurum.beaconbraker.util.data.DataManager;
import de.aurum.beaconbraker.util.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ReloadCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if(Utils.userHasPermission(cmd, sender, "beaconbreaker.admin")){
            if(args.length == 0){
                DataManager.reloadResources();
            }else sender.sendMessage("§cUsage: " + cmd.getUsage());
        }else sender.sendMessage(DataManager.getNoPerm());

        return true;
    }
}

