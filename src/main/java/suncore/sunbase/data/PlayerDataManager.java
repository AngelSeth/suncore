package suncore.sunbase.data;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import suncore.sunbase.Main;

import java.io.File;
import java.io.IOException;

public class PlayerDataManager {
    private Main plugin;
    private File file;
    private FileConfiguration customFile;

    // Constructor
    public PlayerDataManager(Main plugin) {
        // Ensure the directory exists
        if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdirs();
        }

        file = new File(plugin.getDataFolder(), "playerdata.yml");

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        customFile = YamlConfiguration.loadConfiguration(file);
    }

    public FileConfiguration get() {
        return customFile;
    }

    public void save() {
        try {
            customFile.save(file);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Could not save the file");
        }
    }

    public void reload() {
        customFile = YamlConfiguration.loadConfiguration(file);
    }
}
