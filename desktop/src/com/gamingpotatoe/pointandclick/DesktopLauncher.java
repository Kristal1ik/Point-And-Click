package com.gamingpotatoe.pointandclick;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.gamingpotatoe.pointandclick.PointAndClick;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
    public static void main(String[] arg) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setForegroundFPS(60);
        config.setTitle("Point And Click");
        config.setWindowedMode(Globals.SCR_WIDTH, Globals.SCR_HEIGHT);
        new Lwjgl3Application(new PointAndClick(), config);
    }
}
