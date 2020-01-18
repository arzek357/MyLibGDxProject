package com.mystargame.arzek.sprites;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mystargame.arzek.base.Sprite;
import com.mystargame.arzek.math.Rect;

public class Background extends Sprite {

    public Background(TextureRegion region) {
        super(region);
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(worldBounds.getHeight());
        pos.set(worldBounds.pos);
    }
}

