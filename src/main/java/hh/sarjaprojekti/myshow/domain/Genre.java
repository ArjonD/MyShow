package hh.sarjaprojekti.myshow.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

@Entity
public class Genre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long genreid;
    private String name;

    @OneToMany(mappedBy = "genre")
    @JsonIgnore
    private List<Serie> series;

    public Genre() {
        this.genreid = null;
        this.name = null;
        this.series = null;
    }

    public Genre(String name) {
        this.name = name;
    }

    public Long getGenreid() {
        return genreid;
    }

    public void setGenreid(Long genreid) {
        this.genreid = genreid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Serie> getSeries() {
        return series;
    }

    public void setSeries(List<Serie> series) {
        this.series = series;
    }

    @Override
    public String toString() {
        return "Genre [genreid=" + genreid + ", name=" + name + "]";
    }
}
