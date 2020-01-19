package com.mystargame.arzek.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.mystargame.arzek.base.BaseScreen;
import com.mystargame.arzek.math.Rect;
import com.mystargame.arzek.pool.BulletPool;
import com.mystargame.arzek.sprites.Background;
import com.mystargame.arzek.sprites.MainShip;
import com.mystargame.arzek.sprites.Star;

public class GameScreen extends BaseScreen {
    private Texture imgBackground;
    private Background background;
    private TextureAtlas atlas;
    private Star[] stars;
    private MainShip mainShip;
    private BulletPool bulletPool;
    @Override
    public void show() {
        super.show();
        imgBackground = new Texture("textures/galaxy.jpg");
        background=new Background(new TextureRegion(imgBackground));
        atlas = new TextureAtlas(Gdx.files.internal("textures/mainAtlas.tpack"));
        stars=new Star[128];
        for (int i =0;i<stars.length;i++){
            stars[i] = new Star(atlas);
        }
        bulletPool=new BulletPool();
        mainShip = new MainShip(atlas,bulletPool);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        freeAllDestroyed();
        draw();
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        background.resize(worldBounds);
        for (Star star:stars){
            star.resize(worldBounds);
        }
        mainShip.resize(worldBounds);
    }

    @Override
    public void dispose() {
        imgBackground.dispose();
        atlas.dispose();
        bulletPool.dispose();
        super.dispose();
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        mainShip.touchDown(touch, pointer, button);
        return super.touchDown(touch, pointer, button);
    }
    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        mainShip.touchUp(touch, pointer, button);
        return super.touchUp(touch, pointer, button);
    }
    @Override
    public boolean keyDown(int keycode) {
        mainShip.keyDown(keycode);
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        mainShip.keyUp(keycode);
        return false;
    }
    private void update(float delta){
        bulletPool.updateActiveSprites(delta);
        for (Star star:stars){
            star.update(delta);
        }
        mainShip.update(delta);
    }
    private void draw(){
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        background.draw(batch);
        for (Star star:stars){
            star.draw(batch);
        }
        mainShip.draw(batch);
        bulletPool.drawActiveSprites(batch);
        batch.end();
    }
    private void freeAllDestroyed(){
        bulletPool.freeAllDestroyedActiveObjects();
    }
}
