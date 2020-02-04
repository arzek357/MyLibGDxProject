package com.mystargame.arzek.pool;

import com.badlogic.gdx.audio.Sound;
import com.mystargame.arzek.base.SpritesPool;
import com.mystargame.arzek.math.Rect;
import com.mystargame.arzek.sprites.EnemyShip;

public class EnemyPool extends SpritesPool<EnemyShip> {
    private BulletPool bulletPool;
    private ExplosionPool explosionPool;
    private Sound sound;
    private Rect worldBounds;

    public EnemyPool(BulletPool bulletPool,ExplosionPool explosionPool,Sound sound, Rect worldBounds) {
        this.bulletPool = bulletPool;
        this.explosionPool = explosionPool;
        this.sound = sound;
        this.worldBounds = worldBounds;
    }

    @Override
    public EnemyShip newObject() {
        return new EnemyShip(bulletPool,explosionPool,sound,worldBounds);
    }
}
