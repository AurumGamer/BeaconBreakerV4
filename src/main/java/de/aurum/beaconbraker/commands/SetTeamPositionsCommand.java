package de.aurum.beaconbraker.commands;

import de.aurum.beaconbraker.main.Data;
import de.aurum.beaconbraker.util.Utils;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class SetTeamPositionsCommand implements CommandExecutor {

    FileConfiguration locationsConfig = Data.getLocationsConfig();
    FileConfiguration defaultConfig = Data.getDefaultConfig();
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(sender instanceof Player) {
            Player player = (Player) sender;
            if (Utils.userHasPermission(cmd, sender, "setLobbySpawn")) {
                if (args.length == 1) {

                    Location playerLocation = player.getLocation();

                }else player.sendMessage(Data.getUsage() + cmd.getUsage());
            }else player.sendMessage(Data.getNoPerm());
        }else sender.sendMessage(Data.getWrongSender());

//        if(sender instanceof Player){
//            Player player = (Player)sender;
//            if(player.hasPermission(BeaconBreaker.getPlugin().getCommand(label).getPermission()) || player.hasPermission("game.setup")){
//                if(args.length == 1){
//                    Location playerLocation = Data.roundLocation(player.getLocation());
//                    FileConfiguration cfg = Data.getLocationsConfig();
//                    switch (args[0].toLowerCase()){
//                        case "spawnteamred":
//                            cfg.set("locations.teams.teamRed.spawn",playerLocation);
//                            player.sendMessage(Data.getPrefix() + "Spawn von §cTeam rot §bgesetzt");
//                            break;
//                        case "spawnteamblue":
//                            cfg.set("locations.teams.teamBlue.spawn",playerLocation);
//                            player.sendMessage(Data.getPrefix() + "Spawn von §cTeam blau §bgesetzt");
//                            break;
//                        case "shopteamred":
//                            cfg.set("locations.teams.teamRed.shop",playerLocation);
//                            player.sendMessage(Data.getPrefix() + "Shop von §cTeam rot §bgesetzt");
//                            break;
//                        case "shopteamblue":
//                            cfg.set("locations.teams.teamBlue.shop",playerLocation);
//                            player.sendMessage(Data.getPrefix() + "Shop von §cTeam blau §bgesetzt");
//                            break;
//                        case "upgradesteamred":
//                            cfg.set("locations.teams.teamRed.upgrades",playerLocation);
//                            player.sendMessage(Data.getPrefix() + "Upgrades Shop von §cTeam rot §bgesetzt");
//                            break;
//                        case "upgradesteamblue":
//                            cfg.set("locations.teams.teamBlue.upgrades",playerLocation);
//                            player.sendMessage(Data.getPrefix() + "Upgrades Shop von §cTeam blau §bgesetzt");
//                            break;
//                        default:
//                            player.sendMessage(Data.getUsage() + cmd.getUsage());
//                            break;
//                    }
//                    Data.saveLocationsConfig();
//                }else player.sendMessage(Data.getUsage() + cmd.getUsage());
//            }else player.sendMessage(Data.getNoPerm());
//        }else sender.sendMessage(Data.getWrongSender());
        return false;
    }

    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if(args.length == 1){
            List<String> options = new ArrayList<>();
            if (defaultConfig.getConfigurationSection("game.teams") != null) {
                for (String key : defaultConfig.getConfigurationSection("game.teams").getKeys(false)) {
                    if (defaultConfig.getConfigurationSection("game.teams") != null) {
                        for (String locationsKey : locationsConfig.getConfigurationSection("locations.teams." + key).getKeys(false)) {
                            options.add("team" + key.substring(0,1).toUpperCase() + locationsKey.substring(0,1).toUpperCase());
                        }
                    }

                }
            }
            return options;
        }
        return null;
    }
}
