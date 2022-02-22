package noroff.assignment_6.data_access_and_display.Models;

public class Customer {
    String id;
    String firstName;
    String lastName;
    String country;
    String postalCode;
    String phoneNumber;
    String email;

    public Customer(String id, String firstName, String lastName, String country, String postalCode, String phoneNumber, String email) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.country = country;
        this.postalCode = postalCode;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getCountry() {
        return country;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }
}
