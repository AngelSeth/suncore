package suncore.sunbase.classes;

import suncore.sunbase.abilities.Ability;

import java.util.List;

public interface PlayerClass {
    String getName();
    String getDescription();
    //add abilities and stat functionality
    List<Ability> getAbilities();
    //Map<Stat, Double> getStatModifiers();*/
}
