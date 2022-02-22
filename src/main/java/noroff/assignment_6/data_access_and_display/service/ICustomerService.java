package noroff.assignment_6.data_access_and_display.service;

import noroff.assignment_6.data_access_and_display.models.Customer;

import java.sql.SQLException;

public interface ICustomerService {
    Object addCustomer(Customer c);
    Object addCustomerToDatabase(Customer c);
    Object updateCustomerById(String id) throws SQLException;
    Object getCustomers(String limit, String offset);
    Object getCustomerById(String id);
    Object getCustomerByIdFromDatabase(String id);
    Object getCountries();
    Object getCustomerCountryCountFromDatabase();
    Object getSpenders();
    Object getSpendersFromDatabase();
    Object getPopularGenre(String id);
    Object getCustomersFromDatabase();
    Object getPopularGenreFromDatabase(String id);
}
