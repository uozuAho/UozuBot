package aho.uozu.uozubot.strategy;

import bwapi.UnitType;

public class ZerglingRush implements Strategy {
    @Override
    public UnitType getNextUnitToBuild() {
        return UnitType.Zerg_Drone;
    }
}
