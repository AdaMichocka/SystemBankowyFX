package adamichocka;

import adamichocka.adminData.AdminData;
import adamichocka.transactionData.TransactionData;
import adamichocka.userData.UserData;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("loginWindow.fxml"));
        primaryStage.setTitle("System Bankowy Logowanie");
        primaryStage.setScene(new Scene(root, 500, 300));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void stop() {
        try {
            TransactionData.getInstance().storeTransactions();
            UserData.getInstance().storeUsers();
            AdminData.getInstance().storeAdmins();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void init() {
        try {
            TransactionData.getInstance().loadTransactions();
            UserData.getInstance().loadUsers();
            AdminData.getInstance().loadAdmins();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
