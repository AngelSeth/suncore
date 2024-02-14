package suncore.sunbase.util;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import suncore.sunbase.abilities.Ability;
import suncore.sunbase.abilities.AbilityBinder;
import suncore.sunbase.data.PlayerClassManager;
import java.util.Optional;

public class BindAbilityCommand implements CommandExecutor {
    private final PlayerClassManager playerClassManager;

    public BindAbilityCommand(PlayerClassManager playerClassManager) {
        this.playerClassManager = playerClassManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be used by players.");
            return true;
        }

        Player player = (Player) sender;
        if (args.length != 2) {
            player.sendMessage("Usage: /bind <abilityName> <slot>");
            return true;
        }

        String abilityName = args[0];
        int slot;
        try {
            slot = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            player.sendMessage("The slot must be a number.");
            return true;
        }

        if (slot < 0 || slot > 8) {
            player.sendMessage("Invalid slot. Must be between 0 and 8.");
            return true;
        }

        // Retrieve the player's class and then the ability by name
        Optional<Ability> abilityOptional = playerClassManager.getPlayerClass(player)
                .getAbilityByName(abilityName);

        if (!abilityOptional.isPresent()) {
            player.sendMessage("Ability not found or not unlocked.");
            return true;
        }

        Ability ability = abilityOptional.get();
        AbilityBinder.bindAbility(player, slot, ability);

        player.sendMessage("Bound " + abilityName + " to slot " + slot + ".");
        return true;
    }
}
