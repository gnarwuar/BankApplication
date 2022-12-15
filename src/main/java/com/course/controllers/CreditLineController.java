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
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class CreditLineController implements Initializable {
    @FXML
    private Stage stage;
    private Scene scene;
    @FXML
    private ChoiceBox<Loan> Choicebox;

    @FXML
    private TextField InputLimit;

    @FXML
    public void ContinueButton(ActionEvent event) {

        if (Choicebox.getValue() == null) {
            System.out.println("Ще не вибарно жодного кредиту");
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
        DatabaseHandler dataBaseHandler = new DatabaseHandler();
        double sum = InputSum();
        if (sum > loan.getSum()) {
            loan.setSum(sum);
            String query = "UPDATE " + Const.USER_LOANS_TABLE + " SET " + Const.USER_LOANS_SUM + " = "
                    + loan.getSum() + ", " + Const.USER_LOANS_MONT_PAY + " = " + loan.getMonth_pay() +
                    " WHERE " + Const.USER_LOANS_BANK_ID + " = " + User.getBankID()
                    + " AND  " + Const.USER_LOANS_NAME + " = '" + loan.getLoan_name() + "' ;";
            try {
                Statement statement = dataBaseHandler.getDbConnection().createStatement();
                statement.executeUpdate(query);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Зміна кредитного ліміту");
                alert.setHeaderText("Ваш кредитний ліміт змінено. Тепер він становить " + loan.getSum() + "грн.");
                alert.showAndWait();
                System.out.println(loan);
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Ви не можете зменшити Ваш кредитний ліміт.");
            alert.showAndWait();
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
    public double InputSum() {
        String stringSum = InputLimit.getText().trim();
        if (stringSum.equals("")) {
            System.out.println("Ви нічого не ввели");
            return -1;
        } else {

            return Double.parseDouble(stringSum);
        }


    }

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
        String query = "SELECT * FROM " + Const.USER_LOANS_TABLE + " WHERE " + Const.USER_LOANS_BANK_ID + " = " + User.getBankID() +
                " AND " + Const.LOANS_USERS_ID + " = " + User.getID();

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
