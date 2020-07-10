package ir.mohamadhoseini.movieapp.database;

import androidx.room.TypeConverter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.List;
import ir.mohamadhoseini.movieapp.models.Rating;

public class DatabaseModelConverter {

    @TypeConverter
    public static List<Rating> stringToRating(String json) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<Rating>>() {}.getType();
        return gson.fromJson(json, type);
    }

    @TypeConverter
    public static String RatingToString(List<Rating> list) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<Rating>>() {}.getType();
        return gson.toJson(list, type);
    }

}
