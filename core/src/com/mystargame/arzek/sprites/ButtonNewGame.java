package com.mystargame.arzek.sprites;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.mystargame.arzek.base.ScaledButton;
import com.mystargame.arzek.math.Rect;
import com.mystargame.arzek.screen.GameScreen;

public class ButtonNewGame extends ScaledButton {

    private static final float HEIGHT = 0.06f;
    private static final float BOTTOM = -0.05f;

    private GameScreen gameScreen;

    public ButtonNewGame(TextureAtlas atlas, GameScreen gameScreen) {
        super(atlas.findRegion("button_new_game"));
        this.gameScreen=gameScreen;
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        setHeightProportion(HEIGHT);
        setBottom(BOTTOM);
    }

    @Override
    public void action() {
        gameScreen.startNewGame();
    }
}
