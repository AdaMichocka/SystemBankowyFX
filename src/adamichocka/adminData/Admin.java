package adamichocka.adminData;

public class Admin {

    private String adminId;
    private String password;
    private String firstName;
    private String lastName;

    public Admin(String adminId, String password, String firstName, String lastName) {
        this.adminId = adminId;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getAdminId() {
        return adminId;
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

    public String getAccountDetails(Admin admin) {
        StringBuilder sb = new StringBuilder();
        sb.append("Login użytkownika: ");
        sb.append(admin.adminId);
        sb.append("\n\n");
        sb.append("Imię: ");
        sb.append(admin.firstName);
        sb.append("\n\n");
        sb.append("Nazwisko: ");
        sb.append(admin.lastName);
        sb.append("\n\n");

        return String.valueOf(sb);
    }

}
