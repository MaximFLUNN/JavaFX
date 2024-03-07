package com.example.labwork;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class GamePool {
    private List<GameElement> pool;

    public GamePool() {
        pool = new CopyOnWriteArrayList<>();
    }

    public void add(GameElement element) {
        pool.add(element);
    }

    public void remove(GameElement element) {
        pool.remove(element);
    }

    public void move() {
        Iterator<GameElement> iterator = pool.iterator();
        while (iterator.hasNext()) {
            GameElement gameElement = iterator.next();
            gameElement.move();
        }
    }

    public List<GameElement> getPool() {
        return pool;
    }
}
