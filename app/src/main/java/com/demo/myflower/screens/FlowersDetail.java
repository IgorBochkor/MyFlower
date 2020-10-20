package com.demo.myflower.screens;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.demo.myflower.R;
import com.demo.myflower.pojo.Flower;
import com.squareup.picasso.Picasso;

public class FlowersDetail extends AppCompatActivity {
    private TextView textViewName;
    private TextView textDescription;
    private ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flowers_detail);
        textViewName = findViewById(R.id.textViewName);
        textDescription = findViewById(R.id.textViewDescription);
        image = findViewById(R.id.image);
        Intent intent = getIntent();
        Flower flower = (Flower)intent.getSerializableExtra("flower");
        textViewName.setText(flower.getName());
        textDescription.setText(flower.getInstructions());
        Picasso.with(getApplicationContext()).load("https://app-demo-web-august.web.app/pictures/" +
                flower.getPhoto()).into(image);
    }
}