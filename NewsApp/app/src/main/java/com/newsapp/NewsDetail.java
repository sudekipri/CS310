package com.newsapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.newsapp.request.newslist.Item;
import com.newsapp.request.newslist.NewsList;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.atomic.AtomicReference;

public class NewsDetail extends AppCompatActivity {

    public static Menu topMenu;
    public static int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        ProgressBar progressBar = findViewById(R.id.commentSpinner);
        ImageView imageView = findViewById(R.id.imageView);
        TextView editText = findViewById(R.id.editTextTextMultiLine);
        TextView titleView = findViewById(R.id.titleView);
        TextView textDate = findViewById(R.id.textDate2);

        progressBar.setVisibility(getCurrentFocus().VISIBLE);

        id = (int) getIntent().getExtras().get("newId");
        AtomicReference<NewsList> detail = new AtomicReference<>(new NewsList());
        try {
            RequestQueue queue = Volley.newRequestQueue(this);
            String url = "http://10.3.0.14:8080/newsapp/getnewsbyid/" + id;

            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    response -> {
                        System.out.println(response);
                        detail.set(new Gson().fromJson(response, NewsList.class));
                        Item newsDetail = detail.get().getItems().get(0);
                        new DownloadImageTask(imageView).execute(newsDetail.getImage());
                        try {
                            titleView.setText(new String(newsDetail.getTitle().getBytes("ISO-8859-1"), "UTF-8"));
                            editText.setText(new String(newsDetail.getText().getBytes("ISO-8859-1"), "UTF-8"));
                        } catch (UnsupportedEncodingException e) {
                            titleView.setText(newsDetail.getTitle());
                            editText.setText(newsDetail.getText());
                        }

                        progressBar.setVisibility(View.GONE);
                        textDate.setText(newsDetail.getDate().substring(0, 10));
                        ActionBar actionBar = getSupportActionBar();
                        actionBar.setTitle(newsDetail.getCategoryName());

                    }, Throwable::printStackTrace);
            queue.add(stringRequest);

        } catch (Exception e) {
            e.printStackTrace();
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        topMenu = menu;
        MenuItem menuItem = menu.findItem(R.id.comments);

        menuItem.setOnMenuItemClickListener(x -> {
            Intent intent = new Intent(this, CommentActivity.class);
            intent.putExtra("newId", id);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            getApplicationContext().getApplicationContext().startActivity(intent);

            return true;
        });
        return super.onCreateOptionsMenu(menu);
    }
}