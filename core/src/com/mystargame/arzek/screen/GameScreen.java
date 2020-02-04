package com.mystargame.arzek.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;
import com.mystargame.arzek.base.BaseScreen;
import com.mystargame.arzek.base.Font;
import com.mystargame.arzek.math.Rect;
import com.mystargame.arzek.pool.BulletPool;
import com.mystargame.arzek.pool.EnemyPool;
import com.mystargame.arzek.pool.ExplosionPool;
import com.mystargame.arzek.sprites.Background;
import com.mystargame.arzek.sprites.Bullet;
import com.mystargame.arzek.sprites.ButtonNewGame;
import com.mystargame.arzek.sprites.EnemyShip;
import com.mystargame.arzek.sprites.MainShip;
import com.mystargame.arzek.sprites.MessageGameOver;
import com.mystargame.arzek.sprites.Star;
import com.mystargame.arzek.utils.EnemyGenerator;

import java.util.List;

public class GameScreen extends BaseScreen {

    private static final float FONT_PADDING = 0.01f;
    private static final float FONT_SIZE = 0.02f;

    private static final String FRAGS = "Frags: ";
    private static final String HP = "HP: ";
    private static final String LEVEL = "Level: ";

    private enum State {PLAYING,GAME_OVER}

    private Texture imgBackground;
    private Background background;
    private TextureAtlas atlas;
    private Star[] stars;
    private MainShip mainShip;

    private BulletPool bulletPool;
    private EnemyPool enemyPool;
    private ExplosionPool explosionPool;

    private Music music;
    private Sound laserSound;
    private Sound bulletSound;
    private Sound explosionSound;

    private EnemyGenerator enemyGenerator;
    private int frags;

    private State state;

    private MessageGameOver messageGameOver;
    private ButtonNewGame buttonNewGame;

