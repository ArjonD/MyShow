package hh.sarjaprojekti.myshow;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import hh.sarjaprojekti.myshow.domain.Genre;
import hh.sarjaprojekti.myshow.domain.GenreRepository;
import hh.sarjaprojekti.myshow.domain.Serie;
import hh.sarjaprojekti.myshow.domain.SerieRepository;
import hh.sarjaprojekti.myshow.domain.User;
import hh.sarjaprojekti.myshow.domain.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class MyshowApplication {

	private static final Logger log = LoggerFactory.getLogger(MyshowApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(MyshowApplication.class, args);
	}

	@Bean
	public CommandLineRunner demo(SerieRepository serieRepository, GenreRepository genreRepository) {
		return (args) -> {
			Genre drama = genreRepository.save(new Genre("Drama"));
			Genre sciFi = genreRepository.save(new Genre("Sci-Fi"));
			Genre comedy = genreRepository.save(new Genre("Comedy"));

			serieRepository.save(new Serie(null, "Breaking Bad", "Vince Gilligan", 2008,
					"A high school chemistry teacher turned methamphetamine producer.", 5, "Completed", null,
					drama));
			serieRepository.save(new Serie(null, "Stranger Things", "The Duffer Brothers", 2016,
					"A group of kids uncover supernatural mysteries in their small town.", 4, "Ongoing", null,
					sciFi));
			serieRepository.save(new Serie(null, "The Office", "Greg Daniels", 2005,
					"A mockumentary on a group of typical office workers.", 9, "Completed", null, comedy));
			serieRepository.save(new Serie(null, "The Mandalorian", "Jon Favreau", 2019,
					"A bounty hunter travels through the galaxy far, far away.", 3, "Ongoing", null, sciFi));
			serieRepository.save(new Serie(null, "Friends", "David Crane & Marta Kauffman", 1994,
					"Follows the personal and professional lives of six friends in New York City.", 10, "Completed",
					null, comedy));

			log.info("Genres added:");
			genreRepository.findAll().forEach(genre -> log.info(genre.toString()));

			log.info("Series added:");
			serieRepository.findAll().forEach(serie -> log.info(serie.toString()));
		};
	}

	@Bean
	public CommandLineRunner addDefaultUsers(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		return (args) -> {
			if (userRepository.findByUsername("admin").isEmpty()) {
				User admin = new User("admin", passwordEncoder.encode("admin"), "admin@example.com", "ADMIN");
				userRepository.save(admin);
			}
			if (userRepository.findByUsername("user").isEmpty()) {
				User user = new User("user", passwordEncoder.encode("user"), "user@example.com", "USER");
				userRepository.save(user);
			}
			if (userRepository.findByUsername("user1").isEmpty()) {
				User user = new User("user1", passwordEncoder.encode("user1"), "user1@example.com", "USER");
				userRepository.save(user);
			}
		};
	}

}
