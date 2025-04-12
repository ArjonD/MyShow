package hh.sarjaprojekti.myshow.web;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hh.sarjaprojekti.myshow.domain.Genre;
import hh.sarjaprojekti.myshow.domain.GenreRepository;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class GenreRestController {

    @Autowired
    private GenreRepository genreRepository;

    @GetMapping("/genres")
    public List<Genre> getAllGenres() {
        return (List<Genre>) genreRepository.findAll();
    }

    @GetMapping("/genres/{id}")
    public Optional<Genre> getGenreById(@PathVariable Long id) {
        return genreRepository.findById(id);
    }
}