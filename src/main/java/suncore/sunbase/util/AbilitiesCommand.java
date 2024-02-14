package suncore.sunbase.util;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import suncore.sunbase.abilities.Ability;
import suncore.sunbase.classes.PlayerClass;
import suncore.sunbase.data.PlayerClassManager;
import suncore.sunbase.data.PlayerLevelManager;

public class AbilitiesCommand implements CommandExecutor {

    private PlayerClassManager playerClassManager;

    public AbilitiesCommand(PlayerClassManager playerClassManager) {
        this.playerClassManager = playerClassManager;
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can use this command.");
            return true;
        }

        if (args.length != 1) {
            sender.sendMessage("Usage: /abilities <class>");
            return true;
        }

        String className = args[0].toLowerCase();
        PlayerClass playerClass = playerClassManager.getClassByName(className);

        if (playerClass == null) {
            sender.sendMessage("Class not found.");
            return true;
        }

        sender.sendMessage("Abilities for " + className + ":");
        for (Ability ability : playerClass.getAbilities()) {
            sender.sendMessage(ability.getClass().getSimpleName() + " - Unlock Level: " + ability.getUnlockLevel());
        }

        return true;
    }
}
