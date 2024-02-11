package suncore.sunbase;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import suncore.sunbase.data.PlayerDataManager;
import suncore.sunbase.data.PlayerLevelManager;

import java.util.UUID;

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
        playerLevelManager.saveTestPlayerData();
    }
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        getLogger().info("Accessing PlayerDataManager: " + (getPlayerDataManager() != null));
        Player player = event.getPlayer();
        //will have to read playerclass to replace "Archer"
        String playerClass = "Archer";
        int level = playerLevelManager.getPlayerLevel(player, playerClass);
        playerLevelManager.setLevel(player, playerClass, level);
        int experience = playerLevelManager.getPlayerExperience(player, playerClass);
        playerLevelManager.addExperience(player, playerClass, experience);
        player.sendMessage("Your current level as the class, " + playerClass + ", is " + level);
        player.sendMessage("Your currently have " + experience + " experience points");
    }
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        UUID playerUUID = player.getUniqueId();
        //will have to read playerclass to replace "Archer"
        String playerClass = "Archer";
        //loading the data
        int lLevel = playerLevelManager.getLevel(player, playerClass);
        int lExperience = playerLevelManager.getExperience(player, playerClass);
        //saving the data
        //if (playerUUID != null && playerClass != null && level >= 0 && experience >= 0) {
            playerLevelManager.savePlayerData(event.getPlayer(), playerClass, lLevel, lExperience);
            getLogger().info("It's working, Sugar");
            getLogger().info("Honey, I am saving data for player: " + player.getName() + "! They are level, " + lLevel + ", and have " + lExperience + " experience points. Hallelujah!");
        //} else {getLogger().info("It's not working, sugar...");}
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
        String playerClass = "Archer";
        playerLevelManager.addExperience(event.getPlayer(), playerClass, 10);
        getLogger().info("Adding experience to " + event.getPlayer().getScoreboardEntryName());
    }

    /*@EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        Entity entity = event.getEntity();
        if(entity.getLastDamageCause() instanceof Player) {
            playerLevelManager.addExperience(((Player) entity.getLastDamageCause()), 10);
            getLogger().info("Adding experience to " + ((Player) entity.getLastDamageCause()).getScoreboardEntryName());
        }
    }*/
}
