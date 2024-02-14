package suncore.sunbase.abilities;

import org.bukkit.entity.Player;

public interface Ability {
    //method for executing the ability
    void execute (Player player);

    //method to check if ability is on cooldown
    boolean isOnCooldown(Player player);

    //method to start ability cooldown
    void startCooldown(Player player);

    /*//Implement if set up is required for abilities
    default void setup (Player player){
        // Default implementation (if any)
    }


    // Optional: Method for any cleanup required when the ability is no longer needed
    default void cleanup(Player player) {
        // Default implementation (if any)
    }*/
}
