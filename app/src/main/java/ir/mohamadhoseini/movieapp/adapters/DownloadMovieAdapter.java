package ir.mohamadhoseini.movieapp.adapters;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

import ir.mohamadhoseini.movieapp.R;
import ir.mohamadhoseini.movieapp.activities.MovieDetailActivity;
import ir.mohamadhoseini.movieapp.database.MovieDatabase;
import ir.mohamadhoseini.movieapp.models.Movie;

public class DownloadMovieAdapter extends RecyclerView.Adapter<DownloadMovieAdapter.MovieViewHolder>  {

    private List<Movie> list = new ArrayList<>();
    private Activity context;

    public DownloadMovieAdapter(List<Movie> list, Activity context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item_movie, parent, false);
        MovieViewHolder holder = new MovieViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MovieViewHolder holder, final int position) {

        holder.txt_name.setText(list.get(position).getTitle());

        RequestOptions requestOptions = new RequestOptions();
        requestOptions = requestOptions.transforms(new CenterCrop(), new RoundedCorners(16));
        Glide.with(context)
                .load(list.get(position).getPoster())
                .apply(requestOptions)
                .into(holder.img_cover);

        holder.img_cover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //go to movie details page
                Intent intent = new Intent(context, MovieDetailActivity.class);
                intent.putExtra(MovieDetailActivity.IMDB_ID , list.get(position).getImdbID());
                context.startActivity(intent);

            }
        });
        holder.img_cover.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                // delete movie from local database
                MovieDatabase movieDatabase = MovieDatabase.getDatabase(context);
                movieDatabase.daoAccess().deleteMovie(list.get(position).getImdbID());
                Toast.makeText(context, "Item Deleted!", Toast.LENGTH_SHORT).show();
                return true;
            }
        });

    }

    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    static class MovieViewHolder extends RecyclerView.ViewHolder {

        View layout;
        ImageView img_cover;
        TextView txt_name;
        MovieViewHolder(View view) {
            super(view);
            layout = view.findViewById(R.id.layout);
            img_cover = view.findViewById(R.id.img_cover);
            txt_name = view.findViewById(R.id.txt_name);

        }
    }

    }
