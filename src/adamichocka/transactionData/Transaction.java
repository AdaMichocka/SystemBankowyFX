package adamichocka.transactionData;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Transaction {

    private String transactionId;
    private LocalDate transactionDate;
    private String transactionType;
    private String userId;
    private Double transactionAmount;
    private String receiverId;

    DateTimeFormatter df = DateTimeFormatter.ofPattern("dd-MM-yyyy");


    public Transaction(String transactionId, LocalDate transactionDate, String transactionType, String userId, Double transactionAmount) {
        this.transactionId = transactionId;
        this.transactionDate = transactionDate;
        this.transactionType = transactionType;
        this.userId = userId;
        this.transactionAmount = transactionAmount;
    }

    public Transaction(String transactionId, LocalDate transactionDate, String transactionType, String userId, Double transactionAmount, String receiverId) {
        this.transactionId = transactionId;
        this.transactionDate = transactionDate;
        this.transactionType = transactionType;
        this.userId = userId;
        this.transactionAmount = transactionAmount;
        this.receiverId = receiverId;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public LocalDate getTransactionDate() {
        return transactionDate;
    }

    public String getUserId() {
        return userId;
    }

    public Double getTransactionAmount() {
        return transactionAmount;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    @Override
    public String toString() {
        return transactionId + "\t" + df.format(transactionDate);
    }

    public String getTransferDetails(Transaction transaction) {
        StringBuilder sb = new StringBuilder();
        sb.append("Numer transakcji: ");
        sb.append(transaction.getTransactionId());
        sb.append("\n\n");
        sb.append("Data transakcji: ");
        sb.append(df.format(transaction.transactionDate));
        sb.append("\n\n");
        sb.append("Typ transakcji: ");
        sb.append(transaction.getTransactionType());
        sb.append("\n\n");
        sb.append("Login użytkownika: ");
        sb.append(transaction.getUserId());
        sb.append("\n\n");
        sb.append("Login odbiorcy: ");
        sb.append(transaction.getReceiverId());
        sb.append("\n\n");
        sb.append("Kwota: ");
        sb.append(transaction.getTransactionAmount());
        return String.valueOf(sb);
    }

    public String getHistoryDetails(Transaction transaction) {

        StringBuilder sb = new StringBuilder();
        sb.append("Numer transakcji: ");
        sb.append(transaction.getTransactionId());
        sb.append("\n\n");
        sb.append("Data transakcji: ");
        sb.append(df.format(transaction.transactionDate));
        sb.append("\n\n");
        sb.append("Typ transakcji: ");
        sb.append(transaction.getTransactionType());
        sb.append("\n\n");
        sb.append("Login użytkownika: ");
        sb.append(transaction.getUserId());
        sb.append("\n\n");
        sb.append("Kwota: ");
        sb.append(transaction.getTransactionAmount());
        return String.valueOf(sb);
    }


}
