package adamichocka;

import adamichocka.transactionData.Transaction;
import adamichocka.transactionData.TransactionData;
import adamichocka.userData.User;
import adamichocka.userData.UserData;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class TransferController {

    @FXML
    private Label userLoginTextArea;

    @FXML
    private Label dateTextArea;

    @FXML
    private TextField receiverLoginTextField;

    @FXML
    private TextField amountField;

    public void initialize() {
        User user = null;
        try {
            user = UserData.getInstance().loadLoggedUser();
            userLoginTextArea.setText(user.getUserId());
        } catch (IOException e) {
            e.printStackTrace();
        }

        LocalDate date = LocalDate.now();
        DateTimeFormatter df = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        dateTextArea.setText(df.format(date));
    }

    public Transaction transferProcesResult() {
        Double amount = Double.valueOf(amountField.getText());
        String receiverLogin = receiverLoginTextField.getText();

        User user = null;
        List<User> users = UserData.getInstance().getUsersList();
        List<User> receiverList = new ArrayList<>();

        for (User receiver : users) {
            if (receiver.getUserId().equals(receiverLogin)) {
                receiverList.add(receiver);
            }
        }

        User receiverUser = receiverList.get(0);

        try {
            user = UserData.getInstance().loadLoggedUser();
            UserData.getInstance().transferBalance(user, receiverUser, amount);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String transactionId = TransactionData.getInstance().setTransactionNumber();

        LocalDate date = LocalDate.now();
        String type = "przelew";

        String userId = user.getUserId();
        Transaction transaction = new Transaction(transactionId, date, type, userId, amount, receiverLogin);

        TransactionData.getInstance().addTransaction(transaction);

        return transaction;
    }
}