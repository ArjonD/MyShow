package hh.sarjaprojekti.myshow.domain;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface SerieRepository extends CrudRepository<Serie, Long> {
    List<Serie> findByGenre(Genre genre);

    List<Serie> findByShowStatus(String showStatus);

    List<Serie> findByGenreAndShowStatus(Genre genre, String showStatus);
}
