package juegoRpg;

import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LoadingScreen extends Application {
    @Override
    public void start(Stage primaryStage) {
        // Crear la barra de progreso y el mensaje de carga
        ProgressBar progressBar = new ProgressBar(0);
        Label loadingLabel = new Label("Cargando...");

        // Configurar el diseño de la pantalla de carga
        VBox root = new VBox(10, loadingLabel, progressBar);
        root.setAlignment(Pos.CENTER);

        Scene scene = new Scene(root, 300, 150);

        primaryStage.setTitle("Projecto");
        primaryStage.setScene(scene);
        primaryStage.show();

        // Crear la tarea en segundo plano
        Task<Void> task = new Task<>() {
            @Override
            protected Void call() throws Exception {
                for (int i = 1; i <= 100; i++) {
                    Thread.sleep(50); // Simula la carga
                    updateProgress(i, 100);
                }
                return null;
            }
        };

        // Actualizar la barra de progreso y el mensaje
        progressBar.progressProperty().bind(task.progressProperty());
        task.setOnSucceeded(e -> {
            // Mostrar la nueva ventana con la imagen
            showImageWindow();
            // Cerrar la ventana principal
            primaryStage.close();
        });
        new Thread(task).start();
    }

    private void showImageWindow() {
        Stage imageStage = new Stage();
        Image image = new Image("file:path_to_your_image.jpg"); // ingresa la ruta de tu imagen
        ImageView imageView = new ImageView(image);

        VBox imageRoot = new VBox(imageView);
        imageRoot.setAlignment(Pos.CENTER);

        Scene imageScene = new Scene(imageRoot, 400, 300);
        imageStage.setTitle("Projecto Imagen Cargada");
        imageStage.setScene(imageScene);
        imageStage.show();
    }
}
