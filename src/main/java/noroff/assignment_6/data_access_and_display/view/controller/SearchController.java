package noroff.assignment_6.data_access_and_display.view.controller;

import noroff.assignment_6.data_access_and_display.models.Customer;
import noroff.assignment_6.data_access_and_display.models.Song;
import noroff.assignment_6.data_access_and_display.service.CustomerService;
import noroff.assignment_6.data_access_and_display.service.MusicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SearchController {

    MusicService musicService;

    public SearchController(@Autowired MusicService musicService) {
        this.musicService = musicService;
    }

    @GetMapping("/search")
    public String results(@RequestParam(name = "query") String query, Model model) {
        model.addAttribute("songs", musicService.getSongs(query));
        return "search";
    }


}
