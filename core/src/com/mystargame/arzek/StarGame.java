package com.mystargame.arzek;

import com.badlogic.gdx.Game;
import com.mystargame.arzek.screen.MenuScreen;

public class StarGame extends Game {
	@Override
	public void create () {
		setScreen(new MenuScreen(this));
	}
}
