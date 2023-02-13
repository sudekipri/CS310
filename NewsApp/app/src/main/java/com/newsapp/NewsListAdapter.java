package com.newsapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.newsapp.request.newslist.Item;
import com.newsapp.request.newslist.NewsList;

import java.io.UnsupportedEncodingException;

public class NewsListAdapter extends RecyclerView.Adapter<NewsListAdapter.NewsHolder> {

    private NewsList news;


    public NewsListAdapter(NewsList news, Context context) {
        this.news = news;
    }

    @NonNull
    @Override
    public NewsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item, parent, false);
        return new NewsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsHolder holder, int position) {
        Item newsItem = news.getItems().get(position);
        holder.setData(newsItem);
    }

    @Override
    public int getItemCount() {
        if (news != null) {
            return news.getItems().size();
        } else {
            return 0;
        }
    }


    static class NewsHolder extends RecyclerView.ViewHolder {
        TextView dateText, descriptionText;
        ImageView imageView;
        View itemView;


        public NewsHolder(@NonNull View itemView) {
            super(itemView);
            dateText = itemView.findViewById(R.id.dateText);
            descriptionText = itemView.findViewById(R.id.descriptionText);
            imageView = itemView.findViewById(R.id.news_item_imageView);
            itemView.setClickable(true);
            this.itemView = itemView;
        }


        public void setData(Item newsItem) {
            this.dateText.setText(newsItem.getDate().substring(0, 10));
            try {
                this.descriptionText.setText(new String(newsItem.getTitle().getBytes("ISO-8859-1"), "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                this.descriptionText.setText(newsItem.getTitle());
            }
            new DownloadImageTask(this.imageView).execute(newsItem.getImage());

            itemView.setOnClickListener(x -> {

                Intent intent = new Intent(itemView.getContext(), NewsDetail.class);
                intent.putExtra("newId", newsItem.getId());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                itemView.getContext().getApplicationContext().startActivity(intent);

            });
        }


    }

}
