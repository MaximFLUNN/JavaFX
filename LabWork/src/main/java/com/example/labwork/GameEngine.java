package com.example.labwork;

import javafx.application.Platform;

import static com.example.labwork.GameRules.TIME_DELAY;

public class GameEngine extends Thread {
    private final Object key = new Object();
    private Controller controller;

    public GameEngine(Controller controller) {
        this.controller = controller;
    }

    @Override
    public void run() {
        while (true) {
            synchronized (key) {
                while (controller.isPaused() || !controller.isStart()) {
                    try {
                        key.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                try {
                    sleep(TIME_DELAY);
                    Platform.runLater(() -> {
                        controller.getPoolTargets().move();
                        controller.getPoolBullets().move();
                    });
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public void resumeThread() {
        synchronized (key) {
            key.notify();
        }
    }

    public void stopThread() {
        super.interrupt();
    }
}
