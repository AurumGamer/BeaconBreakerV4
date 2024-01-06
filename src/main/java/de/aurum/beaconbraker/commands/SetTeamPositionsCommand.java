package de.aurum.beaconbraker.commands;

import de.aurum.beaconbraker.main.Data;
import de.aurum.beaconbraker.teams.Team;
import de.aurum.beaconbraker.teams.TeamManager;
import de.aurum.beaconbraker.util.TeleportManager;
import de.aurum.beaconbraker.util.Utils;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SetTeamPositionsCommand implements CommandExecutor, TabCompleter {

    FileConfiguration locationsConfig = Data.getLocationsConfig();
    FileConfiguration defaultConfig = Data.getDefaultConfig();
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(sender instanceof Player) {
            Player player = (Player) sender;
            if (Utils.userHasPermission(cmd, sender, "setLobbySpawn")) {
                if (args.length == 2) {
                    Team playerTeam = TeamManager.getTeam(args[0]);
                    if(playerTeam != null && (args[1].equals("spawn") || args[1].equals("shop") || args[1].equals("upgrades") || args[1].equals("beacon"))){
                        Location playerLocation = Data.roundLocation(player.getLocation());
                        locationsConfig.set("locations.teams." + args[0] + "." + args[1], playerLocation);
                        Data.saveLocationsConfig();
                    }else player.sendMessage(Data.getPrefix() +  cmd.getUsage());
                }else player.sendMessage(Data.getPrefix() +  cmd.getUsage());
            }else player.sendMessage(Data.getNoPerm());
        }else sender.sendMessage(Data.getWrongSender());

        return true;
    }

    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if(args.length <= 2){
            List<String> options = new ArrayList<>();
            if (defaultConfig.getConfigurationSection("game.teams") != null) {
                for (String teamKey : defaultConfig.getConfigurationSection("game.teams").getKeys(false)) {
                    if(args.length == 1){
                        options.add(teamKey);
                    }else Collections.addAll(options, "shop", "spawn", "upgrades", "beacon");
                }
            }
            return options;
        }
        return null;
    }
}
