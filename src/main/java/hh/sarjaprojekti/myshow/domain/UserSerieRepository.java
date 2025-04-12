package hh.sarjaprojekti.myshow.domain;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

public interface UserSerieRepository extends CrudRepository<UserSerie, Long> {
    List<UserSerie> findByUser(User user);

    Optional<UserSerie> findByUserAndSerie(User user, Serie serie);

    List<UserSerie> findByUserAndUserStatus(User user, String userStatus);

    List<UserSerie> findByUserAndSerie_ShowStatus(User user, String showStatus);

    List<UserSerie> findByUserAndSerie_Genre(User user, Genre genre);

    List<UserSerie> findByUserAndUserStatusAndSerie_ShowStatus(User user, String userStatus, String showStatus);

    List<UserSerie> findByUserAndUserStatusAndSerie_Genre(User user, String userStatus, Genre genre);

    List<UserSerie> findByUserAndSerie_ShowStatusAndSerie_Genre(User user, String showStatus, Genre genre);

    List<UserSerie> findByUserAndUserStatusAndSerie_ShowStatusAndSerie_Genre(User user, String userStatus,
            String showStatus, Genre genre);
}