package com.example.retrofithe;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.example.retrofit.R;
import com.google.androidgamesdk.gametextinput.Listener;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    Button button;


    private TextView textViewResult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUI();



        textViewResult = findViewById(R.id.text_view_result);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://gorest.co.in/public/v1/")
                .addConverterFactory((GsonConverterFactory.create()))
                .build();
        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        Call<List<Post>> call = jsonPlaceHolderApi.getPost();
        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if(!response.isSuccessful())
                {
                    textViewResult.setText("Code: " + response.code());
                    return;
                }
                List<Post> posts = response.body();
                for(Post post :posts )
                {
                    String content = "";
                    content += "id:" +post.getId() + "\n";
                    content += "name" +post.getName() + "\n";
                    content += "email" +post.getEmail() + "\n";
                    content += "gender" +post.getGender() + "\n";
                    content += "status" +post.getStatus() + "\n";
                    textViewResult.append(content);
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {

            }
        });
    }

    private void initUI(){
        button=findViewById(R.id.btnEjecutar);
    }

}