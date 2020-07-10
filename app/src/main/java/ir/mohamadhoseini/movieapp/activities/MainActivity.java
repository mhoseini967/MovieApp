package ir.mohamadhoseini.movieapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import ir.mohamadhoseini.movieapp.R;
import ir.mohamadhoseini.movieapp.fragments.FragmentDownloads;
import ir.mohamadhoseini.movieapp.fragments.FragmentMain;

public class MainActivity extends AppCompatActivity {

    public static BottomNavigationView bottomNavigationView;
    public static FragmentManager fragmentManager;
    public static Fragment active;
    private FragmentMain fragmentMain = new FragmentMain();
    public static FragmentDownloads fragmentDownloads = new FragmentDownloads();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        active = fragmentMain;


        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().add(R.id.fragment_container, fragmentDownloads, "fragmentDownloads").hide(fragmentDownloads).commit();
        fragmentManager.beginTransaction().add(R.id.fragment_container,fragmentMain, "fragmentMain").commit();

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_home:

                        fragmentManager.beginTransaction().hide(active).show(fragmentMain).commit();
                        active = fragmentMain;
                        break;
                    case R.id.action_downloads:
                        fragmentManager.beginTransaction().hide(active).show(fragmentDownloads).commit();
                        active = fragmentDownloads;
                        break;

                }
                return true;
            }
        });
    }

    private void initViews() {
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
    }
}