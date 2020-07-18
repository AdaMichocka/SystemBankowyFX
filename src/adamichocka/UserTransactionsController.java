package adamichocka;

import adamichocka.transactionData.Transaction;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

public class UserTransactionsController {

    @FXML
    private ListView<Transaction> transactionListView;

    public void initialize() {
        transactionListView.getSelectionModel().selectAll();
    }
}
