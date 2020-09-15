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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SignupActivity extends AppCompatActivity {

    Button requestButton;
    TextView alreadySignin;
    TextView dataName; // a text field to display the request response
    TextView dataEmail; // a text field where the data to be sent is entered
    TextView dataPassword; // a text field where the data to be sent is entered
    RequestQueue requestQueue;
    ProgressDialog progressDialog;
    String NameHolder, EmailHolder, PasswordHolder ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);


        requestButton = (Button) findViewById(R.id.buttonSubmit);
        alreadySignin = (TextView) findViewById(R.id.already_signin);
        requestButton.setOnClickListener(mMyListener);
        alreadySignin.setOnClickListener(mMyListener);

        dataName = (TextView) findViewById(R.id.editTextName);
        dataEmail = (TextView) findViewById(R.id.editTextEmail);
        dataPassword = (TextView) findViewById(R.id.editPassword);



        progressDialog = new ProgressDialog(SignupActivity.this);

    }

    private View.OnClickListener mMyListener = new View.OnClickListener() {
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.buttonSubmit:
                    regUser();
                    break;
                case R.id.already_signin:
                    Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(i);
                    break;
                default:
                    break;
            }
        }
    };
    public void regUser(){
        progressDialog.setMessage("Please Wait, We are Inserting Your Data on Server");
        progressDialog.show();

        NameHolder = dataName.getText().toString();
        EmailHolder = dataEmail.getText().toString();
        PasswordHolder = dataPassword.getText().toString();


        String myurl = "https://cse491.000webhostapp.com/registerUser.php?username="+ NameHolder+"&email="+ EmailHolder+"&password="+PasswordHolder;

        Log.d("testdebug",myurl);
        OkHttpClient httpClient = new OkHttpClient();

        Request request = new Request.Builder()
                .url(myurl)
                .build();
        httpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                progressDialog.dismiss();
                Log.d("testdebug","This is not working");
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful()){
                    progressDialog.dismiss();
                    Log.d("testdebug","response successful");

                    String result= response.body().string().toString();
                    Log.d("testdebug",result);

//                    if(result.equalsIgnoreCase("User registered successfully")){
//                        Log.d("testdebug","OMG working");
//                    }
                    SignupActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                            startActivity(intent);
                        }
                    });
                }

            }
        });


    }
    public boolean checkString(String  s1, String s2){
        for (int i=0; i<s1.length(); i++){
            if (s1.charAt(i) != s2.charAt(i)){
                return false;
            }
        }
        return true;
    }

}
