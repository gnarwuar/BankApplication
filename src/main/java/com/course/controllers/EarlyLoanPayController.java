package com.course.controllers;


import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import Banks.Loan;
import Database.Const;
import Database.DatabaseHandler;
import UserData.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;

import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class EarlyLoanPayController implements Initializable {

    @FXML
    private Stage stage;
    private Scene scene;
    private List<Loan> loan = new ArrayList<>();

    private void DeleteLoan(Loan loan) {
        String checkquery =  "DELETE  FROM " + Const.USER_LOANS_TABLE + " WHERE " + Const.USER_LOANS_BANK_ID + " = " + User.getBankID()
                + " AND  "+ Const.USERS_ID+ " = " + User.getID() +" AND  " + Const.USER_LOANS_NAME + " = '" + loan.getLoan_name() + "' ;";
        DatabaseHandler dataBaseHandler = new DatabaseHandler();
        try {
            Statement statement = dataBaseHandler.getDbConnection().createStatement();
            statement.executeUpdate(checkquery);

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    @FXML
    public void ReturnToMainMenu(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("bankOptions.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
    }

    @FXML
    private ChoiceBox<Loan> Choicebox;

    @FXML
    private Label labelBank;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        accessToTheDatabase(loan);
        Choicebox.getItems().addAll(loan);
        Choicebox.setOnAction(this::getBank);
    }

    public void getBank(ActionEvent event){
        if(Choicebox.getValue()==null){
            labelBank.setText("Нема кредитів");
        }
        else{
            String nameBank = Choicebox.getValue().getLoan_name();
            String text = "Ви обрали - " + nameBank;
            labelBank.setText(text);
        }

    }

    private void accessToTheDatabase(List<Loan> loans) {
        String query = "SELECT * FROM " + Const.USER_LOANS_TABLE + " WHERE " + Const.USER_LOANS_BANK_ID + " = " + User.getBankID() +
                " AND "+Const.LOANS_USERS_ID + " = " + User.getID();

        DatabaseHandler dataBaseHandler = new DatabaseHandler();

        try {
            Statement statement = dataBaseHandler.getDbConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            Loan loan_;
            String loan_name;
            double sum;
            int duration;
            double percents;
            while (resultSet.next()) {
                sum = resultSet.getDouble(Const.LOANS_SUM);
                duration = resultSet.getInt(Const.LOANS_DURATION);
                percents = resultSet.getDouble(Const.LOANS_PERCENT);
                loan_name = resultSet.getString(Const.LOANS_NAME);
                loan_ = new Loan(loan_name, sum, duration, percents);
                loans.add(loan_);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
    @FXML
    public void EarlyPay(ActionEvent event){

        if(Choicebox.getValue() == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Ще не вибарно жодного кредиту");
            alert.showAndWait();
        }else {
            DeleteLoan(Choicebox.getValue());
            accessToTheDatabase(loan);
            Choicebox.getItems().removeAll(loan);
            loan.removeAll(loan);
            Choicebox.getItems().addAll(loan);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Оплата кредиту");
            alert.setHeaderText("Ви успішно погасили кредит!\n Дякуємо за співпрацю!");
            alert.showAndWait();
        }


    }

}
