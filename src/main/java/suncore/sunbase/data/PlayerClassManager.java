package suncore.sunbase.data;

import org.bukkit.entity.Player;
import suncore.sunbase.classes.Archer;
import suncore.sunbase.classes.PlayerClass;
import suncore.sunbase.classes.Warrior;

import java.util.*;

public class PlayerClassManager {
    private PlayerDataManager playerDataManager;
    private Map<UUID, PlayerClass> playerClassAssignments = new HashMap<>();
    private List<PlayerClass> availableClasses = new ArrayList<>();

    public PlayerClassManager(PlayerDataManager playerDataManager) {
        this.playerDataManager = playerDataManager;

        // Initialize availableClasses with instances of your player classes
        availableClasses.add(new Archer());
        availableClasses.add(new Warrior());
    }

    public void assignClassToPlayer(Player player, PlayerClass playerClass) {
        playerClassAssignments.put(player.getUniqueId(), playerClass);
    }


    public PlayerClass getPlayerClass(Player player) {
        return playerClassAssignments.getOrDefault(player.getUniqueId(), new Archer()); // Ensure there's a default class
    }

    public PlayerClass loadPlayerClass(Player player) {
        UUID playerUUID = player.getUniqueId();
        // Attempt to retrieve player class name
        String className = playerDataManager.get().getString(playerUUID + ".class");


        // Check if className is null or empty and assign a default class name if necessary
        if (className == null || className.isEmpty()) {
            className = "Archer"; // Use your default class name
        }
        // Determine the player's class based on the saved class name
        // This is a simple example; you might have a more sophisticated method for instantiating classes
        PlayerClass playerClass;
        switch (className) {
            case "Archer":
                playerClass = new Archer();
                break;
            // Add cases for other classes
            default:
                playerClass = new Warrior(); // Fallback class
                break;
        }

        return playerClass;
    }
}
