package noroff.assignment_6.data_access_and_display.view.controller;

import noroff.assignment_6.data_access_and_display.models.Artist;
import noroff.assignment_6.data_access_and_display.models.Customer;
import noroff.assignment_6.data_access_and_display.models.Genre;
import noroff.assignment_6.data_access_and_display.models.Song;
import noroff.assignment_6.data_access_and_display.service.CustomerService;
import noroff.assignment_6.data_access_and_display.service.MusicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collection;
import java.util.List;

@Controller
public class HomeController {
    MusicService musicService;

    public HomeController(@Autowired MusicService musicService) {
        this.musicService = musicService;
    }

    @GetMapping("")
    public String home(Model model) {
        model.addAttribute("genres", musicService.getGenres(5));
        model.addAttribute("artists", musicService.getArtists(5));
        model.addAttribute("songs", musicService.getSongs(5));
        return "index";
    }
}
