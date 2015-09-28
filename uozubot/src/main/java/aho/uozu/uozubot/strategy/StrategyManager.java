package aho.uozu.uozubot.strategy;

public class StrategyManager {
    private static StrategyManager instance;
    private Strategy strategy;

    private StrategyManager() {}

    public static StrategyManager getInstance() {
        if (instance == null) {
            instance = new StrategyManager();
        }
        return instance;
    }

    public Strategy getCurrentStrategy() {
        if (strategy == null) {
            strategy = new ZerglingRush();
        }
        return strategy;
    }
}
