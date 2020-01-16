package com.mystargame.arzek.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.mystargame.arzek.base.BaseScreen;

public class MenuScreen extends BaseScreen {
    private static final float OBJECT_SPEED = 5f;
    private Texture imgBackground, imgLogo;
    private Vector2 pos,click;

    @Override
    public void show() {
        super.show();
        pos = new Vector2();
        click = new Vector2();
        imgBackground = new Texture("galaxy.jpg");
        imgLogo = new Texture("badlogic.jpg");
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(imgBackground, 0, 0);
        batch.draw(imgLogo,pos.x,pos.y,100,100);
        batch.end();
        moveLogo();
    }
    @Override
    public void dispose() {
        imgBackground.dispose();
        imgLogo.dispose();
        super.dispose();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        click.set(screenX,Gdx.graphics.getHeight()-screenY);
        System.out.println(screenX+"  "+(Gdx.graphics.getHeight()-screenY));
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

    @Override
    public boolean keyTyped(char character) {
        if (character=='w'||character=='ц'){
            click.add(0,10);}
        else if(character=='s'||character=='ы'){
            click.add(0,-10);
            }
        else if(character=='a'||character=='ф'){
            click.add(-10,0);
        }
        else if(character=='d'||character=='в'){
            click.add(10,0);
        }
        return false;
    }
    private void checkDistanceAndMove(Vector2 move,Vector2 distance){
        if(distance.len()>move.len())
            pos.add(move);
        else
            pos.set(click);
    }
}
