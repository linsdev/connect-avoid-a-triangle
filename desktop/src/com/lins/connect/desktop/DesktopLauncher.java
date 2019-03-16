package com.lins.connect.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.lins.Ad;
import com.lins.connect.App;

public class DesktopLauncher {
    public static void main (String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        // Near by real screen size
        config.width = 236;
        config.height = 420;
        //config.width = 405;  //  720
        //config.height = 720; // 1280
        //config.width = 600;
        //config.height = 1024;

        config.backgroundFPS = -1;
        config.samples = 8;
        config.vSyncEnabled = true;

        new LwjglApplication(new App(new Ad()), config);
    }
}