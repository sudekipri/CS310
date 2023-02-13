package com.newsapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.newsapp.request.comments.Comment;

public class CommentActivity extends AppCompatActivity {
    public static Context context;
    public static int id;
    public static Comment comment;
    public static RecyclerView recyclerView;
    public static ProgressBar spinner;
    public static View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comments_item);

        view = getCurrentFocus();
        id = (int) getIntent().getExtras().get("newId");
        context = getApplicationContext();
        recyclerView = findViewById(R.id.recycler_view_comment);
        spinner = findViewById(R.id.commentSpinner);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Comments");

        getComments();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    public void onRestart() {
        super.onRestart();
        finish();
        startActivity(getIntent());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.comment_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.create_comment2);

        menuItem.setOnMenuItemClickListener(x -> {

            Intent intent = new Intent(this, CreateCommentActivity.class);
            intent.putExtra("newId", id);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            getApplicationContext().getApplicationContext().startActivity(intent);

            return true;
        });


        return super.onCreateOptionsMenu(menu);
    }


    public static void getComments() {
        try {
            spinner.setVisibility(view.VISIBLE);
            RequestQueue queue = Volley.newRequestQueue(context);
            String url = "http://10.3.0.14:8080/newsapp/getcommentsbynewsid/" + id;

            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    response -> {
                        comment = new Gson().fromJson(response, Comment.class);
                        if (comment != null) {
                            DividerItemDecoration divider = new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL);
                            recyclerView.addItemDecoration(divider);
                            CommentListAdapter adapter = new CommentListAdapter(comment);
                            recyclerView.setAdapter(adapter);
                            recyclerView.setLayoutManager(new GridLayoutManager(context.getApplicationContext(), 1));

                        }
                    }, Throwable::printStackTrace);
            queue.add(stringRequest);
            spinner.setVisibility(view.GONE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}