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

public class WithdrawalController {
    @FXML
    private Label userLoginTextArea;

    @FXML
    private Label dateTextArea;

    @FXML
    private TextField amountField;

    User user = new User("1111");

    public void initialize() {
        LocalDate date = LocalDate.now();
        DateTimeFormatter df = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        userLoginTextArea.setText(this.user.getUserId());
        dateTextArea.setText(df.format(date));
    }

    public Transaction withdrawalProcesResult() {
        Double amount = Double.valueOf(amountField.getText());

        User user = null;
        try {
            user = UserData.getInstance().loadLoggedUser();
            UserData.getInstance().subBalance(user, amount);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String transactionId = TransactionData.getInstance().setTransactionNumber();

        LocalDate date = LocalDate.now();
        String type = "wypłata";

        String userId = user.getUserId();
        Transaction transaction = new Transaction(transactionId, date, type, userId, amount);

        TransactionData.getInstance().addTransaction(transaction);

        return transaction;
    }
}