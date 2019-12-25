package com.mystargame.arzek.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.mystargame.arzek.base.BaseScreen;

public class MenuScreen extends BaseScreen {
    private static final float OBJECT_SPEED = 5f;
    private Texture img,img2;
    private Vector2 pos,click,lastMove;

    @Override
    public void show() {
        super.show();
        pos = new Vector2();
        lastMove = new Vector2();
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
        moveLogo();
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
        System.out.println(screenX+"  "+(Gdx.graphics.getHeight()-screenY));
        lastMove.set(0,0);
        return false;
    }
    private void moveLogo(){
        if (!pos.equals(click)){
            Vector2 clickCopy = new Vector2(click);
            Vector2 move = clickCopy.sub(pos);
            move=move.setLength(OBJECT_SPEED);
            checkPosition(move);
            lastMove.set(move);
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
    private void checkPosition(Vector2 move){
        if((lastMove.x/move.x>0||lastMove.y/move.y>0)||(lastMove.x==0&lastMove.y==0)){
            pos.add(move.x,move.y);
        }
        else{
            pos.set(click);
        }
    }
}
