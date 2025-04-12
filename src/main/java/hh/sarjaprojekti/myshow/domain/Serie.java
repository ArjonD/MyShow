package hh.sarjaprojekti.myshow.domain;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;

@Entity
public class Serie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String author;
    private int publicationYear;
    private String description;
    private int season;
    private String showStatus;
    private String userStatus;

    @ManyToOne
    @JoinColumn(name = "genre_id")
    private Genre genre;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "serie")
    private List<UserSerie> userSeries;

    public Serie() {
        this.id = null;
        this.title = null;
        this.author = null;
        this.publicationYear = 0;
        this.description = null;
        this.season = 0;
        this.showStatus = null;
        this.userStatus = null;
        this.genre = null;
    }

    public Serie(Long id, String title, String author, int publicationYear, String description, int season,
            String showStatus, String userStatus, Genre genre) {
        this.title = title;
        this.author = author;
        this.publicationYear = publicationYear;
        this.description = description;
        this.season = season;
        this.showStatus = showStatus;
        this.userStatus = userStatus;
        this.genre = genre;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(int publicationYear) {
        this.publicationYear = publicationYear;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getSeason() {
        return season;
    }

    public void setSeason(int season) {
        this.season = season;
    }

    public String getShowStatus() {
        return showStatus;
    }

    public void setShowStatus(String showStatus) {
        this.showStatus = showStatus;
    }

    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public List<UserSerie> getUserSeries() {
        return userSeries;
    }

    public void setUserSeries(List<UserSerie> userSeries) {
        this.userSeries = userSeries;
    }

    @Override
    public String toString() {
        return "Serie [id=" + id + ", title=" + title + ", author=" + author + ", publicationYear=" + publicationYear
                + ", description=" + description + ", season=" + season + ", showStatus=" + showStatus + ", userStatus="
                + userStatus + ", genre=" + genre + "]";
    }

}
