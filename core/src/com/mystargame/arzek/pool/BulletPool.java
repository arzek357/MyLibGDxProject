package com.mystargame.arzek.pool;

import com.mystargame.arzek.base.SpritesPool;
import com.mystargame.arzek.sprites.Bullet;

public class BulletPool extends SpritesPool<Bullet> {
    @Override
    public Bullet newObject() {
        return new Bullet();
    }
}
