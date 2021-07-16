package android.example.newzer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {


    private String LOG_TAG = MainActivity.class.getSimpleName();
    private String GUARDIAN_API = "https://content.guardianapis.com/";
    private String contentString = "No content";
    private List<Result> newsArraylist = null;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerView_news);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        loadNewsFromJson();
    }

    private void loadNewsFromJson() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GUARDIAN_API)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        GuardianNewsApi guardianNewsApi = retrofit.create(GuardianNewsApi.class);
        Call<NewsList> call = guardianNewsApi.getNewsLists();
        call.enqueue(new Callback<NewsList>() {
            @Override
            public void onResponse(Call<NewsList> call, Response<NewsList> response) {
//                if (!response.isSuccessful()) {
//                    Toast.makeText(context, "response is unsuccessful", Toast.LENGTH_SHORT).show();
//                    return;
//                }
                NewsList newsList = response.body();
                if (newsList == null) {
                    Log.d(LOG_TAG, "the newsList is empty");
                    return;
                } else {
                    Toast.makeText(MainActivity.this, "newsList is not empty ", Toast.LENGTH_SHORT).show();
                }
                newsArraylist = newsList.getResponse().getResults();
                if (newsArraylist == null) {
                    Toast.makeText(MainActivity.this, "newsArrayList is null ", Toast.LENGTH_SHORT).show();
                    return;
                }
                recyclerView.setAdapter(new NewsAdapter(MainActivity.this, newsArraylist));
            }

            @Override
            public void onFailure(Call<NewsList> call, Throwable t) {
                if (t instanceof IOException) {
                    Toast.makeText(MainActivity.this, "this is an actual network failure :( inform the user and possibly retry", Toast.LENGTH_SHORT).show();
                    // logging probably not necessary
                } else {
                    Toast.makeText(MainActivity.this, "conversion issue! big problems :(", Toast.LENGTH_SHORT).show();
                    // todo log to some central bug tracking service
                    Log.d(LOG_TAG, "conversion issue faced here");
                }
            }
        });
    }
}