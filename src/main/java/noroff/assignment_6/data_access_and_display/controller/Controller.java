package noroff.assignment_6.data_access_and_display.controller;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * REST API
 */

@RestController
@CrossOrigin
@RequestMapping("/api/customers")
public class Controller {
    static public class Customer {
    }

    // POSTS
    // /api/customers
    @CrossOrigin
    @PostMapping("")
    public String addCustomer(@RequestBody Customer body) {
        return "add new customer" + body;
    }

    // /api/customers/:id
    @CrossOrigin
    @PatchMapping("/{id}")
    public String findById(@PathVariable("id") String id) {
        return "Id" + id;
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
