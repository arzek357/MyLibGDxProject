package com.mystargame.arzek.base;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mystargame.arzek.math.Rect;

import java.util.ArrayList;
import java.util.List;

public abstract class SpritesPool<T extends Sprite> {
    private final List<T> activeObjects=new ArrayList<>();

    private final List<T> freeObjects=new ArrayList<>();

    public abstract T newObject();
    public T obtain(){
        T object;
        if(freeObjects.isEmpty())
            object=newObject();
        else
            object=freeObjects.remove(freeObjects.size()-1);
        activeObjects.add(object);
        return object;
    }
    public void updateActiveSprites(float delta){
        for (T item:activeObjects){
            if(!item.isDestroyed()){
                item.update(delta);
            }
        }
    }
    public void drawActiveSprites(SpriteBatch sprite){
        for (T item:activeObjects){
            if(!item.isDestroyed()){
                item.draw(sprite);
            }
        }
    }
    public void freeAllDestroyedActiveObjects(){
        for (int i=0;i<activeObjects.size();i++){
            T item = activeObjects.get(i);
            if(item.isDestroyed()){
                free(item);
                i--;
                item.flushDestroy();
            }
        }
    }
    public void freeAllActiveObjects(){
        freeObjects.addAll(activeObjects);
        activeObjects.clear();
    }
    private void free(T object){
        if (activeObjects.remove(object))
            freeObjects.add(object);
    }

    public List<T> getActiveObjects() {
        return activeObjects;
    }
    public void dispose(){
        activeObjects.clear();
        freeObjects.clear();
    }
}
