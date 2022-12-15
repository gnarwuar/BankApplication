package com.course.controllers;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;

public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("Authorization.fxml"));
        InputStream iconStream = getClass().getResourceAsStream("/pictures/4-512_23910.png");
        Image image = new Image(iconStream);
        stage.getIcons().add(image);
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("EmpBank");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
        stage.setOnCloseRequest(event -> {
            event.consume();
            logout(stage);
        });
    }
    public void logout(Stage stage){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Вихід");
        alert.setHeaderText("Ви дійно бажаєте вийти з програми?");
        if(alert.showAndWait().get()== ButtonType.OK){
            stage.close();
        }
    }
    public static void main(String[] args) {
        launch(args);
    }
}