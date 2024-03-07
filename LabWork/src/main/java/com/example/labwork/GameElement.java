package com.example.labwork;

import javafx.scene.shape.Circle;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import static com.example.labwork.GameRules.*;

public class GameElement {
    private Circle circle;
    private double speed;
    private boolean isHorizontal;
    private final Controller controller;

    public GameElement(Circle circle, boolean isHorizontal, Controller controller) {
        this.circle = circle;
        this.isHorizontal = isHorizontal;
        speed = calculateSpeed(circle);
        this.controller = controller;
    }

    private double calculateSpeed(Circle circle) {
        return ((isHorizontal ? SMALL_MULTY : BIG_MULTY) / circle.getRadius());
    }

    public void move() {
        GameDetection gameDetection;
        if (isHorizontal) {
            gameDetection = moveHorizontal();
        }
        else {
            gameDetection = moveVertical();
        }
        if (gameDetection != null) {
            if (gameDetection.isTargetIntersection()) {
                controller.addScore();
            }
            controller.deleteGameElementAnywhere(gameDetection.getGameElement());
        }
    }

    private GameDetection moveHorizontal() {
        circle.setCenterX(circle.getCenterX() + speed);
        if (intersectionRight()) {
            return new GameDetection(false, this);
        }
        return null;
    }

    private GameDetection moveVertical() {
        circle.setCenterY(circle.getCenterY() + speed);
        if (intersectionTop() || intersectionBottom()) speed *= -1;
        GameElement resultIntersection = intersectionElements();
        if (resultIntersection != null) {
            return new GameDetection(true, resultIntersection);
        }
        return null;
    }

    private GameElement intersectionElements() {
        List<GameElement> poolBullets = controller.getPoolBullets().getPool();
        ListIterator<GameElement> iterator = poolBullets.listIterator();
        while(iterator.hasNext()) {
            GameElement element = iterator.next();
            if (checkIfNear(element.circle) && circle.intersects(element.circle.getBoundsInParent())) {
                return element;
            }
        }
        return null;
    }

    private boolean checkIfNear(Circle bulletCircle) {
        return (checkLeft(bulletCircle) && checkUnder(bulletCircle) ||
                checkLeft(bulletCircle) && checkAbove(bulletCircle) ||
                checkRight(bulletCircle) && checkUnder(bulletCircle) ||
                checkRight(bulletCircle) && checkAbove(bulletCircle)
        );
    }

    private boolean checkLeft(Circle bulletCircle) {
        return (bulletCircle.getCenterX() + bulletCircle.getRadius() >= circle.getCenterX() - circle.getRadius());
    }
    private boolean checkRight(Circle bulletCircle) {
        return (bulletCircle.getCenterX() - bulletCircle.getRadius() <= circle.getCenterX() + circle.getRadius());
    }
    private boolean checkUnder(Circle bulletCircle) {
        return (bulletCircle.getCenterY() - bulletCircle.getRadius() <= circle.getCenterY() + circle.getRadius());
    }
    private boolean checkAbove(Circle bulletCircle) {
        return (bulletCircle.getCenterY() + bulletCircle.getRadius() >= circle.getCenterY() - circle.getRadius());
    }

    private boolean intersectionBottom() {
        return (circle.getCenterY() + circle.getRadius() >= MAX_HEIGHT);
    }

    private boolean intersectionRight() {
        return (circle.getCenterX() + circle.getRadius() >= MAX_WIDTH);
    }

    private boolean intersectionTop() {
        return (circle.getCenterY() - circle.getRadius() <= MIN_HEIGHT);
    }

    public Circle getCircle() {
        return circle;
    }
}
