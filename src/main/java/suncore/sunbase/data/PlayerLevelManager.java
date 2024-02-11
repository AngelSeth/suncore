package suncore.sunbase.data;

import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class PlayerLevelManager {
    private PlayerDataManager playerDataManager;
    //hash for saving the data in the yaml
    private final HashMap<UUID, HashMap<String, Integer>> playerLevels = new HashMap<>();
    private final HashMap<UUID, HashMap<String, Integer>> playerExperience = new HashMap<>();

    //to call playerdatamanager
    public PlayerLevelManager(PlayerDataManager playerDataManager) {
        this.playerDataManager = playerDataManager;
    }

    public int getLevel(Player player, String playerClass) {
        return playerLevels.getOrDefault(player.getUniqueId(), new HashMap<>()).getOrDefault(playerClass, 1);
    }
    public void setLevel(Player player, String playerClass, int level) {
        playerLevels.computeIfAbsent(player.getUniqueId(), k -> new HashMap<>()).put(playerClass, level);
    }
    public int getExperience(Player player, String playerClass) {
        return playerExperience.getOrDefault(player.getUniqueId(), new HashMap<>()).getOrDefault(playerClass, 0);
    }

    public void addExperience(Player player, String playerClass, int amount) {
        int currentExp = getExperience(player, playerClass);
        int newExp = currentExp + amount;
        int level = getLevel(player, playerClass);
        int expToNextLevel = calculateExpToNextLevel(level);

        if (newExp >= expToNextLevel) {
            newExp -= expToNextLevel;
            level++;
            player.sendMessage("Congratulations, you've reached level " + level + "!");
            setLevel(player, playerClass, level);
        }
        playerExperience.computeIfAbsent(player.getUniqueId(), k -> new HashMap<>()).put(playerClass, newExp);
    }

    private int calculateExpToNextLevel(int currentLevel) {
        // Placeholder calculation, put formula here
        return 100 * currentLevel;
    }


    //saving player Level, etc
    public void savePlayerData(Player player, String playerClass, int level, int experience) {
        UUID playerUUID = player.getUniqueId();
        //eventually save by world
        //World world = player.getWorld();
        //test saving
        //playerDataManager.get().set(playerUUID + "." + "test.path", "testValue");
        String lvlPath = playerUUID + "." + playerClass + ".level";
        //System.out.println("Saving path: " + lvlPath);
        playerDataManager.get().set(lvlPath, level);
        playerDataManager.get().set(playerUUID + "." + playerClass + ".experience", experience);
        playerDataManager.save();
    }

    public void saveTestPlayerData() {
        UUID testUUID = UUID.fromString("5af5b320-d5b9-499b-8903-9e14744438f6");
        String testPlayerClass = "Archer";
        int testLevel = 6;
        int testExperience = 100;

        playerDataManager.get().set(testUUID + "." + testPlayerClass + ".level", testLevel);
        playerDataManager.get().set(testUUID + "." + testPlayerClass + ".experience", testExperience);
        playerDataManager.save();
    }

    public int getPlayerLevel(Player player, String playerClass) {
        UUID playerUUID = player.getUniqueId();
        return playerDataManager.get().getInt(playerUUID + "." + playerClass + ".level", 1); // Default level is 1
    }

    public int getPlayerExperience(Player player, String playerClass) {
        UUID playerUUID = player.getUniqueId();
        return playerDataManager.get().getInt(playerUUID + "." + playerClass + ".experience", 0); // Default exp is 0
    }
}
