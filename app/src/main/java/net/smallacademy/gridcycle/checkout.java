package net.smallacademy.gridcycle;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class checkout extends AppCompatActivity {
    TextView email;
    TextView address;
    TextView phoneno;
    String paymentMethod;
    Button proceedBtn;
    String[] spinnerData = {"","COD","Bkash","Card","Other"};
    ProgressDialog progressDialog;

    Spinner spinner1;

    ArrayList<String> session;
    ArrayList<String> myArr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        progressDialog = new ProgressDialog(checkout.this);

        spinner1 = findViewById(R.id.spinner1);
        email = findViewById(R.id.email);
        address = findViewById(R.id.address);
        phoneno = findViewById(R.id.phoneno);
        proceedBtn = findViewById(R.id.proceed);
        session = new ArrayList<String>();


        //getting products from the last activity
        Intent intent = getIntent();

        Bundle extras = getIntent().getExtras();
        myArr= extras.getStringArrayList("data");


        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(checkout.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.checkoutMethod));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(myAdapter);

        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                paymentMethod = spinnerData[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        proceedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.setMessage("Please Wait, We are Inserting Your Data on Server");
                progressDialog.show();

                readItems();
                String uname = session.get(0);
                String EmailHolder = email.getText().toString();
                String addressHolder = address.getText().toString();
                String phoneNo = phoneno.getText().toString();
                String productsHolder = myArr.toString();

                String myurl = "https://cse491.000webhostapp.com/checkout.php?email="+ EmailHolder +"&uname="+ uname +"&phoneno="+ phoneNo+"&address="+addressHolder +"&pmethod="+ paymentMethod+"&products="+productsHolder;
                Log.d("dargggg", myurl);
                OkHttpClient okHttpClient = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(myurl)
                        .build();
                okHttpClient.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                        e.printStackTrace();
                        progressDialog.dismiss();
                        Log.d("debaaaa","Invalid request");
                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        if (response.isSuccessful()){
                            progressDialog.dismiss();
                            Log.d("dargggg","Success");
                            String myResponse = response. body().string();
                            checkout.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Intent intent = new Intent(checkout.this, SuccessPage.class);
                                    startActivity(intent);
                                }
                            });



                        }

                    }
                });
            }
        });



    }
    private void readItems(){


        File filesDir = getFilesDir();
        File toDoFile = new File(filesDir, "session.txt");
        try {
            session = new ArrayList<String>(FileUtils.readLines(toDoFile));
        } catch (IOException e) {
            session = new ArrayList<String>();
            e.printStackTrace();
        }

    }

    public void backPressed(View view) {
        super.onBackPressed();
    }
}
