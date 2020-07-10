package ir.mohamadhoseini.movieapp.fragments;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import ariagp.amin.arialib.AriaLib;
import ir.mohamadhoseini.movieapp.R;
import ir.mohamadhoseini.movieapp.activities.MainActivity;
import ir.mohamadhoseini.movieapp.adapters.CardAdapter;
import ir.mohamadhoseini.movieapp.models.MediaCollection;
import ir.mohamadhoseini.movieapp.models.MediaCollectionType;
import ir.mohamadhoseini.movieapp.models.Movie;
import ir.mohamadhoseini.movieapp.models.MovieCollection;
import ir.mohamadhoseini.movieapp.server.ApiService;
import ir.mohamadhoseini.movieapp.server.ApiUtils;
import rx.Observable;
import rx.Subscriber;

public class FragmentMain extends Fragment {

    int i = 0;
    private String[] strings  = {"Batman" ,"Superman" ,"Avengers", "Sonic" , "Hulk" , "Mars"};
    private CardAdapter cardAdapter;
    private RecyclerView recyclerViewCollections;
    private ApiService apiService;
    private List<MediaCollection> mediaCollections = new ArrayList<>();
    private List<Observable<JsonObject>> observables = new ArrayList<>();
    private Type listType = new TypeToken<List<Movie>>() {}.getType();
    private View layout_no_internet;
    private Button btn_offline;
    private Dialog progressDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_main, container, false);
        initViews(rootview);
        setProgressDialog();

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        //check internet connection
        AriaLib ariaLib = new AriaLib(getActivity());
        if (ariaLib.Sharing().TestInterntConnection()){
            getMovies();
        }else {
            layout_no_internet.setVisibility(View.VISIBLE);
            recyclerViewCollections.setVisibility(View.GONE);
        }

        btn_offline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // navigate to downloads fragment
                MainActivity.bottomNavigationView.setSelectedItemId(R.id.action_downloads);
                MainActivity.fragmentManager.beginTransaction().hide(MainActivity.active).show(MainActivity.fragmentDownloads).commit();
                MainActivity.active = MainActivity.fragmentDownloads;
            }
        });



        return rootview;
    }
    private void initViews(View rootview){
        recyclerViewCollections = rootview.findViewById(R.id.recyclerview_collections);
        layout_no_internet = rootview.findViewById(R.id.layout_no_internet);
        btn_offline = rootview.findViewById(R.id.btn_downloads);
    }
    private void getMovies (){
        progressDialog.show();
        apiService = ApiUtils.getApiService();

        //create some collection and  requests
        for (String s :strings){
            observables.add(apiService.getObservableListMovies(s));
        }

        //merge multiply request by rx java
        Observable.merge(observables).subscribe(new Subscriber<JsonObject>() {
            @Override
            public void onCompleted() {
                Log.d("RxJAVACall", "DONE: --------->");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        cardAdapter = new CardAdapter(mediaCollections,getActivity());
                        recyclerViewCollections.setLayoutManager(new LinearLayoutManager(getContext()));
                        recyclerViewCollections.setAdapter(cardAdapter);
                        progressDialog.dismiss();
                    }
                },500);
            }
            @Override
            public void onError(Throwable e) {
                Log.d("RxJAVACall", "onNext: --------->" + e.toString());
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        layout_no_internet.setVisibility(View.VISIBLE);
                        recyclerViewCollections.setVisibility(View.GONE);
                        progressDialog.dismiss();
                    }
                },500);
            }

            @Override
            public void onNext(JsonObject jsonObject) {
                Log.d("RxJAVACall", "onNext: --------->" + jsonObject.toString());
                List<Movie> movieList = new Gson().fromJson(jsonObject.getAsJsonArray("Search"), listType);

                if (movieList != null && !movieList.isEmpty()){
                    mediaCollections.add(new MovieCollection(MediaCollectionType.COLLECTION_MOVIE_HORIZONTAL,strings[i],movieList));
                }

                i++;
            }
        });
    }
    private void setProgressDialog (){
        progressDialog = new Dialog(getActivity());
        progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        progressDialog.setContentView(R.layout.custom_dialog_loading);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
   }


}

