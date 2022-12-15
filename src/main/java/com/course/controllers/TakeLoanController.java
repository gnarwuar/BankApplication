package com.course.controllers;

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

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class TakeLoanController implements Initializable {
    @FXML
    private Stage stage;
    private Scene scene;

    @FXML
    public void ContinueButton(ActionEvent event) {

        if (Choicebox.getValue() == null) {
            System.out.println("Ще не вибарно жодного банку");
        } else {

            RecordInTheDatabase(Choicebox.getValue());

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

    private void RecordInTheDatabase(Loan loan) {
        String checkquery =  "SELECT * FROM " + Const.USER_LOANS_TABLE + " WHERE " + Const.USER_LOANS_BANK_ID + " = " + User.getBankID()
                + " AND  " + Const.USER_LOANS_NAME + " = '" + loan.getLoan_name() + "' ;";

        DatabaseHandler dataBaseHandler = new DatabaseHandler();

        try {
            Statement statement = dataBaseHandler.getDbConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(checkquery);

            if (resultSet.next()) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Warning");
                alert.setHeaderText("Даний кредит вже обрано" );
                alert.showAndWait();
                return;
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        String query = "INSERT INTO " + Const.USER_LOANS_TABLE + " (" +
                Const.LOANS_USERS_ID + " , " + Const.USER_LOANS_BANK_ID + " , " + Const.USER_LOANS_NAME + " , " +
                Const.USER_LOANS_SUM + " , " + Const.USER_LOANS_DURATION + " , " + Const.USER_LOANS_PERCENT + " , " +
                Const.USER_LOANS_MONT_PAY + ") "
                + " VALUES (" + User.getID() + " , " + User.getBankID() + " , '" + loan.getLoan_name() + "' , " +
                loan.getSum() + " , " + loan.getDuration() + " , " + loan.getPercents() + " , " + loan.getMonth_pay() + ");";


        try {
            Statement statement = dataBaseHandler.getDbConnection().createStatement();
            statement.executeUpdate(query);
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
        List<Loan> loans = new ArrayList<>();
        loans = accessToTheDatabase(loans);
        Choicebox.getItems().addAll(loans);
        Choicebox.setOnAction(this::getBank);
    }

    public void getBank(ActionEvent event) {
        String loan_name = Choicebox.getValue().getLoan_name();
        String text = "Ви обрали - " + loan_name;
        labelBank.setText(text);
    }

    private List<Loan> accessToTheDatabase(List<Loan> loans) {
        String query = "SELECT * FROM " + Const.LOANS_TABLE + " WHERE " + Const.LOANS_BANK_ID + " = " + User.getBankID() + " ;";

        DatabaseHandler dataBaseHandler = new DatabaseHandler();

        try {
            Statement statement = dataBaseHandler.getDbConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            Loan loan;
            String loan_name;
            double sum;
            int duration;
            double percents;


            while (resultSet.next()) {
                sum = resultSet.getDouble(Const.LOANS_SUM);
                duration = resultSet.getInt(Const.LOANS_DURATION);
                percents = resultSet.getDouble(Const.LOANS_PERCENT);
                loan_name = resultSet.getString(Const.LOANS_NAME);
                loan = new Loan(loan_name, sum, duration, percents);
                loans.add(loan);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return loans;
    }
}
