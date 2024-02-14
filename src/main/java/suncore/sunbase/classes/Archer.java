package suncore.sunbase.classes;

import suncore.sunbase.abilities.Ability;
import suncore.sunbase.abilities.LeapAbility;
import suncore.sunbase.data.PlayerLevelManager;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Archer implements PlayerClass{

    private PlayerLevelManager playerLevelManager;
    private List<Ability> abilities;

    public Archer(PlayerLevelManager playerLevelManager) {
        this.playerLevelManager = playerLevelManager;
        //cooldown time in milliseconds
        //this.leapAbility = new LeapAbility(5000, 1);
       // abilities = Arrays.asList(new LeapAbility(5000, 1));
    }
    @Override
    public String getName() {
        return "Archer";
    }

    @Override
    public String getDescription() {
        return "A skilled and agile marksman";
    }

    @Override
    public List<Ability> getAbilities() {
        return null;
    }

    /*@Override
    public List<Ability> getAbilities() {
        // Return a list of abilities specific to the Archer class
        return Arrays.asList(new PrecisionShot(), new RapidFire(), new Bullseye());
    }

    @Override
    public Map<Stat, Double> getStatModifiers() {
        // Return any stat modifiers that apply to the Archer class
        Map<Stat, Double> modifiers = new HashMap<>();
        modifiers.put(Stat.DEXTERITY, 1.2);
        return modifiers;
    }*/
}
