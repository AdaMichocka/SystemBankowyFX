package adamichocka;

import adamichocka.userData.User;
import adamichocka.transactionData.Transaction;
import adamichocka.transactionData.TransactionData;
import adamichocka.userData.UserData;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DepositController {

    @FXML
    private Label userLoginTextArea;

    @FXML
    private Label dateTextArea;

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

    public Transaction depositProcesResult() {

        Double amount = Double.valueOf(amountField.getText());

        User user = null;
        try {
            user = UserData.getInstance().loadLoggedUser();
            UserData.getInstance().addBalance(user, amount);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String transactionId = TransactionData.getInstance().setTransactionNumber();

        LocalDate date = LocalDate.now();
        String type = "wpłata";

        String userId = user.getUserId();
        Transaction transaction = new Transaction(transactionId, date, type, userId, amount);

        TransactionData.getInstance().addTransaction(transaction);

        return transaction;
    }
}