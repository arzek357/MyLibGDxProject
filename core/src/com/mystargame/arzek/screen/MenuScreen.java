package com.mystargame.arzek.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.mystargame.arzek.base.BaseScreen;
import com.mystargame.arzek.math.Rect;
import com.mystargame.arzek.sprites.Background;
import com.mystargame.arzek.sprites.ButtonExit;
import com.mystargame.arzek.sprites.ButtonPlay;
import com.mystargame.arzek.sprites.Star;

public class MenuScreen extends BaseScreen {
    private Game game;
    private Texture imgBackground;
    private Background background;
    private TextureAtlas atlas;
    private ButtonExit buttonExit;
    private ButtonPlay buttonPlay;
    private Star[] stars;

    public MenuScreen(Game game) {
        this.game = game;
    }

    @Override
    public void show() {
        super.show();
        imgBackground = new Texture("textures/galaxy.jpg");
        background=new Background(new TextureRegion(imgBackground));
        atlas = new TextureAtlas(Gdx.files.internal("textures/menuAtlas.tpack"));
        buttonExit = new ButtonExit(atlas);
        buttonPlay = new ButtonPlay(atlas,game);
        stars=new Star[256];
        for (int i =0;i<stars.length;i++){
            stars[i] = new Star(atlas);
        }
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
        atlas.dispose();
        super.dispose();
    }

    @Override
    public boolean keyTyped(Vector2 touch, char character) {
        return super.keyTyped(touch, character);
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        background.resize(worldBounds);
        buttonExit.resize(worldBounds);
        buttonPlay.resize(worldBounds);
        for (Star star:stars){
            star.resize(worldBounds);
        }
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        buttonExit.touchDown(touch,pointer,button);
        buttonPlay.touchDown(touch,pointer,button);
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        buttonExit.touchUp(touch,pointer,button);
        buttonPlay.touchUp(touch,pointer,button);
        return false;
    }

    private void update(float delta){
        for (Star star:stars){
            star.update(delta);
        }
    }
    private void draw(){
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        background.draw(batch);
        for (Star star:stars){
            star.draw(batch);
        }
        buttonExit.draw(batch);
        buttonPlay.draw(batch);
        batch.end();
    }
}
