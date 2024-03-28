package de.aurum.beaconbraker.commands;

import de.aurum.beaconbraker.util.data.DataManager;
import de.aurum.beaconbraker.util.Utils;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.*;

public class SetTeamPositionsCommand implements CommandExecutor, TabCompleter {

    private static FileConfiguration locationsConfig = DataManager.getLocationsConfig();
    private static FileConfiguration defaultConfig = DataManager.getDefaultConfig();
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
                        Location playerLocation = DataManager.roundLocation(player.getLocation());
                        locationsConfig.set("locations.teams." + args[0].toLowerCase() + "." + position.toLowerCase(), playerLocation);
                        DataManager.saveLocationsConfig();
                        player.sendMessage(DataManager.getPrefix() + position + " von Team " + args[0] + " wurde platziert");
                    }else Utils.sendUsage(player, cmd);
                }else Utils.sendUsage(player, cmd);
            }else player.sendMessage(DataManager.getNoPerm());
        }else sender.sendMessage(DataManager.getWrongSender());

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