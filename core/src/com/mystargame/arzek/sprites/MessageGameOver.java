package com.mystargame.arzek.sprites;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.mystargame.arzek.base.Sprite;
import com.mystargame.arzek.math.Rect;

public class MessageGameOver extends Sprite {

    private static final float HEIGHT = 0.08f;
    private static final float TOP = 0.14f;

    public MessageGameOver(TextureAtlas atlas) {
        super(atlas.findRegion("message_game_over"));
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        setHeightProportion(HEIGHT);
        setTop(TOP);
    }
}
