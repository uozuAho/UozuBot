package aho.uozu.uozubot.buildorder;

import aho.uozu.uozubot.utils.GameState;
import bwapi.Player;
import bwapi.UnitType;

import java.util.HashMap;

public class ZerglingRush implements BuildOrder {

    private Player player;
    private GameState gameState;
    private HashMap<Integer, UnitType> supplyUnitMap;

    public ZerglingRush(Player player, GameState gameState) {
        supplyUnitMap = new HashMap<>();
        supplyUnitMap.put(10, UnitType.Zerg_Spawning_Pool);
        supplyUnitMap.put(12, UnitType.Zerg_Zergling);
        supplyUnitMap.put(14, UnitType.Zerg_Zergling);
        supplyUnitMap.put(16, UnitType.Zerg_Zergling);
        supplyUnitMap.put(18, UnitType.Zerg_Overlord);

        this.player = player;
        this.gameState = gameState;
    }

    @Override
    public UnitType nextUnitToBuild() {
        UnitType unit = supplyUnitMap.get(player.supplyUsed());
        // If nothing specified for this supply, build drones
        if (unit == null) {
            return UnitType.Zerg_Drone;
        }
        // If there's already a spawning pool, change to drone
        else if (unit == UnitType.Zerg_Spawning_Pool) {
            if (hasSpawningPool()) unit = UnitType.Zerg_Drone;
        }
        return unit;
    }

    private boolean hasSpawningPool() {
        return gameState.getUnits(UnitType.Zerg_Spawning_Pool).size() > 0;
    }
}
