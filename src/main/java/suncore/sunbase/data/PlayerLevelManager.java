package suncore.sunbase.data;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class PlayerLevelManager {
    private PlayerDataManager playerDataManager;
    private final HashMap<UUID, Integer> playerLevels = new HashMap<>();
    private final HashMap<UUID, Integer> playerExperience = new HashMap<>();

    public int getLevel(Player player) {
        return playerLevels.getOrDefault(player.getUniqueId(), 1);
    }
    public void setLevel(Player player, int level) {
        playerLevels.put(player.getUniqueId(), level);
    }
    public int getExperience(Player player) {
        return playerExperience.getOrDefault(player.getUniqueId(), 0);
    }

    public void addExperience(Player player, int amount) {
        int currentExp = getExperience(player);
        int newExp = currentExp + amount;
        int level = getLevel(player);
        int expToNextLevel = calculateExpToNextLevel(level);

        if (newExp >= expToNextLevel) {
            newExp -= expToNextLevel;
            level++;
            setLevel(player, level);
            player.sendMessage("Congratulations, you've reached level " + level + "!");
        }

        playerExperience.put(player.getUniqueId(), newExp);
    }

    private int calculateExpToNextLevel(int currentLevel) {
        // Placeholder calculation, put formula here
        return 100 * currentLevel;
    }


    //saving player Level, etc
    public void savePlayerData(Player player, String playerClass, int level, int experience) {
        UUID playerUUID = player.getUniqueId();
        playerDataManager.get().set(playerUUID + "." + playerClass + ".level", level);
        playerDataManager.get().set(playerUUID + "." + playerClass + ".experience", experience);
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