    private Font font;
    private StringBuilder sbFrags;
    private StringBuilder sbHp;
    private StringBuilder sbLevel;
    @Override
    public void show() {
        super.show();
        frags = 0;
        imgBackground = new Texture("textures/galaxy.jpg");
        background=new Background(new TextureRegion(imgBackground));
        atlas = new TextureAtlas(Gdx.files.internal("textures/mainAtlas.tpack"));
        laserSound = Gdx.audio.newSound(Gdx.files.internal("sounds/laser.wav"));
        bulletSound = Gdx.audio.newSound(Gdx.files.internal("sounds/bullet.wav"));
        explosionSound = Gdx.audio.newSound(Gdx.files.internal("sounds/explosion.wav"));
        music = Gdx.audio.newMusic(Gdx.files.internal("sounds/music.mp3"));
        stars=new Star[128];
        for (int i =0;i<stars.length;i++){
            stars[i] = new Star(atlas);
        }
        bulletPool=new BulletPool();
        explosionPool=new ExplosionPool(atlas,explosionSound);
        enemyPool=new EnemyPool(bulletPool,explosionPool,bulletSound,worldBounds);
        mainShip = new MainShip(atlas,bulletPool,explosionPool,laserSound);
        enemyGenerator = new EnemyGenerator(atlas,enemyPool,worldBounds);
        messageGameOver = new MessageGameOver(atlas);
        buttonNewGame = new ButtonNewGame(atlas,this);
        font = new Font("font/font.fnt", "font/font.png");
        sbFrags = new StringBuilder();
        sbHp = new StringBuilder();
        sbLevel = new StringBuilder();
        music.setLooping(true);
        music.play();
        state = State.PLAYING;
    }
    public void startNewGame(){
        enemyGenerator.setLevel(1);
        frags = 0;
        state = State.PLAYING;
        mainShip.startNewGame();
        bulletPool.freeAllActiveObjects();
        explosionPool.freeAllActiveObjects();
        enemyPool.freeAllActiveObjects();
    }
    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        checkCollisions();
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
        messageGameOver.resize(worldBounds);
        buttonNewGame.resize(worldBounds);
        font.setSize(FONT_SIZE);
    }

    @Override
    public void dispose() {
        imgBackground.dispose();
        atlas.dispose();
        bulletPool.dispose();
        explosionPool.dispose();
        enemyPool.dispose();
        super.dispose();
        music.dispose();
        laserSound.dispose();
        bulletSound.dispose();
        explosionSound.dispose();
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        if (state == State.PLAYING){
            mainShip.touchDown(touch, pointer, button);
        } else if (state == State.GAME_OVER){
            buttonNewGame.touchDown(touch, pointer, button);
        }
        return false;
    }
    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        if (state == State.PLAYING){
            mainShip.touchUp(touch, pointer, button);
        } else if (state == State.GAME_OVER){
            buttonNewGame.touchUp(touch, pointer, button);
        }
        return false;
    }
    @Override
    public boolean keyDown(int keycode) {
        if (state == State.PLAYING){
            mainShip.keyDown(keycode);
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (state == State.PLAYING){
            mainShip.keyUp(keycode);
        }
        return false;
    }
    private void update(float delta){
        for (Star star:stars){
            star.update(delta);
        }
        explosionPool.updateActiveSprites(delta);
        if (state == State.PLAYING){
        mainShip.update(delta);
        bulletPool.updateActiveSprites(delta);
        enemyPool.updateActiveSprites(delta);
        enemyGenerator.generate(delta,frags);
        }
    }
    private void checkCollisions(){
        if (state != State.PLAYING){
            return;
        }
        List<EnemyShip> enemyShipList = enemyPool.getActiveObjects();
        for (EnemyShip enemyShip : enemyShipList){
            float minDist = enemyShip.getHalfWidth()+mainShip.getHalfWidth();
            if (enemyShip.pos.dst(mainShip.pos)<minDist){
                enemyShip.destroy();
                frags++;
                mainShip.damage(enemyShip.getDamage());
            }
        }
        List<Bullet> bulletList = bulletPool.getActiveObjects();
        for (Bullet bullet:bulletList){
            if (bullet.isDestroyed()){
                continue;
            }
            if (bullet.getOwner() !=mainShip){
                if (mainShip.isBulletCollision(bullet)){
                    mainShip.damage(bullet.getDamage());
                    bullet.destroy();
                }
            } else {
                for (EnemyShip enemyShip : enemyShipList) {
                    if (enemyShip.isBulletCollision(bullet)) {
                        enemyShip.damage(bullet.getDamage());
                        if (enemyShip.isDestroyed()){
                            frags++;
                        }
                        bullet.destroy();
                    }
                }
            }
        }
        if (mainShip.isDestroyed()){
            state = State.GAME_OVER;
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
        explosionPool.drawActiveSprites(batch);
        if (state == State.PLAYING){
            mainShip.draw(batch);
            bulletPool.drawActiveSprites(batch);
            enemyPool.drawActiveSprites(batch);
        } else if (state == State.GAME_OVER){
            messageGameOver.draw(batch);
            buttonNewGame.draw(batch);
        }
        printInfo();
        batch.end();
    }
    private void freeAllDestroyed(){
        bulletPool.freeAllDestroyedActiveObjects();
        enemyPool.freeAllDestroyedActiveObjects();
        explosionPool.freeAllDestroyedActiveObjects();
    }
    private void printInfo(){
        sbFrags.setLength(0);
        sbHp.setLength(0);
        sbLevel.setLength(0);
        font.draw(batch,sbFrags.append(FRAGS).append(frags),worldBounds.getLeft()+FONT_PADDING,worldBounds.getTop()-FONT_PADDING);
        font.draw(batch,sbHp.append(HP).append(mainShip.getHP()),worldBounds.pos.x,worldBounds.getTop()-FONT_PADDING, Align.center);
        font.draw(batch,sbLevel.append(LEVEL).append(enemyGenerator.getLevel()),worldBounds.getRight() - FONT_PADDING,worldBounds.getTop()-FONT_PADDING,Align.right);
    }
}
