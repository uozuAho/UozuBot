package aho.uozu.uozubot.buildorder;

import bwapi.UnitType;

/**
 * Represents a build order. Intended for early game use,
 * rigid build orders don't really make sense later on...
 */
public interface BuildOrder {
    UnitType nextUnitToBuild();
}
