package noroff.assignment_6.data_access_and_display.controller;

import org.springframework.web.bind.annotation.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * REST API
 */

@RestController
@CrossOrigin
@RequestMapping("/api/customers")
public class Controller {
    // SQL Queries
    // Add database connection
    ConnectionManager manager = new ConnectionManager();
    Connection db = manager.getConnection();

    // POSTS
    // /api/customers
    @CrossOrigin
    @PostMapping("")
    public Customer addCustomer(@RequestBody Customer body) {
        return body;
    }

    // /api/customers/:id
    @CrossOrigin
    @PatchMapping("/{id}")

    public String findById(@PathVariable("id") String id) throws SQLException {
        PreparedStatement statement = db.prepareStatement("" +
                " ?");
        statement.setString(1, id);
        ResultSet result = statement.executeQuery();

        return result.toString();
    }

    // GET
    // /api/customers
    // /api/customers?limit=10&offset=50
    @CrossOrigin
    @GetMapping("")
    public String getCustomer(@RequestParam(required = false) String limit, @RequestParam(required = false) String offset) {
        return "customer object" + " limit: " + limit + " offset: " + offset;
    }

    // /api/customers/:id
    @CrossOrigin
    @GetMapping("/{id}")
    public Customer getCustomerById(@PathVariable("id") String id) throws SQLException {
        PreparedStatement statement = db.prepareStatement("select CustomerId, FirstName, LastName, Country, PostalCode, Phone, Email from Customer where CustomerId = ?");
        statement.setString(1, id);
        ResultSet r = statement.executeQuery();
        StringBuilder results = new StringBuilder();
        String[] queryParams = "CustomerId, FirstName, LastName, Country, PostalCode, Phone, Email".split(", ");
        Customer customer = new Customer(r.getString("CustomerId"), r.getString("FirstName"), r.getString("LastName"), r.getString("Country"), r.getString("PostalCode"), r.getString("Phone"), r.getString("Email"));
        return customer;
    }

    // /api/customers/countries
    @CrossOrigin
    @GetMapping("/countries")
    public List<?> getCountries() {
        return new ArrayList<String>();
    }

    // /api/customers/spenders
    @CrossOrigin
    @GetMapping("/spenders")
    public List<?> getSpenders() {
        return new ArrayList<String>();
    }

    // /api/customers/:id/popular/genre
    @CrossOrigin
    @GetMapping("/{id}/popular/genre")
    public String getPopularGenre(@PathVariable("id") String id) {
        return "genre" + id;
    }

    static class ConnectionManager {
        // Pull a file from the resource folder: Northwind_small.sqlite
        static final String URL = "jdbc:sqlite:src/main/resources/data.sqlite";
        static private ConnectionManager instance;
        private Connection connection;

        private ConnectionManager() {
            try {
                connection = DriverManager.getConnection(URL);
            } catch (SQLException e) {
                e.printStackTrace();
                System.exit(-1);
            }
        }

        // Single pattern
        static ConnectionManager getInstance() {
            if (instance == null) instance = new ConnectionManager();
            return instance;
        }

        ;

        public Connection getConnection() {
            return connection;
        }
    }

    static public class Customer {
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

    static class Genre {

    }

}
