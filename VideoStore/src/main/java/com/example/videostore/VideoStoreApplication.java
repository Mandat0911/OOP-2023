package com.example.videostore;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


public class VideoStoreApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(VideoStoreApplication.class.getResource("login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 650, 425);
        stage.setMaximized(false);
        stage.setResizable(false);
        stage.setTitle("Genie Video Store");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) throws IOException {
        launch();
    }
}
