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
}
