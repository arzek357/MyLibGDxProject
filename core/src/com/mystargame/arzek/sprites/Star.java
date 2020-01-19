package com.mystargame.arzek.sprites;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.mystargame.arzek.base.Sprite;
import com.mystargame.arzek.math.Rect;
import com.mystargame.arzek.math.Rnd;

public class Star extends Sprite {
    private float starAnimateTimer;
    private float starAnimateInterval;
    private float height;
    private Vector2 v;
    private Rect worldBounds;
    public Star(TextureAtlas atlas) {
        super(atlas.findRegion("star"));
        float vx = Rnd.nextFloat(-0.005f,0.005f);
        float vy = Rnd.nextFloat(-0.2f,-0.01f);
        v=new Vector2(vx,vy);
        starAnimateInterval = 1f;
        height=0.01f;
        starAnimateTimer = Rnd.nextFloat(0f,1f);
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        this.worldBounds=worldBounds;
        float posx = Rnd.nextFloat(worldBounds.getLeft(),worldBounds.getRight());
        float posy = Rnd.nextFloat(worldBounds.getBottom(),worldBounds.getTop());
        pos.set(posx,posy);
        setHeightProportion(height);
    }

    @Override
    public void update(float delta) {
        pos.mulAdd(v,delta);
        checkBounds();
        height+=0.00005f;
        setHeightProportion(height);
        starAnimateTimer+=delta;
        if(starAnimateTimer>starAnimateInterval){
            starAnimateTimer=0f;
            height=0.01f;
        }
    }
    private void checkBounds(){
        if(getRight()<worldBounds.getLeft()) setLeft(worldBounds.getRight());
        if(getLeft()>worldBounds.getRight()) setRight(worldBounds.getLeft());
        if(getTop()<worldBounds.getBottom()) setBottom(worldBounds.getTop());
        if(getBottom()>worldBounds.getTop()) setTop(worldBounds.getBottom());
    }
}
