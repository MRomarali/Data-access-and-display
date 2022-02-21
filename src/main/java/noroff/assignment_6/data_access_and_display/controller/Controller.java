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
    static class ConnectionManager {
        // Pull a file from the resource folder: Northwind_small.sqlite
        static final String URL = "jdbc:sqlite:src/main/resources/chinook.db";
        static private ConnectionManager instance;
        private Connection connection;
        // Single pattern
        static ConnectionManager getInstance(){
            if(instance == null) instance = new ConnectionManager();
            return instance;
        }
        private ConnectionManager(){
            try {
                connection = DriverManager.getConnection(URL);
            } catch (SQLException e) {
                e.printStackTrace();
                System.exit(-1);
            }
        };
        public Connection getConnection() {
            return connection;
        }
    }
    // SQL Queries
    // Add database connection
    ConnectionManager manager = new ConnectionManager();
    Connection db = manager.getConnection();
    static public class Customer {
        String name;
    }

    static class Genre{

    }


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
        PreparedStatement statement = db.prepareStatement("select CustomerId, FirstName, LastName, Country, PostalCode, Phone, Email from Customer where CustomerId = ?");
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
    public String getCustomerById(@PathVariable("id") String id) {
        return " customerById";
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

}
