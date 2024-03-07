package com.example.labwork;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

import static com.example.labwork.GameRules.*;

public class Controller {
    private boolean isStart;
    private boolean isPaused;
    private int shotsCount;
    private int scoreCount;
    private GamePool poolBullets;
    private GamePool poolTargets;
    private GameEngine gameEngine;
    @FXML
    private Polygon playerPolygon;
    @FXML
    private Circle firstCircle;
    @FXML
    private Circle secondCircle;
    @FXML
    private Button buttonStart;
    @FXML
    private Button buttonPause;
    @FXML
    private Button buttonShot;
    @FXML
    private Label labelScore;
    @FXML
    private Label labelShots;
    @FXML
    private Pane mainPane;

    void settingInitialSettings() {
        buttonStart.setOnMouseClicked(mouseEvent -> {
            eventButtonStart();
        });
        buttonPause.setOnMouseClicked(mouseEvent -> {
            eventButtonPause();
        });
        buttonShot.setOnMouseClicked(mouseEvent -> {
            eventButtonShot();
        });
        isStart = false;
        isPaused = false;
        poolBullets = new GamePool();
        poolTargets = new GamePool();
        poolTargets.add(new GameElement(firstCircle, false, this));
        poolTargets.add(new GameElement(secondCircle, false, this));
    }
    private void firstStart() {
        isStart = true;
        gameEngine = new GameEngine(this);
        gameEngine.start();
    }
    void eventButtonStart() {
        if (!isStart) {
            firstStart();
        }
        shotsCount = 0;
        scoreCount = 0;
        buttonStart.setText("Restart");
        updateShots();
        updateScore();
        deleteAllElementsAnywhere();
    }
    void eventButtonPause() {
        if (!isStart) return;
        isPaused = !isPaused;
        buttonPause.setText((isPaused ? "Unpause" : "Pause"));
        if (!isPaused) {
            gameEngine.resumeThread();
        }
    }
    void eventButtonShot() {
        if (isPaused) return;
        GameElement gameElement = new GameElement(
                new Circle(PLAYER_X_POS, PLAYER_Y_POS, BULLET_RADIUS),
                true,
                this
        );
        addGameElementAnywhere(gameElement);
        shotsCount++;
        updateShots();
    }
    void addScore() {
        scoreCount++;
        updateScore();
    }
    void deleteAllElementsAnywhere() {
        poolBullets.getPool().forEach(gameElement -> {
            mainPane.getChildren().remove(gameElement.getCircle());
        });
        poolBullets.getPool().clear();
    }
    void addGameElementAnywhere(GameElement gameElement) {
        poolBullets.add(gameElement);
        mainPane.getChildren().add(gameElement.getCircle());
    }
    void deleteGameElementAnywhere(GameElement gameElement) {
        poolBullets.remove(gameElement);
        mainPane.getChildren().remove(gameElement.getCircle());
    }
    void updateScore() {
        labelScore.setText(String.valueOf(scoreCount));
    }
    void updateShots() {
        labelShots.setText(String.valueOf(shotsCount));
    }

    public boolean isPaused() {
        return isPaused;
    }

    public GamePool getPoolBullets() {
        return poolBullets;
    }

    public GamePool getPoolTargets() {
        return poolTargets;
    }

    public boolean isStart() {
        return isStart;
    }
}
