package adamichocka;

import adamichocka.userData.User;
import adamichocka.userData.UserData;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.util.Iterator;
import java.util.List;

public class ChangeUserDataController {

    @FXML
    private TextField passwordField;

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private Label loginLabel;

    @FXML
    private Label accountNumberLabel;

    @FXML
    private Label firstNameLabel;

    @FXML
    private Label lastNameLabel;

    @FXML
    private Label passwordLabel;
    private User user;

    public void initialize() {

        User user = null;
//        loginLabel.setText(user.getUserId());
//        firstNameLabel.setText(user.getFirstName());
//        lastNameLabel.setText(user.getLastName());
//        passwordLabel.setText(user.getPassword());
//        accountNumberLabel.setText(user.getAccountNumber());

    }

    public User changeUserDataProcesResult(User user) {
        String newFirstName = firstNameField.getText();
        String newLastName = lastNameField.getText();
        String newPassword = passwordField.getText();

        List users = UserData.getInstance().getUsersList();
        Iterator<User> iter = users.iterator();
        while (iter.hasNext()) {
            User user1 = iter.next();
            String.format("%s\t%s\t%s\t%s\t%s\t%s",
                    user1.getUserId(),
                    user1.getPassword(),
                    user1.getFirstName(),
                    user1.getLastName(),
                    user1.getAccountNumber(),
                    user1.getBalance());

            if (user1.getUserId().equals(user.getUserId())) {
                User newUser = new User(user.getUserId(), newPassword, newFirstName, newLastName, user.getAccountNumber(), user.getBalance());
                if (newFirstName.isEmpty()) {
                    newUser.setFirstName(user.getFirstName());
                }
                if (newLastName.isEmpty()) {
                    newUser.setLastName(user.getLastName());
                }
                if (newPassword.isEmpty()) {
                    newUser.setPassword(user.getPassword());
                }
                users.remove(user);
                users.add(newUser);

                return newUser;
            }
        }
        return null;
    }
}
