package adamichocka.userData;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class UserData {

    private static UserData instance = new UserData();
    private static String filename = "users.txt";

    private ObservableList<User> usersList;

    public static UserData getInstance() {
        return instance;
    }

    public ObservableList<User> getUsersList() {
        return usersList;
    }

    public void addUser(User user) {
        usersList.add(user);
    }

    public User loadLoggedUser() throws IOException {
        List users = UserData.getInstance().getUsersList();
        Iterator<User> iter = users.iterator();
        while (iter.hasNext()) {
            User user = iter.next();
            String.format("%s\t%s\t%s\t%s\t%s\t%s",
                    user.getUserId(),
                    user.getPassword(),
                    user.getFirstName(),
                    user.getLastName(),
                    user.getAccountNumber(),
                    user.getBalance());

            if (user.isLoggedIn()) {
                return user;
            } else {
                System.out.println("Error");
            }
        }
        return null;
    }

    public void loadUsers() throws IOException {
        usersList = FXCollections.observableArrayList();
        Path path = Paths.get(filename);
        BufferedReader br = Files.newBufferedReader(path);

        String input;

        try {
            while ((input = br.readLine()) != null) {
                String[] userPieces = input.split("\t");

                String userId = userPieces[0];
                String password = userPieces[1];
                String firstName = userPieces[2];
                String lastName = userPieces[3];
                String accountNumber = userPieces[4];
                String balanceString = userPieces[5];

                Double balance = Double.valueOf(balanceString);

                User user = new User(userId, password, firstName, lastName, accountNumber, balance);

                usersList.add(user);
            }
        } finally {
            if (br != null) {
                br.close();
            }
        }
    }

    public void storeUsers() throws IOException {
        Path path = Paths.get(filename);
        BufferedWriter bw = Files.newBufferedWriter(path);

        try {

            Iterator<User> iter = usersList.iterator();
            while (iter.hasNext()) {

                User user = iter.next();
                bw.write(String.format("%s\t%s\t%s\t%s\t%s\t%s",
                        user.getUserId(),
                        user.getPassword(),
                        user.getFirstName(),
                        user.getLastName(),
                        user.getAccountNumber(),
                        user.getBalance()));
                bw.newLine();
            }
        } finally {
            if (bw != null) {
                bw.close();
            }
        }
    }

    public void addBalance(User user, Double amount) {
        Double balance = user.getBalance() + amount;

        List<User> users = UserData.getInstance().getUsersList();
        List<User> userToRemove = new ArrayList<>();
        List<User> userToAdd = new ArrayList<>();
        User newUser = null;

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
                newUser = new User(user.getUserId(), user.getPassword(), user.getFirstName(), user.getLastName(),
                        user.getAccountNumber(), balance);

                userToRemove.add(user);
                userToAdd.add(newUser);
            }
        }
        users.remove(user);
        users.add(newUser);

    }

    public void subBalance(User user, Double amount) {
        Double balance = user.getBalance() - amount;
        if (balance < 0) {
            System.out.println("Za mało środków na koncie!");
        } else {
            List<User> users = UserData.getInstance().getUsersList();
            List<User> userToRemove = new ArrayList<>();
            List<User> userToAdd = new ArrayList<>();
            User newUser = null;

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
                    newUser = new User(user.getUserId(), user.getPassword(), user.getFirstName(), user.getLastName(),
                            user.getAccountNumber(), balance);
                    userToRemove.add(user);
                    userToAdd.add(newUser);
                }
            }
            users.remove(user);
            users.add(newUser);
        }
    }

    public void transferBalance(User user, User receiver, Double amount) {
        Double balance = user.getBalance() - amount;
        if (balance < 0) {
            Label label = new Label();
            label.setText("Za mało środków na koncie");

            System.out.println("Za mało środków na koncie!");
        } else {
            List<User> users = UserData.getInstance().getUsersList();
            List<User> userToRemove = new ArrayList<>();
            List<User> userToAdd = new ArrayList<>();
            User newUser = null;
            User user2 = null;

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
                    newUser = new User(user.getUserId(), user.getPassword(), user.getFirstName(), user.getLastName(),
                            user.getAccountNumber(), balance);

                    userToRemove.add(user);
                    userToAdd.add(newUser);
                    subBalance(user, amount);
                }
                if (user1.getUserId().equals(receiver.getUserId())) {
                    user2 = new User(receiver.getUserId(), receiver.getPassword(), receiver.getFirstName(), receiver.getLastName(),
                            receiver.getAccountNumber(), receiver.getBalance() + amount);

                    userToRemove.add(receiver);
                    userToAdd.add(user2);
                    addBalance(receiver, amount);
                }
            }
            users.removeAll(userToRemove);
            users.addAll(userToAdd);
        }
    }

    public void deleteUser(User user) {
        usersList.remove(user);
    }
}
