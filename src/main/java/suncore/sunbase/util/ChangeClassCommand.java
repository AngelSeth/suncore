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
import suncore.sunbase.data.PlayerLevelManager;

import java.util.UUID;

public class ChangeClassCommand implements CommandExecutor {
    private PlayerClassManager playerClassManager;
    private PlayerLevelManager playerLevelManager;
    public ChangeClassCommand(PlayerClassManager playerClassManager, PlayerLevelManager playerLevelManager) {
        this.playerClassManager = playerClassManager;
        this.playerLevelManager = playerLevelManager;
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
        if (args.length == 1) {
            String className = args[0].toLowerCase();
            PlayerClass newClass = null;

            switch (className) {
                case "warrior":
                    newClass = new Warrior(playerLevelManager); // Pass PlayerLevelManager to Warrior
                    break;
                // Add cases for other classes here
                default:
                    sender.sendMessage("Class not recognized.");
                    return true;
            }

            if (newClass != null) {
                playerClassManager.assignClassToPlayer(player, newClass);
                player.sendMessage("You have now changed to class: " + newClass.getName());
            }
        } else {
            sender.sendMessage("Usage: /class <classname>");
        }

        return true;
    }
    }

