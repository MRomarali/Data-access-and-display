package noroff.assignment_6.data_access_and_display.view.controller;

import noroff.assignment_6.data_access_and_display.service.CustomerService;
import noroff.assignment_6.data_access_and_display.service.MusicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CustomerController {
    CustomerService customerService;

    public CustomerController(@Autowired CustomerService customerService) {
        this.customerService = customerService;
    }

    // user url: /customer?limit=10&offset=100
    @GetMapping("/customers")
    public String home(Model model, @RequestParam(required = false) String limit, @RequestParam(required = false) String offset) {
        System.out.println("limit: "+ limit + " offset: "+ offset);
        model.addAttribute("limit", limit);
        model.addAttribute("offset", offset);
        model.addAttribute("customers", customerService.getCustomersFromDatabase(limit, offset));
        return "customers";
    }
}
