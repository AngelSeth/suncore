package suncore.sunbase.util;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import suncore.sunbase.classes.Archer;
import suncore.sunbase.classes.PlayerClass;
import suncore.sunbase.classes.Warrior;
import suncore.sunbase.data.PlayerClassManager;

import java.util.UUID;

public class ChangeClassCommand implements CommandExecutor {
    private PlayerClassManager playerClassManager;
    public ChangeClassCommand(PlayerClassManager playerClassManager) {
        this.playerClassManager = playerClassManager;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be used by players.");
            return true;
        }
        if (args.length != 1) {
            sender.sendMessage("Usage: /class <classname>");
            return false;
        }
        Player player = (Player) sender;
        String className = args[0].toLowerCase();

        PlayerClass newClass = switch (className) {
            case "archer" -> new Archer();
            case "warrior" -> new Warrior();
            // Add cases for other classes
            default -> null;
        };

        if (newClass == null) {
            player.sendMessage("Class not found.");
            return true;
        }
        /*UUID playerUUID = player.getUniqueId();
        PlayerClass playerClass = playerClassManager.loadPlayerClass(player);
        //loading the data
        int lLevel = playerLevelManager.getLevel(player, playerClass);
        int lExperience = playerLevelManager.getExperience(player, playerClass);
        //saving the data
        playerLevelManager.savePlayerData(event.getPlayer(), playerClass, lLevel, lExperience);*/
        playerClassManager.assignClassToPlayer(player, newClass);
        player.sendMessage("You are now a " + newClass.getName() + ".");
        return true;
    }
}
