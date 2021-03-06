package noroff.assignment_6.data_access_and_display.service;

import noroff.assignment_6.data_access_and_display.data_access.ConnectionFactory;
import noroff.assignment_6.data_access_and_display.models.Customer;
import noroff.assignment_6.data_access_and_display.models.Genre;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.sql.*;
import java.util.Collection;
import java.util.LinkedList;

@Service
public class CustomerService implements ICustomerService {

    // SQL Queries
    // Add database connection
    private CustomerService customerRepository;

    public Object addCustomer(@RequestBody Customer c) {
        return addCustomerToDatabase(c);
    }

    public Object addCustomerToDatabase(Customer c) {
        try (Connection db = ConnectionFactory.getConnection()) {

            var s = db.prepareStatement("insert into Customer (FirstName, LastName, Country, PostalCode, Phone, Email) values(?,?,?,?,?,?);");
            s.setString(1, c.getFirstName());
            s.setString(2, c.getLastName());
            s.setString(3, c.getCountry());
            s.setString(4, c.getPostalCode());
            s.setString(5, c.getPhoneNumber());
            s.setString(6, c.getEmail());
            var result = s.executeUpdate();
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error user is not found";
        }
    }


    // /api/customers/:id
    public Object updateCustomerById(String id, Customer customer) {
        try (Connection db = ConnectionFactory.getConnection()) {
            if (!customer.getId().equals(id)) return "Id does not match customer Id";
            var s = db.prepareStatement("update Customer set FirstName = ?, LastName = ?, Country = ?, PostalCode = ?, Phone = ?, Email = ? where CustomerId = ?;");
            s.setString(1, customer.getFirstName());
            s.setString(2, customer.getLastName());
            s.setString(3, customer.getCountry());
            s.setString(4, customer.getPostalCode());
            s.setString(5, customer.getPhoneNumber());
            s.setString(6, customer.getEmail());
            s.setString(7, customer.getId());
            var result = s.executeUpdate();
            if (result > 0) {
                return customer;
            } else {
                return "customer with id:" + id + " not found";
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return "Server error";
        }
    }

    public Collection<Customer> getCustomersFromDatabase(String limit, String offset) {
        try (Connection db = ConnectionFactory.getConnection()) {
            if(limit == null) {
                limit = "50";
            }
            if(offset == null){
                offset = "0";
            }
            var statement = db.prepareStatement("select CustomerId, FirstName, LastName, Country, PostalCode, Phone, Email from Customer limit ? offset ?");
        statement.setInt(1, Integer.parseInt(limit));
        statement.setInt(2, Integer.parseInt(offset));
        var r = statement.executeQuery();

            var customers = new LinkedList<Customer>();
            while (r.next()) {
                var customer = new Customer(r.getString("CustomerId"), r.getString("FirstName"), r.getString("LastName"), r.getString("Country"), r.getString("PostalCode"), r.getString("Phone"), r.getString("Email"));
                customers.addLast(customer);
            }
            return customers;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Object getCustomerByIdFromDatabase(String id) {
        try (Connection db = ConnectionFactory.getConnection()) {
            PreparedStatement statement = db.prepareStatement("select CustomerId, FirstName, LastName, Country, PostalCode, Phone, Email from Customer where CustomerId = ?");
            statement.setString(1, id);
            var r = statement.executeQuery();
            Customer customer = new Customer(r.getString("CustomerId"), r.getString("FirstName"), r.getString("LastName"), r.getString("Country"), r.getString("PostalCode"), r.getString("Phone"), r.getString("Email"));
            return customer;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("No customer was found with id: "+ id);
            return "Error no customer was found with id: " + id;
        }
    }

    public Object getCustomerCountryCountFromDatabase() {
        try (Connection db = ConnectionFactory.getConnection()) {
            var statement = db.prepareStatement("select Country, count(CustomerId) as \"count\" from Customer group by Country order by count desc;");
            class CountryCount {
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
            while (r.next()) {
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

    public Object getSpendersFromDatabase() {
        try (Connection db = ConnectionFactory.getConnection()) {
            var statement = db.prepareStatement("select Customer.CustomerId, FirstName, LastName, sum(total) as \"total\"\n" +
                    "from Customer\n" +
                    "    join Invoice\n" +
                    "        on Customer.CustomerId = Invoice.CustomerId\n" +
                    "group by Invoice.CustomerId\n" +
                    "order by total desc;");

            var resultSet = statement.executeQuery();
            class Spender {
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
            while (resultSet.next()) {
                var id = resultSet.getString("CustomerId");
                var firstName = resultSet.getString("FirstName");
                var lastName = resultSet.getString("LastName");
                var total = resultSet.getString("total");
                spenders.addLast(new Spender(id, firstName, lastName, total));
            }

            return spenders;
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error";
        }
    }

    public Object getPopularGenreFromDatabase(String id) {
        PreparedStatement statement = null;
        try (Connection db = ConnectionFactory.getConnection()) {
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
            class GenreCount extends Genre {
                final String count;

                public GenreCount(String id, String name, String count) {
                    super(id, name);
                    this.count = count;
                }

                public String getCount() {
                    return count;
                }
            }
            while (result.next()) {
                var genreId = result.getString("GenreId");
                var name = result.getString("Name");
                var count = result.getString("count");
                genres.addLast(new GenreCount(genreId, name, count));
            }

            return genres;
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error";
        }
    }

    @Override
    public Object getCustomerByName(String name) {
        try (Connection db = ConnectionFactory.getConnection()) {
            PreparedStatement statement = db.prepareStatement("select CustomerId, FirstName, LastName, Country, PostalCode, Phone, Email from Customer where FirstName like ? or LastName like ? limit 1");
            statement.setString(1, "%"+name+"%");
            var r = statement.executeQuery();
            Customer customer = new Customer(r.getString("CustomerId"), r.getString("FirstName"), r.getString("LastName"), r.getString("Country"), r.getString("PostalCode"), r.getString("Phone"), r.getString("Email"));
            return customer;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("No customer was found with id: "+ name);
            return "Error no customer was found with id: " + name;
        }
    }
}
