package adamichocka;

import adamichocka.adminData.Admin;
import adamichocka.adminData.AdminData;
import adamichocka.userData.User;
import adamichocka.userData.UserData;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

public class LoginController {

    @FXML
    private RadioButton userButton;

    @FXML
    private RadioButton adminButton;

    @FXML
    private ToggleGroup UserOrAdmin;

    @FXML
    private TextField loginTextField;

    @FXML
    private PasswordField passwordTextField;

    @FXML
    private BorderPane loginBorderPane;

    public String login;

    public String getLogin() {
        return login;
    }

    @FXML
    public void login() {

        //TODO osobne metody na logowanie usera i admina

        login = loginTextField.getText();
        String password = passwordTextField.getText();

        if (userButton.isSelected()) {
            try {
                List users = UserData.getInstance().getUsersList();

                Iterator<User> iter = users.iterator();
                while (iter.hasNext()) {
                    User user = iter.next();
                    String.format("%s\t%s\t%s\t%s\t%s\t%s",
                            user.getUserId(),
                            user.getPassword(),
                            user.getFirstName(),
                            user.getLastName(),
                            user.getAccountNumber(),
                            user.getBalance());

                    if (user.getUserId().equals(login) && user.getPassword().equals(password)) {
                        user.setLoggedIn(true);
                        Stage primaryStage = new Stage();
                        Parent userRoot = FXMLLoader.load(getClass().getResource("userWindow.fxml"));
                        primaryStage.setTitle("System Bankowy Okno Użytkownika");
                        primaryStage.setScene(new Scene(userRoot, 1150, 650));
                        primaryStage.show();

                    } else {
                        user = null;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else if (adminButton.isSelected()) {
            try {
                List admins = AdminData.getInstance().getAdminsList();

                Iterator<Admin> iter = admins.iterator();
                while (iter.hasNext()) {
                    Admin admin = iter.next();
                    String.format("%s\t%s\t%s\t%s",
                            admin.getAdminId(),
                            admin.getPassword(),
                            admin.getFirstName(),
                            admin.getLastName());

                    if (admin.getAdminId().equals(login) && admin.getPassword().equals(password)) {

                        Stage primaryStage = new Stage();
                        Parent adminRoot = FXMLLoader.load(getClass().getResource("adminWindow.fxml"));
                        primaryStage.setTitle("System Bankowy Okno Administratora");
                        primaryStage.setScene(new Scene(adminRoot, 1150, 650));
                        primaryStage.show();

                    } else {
                        admin = null;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Label label = new Label();
            label.setText("Nieprawidłowe dane logowania");
        }
    }
}