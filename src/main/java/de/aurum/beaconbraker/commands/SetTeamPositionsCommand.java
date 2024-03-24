package de.aurum.beaconbraker.commands;

import de.aurum.beaconbraker.main.Data;
import de.aurum.beaconbraker.util.teams.Team;
import de.aurum.beaconbraker.util.teams.TeamManager;
import de.aurum.beaconbraker.util.Utils;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.*;

public class SetTeamPositionsCommand implements CommandExecutor, TabCompleter {

    private static FileConfiguration locationsConfig = Data.getLocationsConfig();
    private static FileConfiguration defaultConfig = Data.getDefaultConfig();
    private static final Set<String> validCommands = new HashSet<>(Arrays.asList("spawn", "shop", "upgrades", "beacon"));
    private static final Set<String> validTeams = new HashSet<>();

    static {
        for(String key : defaultConfig.getConfigurationSection("game.teams").getKeys(false)){
            if(validTeams.contains(key)){
                validTeams.remove(key);
            }else validTeams.add(key);
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(sender instanceof Player) {
            Player player = (Player) sender;
            if (Utils.userHasPermission(cmd, sender, "setLobbySpawn")) {
                if (args.length == 2) {
                    String position = args[1].toLowerCase();
                    if(validTeams.contains(args[0]) && (validCommands.contains(position.toLowerCase()))){
                        Location playerLocation = Data.roundLocation(player.getLocation());
                        locationsConfig.set("locations.teams." + args[0].toLowerCase() + "." + position.toLowerCase(), playerLocation);
                        Data.saveLocationsConfig();
                        player.sendMessage(Data.getPrefix() + position + " von Team " + args[0] + " wurde platziert");
                    }else Utils.sendUsage(player, cmd);
                }else Utils.sendUsage(player, cmd);
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
                    }else options.addAll(validCommands);
                }
            }
            return options;
        }
        return new ArrayList<>();
    }
}