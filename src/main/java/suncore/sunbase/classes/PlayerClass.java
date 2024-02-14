package suncore.sunbase.classes;

import suncore.sunbase.abilities.Ability;

import java.util.List;
import java.util.Optional;

public interface PlayerClass {
    String getName();
    String getDescription();
    //add abilities and stat functionality
    List<Ability> getAbilities();
    Optional<Ability> getAbilityByName(String name);
    //Map<Stat, Double> getStatModifiers();*/
}
