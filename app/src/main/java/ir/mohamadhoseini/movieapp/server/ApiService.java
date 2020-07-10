package ir.mohamadhoseini.movieapp.server;

import com.google.gson.JsonObject;



import ir.mohamadhoseini.movieapp.models.Movie;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface ApiService {

    @GET("/?apikey=3e974fca")
    Call<Movie> getMovie(@Query("i") String id);

    @GET("/?apikey=3e974fca")
    Call<JsonObject> getListMovies(@Query("s") String search);

    @GET("/?apikey=3e974fca")
    Observable<JsonObject> getObservableListMovies(@Query("s") String search);
}
