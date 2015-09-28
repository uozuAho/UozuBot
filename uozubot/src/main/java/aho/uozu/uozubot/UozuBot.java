package aho.uozu.uozubot;

import aho.uozu.uozubot.utils.GameState;
import bwapi.*;
import bwta.BWTA;
import bwta.BaseLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class UozuBot extends DefaultBWListener {

    private Mirror mirror;
    private Game game;
    private Player self;
    private GameState gameState;
    private static final Logger log = LoggerFactory.getLogger(UozuBot.class);

    private Set<Unit> miningTeam = new HashSet<>();
    private Set<Unit> scoutTeam = new HashSet<>();

    private Position enemyBasePosition;


    public UozuBot(Mirror mirror) {
        this.mirror = mirror;
    }

    @Override
    public void onStart() {
        game = mirror.getGame();
        self = game.self();
        gameState = GameState.getInstance(game, self);

        //Use BWTA to analyze map
        //This may take a few minutes if the map is processed first time!
        log.debug("Analyzing map...");
        BWTA.readMap();
        BWTA.analyze();
        log.debug("Map data ready");

        log.debug("Setting game speed");
        game.setLocalSpeed(10);

        log.debug("Base locations:");
        for (BaseLocation b : BWTA.getBaseLocations()) {
            StringBuilder builder = new StringBuilder();
            builder.append("(")
                    .append(b.getX())
                    .append(", ")
                    .append(b.getY())
                    .append(")");
            if (b.isStartLocation()) {
                builder.append(" : start pos");
            }
            log.debug(builder.toString());
        }
    }

    @Override
    public void onFrame() {
        gameState.update();
        Map<UnitType, Set<Unit>> unitMap = gameState.getUnitMap();

        // TODO: kludge
        // determine enemy base position from map
        if (null == enemyBasePosition) {
            Unit myHatch = null;
            Set<Unit> myHatches = gameState.getUnitMap().get(UnitType.Zerg_Hatchery);
            if (myHatches != null) {
                for (Unit u: myHatches) {
                    myHatch = u;
                    break;
                }
            }
            int maxDist = 0;
            BaseLocation farthestBase = null;
            if (myHatch != null) {
                for (BaseLocation b : BWTA.getBaseLocations()) {
                    if (b.isStartLocation()) {
                        if (farthestBase == null) {
                            farthestBase = b;
                        }
                        int dist = myHatch.getPosition().getApproxDistance(b.getPosition());
                        if (dist > maxDist) {
                            maxDist = dist;
                            farthestBase = b;
                        }
                    }
                }
                if (farthestBase != null) {
                    enemyBasePosition = farthestBase.getPosition();
                }
            }
            else {
                log.error("I broke it");
            }
        }

        // command larva
        Set<Unit> larva = unitMap.get(UnitType.Zerg_Larva);
        if (larva != null) {
            for (Unit u : larva) {
                if (spareSupply() <= 2 && !isOverlordUnderConstruction()) {
                    u.train(UnitType.Zerg_Overlord);
                }
                else {
                    u.train(UnitType.Zerg_Drone);
                }
            }
        }

        // assign drones to teams
        Set<Unit> drones = unitMap.get(UnitType.Zerg_Drone);
        if (drones != null) {
            for (Unit drone : drones) {
                // add unassigned & idle drones to mining team
                if (!miningTeam.contains(drone) && !scoutTeam.contains(drone)) {
                    if (drone.isIdle()) {
                        miningTeam.add(drone);
                    }
                }
                // move drone from mining team to scout team
                if (scoutTeam.size() == 0 && self.supplyUsed() >= 18) {
                    Unit scoutDrone = null;
                    // get first mining unit
                    for (Unit miningUnit : miningTeam) {
                        scoutDrone = miningUnit;
                        break;
                    }
                    if (null != scoutDrone) {
                        miningTeam.remove(scoutDrone);
                        scoutTeam.add(scoutDrone);
                    }
                }
            }
        }

        // command mining team
        for (Unit drone : miningTeam) {
            // mine if idle
            if (drone.isIdle()) {
                Unit closestMineral = null;
                //find the closest mineral
                for (Unit neutralUnit : game.neutral().getUnits()) {
                    if (neutralUnit.getType().isMineralField()) {
                        if (closestMineral == null || drone.getDistance(neutralUnit) <
                                drone.getDistance(closestMineral)) {
                            closestMineral = neutralUnit;
                        }
                    }
                }
                //if a mineral patch was found, send the drone to gather it
                if (closestMineral != null) {
                    drone.gather(closestMineral, false);
                }
            }
        }

        // command scout team
        for (Unit unit : scoutTeam) {
            unit.move(enemyBasePosition);
        }

        onScreenDisplay();
    }

    private void onScreenDisplay() {
        int textx = 10;
        int texty = 10;

        // draw number of each unit
        game.drawTextScreen(textx, texty, "My units:");
        texty += 10;
        for (UnitType type: getSortedUnitTypes(gameState.getUnitMap())) {
            game.drawTextScreen(textx, texty, type.toString() + ": " +
                    gameState.getUnitMap().get(type).size());
            texty += 10;
        }

        // draw supply used
        game.drawTextScreen(textx, texty, "Supply used/total: " +
                self.supplyUsed() + "/" + self.supplyTotal());
        texty += 10;

        // draw unit target positions
        for (Unit myUnit : self.getUnits()) {
            Position target = myUnit.getOrderTargetPosition();
            if (target.getX() != 0 && target.getY() != 0) {
                game.drawLineMap(
                        myUnit.getPosition().getX(),
                        myUnit.getPosition().getY(),
                        myUnit.getOrderTargetPosition().getX(),
                        myUnit.getOrderTargetPosition().getY(), bwapi.Color.Black);
                texty += 10;
            }
        }
    }

    /**
     * Get a list of unit types sorted by name, from the given unit map.
     * Ugh, so ugly!! Save me, Python
     */
    private List<UnitType> getSortedUnitTypes(Map<UnitType, Set<Unit>> unitMap) {
        List<String> sortedKeys = new ArrayList<>();
        Map<String, UnitType> nameTypeMap = new HashMap<>();
        for (UnitType type : unitMap.keySet()) {
            nameTypeMap.put(type.toString(), type);
            sortedKeys.add(type.toString());
        }
        Collections.sort(sortedKeys);
        List<UnitType> sortedTypes = new ArrayList<>();
        for (String name: sortedKeys) {
            sortedTypes.add(nameTypeMap.get(name));
        }
        return sortedTypes;
    }

    private int spareSupply() {
        return self.supplyTotal() - self.supplyUsed();
    }

    private boolean isOverlordUnderConstruction() {
        Set<Unit> overlords = gameState.getUnitsUnderConstruction().get(UnitType.Zerg_Overlord);
        if (overlords == null)
            return false;
        return overlords.size() > 0;
    }

}
