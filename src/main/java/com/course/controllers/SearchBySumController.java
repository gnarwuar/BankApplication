package com.course.controllers;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Banks.Loan;
import Database.Const;
import Database.DatabaseHandler;
import UserData.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class SearchBySumController {

    @FXML
    private Stage stage;
    private Scene scene;

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

    @FXML
    private TextField SearchField;


    @FXML
    private ListView<Loan> myListview;
    private List<Loan> loans = new ArrayList<>();

    @FXML
    public void InputName(ActionEvent event) {
        String stringSum = SearchField.getText().trim();
        if (stringSum.equals("")) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Warning");
            alert.setHeaderText("Ви нічого не ввели" );
            alert.showAndWait();

            return;
        }
        double sum = Double.parseDouble(stringSum);

        for (Loan loan: loans) {
            myListview.getItems().remove(loan);
        }
        loans.removeAll(loans);

        SearchTheDatabase(loans, sum);
        if(loans.size() == 0){
            for (Loan loan: loans) {
                myListview.getItems().remove(loan);
            }
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Warning");
            alert.setHeaderText("Таких кредитів немає" );
            alert.showAndWait();
        }
        else{
            myListview.getItems().addAll(loans);
        }
    }

    private void SearchTheDatabase(List<Loan> loans, double sum) {

        String query = "SELECT * FROM " + Const.LOANS_TABLE + " WHERE " + Const.BANKS_ID + " = " + User.getBankID()
                + " AND  " + Const.LOANS_SUM + " = " + sum + " ;";

        DatabaseHandler dataBaseHandler = new DatabaseHandler();

        try {
            Statement statement = dataBaseHandler.getDbConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            Loan loan;
            String loan_name;
            double sum1;
            int duration;
            double percents;
            double month_pay;
            while (resultSet.next()) {
                sum1 = resultSet.getDouble(Const.LOANS_SUM);
                duration = resultSet.getInt(Const.LOANS_DURATION);
                percents = resultSet.getDouble(Const.LOANS_PERCENT);
                loan_name = resultSet.getString(Const.LOANS_NAME);
                loan = new Loan(loan_name, sum1, duration, percents);
                loans.add(loan);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
