package aho.uozu.uozubot.utils;

import bwapi.Game;
import bwapi.Player;
import bwapi.Unit;
import bwapi.UnitType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Set;

/**
 * Game state info in addition to / in a more useful format
 * than that available through BWAPI.
 */
public class GameState {
    private Game game;
    private Player player;
    private Map<UnitType, Set<Unit>> unitMap;
    private Map<UnitType, Set<Unit>> unitsUnderConstruction;
    private static GameState instance;

    private static final Logger log = LoggerFactory.getLogger(GameState.class);

    private GameState() {}

    /**
     * There's only one game state. Get it with this.
     */
    public static GameState getInstance(Game game, Player player) {
        if (instance == null) {
            instance = new GameState();
            instance.game = game;
            instance.player = player;
        }
        return instance;
    }

    public void update() {
        unitMap = Units.getUnitMap(player.getUnits());
        Set<Unit> eggs = unitMap.get(UnitType.Zerg_Egg);
        if (null != eggs) {
            unitsUnderConstruction = Units.getUnitsUnderConstruction(eggs);
        }
    }

    /**
     * Get all your units, mapped by UnitType. Includes buildings.
     */
    public Map<UnitType, Set<Unit>> getUnitMap() {
        return unitMap;
    }

    /**
     * Get all units under construction, mapped by UnitType.
     */
    public Map<UnitType, Set<Unit>> getUnitsUnderConstruction() {
        return unitsUnderConstruction;
    }
}
