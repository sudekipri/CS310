package com.newsapp;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.newsapp.request.PostCommentRequest;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class CreateCommentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_comment);

        Button button = findViewById(R.id.button);
        EditText editTextTextPersonName = findViewById(R.id.editTextTextPersonName);
        EditText editTextComment = findViewById(R.id.editTextComment);


        button.setOnClickListener(x -> {
            String comment = editTextComment.getText().toString();
            String name = editTextTextPersonName.getText().toString();
            String url = "http://10.3.0.14:8080/newsapp/savecomment";

            if (comment.equals("") || comment.equals("")) {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        if (!isFinishing()) {
                            new AlertDialog.Builder(CreateCommentActivity.this)
                                    .setTitle("ERROR")
                                    .setMessage("Please check Inputs")
                                    .setCancelable(false)
                                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                        }
                                    }).show();
                        }
                    }
                });


            } else {
                try {
                    int id = (int) getIntent().getExtras().get("newId");
                    PostCommentRequest request = new PostCommentRequest();
                    request.setName(name);
                    request.setText(comment);
                    request.setNewsId("" + id);

                    RequestQueue queue = Volley.newRequestQueue(this);
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.e("Rest response", response);
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            error.printStackTrace();
                            Log.e("Rest response", error.toString());
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() {
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("name", name);
                            params.put("text", comment);
                            params.put("news_id", "" + id);
                            return params;
                        }

                        @Override
                        public Map<String, String> getHeaders() throws AuthFailureError {
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("content-type", "application/json");
                            return params;
                        }

                        @Override
                        public byte[] getBody() throws AuthFailureError {
                            return new Gson().toJson(request).getBytes(StandardCharsets.UTF_8);
                        }
                    };
                    finish();
                    queue.add(stringRequest);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

}