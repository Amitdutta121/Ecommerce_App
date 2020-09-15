package net.smallacademy.gridcycle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.app.VoiceInteractor;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    RecyclerView dataList;
    List<String> titles;
    List<String> images;
    List<String> price;
    List<String> ids;
    Adapter adapter;

    ProgressDialog progressDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dataList = findViewById(R.id.dataList);

        titles = new ArrayList<>();
        images = new ArrayList<>();
        price = new ArrayList<>();
        ids = new ArrayList<>();

        progressDialog = new ProgressDialog(MainActivity.this);

        progressDialog.setMessage("Please Wait, Retrieving data from server");
        progressDialog.show();


        Intent intent = getIntent();
        String url = intent.getStringExtra("data");

        RequestQueue queue = Volley.newRequestQueue(this);

// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        try {
                            JSONArray obj = new JSONArray(response.toString());

                            for (int i = 0; i < 50; i++) {
                                JSONObject product = obj.getJSONObject(i);
                                String title = product.getString("title");
                                String pri = product.getString("price");
                                String id = product.getString("id");
                                String category = product.getString("category");
                                String img = product.getString("img");

//                                Log.d("JSON data", title);
//                                Log.d("JSON data", pri);
//                                Log.d("JSON data", img);

                                titles.add(title);
                                images.add("https://cse491.000webhostapp.com/uploads/"+img);
                                price.add("$" + pri);
                                ids.add(id);


                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

// Add the request to the RequestQueue.
        queue.add(stringRequest);


//        titles.add("First Item");
//        titles.add("Second Item");
//        titles.add("Third Item");
//        titles.add("Fourth Item");
//        titles.add("First Item");
//        titles.add("Second Item");
//        titles.add("Third Item");
//        titles.add("Fourth Item");

//        images.add(R.drawable.ic_airline_seat_flat_angled_black_24dp);
//        images.add(R.drawable.ic_airplanemode_active_black_24dp);
//        images.add(R.drawable.ic_brightness_1_black_24dp);
//        images.add(R.drawable.ic_build_black_24dp);
//        images.add(R.drawable.ic_airline_seat_flat_angled_black_24dp);
//        images.add(R.drawable.ic_airplanemode_active_black_24dp);
//        images.add(R.drawable.ic_brightness_1_black_24dp);
//        images.add(R.drawable.ic_build_black_24dp);

//        images.add("https://www.google.com/images/branding/googlelogo/1x/googlelogo_color_272x92dp.png");
//        images.add("https://picsum.photos/200/300");
//        images.add("https://cse491.000webhostapp.com/uploads/63_180694.jpg");
//        images.add("http://i.imgur.com/DvpvklR.png");
//        images.add("http://i.imgur.com/DvpvklR.png");
//        images.add("http://i.imgur.com/DvpvklR.png");
//        images.add("http://i.imgur.com/DvpvklR.png");
//        images.add("http://i.imgur.com/DvpvklR.png");
//
//        price.add("$125");
//        price.add("$18");
//        price.add("$100");
//        price.add("$10");
//        price.add("$700");
//        price.add("$200");
//        price.add("$100");
//        price.add("$1200");

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                progressDialog.dismiss();
                //Do something after 100ms
                adapter = new Adapter(MainActivity.this, titles, images, price,ids);

                GridLayoutManager gridLayoutManager = new GridLayoutManager(MainActivity.this, 2, GridLayoutManager.VERTICAL, false);
                dataList.setLayoutManager(gridLayoutManager);
                dataList.setAdapter(adapter);


            }
        }, 10000);




    }

    public void backPressed(View view) {
        super.onBackPressed();
    }

    public void backToCart(View view) {
        Log.d("ddddd","Image button pressed");
        Intent intent1 = new Intent(view.getContext(), CartActivity.class);
        view.getContext().startActivity(intent1);
    }
}
