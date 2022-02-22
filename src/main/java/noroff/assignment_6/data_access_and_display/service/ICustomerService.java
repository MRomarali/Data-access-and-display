package noroff.assignment_6.data_access_and_display.service;

import noroff.assignment_6.data_access_and_display.models.Customer;

import java.sql.SQLException;

public interface ICustomerService {
    Object addCustomerToDatabase(Customer c);
    Object updateCustomerById(String id, Customer customer);
    Object getCustomerByIdFromDatabase(String id);
    Object getCustomerCountryCountFromDatabase();
    Object getSpendersFromDatabase();
    Object getCustomersFromDatabase();
    Object getPopularGenreFromDatabase(String id);
}
