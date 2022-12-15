package com.course.controllers;


import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import Banks.Bank;
import Database.Const;
import Database.DatabaseHandler;
import UserData.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;

import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class HelloController implements Initializable {

    @FXML
    private Stage stage;
    private Scene scene;

    @FXML
    public void ContinueButton(ActionEvent event){

        if(Choicebox.getValue() == null){
            System.out.println("Ще не вибарно жодного банку");
        }else {

            User.setBankID(Choicebox.getValue().getIdBank());
            User.setNameBank(Choicebox.getValue().getBank_name());

            FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("bankOptions.fxml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            try {
                scene = new Scene(fxmlLoader.load());
            } catch (IOException e) {
                e.printStackTrace();
            }
            stage.setScene(scene);
        }


    }

    @FXML
    public void ReturnToMainMenu(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("Authorization.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
    }

    @FXML
    private ChoiceBox<Bank> Choicebox;

    @FXML
    private Label labelBank;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<Bank> banks = new ArrayList<>();
        banks = accessToTheDatabase(banks);
        Choicebox.getItems().addAll(banks);
        Choicebox.setOnAction(this::getBank);
    }

    public void getBank(ActionEvent event){
        String nameBank = Choicebox.getValue().getBank_name();
        String text = "Ви обрали - " + nameBank;
        labelBank.setText(text);
    }

    private List<Bank> accessToTheDatabase(List<Bank> banks) {
        String query = "SELECT * FROM " + Const.BANKS_TABLE + " WHERE " + Const.BANKS_ID + " ;";

        DatabaseHandler dataBaseHandler = new DatabaseHandler();

        try {
            Statement statement = dataBaseHandler.getDbConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            Bank bank;
            String bank_name;
            int idBank;

            while (resultSet.next()) {
                idBank = resultSet.getInt(Const.BANKS_ID);
                bank_name = resultSet.getString(Const.BANKS_NAME);
                bank = new Bank(idBank, bank_name);
                banks.add(bank);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return banks;
    }

}
