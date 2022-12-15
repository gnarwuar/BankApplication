package com.course.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Banks.Bank;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

public class SearchCreditController{

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;
    @FXML
    private int choice;
    @FXML
    private RadioButton SearchByDurationButton;

    @FXML
    private RadioButton SearchByNameButton;

    @FXML
    private RadioButton SearchByPercentsButton;

    @FXML
    private RadioButton SearchBySumButton;


    @FXML
    public Bank bank;
    private Stage stage;
    private Scene scene;

    @FXML
    void SearchChoice(ActionEvent event) throws IOException {

        if (SearchByNameButton.isSelected()) {
            FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("SearchInfo.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(fxmlLoader.load());
        } else if (SearchBySumButton.isSelected()) {

            FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("SearchBySum.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(fxmlLoader.load());

        } else if (SearchByDurationButton.isSelected()) {

            FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("SearchDuration.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(fxmlLoader.load());

        } else if (SearchByPercentsButton.isSelected()) {
            FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("SearchPercent.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(fxmlLoader.load());
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Warning");
            alert.setHeaderText("Не вибрано нічого" );
            alert.showAndWait();
            return;
        }
        stage.setScene(scene);
    }

    @FXML
    void ReturnToMainMenu(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("bankOptions.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
    }
}
