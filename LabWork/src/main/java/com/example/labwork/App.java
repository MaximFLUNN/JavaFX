package com.example.labwork;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Set;

public class App extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("view.fxml"));
        Parent root = fxmlLoader.load();
        Controller controller = fxmlLoader.getController();
        Scene scene = new Scene(root, 900, 600);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
        controller.settingInitialSettings();
    }

    public static void main(String[] args) {
        launch();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            Set<Thread> threadSet = Thread.getAllStackTraces().keySet();
            for (Thread thread : threadSet) {
                if (thread.getState() == Thread.State.RUNNABLE) {
                    thread.interrupt();
                }
            }
            System.out.println("Все потоки завершены.");
        }));
    }
}