package net.smallacademy.gridcycle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Categories extends AppCompatActivity {


    Button bags;
    Button electronic;
    Button jackets;
    Button jewlerie;
    Button sunglass;
    Button tshirt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        bags = findViewById(R.id.bags);
        electronic = findViewById(R.id.electronic);
        jackets = findViewById(R.id.jackets);
        jewlerie = findViewById(R.id.jewlery);
        sunglass = findViewById(R.id.sunglass);
        tshirt = findViewById(R.id.tshirt);

        bags.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent1 = new Intent(v.getContext(), MainActivity.class);
                intent1.putExtra("data", "https://cse491.000webhostapp.com/getProductsByCat.php?cat=Bags");
                v.getContext().startActivity(intent1);
            }
        });
        electronic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent1 = new Intent(v.getContext(), MainActivity.class);
                intent1.putExtra("data", "https://cse491.000webhostapp.com/getProductsByCat.php?cat=electronic");
                v.getContext().startActivity(intent1);
            }
        });
        jackets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent1 = new Intent(v.getContext(), MainActivity.class);
                intent1.putExtra("data", "https://cse491.000webhostapp.com/getProductsByCat.php?cat=Jacket");
                v.getContext().startActivity(intent1);
            }
        });
        jewlerie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent1 = new Intent(v.getContext(), MainActivity.class);
                intent1.putExtra("data", "https://cse491.000webhostapp.com/getProductsByCat.php?cat=Jewelry");
                v.getContext().startActivity(intent1);
            }
        });
        sunglass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent1 = new Intent(v.getContext(), MainActivity.class);
                intent1.putExtra("data", "https://cse491.000webhostapp.com/getProductsByCat.php?cat=Sunglass");
                v.getContext().startActivity(intent1);
            }
        });
        tshirt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent1 = new Intent(v.getContext(), MainActivity.class);
                intent1.putExtra("data", "https://cse491.000webhostapp.com/getProductsByCat.php?cat=Tshirt");
                v.getContext().startActivity(intent1);
            }
        });


    }

    public void backToCart(View view) {
        Intent intent1 = new Intent(this, CartActivity.class);
        startActivity(intent1);
    }

    public void backPressed(View view) {
        super.onBackPressed();

    }
}
