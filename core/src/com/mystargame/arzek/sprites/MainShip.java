package com.mystargame.arzek.sprites;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.mystargame.arzek.base.Sprite;
import com.mystargame.arzek.math.Rect;
import com.mystargame.arzek.pool.BulletPool;

public class MainShip extends Sprite {
    private static final int INVALID_POINTER=-1;

    BulletPool bulletPool;
    private TextureRegion bulletRegion;
    private float bulletHeight;
    private Vector2 bulletV;
    private int damage;

    private Vector2 v;
    private Vector2 v0;

    private boolean pressedLeft;
    private boolean pressedRight;

    private int leftPointer = INVALID_POINTER;
    private int rightPointer = INVALID_POINTER;

    private Rect worldBounds;
    public MainShip(TextureAtlas atlas, BulletPool bulletPool) {
        super(atlas.findRegion("main_ship"),1,2,2);
        v = new Vector2();
        bulletV=new Vector2(0,0.5f);
        bulletHeight=0.01f;
        damage=1;
        this.bulletPool=bulletPool;
        bulletRegion=atlas.findRegion("bulletMainShip");
        v0 = new Vector2(0.4f,0f);
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
        pos.mulAdd(v,delta);
        if(getRight()>worldBounds.getRight()) stop();
        if(getLeft()<worldBounds.getLeft()) stop();
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
            case Input.Keys.UP:
                shoot();
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
    private void moveRight(){
        v.set(v0);
    }
    private void moveLeft(){
        v.set(v0).rotate(180);
    }
    private void stop(){
        v.setZero();
    }
    private void shoot(){
        Bullet bullet = bulletPool.obtain();
        bullet.set(this,bulletRegion,pos,bulletV,bulletHeight,worldBounds,damage);
    }
}
