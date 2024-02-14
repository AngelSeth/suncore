package suncore.sunbase.classes;

import org.bukkit.entity.Player;
import suncore.sunbase.abilities.Ability;
import suncore.sunbase.abilities.LeapAbility;
import suncore.sunbase.data.PlayerLevelManager;

import java.util.Arrays;
import java.util.List;

public class Warrior implements PlayerClass{
    private Ability leapAbility;
    private List<Ability> abilities;
    private PlayerLevelManager playerLevelManager;

    public Warrior(PlayerLevelManager playerLevelManager) {
        this.playerLevelManager = playerLevelManager;
        //cooldown time in milliseconds
        this.leapAbility = new LeapAbility(5000, 1);
        abilities = Arrays.asList(new LeapAbility(5000, 1));
    }

    public void useAbility(Player player) {
        // Ensure the playerLevelManager is not null and properly initialized
        if (this.playerLevelManager != null && this.playerLevelManager.getPlayerLevel(player, this) >= leapAbility.getUnlockLevel()) {
            // Player level is 1 or higher, use the ability
            leapAbility.execute(player);
        } else {
            // Player level is below 1, send a message
            player.sendMessage("You need to reach level 1 to unlock this ability!");
        }
    }
    @Override
    public String getName() {
        return "Warrior";
    }

    @Override
    public String getDescription() {
        return "A strong homie";
    }

    @Override
    public List<Ability> getAbilities() {
        return abilities;
    }
}
