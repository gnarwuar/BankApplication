package com.course.controllers;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import Database.Const;
import Database.DatabaseHandler;
import UserData.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AuthorizationController {

    @FXML
    private PasswordField Password;

    @FXML
    private TextField LoginLabel;

    private Stage stage;
    private Scene scene;

    public void continueButton(ActionEvent event) throws SQLException {

        String username = LoginLabel.getText().trim();
        String password = Password.getText().trim();

        User.setLogin(username);
        User.setPassword(password);

        int res = SignIn();

        if (res == 1) {
            FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("hello-view.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            try {
                scene = new Scene(fxmlLoader.load());
            } catch (IOException e) {
                e.printStackTrace();
            }
            stage.setScene(scene);
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Неправильний логін або пароль! Спробуйте увійти ще раз!" );
            alert.showAndWait();

        }
    }

    private int SignIn() {

        String query = "SELECT " + Const.USERS_USERNAME + " , " + Const.USER_PASSWORD + " , " + Const.USERS_ID + " FROM " + Const.USER_TABLE +
                " WHERE " + Const.USERS_USERNAME + " = '" + User.getLogin() + "' AND " + Const.USER_PASSWORD + " = '" + User.getPassword() + "' ;";

        DatabaseHandler dataBaseHandler = new DatabaseHandler();
        try {
            Statement statement = dataBaseHandler.getDbConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            if (resultSet.next()) {
                if (User.getLogin().equals(resultSet.getString(Const.USERS_USERNAME))
                        && User.getPassword().equals(resultSet.getString(Const.USER_PASSWORD))) {
                User.setID(resultSet.getInt(Const.USERS_ID));
                    return 1;
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return -1;
    }
}
