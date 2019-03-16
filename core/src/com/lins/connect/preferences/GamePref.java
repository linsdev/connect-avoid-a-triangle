package com.lins.connect.preferences;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public final class GamePref {
    static final String prefName = "lins.connect";

    public static Preferences getPref() {
        return Gdx.app.getPreferences(prefName);
    }
}