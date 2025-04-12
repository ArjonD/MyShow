package hh.sarjaprojekti.myshow.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import hh.sarjaprojekti.myshow.domain.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class SerieController {

    @Autowired
    private SerieRepository serieRepository;

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserSerieRepository userSerieRepository;

    private User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }

    @GetMapping("/addserie")
    public String getAddSerieForm(Model model) {
        model.addAttribute("serie", new Serie());
        model.addAttribute("genres", genreRepository.findAll());
        return "addserie";
    }

    @PostMapping("/addserie")
    public String addSerie(@ModelAttribute Serie serie) {
        serieRepository.save(serie);
        return "redirect:/serielist";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("serie", serieRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid serie ID: " + id)));
        model.addAttribute("genres", genreRepository.findAll());
        return "editserie";
    }

    @PostMapping("/update")
    public String updateSerie(@ModelAttribute Serie serie) {
        serieRepository.save(serie);
        return "redirect:/serielist";
    }

    @PostMapping("/series/delete/{id}")
    public String deleteSerie(@PathVariable Long id) {
        serieRepository.deleteById(id);
        return "redirect:/serielist";
    }

    @GetMapping("/series/{id}")
    public String getSerieDetails(@PathVariable Long id, Model model) {
        model.addAttribute("serie", serieRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid serie ID: " + id)));
        return "seriedetails";
    }

    @GetMapping("/genre/{id}")
    public String getSeriesByGenre(@PathVariable("id") Long genreId, Model model) {
        Genre genre = genreRepository.findById(genreId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid genre ID: " + genreId));
        List<Serie> genreSeries = serieRepository.findByGenre(genre);
        User currentUser = getCurrentUser();
        List<UserSerie> userSeries = userSerieRepository.findByUser(currentUser);
        Map<Long, UserSerie> userSeriesMap = userSeries.stream()
                .collect(Collectors.toMap(us -> us.getSerie().getId(), us -> us));
        model.addAttribute("genre", genre);
        model.addAttribute("series", genreSeries);
        model.addAttribute("userSeriesMap", userSeriesMap);
        model.addAttribute("genreFilter", true);
        return "serielist";
    }

    @GetMapping("/serielist")
    public String getSeries(Model model, @RequestParam(required = false) Long genreId,
            @RequestParam(required = false) String status) {
        User currentUser = getCurrentUser();
        List<Serie> filteredSeries;
        model.addAttribute("genres", genreRepository.findAll());
        if (genreId != null && status != null && !status.isEmpty()) {
            Genre genre = genreRepository.findById(genreId)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid genre ID: " + genreId));
            filteredSeries = serieRepository.findByGenreAndShowStatus(genre, status);
            model.addAttribute("selectedGenreId", genreId);
            model.addAttribute("selectedStatus", status);
        } else if (genreId != null) {
            Genre genre = genreRepository.findById(genreId)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid genre ID: " + genreId));
            filteredSeries = serieRepository.findByGenre(genre);
            model.addAttribute("selectedGenreId", genreId);
        } else if (status != null && !status.isEmpty()) {
            filteredSeries = serieRepository.findByShowStatus(status);
            model.addAttribute("selectedStatus", status);
        } else {
            filteredSeries = (List<Serie>) serieRepository.findAll();
        }
        List<UserSerie> userSeries = userSerieRepository.findByUser(currentUser);
        Map<Long, UserSerie> userSeriesMap = userSeries.stream()
                .collect(Collectors.toMap(us -> us.getSerie().getId(), us -> us));
        model.addAttribute("series", filteredSeries);
        model.addAttribute("userSeriesMap", userSeriesMap);
        return "serielist";
    }
}
