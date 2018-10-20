package com.fahimabrar.tracker;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.health.SystemHealthManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class LoginActivity extends AppCompatActivity {

    private EditText inputEmail, inputPassword;
    private ProgressBar progressBar;
    private Button btnSignup, btnLogin, btnReset;
    String id, pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("mypref", Context.MODE_PRIVATE);
        String tempId = sharedPref.getString("id","-1");

        if(!tempId.equals("-1")){
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }

        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        btnLogin = (Button) findViewById(R.id.btn_login);


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = inputEmail.getText().toString();
                final String password = inputPassword.getText().toString();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter ID!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                id = email;
                pass = password;

                MyAsyncTasks myAsyncTasks = new MyAsyncTasks();
                myAsyncTasks.execute();
            }
        });
    }

    public class MyAsyncTasks extends AsyncTask<String, String, String> {

        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // display a progress dialog for good user experiance
            progressDialog = new ProgressDialog(LoginActivity.this);
            progressDialog.setMessage("Please Wait");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {

            // implement API in background and store the response in current variable
            String current = "";
            String apiUrl="https://advisor-assistant.000webhostapp.com/login_app.php?id="+id+"&pass="+pass+"";

            try {
                URL url;
                HttpURLConnection urlConnection = null;
                try {
                    url = new URL(apiUrl);

                    urlConnection = (HttpURLConnection) url
                            .openConnection();

                    InputStream in = urlConnection.getInputStream();

                    InputStreamReader isw = new InputStreamReader(in);

                    int data = isw.read();
                    while (data != -1) {
                        current += (char) data;
                        data = isw.read();
                        System.out.print(current);

                    }
                    // return the data to onPostExecute method
                    return current;

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (urlConnection != null) {
                        urlConnection.disconnect();
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
                return "Exception: " + e.getMessage();
            }
            return current;
        }

        @Override
        protected void onPostExecute(String s) {

            Log.d("data", s.toString());
            String data = s.toString();
            // dismiss the progress dialog after receiving data from API
            //Toast.makeText(getApplicationContext(),data,Toast.LENGTH_LONG).show();
            if(!data.equals("[]")){
                try {
                    // JSON Parsing of data
                    JSONArray jsonArray = new JSONArray(s);

                    JSONObject oneObject = jsonArray.getJSONObject(0);
                    // Pulling items from the array
                    String name,email,tid,tel, image;
                    name = oneObject.getString("username");
                    email = oneObject.getString("email");
                    tid = oneObject.getString("tid");
                    tel = oneObject.getString("tel");
                    image = "https://advisor-assistant.000webhostapp.com/image/"+oneObject.getString("pic");

                    SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("mypref", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString("id", tid);
                    editor.putString("name", name);
                    editor.putString("email", email);
                    editor.putString("tel", tel);
                    editor.putString("image", image);
                    editor.apply();

                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    progressDialog.dismiss();
                    startActivity(intent);
                    finish();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else{
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(),"Invalid ID or Password!",Toast.LENGTH_LONG).show();
            }

        }

    }
}

