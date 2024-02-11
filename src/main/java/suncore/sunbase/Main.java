package suncore.sunbase;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import suncore.sunbase.data.PlayerDataManager;
import suncore.sunbase.data.PlayerLevelManager;

public final class Main extends JavaPlugin implements Listener {

    private PlayerLevelManager playerLevelManager;
    private PlayerDataManager playerDataManager;

    @Override
    public void onEnable() {
        // Plugin startup logic
        this.playerDataManager = new PlayerDataManager(this);
        this.playerLevelManager = new PlayerLevelManager(playerDataManager);
        getServer().getPluginManager().registerEvents(this, this);
        getLogger().info("SunCore has been enabled!");
        getLogger().info("PlayerDataManager initialized: " + (playerDataManager != null));
    }
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        getLogger().info("Accessing PlayerDataManager: " + (getPlayerDataManager() != null));
        Player player = event.getPlayer();
        //will have to read playerclass to replace "Archer"
        String playerClass = "Archer";
        int level = playerLevelManager.getPlayerLevel(player, playerClass);
        playerLevelManager.setLevel(player, level);
        player.sendMessage("Your current level as the class, " + playerClass + ", is " + level);
        int experience = playerLevelManager.getPlayerExperience(player, playerClass);
        playerLevelManager.addExperience(player, experience);
        player.sendMessage("Your currently have " + experience + " experience points");
    }
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        //will have to read playerclass to replace "Archer"
        String playerClass = "Archer";
        //loading the data
        int level = playerLevelManager.getPlayerLevel(player, playerClass);
        int experience = playerLevelManager.getPlayerExperience(player, playerClass);
        //saving the data
        playerLevelManager.savePlayerData(event.getPlayer(), playerClass, level, experience);
    }
    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public PlayerDataManager getPlayerDataManager() {
        return playerDataManager;
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        playerLevelManager.addExperience(event.getPlayer(), 10);
        getLogger().info("Adding experience to " + event.getPlayer().getScoreboardEntryName());
    }
}
