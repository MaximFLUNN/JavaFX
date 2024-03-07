package com.example.labwork;

import javafx.scene.shape.Circle;

public class GameDetection {
    private boolean isTargetIntersection;
    private GameElement gameElement;

    public GameDetection(boolean isTargetIntersection, GameElement gameElement) {
        this.isTargetIntersection = isTargetIntersection;
        this.gameElement = gameElement;
    }

    public boolean isTargetIntersection() {
        return isTargetIntersection;
    }

    public GameElement getGameElement() {
        return gameElement;
    }
}
