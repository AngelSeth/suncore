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
import suncore.sunbase.classes.PlayerClass;
import suncore.sunbase.data.PlayerClassManager;
import suncore.sunbase.data.PlayerDataManager;
import suncore.sunbase.data.PlayerLevelManager;
import suncore.sunbase.util.ChangeClassCommand;

import java.util.UUID;

public final class Main extends JavaPlugin implements Listener {

    private PlayerLevelManager playerLevelManager;
    private PlayerDataManager playerDataManager;
    private PlayerClassManager playerClassManager;

    @Override
    public void onEnable() {
        // Plugin startup logic
        this.playerDataManager = new PlayerDataManager(this);
        this.playerLevelManager = new PlayerLevelManager(playerDataManager);
        this.playerClassManager = new PlayerClassManager(playerDataManager);
        getServer().getPluginManager().registerEvents(this, this);
        getLogger().info("SunCore has been enabled!");
        getLogger().info("PlayerDataManager initialized: " + (playerDataManager != null));
        //playerLevelManager.saveTestPlayerData();

        //load commands
        this.getCommand("class").setExecutor(new ChangeClassCommand(playerClassManager));
    }
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        getLogger().info("Accessing PlayerDataManager: " + (getPlayerDataManager() != null));
        Player player = event.getPlayer();
        PlayerClass playerClass = playerClassManager.getPlayerClass(player);
        int level = playerLevelManager.getPlayerLevel(player, playerClass);
        playerLevelManager.setLevel(player, playerClass, level);
        int experience = playerLevelManager.getPlayerExperience(player, playerClass);
        playerLevelManager.addExperience(player, playerClass, experience);
        player.sendMessage("You are currently a " + playerClass.getName());
        //player.sendMessage("Your current level as the class, " + playerClass.getName() + ", is " + level);
        //player.sendMessage("Your currently have " + experience + " experience points");
    }
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        UUID playerUUID = player.getUniqueId();
        PlayerClass playerClass = playerClassManager.loadPlayerClass(player);
        //loading the data
        int lLevel = playerLevelManager.getLevel(player, playerClass);
        int lExperience = playerLevelManager.getExperience(player, playerClass);
        //saving the data
        playerLevelManager.savePlayerData(event.getPlayer(), playerClass, lLevel, lExperience);
        getLogger().info("It's working, Sugar");
        getLogger().info("Honey, I am saving data for player: " + player.getName() + "! They are level, " + lLevel + ", and have " + lExperience + " experience points. Hallelujah!");
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
        Player player = event.getPlayer();
        PlayerClass playerClass = playerClassManager.getPlayerClass(player); // Retrieve the player's class
        playerLevelManager.addExperience(event.getPlayer(), playerClass, 10);
        getLogger().info("Adding experience to " + player.getName() + " as a " + playerClass.getName());
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
