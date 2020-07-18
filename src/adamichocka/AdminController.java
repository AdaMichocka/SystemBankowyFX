package adamichocka;

import adamichocka.adminData.Admin;
import adamichocka.userData.User;
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
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class AdminController {

    private List<User> usersList;

    Admin admin = new Admin("5555", "5555", "Iwona", "Skała");

    @FXML
    private ListView<User> usersListView;

    @FXML
    private TextArea userDetailsTextArea;

    @FXML
    private TextArea adminData;

    @FXML
    private BorderPane mainAdminBorderPane;

    @FXML
    private ContextMenu listContextMenu;

    public void initialize() {
        listContextMenu = new ContextMenu();
        MenuItem deleteMenuItem = new MenuItem("Usuń użytkownika");

        deleteMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                User user = usersListView.getSelectionModel().getSelectedItem();
                deleteUser(user);
            }
        });

        MenuItem updateMenuItem = new MenuItem("Zmień dane użytkownika");

        updateMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                User user = usersListView.getSelectionModel().getSelectedItem();
                try {
                    updateUser(user);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        MenuItem transactionsMenuItem = new MenuItem("Podgląd transakcji użytkownika");

        transactionsMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                User user = usersListView.getSelectionModel().getSelectedItem();
                usersTransactionsUser(user);
            }
        });

        listContextMenu.getItems().addAll(deleteMenuItem, updateMenuItem, transactionsMenuItem);

        usersListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<User>() {
            @Override
            public void changed(ObservableValue<? extends User> observableValue, User oldUser, User newUser) {
                if (newUser != null) {
                    User user = usersListView.getSelectionModel().getSelectedItem();

                    userDetailsTextArea.setText(user.getAccountDetails(user).toString());

                    adminData.setText(admin.getAccountDetails(admin).toString());
                }
            }
        });

        usersListView.setItems(UserData.getInstance().getUsersList());
        usersListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        usersListView.getSelectionModel().selectFirst();

        usersListView.setCellFactory(new Callback<ListView<User>, ListCell<User>>() {
            @Override
            public ListCell<User> call(ListView<User> userListView) {

                ListCell<User> cell = new ListCell<>() {
                    @Override
                    protected void updateItem(User user, boolean empty) {
                        super.updateItem(user, empty);
                        if (empty) {
                            setText(null);
                        } else {
                            setText(user.toString());
                            setTextFill(Color.BLACK);
                        }
                    }
                };
                cell.emptyProperty().addListener(
                        (obs, wasEmpty, isNowEmpty) -> {
                            if (isNowEmpty) {
                                cell.setContextMenu(null);
                            } else {
                                cell.setContextMenu(listContextMenu);
                            }
                        }
                );
                return cell;
            }
        });

    }


    @FXML
    public void showNewUserDialog() throws IOException {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainAdminBorderPane.getScene().getWindow());
        dialog.setTitle("Utworzenie nowego Użytkownika");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("newUserDialog.fxml"));

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
            NewUserController controller = fxmlLoader.getController();
            User newUser = controller.userProcesResult();
            usersListView.refresh();
            usersListView.getSelectionModel().select(newUser);
        }
    }


    @FXML
    public void showSortedUsers() {
        SortedList<User> sortedList = new SortedList<>(UserData.getInstance().getUsersList(), new Comparator<User>() {
            @Override
            public int compare(User o1, User o2) {
                return o1.getLastName().compareTo(o2.getLastName());
            }
        });
        usersListView.setItems(sortedList);
    }


    //  @FXML
//    public void showChangeDataDialog() {
//        Dialog<ButtonType> dialog = new Dialog<>();
//        dialog.initOwner(mainAdminBorderPane.getScene().getWindow());
//        dialog.setTitle("Zmiana danych użytkownika");
//        FXMLLoader fxmlLoader = new FXMLLoader();
//        fxmlLoader.setLocation(getClass().getResource("changeDataDialog.fxml"));
//
//        try {
//            dialog.getDialogPane().setContent(fxmlLoader.load());
//
//        } catch (IOException e) {
//            System.out.println("Nie można załadować pliku");
//            e.printStackTrace();
//            return;
//        }
//
//        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
//        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
//
//        Optional<ButtonType> result = dialog.showAndWait();
//        if (result.isPresent() && result.get() == ButtonType.OK) {
//            ChangeUserDataController controller = fxmlLoader.getController();
//            User newUser = controller.changeUserDataProcesResult(usersListView.getSelectionModel().getSelectedItem());
//        }
//    }

    public void deleteUser(User user) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Usuń użytkownika");
        alert.setHeaderText("Usunięcie użytkownika: " + user.toString());
        alert.setContentText("Jesteś pewien? Kliknij OK żeby potwierdzić lub CANCEL żeby zrezygnować.");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            UserData.getInstance().deleteUser(user);
        }

    }

    public void updateUser(User user) throws IOException {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainAdminBorderPane.getScene().getWindow());
        dialog.setTitle("Zmiana danych Użytkownika");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("changeUserDataDialog.fxml"));

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
            ChangeUserDataController controller = fxmlLoader.getController();

            User newUser = controller.changeUserDataProcesResult(user);
            usersListView.refresh();
            usersListView.getSelectionModel().select(newUser);
        }
    }

    public void usersTransactionsUser(User user) {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainAdminBorderPane.getScene().getWindow());
        dialog.setTitle("Transakcje użytkownika");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("userTransactionsDialog.fxml"));

        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());

        } catch (IOException e) {
            System.out.println("Nie można załadować pliku");
            e.printStackTrace();
            return;
        }
    }

    @FXML
    public void logOut() {

    }
}
