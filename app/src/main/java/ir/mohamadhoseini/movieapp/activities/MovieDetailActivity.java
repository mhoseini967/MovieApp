package ir.mohamadhoseini.movieapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

import ir.mohamadhoseini.movieapp.R;
import ir.mohamadhoseini.movieapp.database.MovieDatabase;
import ir.mohamadhoseini.movieapp.models.Movie;
import ir.mohamadhoseini.movieapp.server.ApiService;
import ir.mohamadhoseini.movieapp.server.ApiUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetailActivity extends AppCompatActivity {
    public static String IMDB_ID = "IMDB_ID";
    private ApiService apiService;
    private MovieDatabase movieDatabase;
    private ImageView img_poster;
    private TextView txt_awards,txt_writer,txt_actors,txt_plot,txt_imdb,txt_runtime,txt_title,txt_download;
    private CardView card_play,card_download;
    private View layout_content;
    private Dialog progressDialog;
    private Movie movie;
    private String imdb;
    private boolean isDownloaded;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        initViews();
        setProgressDialog();
        apiService = ApiUtils.getApiService();
        movieDatabase = MovieDatabase.getDatabase(this);

        //get id imdb by intent
        imdb = getIntent().getStringExtra(IMDB_ID);
        isDownloaded = movieDatabase.daoAccess().getMovie(imdb) != null;

        //check Movie is Download => getLocal or getServer
        if (isDownloaded){
            setContent(movieDatabase.daoAccess().getMovie(imdb));
            layout_content.setVisibility(View.VISIBLE);
        }else {
            getServerMovie();
        }

        card_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //save Movie to local database
                movieDatabase.daoAccess().insertMovie(movie);
                card_download.setEnabled(false);
                txt_download.setText("Downloaded");
                card_download.setAlpha(0.2f);
                Toast.makeText(MovieDetailActivity.this, "Movie Details Saved!", Toast.LENGTH_SHORT).show();

            }
        });
        card_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MovieDetailActivity.this, "Play Action!", Toast.LENGTH_SHORT).show();
            }
        });


    }
    private void initViews(){
        layout_content = findViewById(R.id.layout_content);
        layout_content.setVisibility(View.GONE);
        img_poster = findViewById(R.id.img_poster);
        txt_download = findViewById(R.id.txt_download);
        txt_awards = findViewById(R.id.txt_awards);
        txt_writer = findViewById(R.id.txt_writer);
        txt_actors = findViewById(R.id.txt_actors);
        txt_plot = findViewById(R.id.txt_plot);
        txt_imdb = findViewById(R.id.txt_imdb);
        txt_runtime = findViewById(R.id.txt_runtime);
        txt_title = findViewById(R.id.txt_title);
        card_play = findViewById(R.id.card_play);
        card_download = findViewById(R.id.card_download);
    }
    private void setProgressDialog (){
        progressDialog = new Dialog(MovieDetailActivity.this);
        progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        progressDialog.setContentView(R.layout.custom_dialog_loading);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


    }
    private void getServerMovie() {
        progressDialog.show();
        apiService.getMovie(imdb).enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                movie = response.body();

                setContent(movie);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        layout_content.setVisibility(View.VISIBLE);
                        progressDialog.dismiss();
                    }
                },500);

            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                Toast.makeText(MovieDetailActivity.this, "Fail", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();

            }
        });
    }
    private void setContent(Movie movie) {
        txt_title.setText(movie.getTitle());
        txt_runtime.setText(movie.getRuntime());
        txt_actors.setText(movie.getActors());
        txt_awards.setText(movie.getAwards());
        txt_imdb.setText(movie.getImdbRating());
        txt_plot.setText(movie.getPlot());
        txt_writer.setText(movie.getWriter());

        if (movieDatabase.daoAccess().getMovie(movie.getImdbID()) == null){
            card_download.setEnabled(true);
        }else {
            card_download.setEnabled(false);
            txt_download.setText("Downloaded");
            card_download.setAlpha(0.2f);

        }
        RequestOptions requestOptions = new RequestOptions();
        requestOptions = requestOptions.transforms(new CenterCrop(), new RoundedCorners(16));
        Glide.with(MovieDetailActivity.this)
                .load(movie.getPoster())
                .apply(requestOptions)
                .into(img_poster);
    }
}