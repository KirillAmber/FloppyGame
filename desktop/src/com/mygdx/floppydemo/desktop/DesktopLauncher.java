package com.mygdx.floppydemo.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.floppydemo.FlappyDemo;

import static com.mygdx.floppydemo.FlappyDemo.HEIGHT;
import static com.mygdx.floppydemo.FlappyDemo.TITLE;
import static com.mygdx.floppydemo.FlappyDemo.WIDTH;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = WIDTH;
		config.height = HEIGHT;
		config.title = TITLE;
		new LwjglApplication(new FlappyDemo(), config);
	}
}
