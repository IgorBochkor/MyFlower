package com.demo.myflower.screens;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.demo.myflower.R;
import com.demo.myflower.pojo.Flower;

import java.util.ArrayList;
import java.util.List;

import com.demo.myflower.adapter.FlowerAdapter;

public class MainFlowerListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FlowerAdapter adapter = new FlowerAdapter();
    private FlowerViewModel flowerViewModel;
    List<Flower> flowers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        flowerViewModel = ViewModelProviders.of(this).get(FlowerViewModel.class);
        initViews();
        initListeners();

        adapter.setFlowerClickListener(new FlowerAdapter.FlowerClickListener() {
            @Override
            public void onClick(Flower flower) {
                Intent intent = new Intent(MainFlowerListActivity.this, FlowersDetail.class);
                intent.putExtra("flower", flower);
                startActivity(intent);
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void initListeners() {
        flowerViewModel.getFlowers().observe(this, new Observer<List<Flower>>() {
            @Override
            public void onChanged(List<Flower> flowers) {
                adapter.setFlowers(flowers);
            }
        });
        flowerViewModel.getErrors().observe(this, new Observer<Throwable>() {
            @Override
            public void onChanged(Throwable throwable) {
                if (throwable != null) {
                    Toast.makeText(MainFlowerListActivity.this
                            , "Помилка завантаження даних. Перевірте наявність доступу до мережі інтернет"
                            , Toast.LENGTH_SHORT).show();
                    flowerViewModel.clearErrors();
                }
            }
        });
    }

    private void initViews() {
        recyclerView = findViewById(R.id.rv_flowers);
        flowers = new ArrayList<>();
        adapter.setFlowers(flowers);
        recyclerView.setAdapter(adapter);
    }

}