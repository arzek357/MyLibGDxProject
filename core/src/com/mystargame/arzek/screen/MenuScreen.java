package com.mystargame.arzek.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.mystargame.arzek.base.BaseScreen;
import com.mystargame.arzek.math.Rect;
import com.mystargame.arzek.sprites.Background;
import com.mystargame.arzek.sprites.Logo;

public class MenuScreen extends BaseScreen {
    private Texture imgBackground, imgLogo;
    private Background background;
    private Logo logo;

    @Override
    public void show() {
        super.show();
        imgBackground = new Texture("galaxy.jpg");
        imgLogo = new Texture("badlogic.jpg");
        logo = new Logo(new TextureRegion(imgLogo));
        background=new Background(new TextureRegion(imgBackground));
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        draw();
    }
    @Override
    public void dispose() {
        imgBackground.dispose();
        imgLogo.dispose();
        super.dispose();
    }

    @Override
    public boolean keyTyped(Vector2 touch, char character) {
        logo.keyTyped(touch,character);
        return super.keyTyped(touch, character);
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        background.resize(worldBounds);
        logo.resize(worldBounds);
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        logo.touchDown(touch, pointer, button);
        return super.touchDown(touch, pointer, button);
    }
    private void update(float delta){
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        logo.update(delta);
    }
    private void draw(){
        batch.begin();
        background.draw(batch);
        logo.draw(batch);
        batch.end();
    }
}
