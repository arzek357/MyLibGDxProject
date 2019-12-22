package com.mystargame.arzek.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.mystargame.arzek.base.BaseScreen;

public class MenuScreen extends BaseScreen {

    private Texture img;
    private Texture img2;
    private Vector2 pos;
    private Vector2 click;

    @Override
    public void show() {
        super.show();
        pos = new Vector2();
        click = new Vector2();
        img = new Texture("galaxy.jpg");
        img2 = new Texture("badlogic.jpg");
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(img, 0, 0);
        batch.draw(img2,pos.x,pos.y,100,100);
        batch.end();
        moveLogoTouch();
    }
    @Override
    public void dispose() {
        img.dispose();
        img2.dispose();
        super.dispose();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        click.set(screenX,Gdx.graphics.getHeight()-screenY);
        return false;
    }
    private void moveLogoTouch(){
        if (!pos.equals(click)){
            Vector2 move = click.cpy().sub(pos);
            move=move.nor();
            pos.add(Math.round(move.x),Math.round(move.y));
        }
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }
}
