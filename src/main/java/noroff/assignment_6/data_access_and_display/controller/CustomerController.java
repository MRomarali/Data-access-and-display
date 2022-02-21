package noroff.assignment_6.data_access_and_display.controller;

import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

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
    public Object updateCustomerById(@PathVariable("id") String id) throws SQLException {
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
            while(r.next()){
                var customer = new Customer(r.getString("CustomerId"), r.getString("FirstName"), r.getString("LastName"), r.getString("Country"), r.getString("PostalCode"), r.getString("Phone"), r.getString("Email"));
                customers.addLast(customer);
            }
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
        return getCustomerCountryCountFromDatabase();


    }

    private Object getCustomerCountryCountFromDatabase() {
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
            while(r.next()){
                String country = r.getString("Country");
                String count = r.getString("count");
                list.addLast(new CountryCount(country, count));
            }
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
        return getSpendersFromDatabase();
    }

    private Object getSpendersFromDatabase() {
        try {
            var statement = db.prepareStatement("select Customer.CustomerId, FirstName, LastName, sum(total) as \"total\"\n" +
                    "from Customer\n" +
                    "    join Invoice\n" +
                    "        on Customer.CustomerId = Invoice.CustomerId\n" +
                    "group by Invoice.CustomerId\n" +
                    "order by total desc;");

            var resultSet = statement.executeQuery();
            class Spender{
                String customerId;
                String firstName;
                String lastName;
                String total;

                public Spender(String customerId, String firstName, String lastName, String total) {
                    this.customerId = customerId;
                    this.firstName = firstName;
                    this.lastName = lastName;
                    this.total = total;
                }

                public String getCustomerId() {
                    return customerId;
                }

                public String getFirstName() {
                    return firstName;
                }

                public String getLastName() {
                    return lastName;
                }

                public String getTotal() {
                    return total;
                }
            }
            var spenders = new LinkedList<Spender>();
            while(resultSet.next()){
                var id = resultSet.getString("CustomerId");
                var firstName = resultSet.getString("FirstName");
                var lastName = resultSet.getString("LastName");
                var total = resultSet.getString("total");
                spenders.addLast(new Spender(id, firstName,lastName, total));
            }

            return spenders;
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error";
        }
    }

    // /api/customers/:id/popular/genre
    @CrossOrigin
    @GetMapping("/{id}/popular/genre")
    public Object getPopularGenre(@PathVariable("id") String id) {
        return getPopularGenreFromDatabase(id);
    }

    private Object getPopularGenreFromDatabase(String id) {
        PreparedStatement statement = null;
        try {
            statement = db.prepareStatement("with genreView as \n" +
                    "(select Genre.GenreId, Genre.Name, count(Genre.GenreId) as \"count\" from customer\n" +
                    "    join Invoice on customer.CustomerId = Invoice.CustomerId\n" +
                    "    join InvoiceLine on Invoice.InvoiceId = InvoiceLine.InvoiceId\n" +
                    "    join Track on InvoiceLine.TrackId = Track.TrackId\n" +
                    "    join Genre on Track.GenreId = Genre.GenreId\n" +
                    "where Invoice.CustomerId = ? group by Genre.GenreId),\n" +
                    "                  maxView as\n" +
                    "         (select max(count) as \"count\" from genreView),\n" +
                    "                  popularGenre as\n" +
                    "         (select GenreId,Name, maxView.count as \"count\" from maxView join genreView on maxView.count = genreView.count)\n" +
                    "select * from popularGenre;\n");
            statement.setString(1, id);
            var result = statement.executeQuery();
            var genres = new LinkedList<Genre>();
             class GenreCount extends Genre{
                 final String count;
                 public GenreCount(String id, String name, String count) {
                     super(id, name);
                     this.count = count;
                 }

                 public String getCount() {
                     return count;
                 }
             }
            while(result.next()){
                var genreId = result.getString("GenreId");
                var name = result.getString("Name");
                var count = result.getString("count");
                genres.addLast(new GenreCount(genreId,name, count));
            }

            return genres;
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error";
        }
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
        String id;
        String name;
        public Genre(String id, String name){
            this.id = id;
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }
    }




}
