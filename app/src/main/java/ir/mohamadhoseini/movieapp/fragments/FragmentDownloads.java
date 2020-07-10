package ir.mohamadhoseini.movieapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ir.mohamadhoseini.movieapp.R;
import ir.mohamadhoseini.movieapp.adapters.DownloadMovieAdapter;
import ir.mohamadhoseini.movieapp.adapters.MovieAdapter;
import ir.mohamadhoseini.movieapp.database.MovieDatabase;
import ir.mohamadhoseini.movieapp.models.Movie;
import ir.mohamadhoseini.movieapp.models.MovieCollection;

public class FragmentDownloads extends Fragment {


    private RecyclerView recyclerViewMovie;
    private GridLayoutManager layoutManager;
    private DownloadMovieAdapter adapter;
    private MovieDatabase movieDatabase;
    private View view_no_data;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_downloads, container, false);
        initViews(rootview);

        // use liveData for handle lifecycle and reset list local movie
        movieDatabase = MovieDatabase.getDatabase(getActivity());
        movieDatabase.daoAccess().getLiveDataAllMovies().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {
                if (movies.isEmpty()){
                    view_no_data.setVisibility(View.VISIBLE);
                    recyclerViewMovie.setVisibility(View.GONE);
                }else {
                    view_no_data.setVisibility(View.GONE);
                    recyclerViewMovie.setVisibility(View.VISIBLE);
                    layoutManager = new GridLayoutManager(getActivity(),3);
                    layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                    recyclerViewMovie.setLayoutManager(layoutManager);
                    recyclerViewMovie.setNestedScrollingEnabled(false);
                    adapter = new DownloadMovieAdapter(movies, getActivity());
                    recyclerViewMovie.setAdapter(adapter);
                }

            }
        });


        return rootview;
    }
    private void initViews(View rootview){
        view_no_data = rootview.findViewById(R.id.view_no_data);
        recyclerViewMovie = rootview.findViewById(R.id.recyclerview_movie);

    }

}

