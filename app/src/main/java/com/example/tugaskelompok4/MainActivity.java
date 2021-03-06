package com.example.tugaskelompok4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    Adapter adapter;
    List<User> userlist = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Tugas Kelompok 4");
        recyclerView = findViewById(R.id.rvdata);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        getData();
    }

    private void getData()
    {
        AndroidNetworking.get("https://reqres.in/api/users?page=2")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray list = response.getJSONArray("data");
                            for (int i = 0; i < list.length(); i++) {
                                JSONObject object = list.getJSONObject(i);
                                User user = new User(object.getString("email"), object.getString("first_name"), object.getString("last_name"), object.getString("avatar"));

                                userlist.add(user);
                            }
                            adapter = new Adapter(userlist);
                            recyclerView.setAdapter(adapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(ANError anError) {
                        adapter = new Adapter(Collections.singletonList(new User("LoremIpsum@gmail.com", "Lorem", "Ipsum", "null")));
                        recyclerView.setAdapter(adapter);
                    }
                });
    }

}