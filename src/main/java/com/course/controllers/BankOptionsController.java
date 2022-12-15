package com.course.controllers;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.stage.Stage;

public class BankOptionsController {
    @FXML
    private Stage stage;
    private Scene scene;

    @FXML
    private RadioButton creditSearchRadioButton;

    @FXML
    private RadioButton takeLoanRadioButton;

    @FXML
    private RadioButton payLoanRadioButton;

    @FXML
    private RadioButton ViewLoanRadioButton;

    @FXML
    public void ReturnToMainMenu(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("hello-view.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
    }

    @FXML
    public void continueButtons(ActionEvent event) throws IOException {

        if (creditSearchRadioButton.isSelected()) {
            FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("SearchCredit.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(fxmlLoader.load());
        } else if (takeLoanRadioButton.isSelected()) {
            FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("Takeloan.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(fxmlLoader.load());
        } else if (payLoanRadioButton.isSelected()) {
            FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("ChoiceOption.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(fxmlLoader.load());
        } else if (ViewLoanRadioButton.isSelected()) {
            FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("AvailableLoans.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(fxmlLoader.load());
        }else {
            System.out.println("Не вибарано нічого");
            return;
        }
        stage.setScene(scene);
    }

}
