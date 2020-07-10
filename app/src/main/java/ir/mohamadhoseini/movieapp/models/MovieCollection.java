package ir.mohamadhoseini.movieapp.models;

import java.io.Serializable;
import java.util.List;

public class MovieCollection extends MediaCollection implements Serializable {
    private String title;
    private List<Movie> movies;

    public MovieCollection(int type,String title, List<Movie> movies) {
        super(type);
        this.title = title;
        this.movies = movies;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }

}
