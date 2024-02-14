package suncore.sunbase.classes;

import org.bukkit.entity.Player;
import suncore.sunbase.abilities.Ability;
import suncore.sunbase.abilities.LeapAbility;
import suncore.sunbase.data.PlayerLevelManager;

public class Warrior implements PlayerClass{
    private Ability leapAbility;
    private PlayerLevelManager playerLevelManager;

    public Warrior() {
        // cooldown time in milliseconds
        this.leapAbility = new LeapAbility(5000);
    }

    public void useAbility(Player player) {
        if (playerLevelManager.getPlayerLevel()>= 1) { // Check if player's level is 1 or higher
            leapAbility.execute(player);
        } else {
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
}
