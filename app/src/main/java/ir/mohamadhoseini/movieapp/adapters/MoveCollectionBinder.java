package ir.mohamadhoseini.movieapp.adapters;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;

import ir.mohamadhoseini.movieapp.R;
import ir.mohamadhoseini.movieapp.activities.ListMovieActivity;
import ir.mohamadhoseini.movieapp.models.MediaCollection;
import ir.mohamadhoseini.movieapp.models.MovieCollection;

public class MoveCollectionBinder extends CardController {

    private final Activity mContext;
    private MovieCollection movieCollection;
    private View view;
    private RecyclerView recyclerView;
    private View layout_more;
    private TextView txt_title;
    private LinearLayoutManager layoutManager;
    private MovieAdapter adapter;

    public MoveCollectionBinder(View view, Activity context, MediaCollection collection) {
        this.mContext = context;
        this.view = view;
        this.movieCollection = (MovieCollection) collection;
        layout_more =(View) view.findViewById(R.id.layout_more);
        recyclerView =(RecyclerView) view.findViewById(R.id.recyclerview);
        txt_title =(TextView) view.findViewById(R.id.txt_title);

    }

    @Override
    public void onCreate() {
        super.onCreate();
        txt_title.setText(movieCollection.getTitle());
        initRecyclerView();

        layout_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "Go to Page:" + movieCollection.getTitle(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(mContext, ListMovieActivity.class);
                intent.putExtra(ListMovieActivity.LIST_MOVIE, movieCollection);
                mContext.startActivity(intent);
            }
        });

    }

    private void initRecyclerView(){
        layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setNestedScrollingEnabled(false);
        adapter = new MovieAdapter(movieCollection.getMovies(), mContext);
        recyclerView.setAdapter(adapter);

    }


}
