package com.example.paints;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;

/**
 * This class was created by Nicole Brown
 * This class starts the Paint program and calls the exit message when closing
 */
public class Paint extends Application {
    @Override
    public void start(Stage stage) throws IOException {


        FXMLLoader fxmlLoader = new FXMLLoader(Paint.class.getResource("Paints-setup.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();

        stage.setOnCloseRequest((WindowEvent w) -> {
            w.consume();
            try {
                new PaintController().bye(w, stage);
            } catch (IOException e) {
                System.out.println("No close");
            }
        });
    }

    public static void main(String[] args) {
        launch();
    }
}