package juegoRpg;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.control.Button;

public class JavaFxPant extends Application {
       @Override
    public void start(Stage primaryStage) {

        // Crear el botón
        Button btnStartGame = new Button("Start Game");
        btnStartGame.setOnAction(event -> {
            startGame();
            primaryStage.close();  // Cierra la ventana de JavaFX
        });

        // Crear el StackPane
        StackPane root = new StackPane();

        // Crear la imagen de fondo
        Image backgroundImage = new Image(getClass().getResource("/assets/Textura/Background_Inicio/DarkestDungeon.png").toExternalForm());
        BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, false, true);
        BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
        root.setBackground(new Background(background));

        // Crear la imagen "Name_RPG" y ajustar su tamaño
        Image imageToShow = new Image(getClass().getResourceAsStream("/assets/Textura/Background_Inicio/Name_RPG.png"));
        double desiredWidth = 400;  // Ancho deseado
        double desiredHeight = 200; // Alto deseado
        ImageView imageView = new ImageView(imageToShow);
        imageView.setFitWidth(desiredWidth);
        imageView.setFitHeight(desiredHeight);

        // Añadir la imagen "Name_RPG" al StackPane, posicionándola más arriba
        StackPane.setAlignment(imageView, Pos.TOP_CENTER); // Alinear la imagen en la parte superior central
        root.getChildren().add(imageView);

        // Añadir el botón al StackPane
        root.getChildren().add(btnStartGame);

        // Crear la escena
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Dungeon Quest");

        // Agregar icono a la pestaña
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/assets/Textura/Background_Inicio/Icon.png")));

        primaryStage.show();
    }

    private void startGame() {
        new Thread(() -> {
            com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration config =
                    new com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration();
            config.setTitle("Dungeon Quest");

            new com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application(new RpgJuego(), config);
        }).start();
    }

    public static void launchApp(String[] args) {
        launch(args);
    }

}
