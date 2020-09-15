package net.smallacademy.gridcycle;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;

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

public class LoginActivity extends AppCompatActivity {

    Button loginButton;
    TextView alreadySignup;
    ProgressDialog progressDialog;
    String EmailHolder, PasswordHolder ;
    TextView dataEmail; // a text field where the data to be sent is entered
    TextView dataPassword; // a text field where the data to be sent is entered

    ArrayList<String> session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        loginButton = (Button) findViewById(R.id.buttonLogin);
        alreadySignup = (TextView) findViewById(R.id.already_signup);
        loginButton.setOnClickListener(mMyListener);
        alreadySignup.setOnClickListener(mMyListener);

        dataEmail = (TextView) findViewById(R.id.editTextEmail);
        dataPassword = (TextView) findViewById(R.id.editPassword);

        session = new ArrayList<String>();

        progressDialog = new ProgressDialog(LoginActivity.this);
    }
    private View.OnClickListener mMyListener = new View.OnClickListener() {
        public void onClick(View v) {
            switch (v.getId() /*to get clicked view id**/) {
                case R.id.buttonLogin:
                    regUser();
                    break;
                case R.id.already_signup:
                    Intent i = new Intent(LoginActivity.this, SignupActivity.class);
                    v.getContext().startActivity(i);
                    break;
                default:
                    break;
            }
        }
    };

    public void regUser(){
        progressDialog.setMessage("Please Wait, We are Inserting Your Data on Server");
        progressDialog.show();

        EmailHolder = dataEmail.getText().toString();
        PasswordHolder = dataPassword.getText().toString();

//        progressDialog.dismiss();



        String myurl = "https://cse491.000webhostapp.com/checkLogin.php?username="+ EmailHolder +"&password="+PasswordHolder;

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
                    String myResponse = response.body().string();
                    session.add(dataEmail.getText().toString());
                    saveItem();
                        LoginActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                startActivity(intent);
                            }
                        });


                }

            }
        });
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
