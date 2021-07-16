package android.example.newzer;


import retrofit2.Call;
import retrofit2.http.GET;

public interface GuardianNewsApi {
    String API_KEY="46935871-c03a-408b-b4bd-6c046a4a5aa9";
    @GET("search?page=1&q=economy&api-key="+API_KEY)
    Call<NewsList> getNewsLists();
}
