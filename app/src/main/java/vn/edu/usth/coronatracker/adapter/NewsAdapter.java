package vn.edu.usth.coronatracker.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import vn.edu.usth.coronatracker.R;
import vn.edu.usth.coronatracker.model.NewsModel;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {
    private static final String TAG = "NEWS ADAPTER";
    private List<NewsModel> newsLists;
    private Context mContext;

    public NewsAdapter(Context mContext, ArrayList<NewsModel> newsLists) {
        this.mContext = mContext;
        this.newsLists = newsLists;
    }

    @NonNull
    @Override
    public NewsAdapter.NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View newsView = inflater.inflate(R.layout.news_item, parent, false);
        return new NewsAdapter.NewsViewHolder(newsView);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsAdapter.NewsViewHolder holder, int position) {
        NewsModel news = newsLists.get(position);
        holder.tvTitleNews.setText(news.getTitle());
        holder.tvDescNews.setText(news.getDesc()+ "Read more");
        holder.tvDateNews.setText(news.getDate());
        Picasso.get().load(news.getImage()).into(holder.imgNews);
        holder.cardView.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent intent =new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(news.getUrl()));
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return newsLists.size();
    }

    public class NewsViewHolder extends RecyclerView.ViewHolder {

        private CardView cardView;
        private TextView tvDateNews, tvDescNews, tvTitleNews;
        private ImageView imgNews;


        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.card_news);
            tvDateNews = itemView.findViewById(R.id.tvDateNews);
            tvDescNews = itemView.findViewById(R.id.tvDescNews);
            tvTitleNews = itemView.findViewById(R.id.tvTitleNews);
            imgNews = itemView.findViewById(R.id.imgNews);
        }
    }
}
