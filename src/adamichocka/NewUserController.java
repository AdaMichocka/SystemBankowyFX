package adamichocka;

import adamichocka.userData.User;
import adamichocka.userData.UserData;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.IOException;

public class NewUserController {

    @FXML
    private TextField loginField;

    @FXML
    private TextField passwordField;

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private TextField accountNumberField;


    public User userProcesResult() throws IOException {

        String userId = loginField.getText();
        String password = passwordField.getText();
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String accountNumber = accountNumberField.getText();
        Double balance = 0.0;

        User user = new User(userId, password, firstName, lastName, accountNumber, balance);

        UserData.getInstance().addUser(user);

        return user;
    }
}