package suncore.sunbase.classes;

import java.util.Arrays;
import java.util.HashMap;

public class Archer implements PlayerClass{
    @Override
    public String getName() {
        return "Archer";
    }

    @Override
    public String getDescription() {
        return "A skilled and agile marksman";
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
