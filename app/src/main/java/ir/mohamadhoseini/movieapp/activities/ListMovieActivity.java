package ir.mohamadhoseini.movieapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import java.util.List;

import ir.mohamadhoseini.movieapp.R;
import ir.mohamadhoseini.movieapp.adapters.MovieAdapter;
import ir.mohamadhoseini.movieapp.models.Movie;
import ir.mohamadhoseini.movieapp.models.MovieCollection;

public class ListMovieActivity extends AppCompatActivity {
    public static String LIST_MOVIE = "LIST_MOVIE";

    private RecyclerView recyclerViewMovie;
    private TextView txt_title;
    private GridLayoutManager layoutManager;
    private MovieAdapter adapter;
    private MovieCollection movieCollection;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_movie);
        recyclerViewMovie = findViewById(R.id.recyclerview_movie);
        txt_title = findViewById(R.id.txt_title);

        Intent intent = getIntent();
        if (getIntent().hasExtra(LIST_MOVIE)){

            //set data to adapter recyclerview
            movieCollection = (MovieCollection) intent.getSerializableExtra(LIST_MOVIE);
            txt_title.setText(movieCollection.getTitle());
            layoutManager = new GridLayoutManager(this,3);
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            recyclerViewMovie.setLayoutManager(layoutManager);
            recyclerViewMovie.setNestedScrollingEnabled(false);
            adapter = new MovieAdapter(movieCollection.getMovies(), this);
            recyclerViewMovie.setAdapter(adapter);
        }

    }
}