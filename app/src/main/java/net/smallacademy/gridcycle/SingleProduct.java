package net.smallacademy.gridcycle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import org.apache.commons.io.FileUtils;

public class SingleProduct extends AppCompatActivity {

    ImageView product_image;
    TextView title;
    TextView description;
    TextView price;

    Button addToCart;

    ArrayList<String> items;

    ArrayList<String> myArr;

    ArrayList<String> prevSession;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_product);


        product_image = findViewById(R.id.imageView);
        title = findViewById(R.id.title_view);
        description = findViewById(R.id.des_view);
        price = findViewById(R.id.price_view);


        addToCart = findViewById(R.id.addTocart);


        items = new ArrayList<>();

        Intent intent = getIntent();
        final String IntentData = intent.getStringExtra("id");

        Bundle extras = getIntent().getExtras();
        myArr= extras.getStringArrayList("data");

        Log.d("checkingsingle",myArr.toString());




        title.setText(myArr.get(3));
        price.setText(myArr.get(2));


        Picasso.get().load(myArr.get(1))
                .error(R.drawable.ic_aspect_ratio_black_24dp)
                .into(product_image);



        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readSession();
                if (prevSession.size() != 0) {
                    Toast.makeText(v.getContext(), "Clicked -> " + "Added to cart", Toast.LENGTH_SHORT).show();
                    readItems();
                    items.add(myArr.get(3) + "," + myArr.get(2));
                    saveItem();
                    Log.d("singleitem", items.toString());
                }
                else{
                    Toast.makeText(v.getContext(), "Please Login", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
    private void readItems(){


        File filesDir = getFilesDir();
        File toDoFile = new File(filesDir, "todo.txt");
        try {
            items = new ArrayList<String>(FileUtils.readLines(toDoFile));
        } catch (IOException e) {
            items = new ArrayList<String>();
            e.printStackTrace();
        }

    }

    private void saveItem(){
        File filesDir = getFilesDir();
        File toDoFile = new File(filesDir, "todo.txt");

        try {
            FileUtils.writeLines(toDoFile, items);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private void readSession(){


        File filesDir = getFilesDir();
        File toDoFile = new File(filesDir, "session.txt");
        try {
            prevSession = new ArrayList<String>(FileUtils.readLines(toDoFile));
        } catch (IOException e) {
            prevSession = new ArrayList<String>();
            e.printStackTrace();
        }

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
