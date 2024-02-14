package suncore.sunbase.abilities;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AbilityBinder {
    private static final Map<UUID, Map<Integer, Ability>> playerBindings = new HashMap<>();

    public static void bindAbility(Player player, int slot, Ability ability) {
        playerBindings.computeIfAbsent(player.getUniqueId(), k -> new HashMap<>()).put(slot, ability);
        // Optionally, inform the player of the binding
        player.sendMessage("Bound " + ability.getClass().getSimpleName() + " to slot " + slot);
    }

    public static Ability getBoundAbility(Player player, int slot) {
        return playerBindings.getOrDefault(player.getUniqueId(), new HashMap<>()).get(slot);
    }
}
