package ir.mohamadhoseini.movieapp.server;

public class ApiUtils {

    public static final String BASE_URL = "https://www.omdbapi.com";

    public static ApiService getApiService() {
        return RetrofitClient.getClient(BASE_URL).create(ApiService.class);
    }
}