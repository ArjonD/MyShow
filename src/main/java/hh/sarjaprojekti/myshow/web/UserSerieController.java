package hh.sarjaprojekti.myshow.web;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import hh.sarjaprojekti.myshow.domain.Genre;
import hh.sarjaprojekti.myshow.domain.GenreRepository;
import hh.sarjaprojekti.myshow.domain.Serie;
import hh.sarjaprojekti.myshow.domain.SerieRepository;
import hh.sarjaprojekti.myshow.domain.User;
import hh.sarjaprojekti.myshow.domain.UserRepository;
import hh.sarjaprojekti.myshow.domain.UserSerie;
import hh.sarjaprojekti.myshow.domain.UserSerieRepository;

@Controller
public class UserSerieController {

    @Autowired
    private UserSerieRepository userSerieRepository;

    @Autowired
    private SerieRepository serieRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GenreRepository genreRepository;

    private User getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + username));
    }

    @GetMapping("/user/series")
    public String getUserSeries(Model model,
            @RequestParam(required = false) Long genreId,
            @RequestParam(required = false) String showStatus,
            @RequestParam(required = false) String userStatus) {
        User currentUser = getCurrentUser();
        List<UserSerie> filteredUserSeries;
        model.addAttribute("genres", genreRepository.findAll());

        if (genreId != null && showStatus != null && !showStatus.isEmpty() && userStatus != null
                && !userStatus.isEmpty()) {
            Genre genre = genreRepository.findById(genreId)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid genre ID: " + genreId));
            filteredUserSeries = userSerieRepository.findByUserAndUserStatusAndSerie_ShowStatusAndSerie_Genre(
                    currentUser, userStatus, showStatus, genre);
            model.addAttribute("selectedGenreId", genreId);
            model.addAttribute("selectedShowStatus", showStatus);
            model.addAttribute("selectedUserStatus", userStatus);
        } else if (genreId != null && showStatus != null && !showStatus.isEmpty()) {
            Genre genre = genreRepository.findById(genreId)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid genre ID: " + genreId));
            filteredUserSeries = userSerieRepository.findByUserAndSerie_ShowStatusAndSerie_Genre(
                    currentUser, showStatus, genre);
            model.addAttribute("selectedGenreId", genreId);
            model.addAttribute("selectedShowStatus", showStatus);
        } else if (genreId != null && userStatus != null && !userStatus.isEmpty()) {
            Genre genre = genreRepository.findById(genreId)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid genre ID: " + genreId));
            filteredUserSeries = userSerieRepository.findByUserAndUserStatusAndSerie_Genre(
                    currentUser, userStatus, genre);
            model.addAttribute("selectedGenreId", genreId);
            model.addAttribute("selectedUserStatus", userStatus);
        } else if (showStatus != null && !showStatus.isEmpty() && userStatus != null && !userStatus.isEmpty()) {
            filteredUserSeries = userSerieRepository.findByUserAndUserStatusAndSerie_ShowStatus(
                    currentUser, userStatus, showStatus);
            model.addAttribute("selectedShowStatus", showStatus);
            model.addAttribute("selectedUserStatus", userStatus);
        } else if (genreId != null) {
            Genre genre = genreRepository.findById(genreId)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid genre ID: " + genreId));
            filteredUserSeries = userSerieRepository.findByUserAndSerie_Genre(currentUser, genre);
            model.addAttribute("selectedGenreId", genreId);
        } else if (showStatus != null && !showStatus.isEmpty()) {
            filteredUserSeries = userSerieRepository.findByUserAndSerie_ShowStatus(currentUser, showStatus);
            model.addAttribute("selectedShowStatus", showStatus);
        } else if (userStatus != null && !userStatus.isEmpty()) {
            filteredUserSeries = userSerieRepository.findByUserAndUserStatus(currentUser, userStatus);
            model.addAttribute("selectedUserStatus", userStatus);
        } else {
            filteredUserSeries = userSerieRepository.findByUser(currentUser);
        }
        model.addAttribute("userSeries", filteredUserSeries);
        return "userSeries";
    }

    @PostMapping("/series/update")
    public String updateUserStatus(@RequestParam Long id, @RequestParam String userStatus) {
        userSerieRepository.findById(id).ifPresent(userSerie -> {
            userSerie.setUserStatus(userStatus);
            userSerieRepository.save(userSerie);
        });
        return "redirect:/user/series";
    }

    @PostMapping("/series/removefromuser")
    public String removeFromUserList(@RequestParam Long id) {
        userSerieRepository.findByUserAndSerie(getCurrentUser(), serieRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid serie ID: " + id)))
                .ifPresent(userSerieRepository::delete);
        return "redirect:/user/series";
    }

    @PostMapping("/series/addtouser")
    public String addToUserList(@RequestParam Long id) {
        User currentUser = getCurrentUser();
        Serie serie = serieRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid serie ID: " + id));
        userSerieRepository.findByUserAndSerie(currentUser, serie)
                .orElseGet(() -> userSerieRepository.save(new UserSerie(currentUser, serie, "Watching")));
        return "redirect:/serielist";
    }

}