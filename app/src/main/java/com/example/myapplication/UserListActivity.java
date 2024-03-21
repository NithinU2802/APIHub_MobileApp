package com.example.myapplication;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class UserListActivity extends AppCompatActivity {

    private ListView userListListView;
    private CustomArrayAdapter userListAdapter;
    private ArrayList<User> userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        userListListView = findViewById(R.id.userListListView);
        userList = new ArrayList<>();
        userListAdapter = new CustomArrayAdapter(this, userList);
        userListListView.setAdapter(userListAdapter);

        fetchUserList();

        ImageButton refreshButton = findViewById(R.id.refreshButton);
        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Collections.shuffle(userList);
                userListAdapter.notifyDataSetChanged();
            }
        });

        ImageButton logoutButton = findViewById(R.id.logoutButton);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });
    }

    private void fetchUserList() {
        new FetchUserListTask().execute();
    }

    private void logout() {
        getSharedPreferences("my_shared_pref", MODE_PRIVATE)
                .edit()
                .clear()
                .apply();

        startActivity(new Intent(UserListActivity.this, LoginActivity.class));
        finish();
    }

    private class FetchUserListTask extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {
            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url("https://reqres.in/api/users?page=2")
                    .build();

            try {
                Response response = client.newCall(request).execute();
                return response.body().string();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String responseString) {
            if (responseString != null) {
                try {
                    JSONObject jsonResponse = new JSONObject(responseString);
                    JSONArray usersArray = jsonResponse.getJSONArray("data");

                    userList.clear();

                    for (int i = 0; i < usersArray.length(); i++) {
                        JSONObject userObject = usersArray.getJSONObject(i);
                        String firstName = userObject.getString("first_name");
                        String lastName = userObject.getString("last_name");
                        String email = userObject.getString("email");
                        String avatar = userObject.getString("avatar");

                        User user = new User(firstName, lastName, email, avatar);
                        userList.add(user);
                    }

                    userListAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(UserListActivity.this, "Failed to parse JSON data.", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(UserListActivity.this, "Failed to fetch user list.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}


