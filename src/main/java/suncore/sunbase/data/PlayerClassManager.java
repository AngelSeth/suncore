package suncore.sunbase.data;

import org.bukkit.entity.Player;
import suncore.sunbase.Main;
import suncore.sunbase.classes.PlayerClass;
import suncore.sunbase.classes.Archer;
import suncore.sunbase.classes.Warrior;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PlayerClassManager {
    private PlayerDataManager playerDataManager;
    private Map<UUID, PlayerClass> playerClassAssignments = new HashMap<>();
    private Map<String, Supplier<PlayerClass>> registeredClasses = new HashMap<>();
    //private Logger logger;
    public PlayerClassManager(PlayerDataManager playerDataManager) {
        this.playerDataManager = playerDataManager;
        //this.logger = logger;
        // Register available classes
        registerClass("Archer", Archer::new);
        registerClass("Warrior", Warrior::new);
        // Add more classes as needed
    }

    private void registerClass(String className, Supplier<PlayerClass> classConstructor) {
        registeredClasses.put(className, classConstructor);
    }

    public void assignClassToPlayer(Player player, PlayerClass playerClass) {
        playerClassAssignments.put(player.getUniqueId(), playerClass);
        savePlayerClass(player, playerClass);
        // Consider saving the class assignment here or in a batch operation elsewhere
    }

    private void savePlayerClass(Player player, PlayerClass playerClass) {
        UUID playerUUID = player.getUniqueId();
        String className = playerClass.getName();
        // Save the class name to your data storage
        playerDataManager.get().set(playerUUID + ".class", className);
        playerDataManager.save();
    }

    public PlayerClass getPlayerClass(Player player) {
        return playerClassAssignments.computeIfAbsent(player.getUniqueId(), uuid -> loadPlayerClass(player));
    }

    public PlayerClass loadPlayerClass(Player player) {
        UUID playerUUID = player.getUniqueId();
        String className = playerDataManager.get().getString(playerUUID + ".class", "Warrior"); // Default to Warrior

        Supplier<PlayerClass> classConstructor = registeredClasses.get(className);
        if (classConstructor != null) {
            return classConstructor.get();
        } else {
            // Log an error if the class name does not match any registered class
            //getLogger().log(Level.WARNING, "Unrecognized class '" + className + "' for player " + player.getName() + ". Assigning default class.");
            return new Warrior(); // Return a default class instance
        }
    }
}
