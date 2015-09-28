package aho.uozu.uozubot.utils;

import bwapi.BWEventListener;
import bwapi.Player;
import bwapi.Position;
import bwapi.Unit;

/**
 * Wraps a BWEventListener.
 *
 * Currently just prints exception stack traces whenever they occur,
 * since the plain listener does not.
 */
public class BWEventListenerWrapper implements BWEventListener {

    private BWEventListener listener;

    public BWEventListenerWrapper(BWEventListener listener) {
        this.listener = listener;
    }

    @Override
    public void onStart() {
        try {
            this.listener.onStart();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onEnd(boolean var1) {
        try {
            this.listener.onEnd(var1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onFrame() {
        try {
            this.listener.onFrame();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onSendText(String var1) {
        try {
            this.listener.onSendText(var1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onReceiveText(Player var1, String var2) {
        try {
            this.listener.onReceiveText(var1, var2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPlayerLeft(Player var1) {
        try {
            this.listener.onPlayerLeft(var1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onNukeDetect(Position var1) {
        try {
            this.listener.onNukeDetect(var1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUnitDiscover(Unit var1) {
        try {
            this.listener.onUnitDiscover(var1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUnitEvade(Unit var1) {
        try {
            this.listener.onUnitEvade(var1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUnitShow(Unit var1) {
        try {
            this.listener.onUnitShow(var1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUnitHide(Unit var1) {
        try {
            this.listener.onUnitHide(var1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUnitCreate(Unit var1) {
        try {
            this.listener.onUnitCreate(var1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUnitDestroy(Unit var1) {
        try {
            this.listener.onUnitDestroy(var1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUnitMorph(Unit var1) {
        try {
            this.listener.onUnitMorph(var1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUnitRenegade(Unit var1) {
        try {
            this.listener.onUnitRenegade(var1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onSaveGame(String var1) {
        try {
            this.listener.onSaveGame(var1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUnitComplete(Unit var1) {
        try {
            this.listener.onUnitComplete(var1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPlayerDropped(Player var1) {
        try {
            this.listener.onPlayerDropped(var1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
