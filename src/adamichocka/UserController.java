package adamichocka;

import adamichocka.userData.User;
import adamichocka.transactionData.Transaction;
import adamichocka.transactionData.TransactionData;
import adamichocka.userData.UserData;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.util.Callback;

import java.io.IOException;
import java.util.*;

public class UserController {

    private List<Transaction> transactionList;

    @FXML
    private ListView<Transaction> transactionListView;

    @FXML
    private TextArea transactionDetailsTextArea;

    @FXML
    private TextArea userData;

    @FXML
    private BorderPane mainUserBorderPane;

    public void initialize() {
        User user = null;
        try {
            user = UserData.getInstance().loadLoggedUser();
            userData.setText(user.getAccountDetails(user).toString());

        } catch (IOException e) {
            e.printStackTrace();
        }

        //  transactionListView.setItems((ObservableList<Transaction>) onlyUserTransactions);
        //tutaj widok na pierwszą transackję ze szczegółami razem z odpaleniem apki
        transactionListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Transaction>() {
            @Override
            public void changed(ObservableValue<? extends Transaction> observableValue, Transaction oldValue, Transaction newValue) {
                if (newValue != null) {
                    Transaction transaction = transactionListView.getSelectionModel().getSelectedItem();

                    if (transaction.getTransactionType().equals("wpłata") || transaction.getTransactionType().equals("wypłata")) {

                        transactionDetailsTextArea.setText(transaction.getHistoryDetails(transaction));
                    } else if (transaction.getTransactionType().equals("przelew")) {

                        transactionDetailsTextArea.setText(transaction.getTransferDetails(transaction));
                    }
                }
            }
        });

        transactionListView.setItems(TransactionData.getInstance().getTransactionsList());
        transactionListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        transactionListView.getSelectionModel().selectFirst();

        transactionListView.setCellFactory(new Callback<ListView<Transaction>, ListCell<Transaction>>() {
            @Override
            public ListCell<Transaction> call(ListView<Transaction> transactionListView) {

                ListCell<Transaction> cell = new ListCell<>() {
                    @Override
                    protected void updateItem(Transaction transaction, boolean empty) {
                        super.updateItem(transaction, empty);
                        if (empty) {
                            setText(null);
                        } else {
                            setText(transaction.toString());
                            if (transaction.getTransactionType().equals("wpłata")) {
                                setTextFill(Color.GREEN);
                            } else if (transaction.getTransactionType().equals("wypłata")) {
                                setTextFill(Color.RED);
                            } else if (transaction.getTransactionType().equals("przelew")) {
                                setTextFill(Color.BLUE);
                            }
                        }
                    }
                };
                return cell;
            }
        });
    }

    @FXML
    public void showNewDepositDialog() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainUserBorderPane.getScene().getWindow());
        dialog.setTitle("Nowa wpłata środków na konto");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("depositDialog.fxml"));

        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());

        } catch (IOException e) {
            System.out.println("Nie można załadować pliku");
            e.printStackTrace();
            return;
        }

        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            DepositController controller = fxmlLoader.getController();
            Transaction newTransaction = controller.depositProcesResult();

            // transactionListView.getItems().setAll(TransactionData.getInstance().getTransactionsList());

            transactionListView.getSelectionModel().select(newTransaction);
            transactionListView.refresh();
        }
    }

    @FXML
    public void showNewWithdrawalDialog() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainUserBorderPane.getScene().getWindow());
        dialog.setTitle("Nowa wypłata środków z konta");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("withdrawalDialog.fxml"));

        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());

        } catch (IOException e) {
            System.out.println("Nie można załadować pliku");
            e.printStackTrace();
            return;
        }
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            WithdrawalController controller = fxmlLoader.getController();
            Transaction newTransaction = controller.withdrawalProcesResult();
            // transactionListView.getItems().setAll(TransactionData.getInstance().getTransactionsList());
            transactionListView.getSelectionModel().select(newTransaction);
            transactionListView.refresh();
        }
    }

    @FXML
    public void showSortedByData() {

        SortedList<Transaction> sortedList = new SortedList<Transaction>(TransactionData.getInstance().getTransactionsList(), new Comparator<Transaction>() {
            @Override
            public int compare(Transaction o1, Transaction o2) {
                return o2.getTransactionDate().compareTo(o1.getTransactionDate());
            }
        });
        transactionListView.setItems(sortedList);
    }

    @FXML
    public void showSortedByType() {
        SortedList<Transaction> sortedList = new SortedList<Transaction>(TransactionData.getInstance().getTransactionsList(), new Comparator<Transaction>() {
            @Override
            public int compare(Transaction o1, Transaction o2) {
                return o1.getTransactionType().compareTo(o2.getTransactionType());
            }
        });
        transactionListView.setItems(sortedList);
    }

    @FXML
    public void showNewTransferDialog() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainUserBorderPane.getScene().getWindow());
        dialog.setTitle("Nowy przelew");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("transferDialog.fxml"));

        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());

        } catch (IOException e) {
            System.out.println("Nie można załadować pliku");
            e.printStackTrace();
            return;
        }

        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            TransferController controller = fxmlLoader.getController();
            Transaction newTransaction = controller.transferProcesResult();
            transactionListView.getSelectionModel().select(newTransaction);
            transactionListView.refresh();
        }
    }

    @FXML
    public void showChangePasswordDialog() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainUserBorderPane.getScene().getWindow());
        dialog.setTitle("Zmiana hasła");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("changePasswordDialog.fxml"));

        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());

        } catch (IOException e) {
            System.out.println("Nie można załadować pliku");
            e.printStackTrace();
            return;
        }

        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            ChangePasswordController controller = fxmlLoader.getController();
            User newUser = controller.changePasswordResult();
        }
    }

    @FXML
    public void logOut() throws IOException {
        new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                //actions
//                User user = null;
//                try {
//                    user = UserData.getInstance().loadLoggedUser();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                user.setLoggedIn(false);
//                Node source = (Node) actionEvent.getSource();
//                Stage stage = (Stage) source.getScene().getWindow();
//                stage.close();
            }
        };
    }
}