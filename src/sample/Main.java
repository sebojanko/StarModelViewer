package sample;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    UICreator ui;

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) {
        ui = new UICreator(primaryStage);
    }
}
