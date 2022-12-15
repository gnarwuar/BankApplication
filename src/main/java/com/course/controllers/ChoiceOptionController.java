package com.course.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.stage.Stage;

import java.io.IOException;

public class ChoiceOptionController {
    @FXML
    private Stage stage;
    private Scene scene;
    @FXML
    private RadioButton CreditLineRadioButton;

    @FXML
    private RadioButton EarlyPayRadioButton;

    @FXML
    private RadioButton MonthPayRadioButton;

    @FXML
    private Label labelBank;

    @FXML
    void ContinueButton(ActionEvent event) throws IOException {
        if (CreditLineRadioButton.isSelected()) {
            FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("CreditLine.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(fxmlLoader.load());
        } else if (EarlyPayRadioButton.isSelected()) {
            FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("EarlyLoanPay.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(fxmlLoader.load());
        } else if (MonthPayRadioButton.isSelected()) {
            FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("MonthPay.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(fxmlLoader.load());
        } else {
            System.out.println("Не вибарано нічого");
            return;
        }
        stage.setScene(scene);
    }

    @FXML
    void ReturnToMainMenu(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("bankOptions.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        try {
            scene = new Scene(fxmlLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setScene(scene);
    }

}
