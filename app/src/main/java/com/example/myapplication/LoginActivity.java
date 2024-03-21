package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText emailEditText;
    private EditText passwordEditText;

    private SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME = "my_shared_pref";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_IS_LOGGED_IN = "is_logged_in";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);

        Button loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        // Check if user is already logged in
        if (isLoggedIn()) {
            startUserListActivity();
        }
    }

    private void login() {
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        new LoginTask().execute(email, password);
    }

    private class LoginTask extends AsyncTask<String, Void, Boolean> {
        @Override
        protected Boolean doInBackground(String... params) {
            String email = params[0];
            String password = params[1];

            OkHttpClient client = new OkHttpClient();

            MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
            JSONObject jsonBody = new JSONObject();
            try {
                jsonBody.put("email", email);
                jsonBody.put("password", password);
            } catch (JSONException e) {
                e.printStackTrace();
                return false;
            }
            RequestBody body = RequestBody.create(mediaType, jsonBody.toString());

            Request request = new Request.Builder()
                    .url("https://reqres.in/api/login")
                    .post(body)
                    .build();

            try {
                Response response = client.newCall(request).execute();
                if (response.isSuccessful()) {
                    // Save email and login status in SharedPreferences
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(KEY_EMAIL, email);
                    editor.putBoolean(KEY_IS_LOGGED_IN, true);
                    editor.apply();
                }
                return response.isSuccessful();
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean success) {
            if (success) {
                startUserListActivity();
            } else {
                Toast.makeText(LoginActivity.this, "Login failed. Please try again.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void startUserListActivity() {
        startActivity(new Intent(LoginActivity.this, UserListActivity.class));
        finish(); // Close the LoginActivity to prevent returning to it with back button
    }

    private boolean isLoggedIn() {
        return sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false);
    }
}
