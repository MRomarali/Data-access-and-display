package noroff.assignment_6.data_access_and_display.service;

import noroff.assignment_6.data_access_and_display.models.Customer;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.Collection;

@Repository
public interface ICustomerService {
    Object addCustomerToDatabase(Customer c);
    Object updateCustomerById(String id, Customer customer);
    Object getCustomerByIdFromDatabase(String id);
    Object getCustomerCountryCountFromDatabase();
    Object getSpendersFromDatabase();
    Collection<Customer> getCustomersFromDatabase();
    Object getPopularGenreFromDatabase(String id);
}
