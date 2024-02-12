package suncore.sunbase.data;

import org.bukkit.World;
import org.bukkit.entity.Player;
import suncore.sunbase.classes.Archer;
import suncore.sunbase.classes.PlayerClass;

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

    public int getLevel(Player player, PlayerClass playerClass) {
        return playerLevels.getOrDefault(player.getUniqueId(), new HashMap<>()).getOrDefault(playerClass.getName(), 1);
    }
    public void setLevel(Player player, PlayerClass playerClass, int level) {
        playerLevels.computeIfAbsent(player.getUniqueId(), k -> new HashMap<>()).put(playerClass.getName(), level);
    }
    public int getExperience(Player player, PlayerClass playerClass) {
        return playerExperience.getOrDefault(player.getUniqueId(), new HashMap<>()).getOrDefault(playerClass.getName(), 0);
    }

    public void addExperience(Player player, PlayerClass playerClass, int amount) {
        HashMap<String, Integer> classExperience = playerExperience.computeIfAbsent(player.getUniqueId(), k -> new HashMap<>());
        int currentExp = classExperience.getOrDefault(playerClass.getName(), 0);
        int newExp = currentExp + amount;
        int level = getLevel(player, playerClass);
        int expToNextLevel = calculateExpToNextLevel(level);

        if (newExp >= expToNextLevel) {
            newExp -= expToNextLevel;
            level++;
            player.sendMessage("Congratulations, you've reached level " + level + "!");
            setLevel(player, playerClass, level);
        }
        classExperience.put(playerClass.getName(), newExp);
    }

    private int calculateExpToNextLevel(int currentLevel) {
        // Placeholder calculation, put formula here
        return 100 * currentLevel;
    }


    //saving player Level, etc
    public void savePlayerData(Player player, PlayerClass playerClass, int level, int experience) {
        UUID playerUUID = player.getUniqueId();
        String className = playerClass.getName();
        String basePath = playerUUID + "." + className;
        //eventually save by world
        //World world = player.getWorld();
        //test saving
        //playerDataManager.get().set(playerUUID + "." + "test.path", "testValue");
        //String lvlPath = playerUUID + "." + playerClass + ".level";
        //System.out.println("Saving path: " + lvlPath);
        playerDataManager.get().set(basePath + ".class", className);
        playerDataManager.get().set(basePath + ".level", level);
        playerDataManager.get().set(basePath + ".experience", experience);
        playerDataManager.save();
    }

   /* public void saveTestPlayerData() {
        UUID testUUID = UUID.fromString("5af5b320-d5b9-499b-8903-9e14744438f6");
        String testPlayerClass = "Archer";
        int testLevel = 6;
        int testExperience = 100;

        playerDataManager.get().set(testUUID + "." + testPlayerClass + ".level", testLevel);
        playerDataManager.get().set(testUUID + "." + testPlayerClass + ".experience", testExperience);
        playerDataManager.save();
    }*/




    public int getPlayerLevel(Player player, PlayerClass playerClass) {
        UUID playerUUID = player.getUniqueId();
        return playerDataManager.get().getInt(playerUUID + "." + playerClass + ".level", 1); // Default level is 1
    }

    public int getPlayerExperience(Player player, PlayerClass playerClass) {
        UUID playerUUID = player.getUniqueId();
        return playerDataManager.get().getInt(playerUUID + "." + playerClass + ".experience", 0); // Default exp is 0
    }
}
