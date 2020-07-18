package adamichocka.transactionData;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.List;

public class TransactionData {

    private static TransactionData instance = new TransactionData();
    private static String filename = "transactions.txt";

    private ObservableList<Transaction> transactionsList;
    private DateTimeFormatter formatter;

    public static TransactionData getInstance() {
        return instance;
    }

    private TransactionData() {
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    }

    public ObservableList<Transaction> getTransactionsList() {
        return transactionsList;
    }

    public void loadTransactions() throws IOException {
        transactionsList = FXCollections.observableArrayList();
        Path path = Paths.get(filename);
        BufferedReader br = Files.newBufferedReader(path);

        String input;

        try {
            while ((input = br.readLine()) != null) {
                String[] transactionPieces = input.split("\t");

                String transactionId = transactionPieces[0];
                String dateString = transactionPieces[1];
                String type = transactionPieces[2];
                String userlogin = transactionPieces[3];
                String amountString = transactionPieces[4];

                LocalDate date = LocalDate.parse(dateString, formatter);
                Double amount = Double.valueOf(amountString);

                Transaction transaction = new Transaction(transactionId, date, type, userlogin, amount);
                transactionsList.add(transaction);
            }
        } finally {
            if (br != null) {
                br.close();
            }
        }
    }

    public void storeTransactions() throws IOException {
        Path path = Paths.get(filename);
        BufferedWriter bw = Files.newBufferedWriter(path);

        try {

            Iterator<Transaction> iter = transactionsList.iterator();
            while (iter.hasNext()) {

                Transaction transaction = iter.next();
                bw.write(String.format("%s\t%s\t%s\t%s\t%s",
                        transaction.getTransactionId(),
                        transaction.getTransactionDate(),
                        transaction.getTransactionType(),
                        transaction.getUserId(),
                        transaction.getTransactionAmount()));
                bw.newLine();
            }

        } finally {
            if (bw != null) {
                bw.close();
            }
        }
    }

    public void addTransaction(Transaction transaction) {
        transactionsList.add(transaction);

    }

    public String setTransactionNumber() {
        int max = 0;
        List<Transaction> transactions = TransactionData.getInstance().getTransactionsList();

        Iterator<Transaction> iter = transactions.iterator();
        while (iter.hasNext()) {
            Transaction transaction = iter.next();
            String.format("%s\t%s\t%s\t%s\t%s",
                    transaction.getTransactionId(),
                    transaction.getTransactionDate(),
                    transaction.getTransactionType(),
                    transaction.getUserId(),
                    transaction.getTransactionAmount());

            int number = Integer.parseInt(transaction.getTransactionId());

            if (number > max) {
                int newTransactionNumber = number + 1;
                String newId = String.valueOf(newTransactionNumber);

                Transaction newT = new Transaction(newId, transaction.getTransactionDate(), transaction.getTransactionType(),
                        transaction.getUserId(), transaction.getTransactionAmount());

                transactions.remove(transaction);
                transactions.add(newT);

                return String.valueOf(newTransactionNumber);
            }
        }
        return "0";
    }
}
