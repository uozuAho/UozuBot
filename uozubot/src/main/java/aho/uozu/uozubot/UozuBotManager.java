package aho.uozu.uozubot;

import aho.uozu.uozubot.utils.BWEventListenerWrapper;
import bwapi.BWEventListener;
import bwapi.Mirror;

public class UozuBotManager {

    private Mirror mirror = new Mirror();
    private BWEventListener bot;

    void run() {
        mirror.startGame();
    }

    void setBot(BWEventListener bot) {
        // wrap the event listener in BWEventListenerWrapper to catch exceptions
        this.bot = new BWEventListenerWrapper(bot);
        mirror.getModule().setEventListener(this.bot);
    }

    Mirror getMirror() { return mirror; }

    public static void main(String[] args) {
        UozuBotManager manager = new UozuBotManager();
        manager.setBot(new UozuBot(manager.getMirror()));
        manager.run();
    }
}
