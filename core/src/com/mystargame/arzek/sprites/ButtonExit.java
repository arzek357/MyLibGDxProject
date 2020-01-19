package com.mystargame.arzek.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.mystargame.arzek.base.ScaledButton;
import com.mystargame.arzek.math.Rect;

public class ButtonExit extends ScaledButton {
    public ButtonExit(TextureAtlas atlas) {
        super(atlas.findRegion("btExit"));
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        setHeightProportion(0.15f);
        setRight(worldBounds.getRight()-0.1f);
        setBottom(worldBounds.getBottom()+0.1f);
    }

    @Override
    public void action() {
        Gdx.app.exit();
    }
}
