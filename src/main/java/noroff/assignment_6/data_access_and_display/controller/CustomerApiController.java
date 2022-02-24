package noroff.assignment_6.data_access_and_display.controller;

import noroff.assignment_6.data_access_and_display.models.Customer;
import noroff.assignment_6.data_access_and_display.service.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * REST API
 */

@RestController
@CrossOrigin
@RequestMapping("/api/customers")
public class CustomerApiController {
    // SQL Queries
    // Add database connection


    ICustomerService customerService;
    Customer c = new Customer("1", "Omar", "Ali", "Sweden", "12345", "073 123 45 67", "omar@gmail.com");

    public CustomerApiController(@Autowired ICustomerService customerService) {
        this.customerService = customerService;
    }

    // POSTS
    // /api/customers
    @CrossOrigin
    @PostMapping("")
    public Object addCustomer(@RequestBody Customer c) {
        return customerService.addCustomerToDatabase(c);
    }

    // /api/customers/:id
    @CrossOrigin
    @PutMapping("/{id}")
    public Object updateCustomerById(@PathVariable("id") String id, @RequestBody Customer customer) {
        return customerService.updateCustomerById(id, customer);
    }

    // GET
    // /api/customers
    // /api/customers?limit=10&offset=50
    @CrossOrigin
    @GetMapping("")
    public Object getCustomers(@RequestParam(required = false) String limit, @RequestParam(required = false) String offset) {
        return customerService.getCustomersFromDatabase();
    }

    // /api/customers/:id
    @CrossOrigin
    @GetMapping("/{id}")
    public Object getCustomerById(@PathVariable("id") String id) {
        return customerService.getCustomerByIdFromDatabase(id);
    }

    // /api/customers/countries
    @CrossOrigin
    @GetMapping("/countries")
    public Object getCountries() {
        return customerService.getCustomerCountryCountFromDatabase();
    }


    // /api/customers/spenders
    @CrossOrigin
    @GetMapping("/spenders")
    public Object getSpenders() {
        return customerService.getSpendersFromDatabase();
    }

    // /api/customers/:id/popular/genre
    @CrossOrigin
    @GetMapping("/{id}/popular/genre")
    public Object getPopularGenre(@PathVariable("id") String id) {
        return customerService.getPopularGenreFromDatabase(id);
    }
}
