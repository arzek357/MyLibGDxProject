package com.mystargame.arzek.sprites;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.mystargame.arzek.base.Ship;
import com.mystargame.arzek.math.Rect;
import com.mystargame.arzek.pool.BulletPool;
import com.mystargame.arzek.pool.ExplosionPool;

public class MainShip extends Ship {
    private static final int HP = 100;
    private static final int INVALID_POINTER=-1;


    private boolean pressedLeft;
    private boolean pressedRight;

    private int leftPointer = INVALID_POINTER;
    private int rightPointer = INVALID_POINTER;


    public MainShip(TextureAtlas atlas, BulletPool bulletPool, ExplosionPool explosionPool,Sound shootSound) {
        super(atlas.findRegion("main_ship"),1,2,2);
        this.bulletPool=bulletPool;
        this.explosionPool = explosionPool;
        this.shootSound=shootSound;
        this.v = new Vector2();
        this.bulletV=new Vector2(0,0.5f);
        this.bulletHeight=0.01f;
        this.damage=1;
        this.bulletRegion=atlas.findRegion("bulletMainShip");
        this.v0 = new Vector2(0.4f,0f);
        this.reloadInterval = 0.25f;
        this.reloadTimer=0f;
        this.hp=HP;
    }
    public void startNewGame(){
        this.hp = HP;
        this.pressedRight = false;
        this.pressedLeft=false;
        this.leftPointer = INVALID_POINTER;
        this.rightPointer = INVALID_POINTER;
        stop();
        this.pos.x = worldBounds.pos.x;
        flushDestroy();
    }
    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        this.worldBounds=worldBounds;
        setHeightProportion(0.15f);
        setBottom(worldBounds.getBottom()+0.05f);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        reloadTimer +=delta;
        if(reloadTimer>reloadInterval){
            reloadTimer=0f;
            shoot();
        }
        if(getRight()>worldBounds.getRight()) stop();
        if(getLeft()<worldBounds.getLeft()) stop();
    }

    public int getHP() {
        return hp;
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        if (touch.x<worldBounds.pos.x){
            if(leftPointer!=INVALID_POINTER){
                return false;
            }
            leftPointer = pointer;
            moveLeft();
        }
        else{
            if(rightPointer!=INVALID_POINTER){
                return false;
            }
            rightPointer=pointer;
            moveRight();
        }
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        if (pointer==leftPointer){
            leftPointer = INVALID_POINTER;
            if (rightPointer!=INVALID_POINTER)
                moveRight();
            else
                stop();
        }
        if (pointer==rightPointer){
            rightPointer = INVALID_POINTER;
            if (leftPointer!=INVALID_POINTER)
                moveLeft();
            else
                stop();
        }
        return false;
    }
    public boolean keyDown(int keycode) {
        switch (keycode){
            case Input.Keys.RIGHT:
            case Input.Keys.D:
            moveRight();
            pressedRight = true;
            break;
            case Input.Keys.LEFT:
            case Input.Keys.A:
            moveLeft();
            pressedLeft = true;
            break;
        }
        return false;
    }

    public boolean keyUp(int keycode) {
        switch (keycode){
            case Input.Keys.RIGHT:
            case Input.Keys.D:
                pressedRight = false;
                if (pressedLeft)
                    moveLeft();
                else
                    stop();
                break;
            case Input.Keys.LEFT:
            case Input.Keys.A:
                pressedLeft = false;
                if (pressedRight)
                    moveRight();
                else
                    stop();
                break;
        }
        return false;
    }
    public boolean isBulletCollision(Rect bullet){
        return !(bullet.getRight()<getLeft()||bullet.getLeft()>getRight()||bullet.getBottom()>pos.y||bullet.getTop()<getBottom());
    }
    private void moveRight(){
        v.set(v0);
    }
    private void moveLeft(){
        v.set(v0).rotate(180);
    }
    private void stop(){
        v.setZero();
    }
}
