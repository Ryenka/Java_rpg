package juegoRpg;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.control.Button;

public class JavaFxPant extends Application {
    @Override
    public void start(Stage primaryStage) {

        Button btnStartGame = new Button("Start Game");
        btnStartGame.setOnAction(event -> startGame());

        StackPane root = new StackPane();
        root.getChildren().add(btnStartGame);

        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Rpg en java");
        primaryStage.show();
    }
    private void startGame() {
        new Thread(() -> {
            com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration config =
                    new com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration();
            config.setTitle("Rpg en java");
            new com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application(new RpgJuego(), config);
        }).start();
    }
    public static void launchApp(String[] args) {
        launch(args);
    }

}
