package ir.mohamadhoseini.movieapp.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;
import ir.mohamadhoseini.movieapp.models.Movie;

@Dao
public interface DaoAccess {
    @Insert()
    void insertMovie(Movie movie);

    @Insert()
    void insertListMovie(List<Movie> list);

    @Query("SELECT * From `Movie` WHERE imdbID = :imdbID")
    Movie getMovie(String  imdbID);

    @Query("SELECT * FROM `Movie` ")
    List<Movie> getAllMovies();

    @Query("SELECT * FROM `Movie` ")
    LiveData<List<Movie>> getLiveDataAllMovies();


    @Query("DELETE From `Movie` WHERE imdbID = :imdbID")
    void deleteMovie(String imdbID);

}
