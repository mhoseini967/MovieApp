package ir.mohamadhoseini.movieapp.database;


import android.content.Context;

import androidx.annotation.MainThread;
import androidx.room.Database;
import androidx.room.Room;

import ir.mohamadhoseini.movieapp.models.Movie;

@MainThread
@Database(entities = {Movie.class},
        version = 1,
        exportSchema = false)

public abstract class MovieDatabase extends androidx.room.RoomDatabase {

    public abstract DaoAccess daoAccess();

    private static volatile MovieDatabase INSTANCE;

    public static MovieDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (MovieDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            MovieDatabase.class, "MovieDatabase")
                            .allowMainThreadQueries()
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}



