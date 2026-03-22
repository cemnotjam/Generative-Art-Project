package src;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;

public class GlitchApp extends Application {

    @Override
    public void start(Stage stage) {
        MainView mainView = new MainView();

        Scene scene = new Scene(mainView, 1200, 700);
        scene.getStylesheets().add("file:assets/style.css");

        stage.setTitle("Glitch Art Engine");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}