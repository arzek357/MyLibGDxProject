package com.mystargame.arzek.sprites;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.mystargame.arzek.base.Sprite;
import com.mystargame.arzek.math.Rect;

public class Logo extends Sprite {
    private Vector2 click;
    private static final float OBJECT_SPEED = 0.01f;
    public Logo(TextureRegion region) {
        super(region);
        click = new Vector2();
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(0.15f);
        pos.set(worldBounds.pos);
    }
    @Override
    public void update(float delta) {
        super.update(delta);
        moveLogo();
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        click.set(touch);
        return false;
    }

    @Override
    public boolean keyTyped(Vector2 touch, char character) {
        click.set(touch);
        return false;
    }

    private void moveLogo(){
        if (!pos.equals(click)){
            Vector2 clickCopy = new Vector2(click);
            Vector2 move = clickCopy.sub(pos);
            Vector2 distance = new Vector2(move);
            move=move.setLength(OBJECT_SPEED);
            checkDistanceAndMove(move,distance);
        }
    }
    private void checkDistanceAndMove(Vector2 move,Vector2 distance){
        if(distance.len()>move.len())
            pos.add(move);
        else
            pos.set(click);
    }
}
