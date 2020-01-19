package com.mystargame.arzek.sprites;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.mystargame.arzek.base.ScaledButton;
import com.mystargame.arzek.math.Rect;
import com.mystargame.arzek.screen.GameScreen;

public class ButtonPlay extends ScaledButton {
    private Game game;
    public ButtonPlay(TextureAtlas atlas, Game game) {
        super(atlas.findRegion("btPlay"));
        this.game=game;
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        setHeightProportion(0.2f);
        setBottom(worldBounds.getBottom()+0.1f);
        setLeft(worldBounds.getLeft()+0.1f);
    }

    @Override
    public void action() {
        game.setScreen(new GameScreen());
    }
}
