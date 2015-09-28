package aho.uozu.uozubot.utils;

import bwapi.Unit;
import bwapi.UnitType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class Units {

    private static final Logger log = LoggerFactory.getLogger(Units.class);

    private Units() {}

    /**
     * Get all my units, mapped by type. Includes buildings.
     * @param units
     *      List of all units, which can be got with Player.getUnits()
     */
    public static Map<UnitType, Set<Unit>> getUnitMap(List<Unit> units) {
        Map<UnitType, Set<Unit>> map = new HashMap<>();
        for (Unit unit : units) {
            Set<Unit> unitSet = map.get(unit.getType());
            if (unitSet == null) {
                unitSet = new HashSet<>();
                map.put(unit.getType(), unitSet);
            }
            unitSet.add(unit);
        }
        return map;
    }

    /**
     * Get all units under construction (eggs), mapped by type.
     * @param eggs
     *      Set of eggs. Get this from getUnitMap()
     */
    public static Map<UnitType, Set<Unit>> getUnitsUnderConstruction(Set<Unit> eggs) {
        Map<UnitType, Set<Unit>> map = new HashMap<>();
        for (Unit egg : eggs) {
            UnitType buildType = egg.getBuildType();
            Set<Unit> unitSet = map.get(buildType);
            if (unitSet == null) {
                unitSet = new HashSet<>();
                map.put(buildType, unitSet);
            }
            unitSet.add(egg);
        }
        return map;
    }
}
