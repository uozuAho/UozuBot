package aho.uozu.uozubot.strategy;

import bwapi.UnitType;

public interface Strategy {
    UnitType getNextUnitToBuild();
}
