 package com.extractss.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.extractss.game.ExtractSolarSys;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		new LwjglApplication(new ExtractSolarSys(null), config);
		config.resizable = false;

		//Original size
//		config.height = 960;
//		config.width = 540;

		//min
//		config.height = 480;
//		config.width = 340;

		//1:1
//		config.height = 1000;
//		config.width = 1000;

		//3:2
//		config.height = 1100;
//		config.width = 700;

		//16:9
//		config.height = 1100;
//		config.width = 600;

		//18:9
//		config.height = 1300;
//		config.width = 650;

		//18:9 but small
//		config.height = 650;
//		config.width = 325;

		//Don't use (landscape)
//		config.height = 700;
//		config.width = 900;

		//experimental
		config.height = 1250;
		config.width = 700;
	}
}
