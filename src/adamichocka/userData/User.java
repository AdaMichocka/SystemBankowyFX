package adamichocka.userData;

public class User {

    private String userId;
    private String password;
    private String firstName;
    private String lastName;
    private String accountNumber;
    private Double balance;
    private boolean isLoggedIn;

    public User(String userId, String password, String firstName, String lastName, String accountNumber, Double balance) {
        this.userId = userId;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.accountNumber = accountNumber;
        this.balance = balance;
        isLoggedIn = false;
    }

    public User(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isLoggedIn() {
        return isLoggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        isLoggedIn = loggedIn;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Double getBalance() {
        return balance;
    }

    @Override
    public String toString() {
        return userId + "\t\t" + firstName + "\t" + lastName;
    }

    public String getAccountDetails(User user) {
        StringBuilder sb = new StringBuilder();
        sb.append("Login użytkownika: ");
        sb.append(user.userId);
        sb.append("\n\n");
        sb.append("Imię: ");
        sb.append(user.firstName);
        sb.append("\n\n");
        sb.append("Nazwisko: ");
        sb.append(user.lastName);
        sb.append("\n\n");
        sb.append("Numer konta: ");
        sb.append(user.accountNumber);
        sb.append("\n\n");
        sb.append("Stan konta: ");
        sb.append(user.balance);
        sb.append("\n\n");

        return String.valueOf(sb);
    }
}
