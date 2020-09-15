package net.smallacademy.gridcycle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    Button catBtn;
    Button allBtn;

    Button loginbtn;
    Button logoutbtn;

    ArrayList<String> session;
    ArrayList<String> prevSession;
    TextView uname;
    TextView logout_text;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        catBtn = findViewById(R.id.catBtn);
        allBtn = findViewById(R.id.allBtn);
        loginbtn = findViewById(R.id.loginbtn);
        logoutbtn = findViewById(R.id.logoutbtn);
        uname = findViewById(R.id.uname);
        logout_text = findViewById(R.id.logout_text);

        readItems();
        Log.d("ccccccccccc",prevSession.toString());
        if (prevSession.size() != 0){
            uname.setText(prevSession.get(0));
        }else{
            logoutbtn.setVisibility(View.INVISIBLE);
            logout_text.setVisibility(View.INVISIBLE);

        }

        session = new ArrayList<String>();

        catBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(v.getContext(), Categories.class);
                v.getContext().startActivity(intent1);
            }
        });

        allBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(v.getContext(), MainActivity.class);
                intent1.putExtra("data", "https://cse491.000webhostapp.com/getProduct.php");
                v.getContext().startActivity(intent1);
            }
        });

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(v.getContext(), LoginActivity.class);
                v.getContext().startActivity(intent1);
            }
        });
        logoutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveItem();
                logoutbtn.setVisibility(View.INVISIBLE);
                logout_text.setVisibility(View.INVISIBLE);
                uname.setText("");
                Toast.makeText(getApplicationContext(),"Successfully Logged out", Toast.LENGTH_SHORT).show();

            }
        });



    }

    private void readItems(){


        File filesDir = getFilesDir();
        File toDoFile = new File(filesDir, "session.txt");
        try {
            prevSession = new ArrayList<String>(FileUtils.readLines(toDoFile));
        } catch (IOException e) {
            prevSession = new ArrayList<String>();
            e.printStackTrace();
        }

    }
    private void saveItem(){
        File filesDir = getFilesDir();
        File toDoFile = new File(filesDir, "session.txt");

        try {
            FileUtils.writeLines(toDoFile, session);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
