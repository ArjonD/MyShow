package hh.sarjaprojekti.myshow.web;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hh.sarjaprojekti.myshow.domain.Serie;
import hh.sarjaprojekti.myshow.domain.SerieRepository;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class SerieRestController {

    @Autowired
    private SerieRepository serieRepository;

    @GetMapping("/series")
    public List<Serie> getAllSeries() {
        return (List<Serie>) serieRepository.findAll();
    }

    @GetMapping("/series/{id}")
    public Optional<Serie> getSerieById(@PathVariable Long id) {
        return serieRepository.findById(id);
    }

    @PostMapping("/series")
    public Serie createSerie(@RequestBody Serie serie) {
        return serieRepository.save(serie);
    }

    @PutMapping("/series/{id}")
    public Serie updateSerie(@PathVariable Long id, @RequestBody Serie serieDetails) {
        return serieRepository.findById(id).map(serie -> {
            serie.setTitle(serieDetails.getTitle());
            serie.setAuthor(serieDetails.getAuthor());
            serie.setPublicationYear(serieDetails.getPublicationYear());
            serie.setDescription(serieDetails.getDescription());
            serie.setSeason(serieDetails.getSeason());
            serie.setShowStatus(serieDetails.getShowStatus());
            serie.setGenre(serieDetails.getGenre());
            return serieRepository.save(serie);
        }).orElseThrow(() -> new IllegalArgumentException("Invalid serie ID: " + id));
    }

    @DeleteMapping("/series/{id}")
    public void deleteSerie(@PathVariable Long id) {
        serieRepository.deleteById(id);
    }
}