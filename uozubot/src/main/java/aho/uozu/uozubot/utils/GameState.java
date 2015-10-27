package aho.uozu.uozubot.utils;

import bwapi.Game;
import bwapi.Player;
import bwapi.Unit;
import bwapi.UnitType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
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

    /**
     * Get the game state instance.
     *
     * Game state must have already been initialised when this method
     * is called.
     *
     * @throws IllegalStateException if game state is uninitialised.
     */
    public static GameState getInstance() {
        if (instance == null) {
            throw new IllegalStateException("Game state not initialised");
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
     * Get all of a particular unit type
     * @return Set of units of given type. Can be empty (ie. null not returned)
     */
    public Set<Unit> getUnits(UnitType type) {
        Set<Unit> units =  unitMap.get(type);
        if (units == null)
            return new HashSet<Unit>(1);
        return units;
    }

    /**
     * Get all units under construction, mapped by UnitType.
     */
    public Map<UnitType, Set<Unit>> getUnitsUnderConstruction() {
        return unitsUnderConstruction;
    }

    /**
     * Get the number of units currently under construction.
     */
    public int underConstruction(UnitType type) {
        Set<Unit> units = getUnitsUnderConstruction().get(type);
        if (units == null)
            return 0;
        return units.size();
    }

    /** Available supply - used supply */
    public int spareSupply() {
        return player.supplyTotal() - player.supplyUsed();
    }
}
