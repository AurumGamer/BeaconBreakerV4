package de.aurum.beaconbraker.commands;

import de.aurum.beaconbraker.main.BeaconBreaker;
import de.aurum.beaconbraker.main.Data;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandException;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class SetTeamPositionsCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(sender instanceof Player){
            Player player = (Player)sender;
            if(player.hasPermission(BeaconBreaker.getPlugin().getCommand(label).getPermission()) || player.hasPermission("game.setup")){
                if(args.length == 1){
                    Location playerLocation = Data.roundLocation(player.getLocation());
                    FileConfiguration cfg = Data.getLocationsConfig().getFileConfiguration();
                    switch (args[0].toLowerCase()){
                        case "spawnteamred":
                            cfg.set("game.teamRed.spawn",playerLocation);
                            player.sendMessage(Data.getPrefix() + "Spawn von §cTeam rot §bgesetzt");
                            break;
                        case "spawnteamblue":
                            cfg.set("game.teamBlue.spawn",playerLocation);
                            player.sendMessage(Data.getPrefix() + "Spawn von §cTeam blau §bgesetzt");
                            break;
                        case "shopteamred":
                            cfg.set("game.teamRed.shop",playerLocation);
                            player.sendMessage(Data.getPrefix() + "Shop von §cTeam rot §bgesetzt");
                            break;
                        case "shopteamblue":
                            cfg.set("game.teamBlue.shop",playerLocation);
                            player.sendMessage(Data.getPrefix() + "Shop von §cTeam blau §bgesetzt");
                            break;
                        case "upgradesteamred":
                            cfg.set("game.teamRed.upgrades",playerLocation);
                            player.sendMessage(Data.getPrefix() + "Upgrades Shop von §cTeam rot §bgesetzt");
                            break;
                        case "upgradesteamblue":
                            cfg.set("game.teamBlue.upgrades",playerLocation);
                            player.sendMessage(Data.getPrefix() + "Upgrades Shop von §cTeam blau §bgesetzt");
                            break;
                        default:
                            player.sendMessage(Data.getUsage(label));
                            break;
                    }
                    Data.getLocationsConfig().save();
                }else player.sendMessage(Data.getUsage(label));
            }else player.sendMessage(Data.getNoPerm());
        }else sender.sendMessage(Data.getWrongSender());
        return false;
    }

    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if(args.length == 1){
            List<String> options = new ArrayList<>();
            options.add("spawnTeamRed");
            options.add("spawnTeamBlue");
            options.add("shopTeamRed");
            options.add("shopTeamBlue");
            options.add("upgradesTeamRed");
            options.add("upgradesTeamBlue");
            return options;
        }
        return null;
    }
}
