package noroff.assignment_6.data_access_and_display.controller;

import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * REST API
 */

@RestController
@CrossOrigin
@RequestMapping("/api/customers")
public class CustomerController {
    // SQL Queries
    // Add database connection
    ConnectionManager manager = new ConnectionManager();
    Connection db = manager.getConnection();

    // POSTS
    // /api/customers
    @CrossOrigin
    @PostMapping("")
    public Object addCustomer(@RequestBody Customer c) {
        return addCustomerToDatabase(c);
    }

    private Object addCustomerToDatabase(Customer c) {
        try{
            var s = db.prepareStatement("insert into Customer (FirstName, LastName, Country, PostalCode, Phone, Email) values(?,?,?,?,?,?);");
            s.setString(1, c.firstName);
            s.setString(2, c.lastName);
            s.setString(3, c.country);
            s.setString(4, c.postalCode);
            s.setString(5, c.phoneNumber);
            s.setString(6, c.email);
            var result = s.executeUpdate();
            return result;
        }
        catch(SQLException e){
            e.printStackTrace();
            return "Error user is not found";
        }
    }

    // /api/customers/:id
    @CrossOrigin
    @PatchMapping("/{id}")

    public Object findById(@PathVariable("id") String id) throws SQLException {
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
    public Object getCustomers(@RequestParam(required = false) String limit, @RequestParam(required = false) String offset) {
        return getCustomersFromDatabase();
    }

    private Object getCustomersFromDatabase() {
        try{
            var statement = db.prepareStatement("select CustomerId, FirstName, LastName, Country, PostalCode, Phone, Email from Customer;");
            var r = statement.executeQuery();
            var customers = new LinkedList<Customer>();
            do{
                var customer = new Customer(r.getString("CustomerId"), r.getString("FirstName"), r.getString("LastName"), r.getString("Country"), r.getString("PostalCode"), r.getString("Phone"), r.getString("Email"));
                customers.addLast(customer);
            }while(r.next());
            return customers;
        }
        catch(SQLException e){
            e.printStackTrace();
            return "failed to find any customers";
        }
    }

    // /api/customers/:id
    @CrossOrigin
    @GetMapping("/{id}")
    public Object getCustomerById(@PathVariable("id") String id)  {
        return getCustomerByIdFromDatabase(id);
    }

    private Object getCustomerByIdFromDatabase(String id) {
        try{
            PreparedStatement statement = db.prepareStatement("select CustomerId, FirstName, LastName, Country, PostalCode, Phone, Email from Customer where CustomerId = ?");
            statement.setString(1, id);
            var r = statement.executeQuery();
            Customer customer = new Customer(r.getString("CustomerId"), r.getString("FirstName"), r.getString("LastName"), r.getString("Country"), r.getString("PostalCode"), r.getString("Phone"), r.getString("Email"));
            return customer;
        }
        catch (SQLException e){
            e.printStackTrace();
            return "Error no customer was found with id: " + id;
        }
    }

    // /api/customers/countries
    @CrossOrigin
    @GetMapping("/countries")
    public Object getCountries() {
        try {
            var statement = db.prepareStatement("select Country, count(CustomerId) as \"count\" from Customer group by Country order by count desc;");
            class CountryCount{
                String country;
                String count;
                public CountryCount(String country, String count) {
                    this.country = country;
                    this.count = count;
                }

                public String getCountry() {
                    return country;
                }

                public String getCount() {
                    return count;
                }
            }

            var r = statement.executeQuery();
            var list = new LinkedList<CountryCount>();
            do{
                String country = r.getString("Country");
                String count = r.getString("count");
                list.addLast(new CountryCount(country, count));
            }
            while(r.next());
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error";
        }


    }

    // /api/customers/spenders
    @CrossOrigin
    @GetMapping("/spenders")
    public Object getSpenders() {
        return new ArrayList<String>();
    }

    // /api/customers/:id/popular/genre
    @CrossOrigin
    @GetMapping("/{id}/popular/genre")
    public Object getPopularGenre(@PathVariable("id") String id) {
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

        public Connection getConnection() {
            return connection;
        }

    }

    public static class Customer {
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

    public static class Genre {
        int id;
        String name;
        public Genre(int id, String name){
            this.id = id;
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }
    }




}
