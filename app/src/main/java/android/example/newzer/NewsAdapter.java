package android.example.newzer;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {

    private Context context;
    private List<Result> resultList;

    public NewsAdapter(Context context, List<Result> results) {
        this.context = context;
        this.resultList = results;
    }

    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.news_item, parent, false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsAdapter.NewsViewHolder holder, int position) {
        Result result = resultList.get(position);
        holder.newsTitle.setText(result.getWebTitle());
        holder.newsType.setText(result.getType());
        String PARSE_STRING = result.getWebPublicationDate();
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            DateTimeFormatter f = DateTimeFormatter.ISO_INSTANT;
            Instant instant = Instant.from(f.parse(PARSE_STRING));
            Date date = Date.from(instant);
            SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy");
            String dateToDisplay = dateFormat.format(date);
            holder.dateDetails.setText(dateToDisplay);
            SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
            String timeToDisplay = timeFormat.format(date);
            holder.timeDetails.setText(timeToDisplay);
        }
        String webIntent=result.getWebUrl();
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse(webIntent));
                context.startActivity(intent);
            }
        });
    }



    @Override
    public int getItemCount() {
        return resultList.size();
    }

    public class NewsViewHolder extends RecyclerView.ViewHolder {
        TextView newsType;
        TextView newsTitle;
        TextView dateDetails;
        TextView timeDetails;
        LinearLayout linearLayout;

        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            newsType = itemView.findViewById(R.id.news_type);
            newsTitle = itemView.findViewById(R.id.news_title);
            dateDetails = itemView.findViewById(R.id.date);
            timeDetails = itemView.findViewById(R.id.time);
            linearLayout=itemView.findViewById(R.id.news_item_ll);
        }
    }
}
