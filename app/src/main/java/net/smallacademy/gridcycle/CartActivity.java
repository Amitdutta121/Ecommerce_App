package net.smallacademy.gridcycle;

import androidx.annotation.ArrayRes;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;import android.os.Bundle;
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

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {

    ListView list;
    ArrayList<String> items;
    ArrayAdapter<String> itemsAdapter;

    TextView total;

    Button checkout;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        list = findViewById(R.id.list);
        items = new ArrayList<String>();
        total = findViewById(R.id.total);
        checkout = findViewById(R.id.btn_checkout);

        readItems();

        itemsAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,items);
        list.setAdapter(itemsAdapter);

        setupListViewListener();

        total.setText("123");
//        calculateTotal();
        checker();




        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(v.getContext(), checkout.class);
                intent.putStringArrayListExtra("data", items);

                v.getContext().startActivity(intent);
            }
        });


    }

    public void checker(){
        int tut = 0;
        for (int i=0; i<items.size(); i++){
            tut += Integer.parseInt(items.get(i).split(",")[1].substring(1,items.get(i).split(",")[1].length()));
        }
        total.setText("Total : "+tut+"");
    }



    private void setupListViewListener()
    {
        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                items.remove(position);
                itemsAdapter.notifyDataSetChanged();
                saveItems();
                checker();
                return true;
            }
        });
    }
    private void readItems()
    {
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");
        try
        {
            items = new ArrayList<String>(FileUtils.readLines(todoFile));
        }
        catch(IOException e)
        {
            items = new ArrayList<String>();
            e.printStackTrace();
        }
    }
    private void saveItems()
    {
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");
        try
        {
            FileUtils.writeLines(todoFile, items);
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    public void backPressed(View view) {
        super.onBackPressed();
    }
}
