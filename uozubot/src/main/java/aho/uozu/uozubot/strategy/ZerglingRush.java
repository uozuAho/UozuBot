package aho.uozu.uozubot.strategy;

import aho.uozu.uozubot.buildorder.BuildOrder;
import aho.uozu.uozubot.utils.GameState;
import bwapi.Game;
import bwapi.Player;
import bwapi.UnitType;

public class ZerglingRush implements Strategy {

    private Game game;
    private Player self;
    private GameState gameState;
    private BuildOrder buildOrder;

    public ZerglingRush(Game game) {
        this.game = game;
        this.self = game.self();
        this.gameState = GameState.getInstance();
        this.buildOrder = new aho.uozu.uozubot.buildorder.ZerglingRush(self, gameState);
    }

    @Override
    public UnitType getNextUnitToBuild() {
        return buildOrder.nextUnitToBuild();
    }
}
