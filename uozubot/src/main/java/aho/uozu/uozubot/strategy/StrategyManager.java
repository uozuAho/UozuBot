package aho.uozu.uozubot.strategy;

import aho.uozu.uozubot.utils.GameState;
import bwapi.Game;
import bwapi.Player;

public class StrategyManager {
    private static StrategyManager instance;
    private Strategy strategy;
    private Game game;
    private Player self;
    private GameState gameState;

    private StrategyManager(Game game) {
        this.game = game;
        this.self = game.self();
        this.gameState = GameState.getInstance();
    }

    public static StrategyManager getInstance(Game game) {
        if (instance == null) {
            instance = new StrategyManager(game);
        }
        return instance;
    }

    public Strategy getCurrentStrategy() {
        if (strategy == null) {
            strategy = new ZerglingRush(game);
        }
        return strategy;
    }
}
