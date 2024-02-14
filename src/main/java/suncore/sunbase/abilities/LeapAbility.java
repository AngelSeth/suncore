package suncore.sunbase.abilities;

import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class LeapAbility implements Ability{
    private final long cooldownTime; //cooldown time in milliseconds
    private long lastUsed = 0; //last time ability was used
    private final int unlockLevel;


    public LeapAbility(long cooldownTime, int unlockLevel) {
        this.cooldownTime = cooldownTime;
        this.unlockLevel = unlockLevel;
    }
    @Override
    public void execute(Player player) {
        if (!isOnCooldown(player)) {
            // Logic to make the player leap
            Vector direction = player.getLocation().getDirection();
            direction.multiply(1.5).setY(1); // Adjust these values as needed
            player.setVelocity(player.getVelocity().add(direction));

            // Start the cooldown
            startCooldown(player);
        } else {
            player.sendMessage("Ability is on cooldown!");
        }
    }

    @Override
    public boolean isOnCooldown(Player player) {
        return (System.currentTimeMillis() - lastUsed) < cooldownTime;
    }

    @Override
    public void startCooldown(Player player) {
        lastUsed = System.currentTimeMillis();
    }

    @Override
    public int getUnlockLevel() {
        return unlockLevel;
    }

    @Override
    public String getName() {
        return "Leap";
    }
}
