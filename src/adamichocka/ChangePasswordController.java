package adamichocka;

import adamichocka.userData.User;
import adamichocka.userData.UserData;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

public class ChangePasswordController {
    @FXML
    private TextField passwordField;

    @FXML
    private Label firstNameLabel;

    @FXML
    private Label lastNameLabel;

    @FXML
    private Label passwordLabel;

    @FXML
    private Label loginLabel;

    public void initialize() {
        User user = null;
        try {
            user = UserData.getInstance().loadLoggedUser();
        } catch (IOException e) {
            e.printStackTrace();
        }
        loginLabel.setText(user.getUserId());
        firstNameLabel.setText(user.getFirstName());
        lastNameLabel.setText(user.getLastName());
        passwordLabel.setText(user.getPassword());
    }

    @FXML
    public User changePasswordResult() {
        String newPassword = passwordField.getText();

        User user = null;
        try {
            user = UserData.getInstance().loadLoggedUser();
        } catch (IOException e) {
            e.printStackTrace();
        }

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
                User newUser = new User(user.getUserId(), newPassword, user.getFirstName(), user.getLastName(), user.getAccountNumber(), user.getBalance());

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
