package adamichocka.adminData;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;

public class AdminData {
    private static AdminData instance = new AdminData();
    private static String filename = "admins.txt";

    private ObservableList<Admin> adminsList;

    public static AdminData getInstance() {
        return instance;
    }

    public ObservableList<Admin> getAdminsList() {
        return adminsList;
    }

    public void loadAdmins() throws IOException {
        adminsList = FXCollections.observableArrayList();
        Path path = Paths.get(filename);
        BufferedReader br = Files.newBufferedReader(path);

        String input;

        try {
            while ((input = br.readLine()) != null) {
                String[] adminPieces = input.split("\t");

                String userId = adminPieces[0];
                String password = adminPieces[1];
                String firstName = adminPieces[2];
                String lastName = adminPieces[3];

                Admin admin = new Admin(userId, password, firstName, lastName);

                adminsList.add(admin);
            }
        } finally {
            if (br != null) {
                br.close();
            }
        }
    }

    public void storeAdmins() throws IOException {
        Path path = Paths.get(filename);
        BufferedWriter bw = Files.newBufferedWriter(path);

        try {

            Iterator<Admin> iter = adminsList.iterator();
            while (iter.hasNext()) {

                Admin admin = iter.next();
                bw.write(String.format("%s\t%s\t%s\t%s",
                        admin.getAdminId(),
                        admin.getPassword(),
                        admin.getFirstName(),
                        admin.getLastName()));
                bw.newLine();

            }

        } finally {
            if (bw != null) {
                bw.close();
            }
        }
    }
}
