package com.demo.myflower.screens;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
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
    private FlowerAdapter adapter;
    private FlowerViewModel flowerViewModel;
    List<Flower> flowers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycleView);
        flowers = new ArrayList<>();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new FlowerAdapter();
        adapter.setFlowers(flowers);
        recyclerView.setAdapter(adapter);

        flowerViewModel = ViewModelProviders.of(this).get(FlowerViewModel.class);
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
        flowerViewModel.loadData();

        adapter.setFlowerClickListener(new FlowerAdapter.FlowerClickListener() {
            @Override
            public void onClick(int position) {
                Flower selectedFlower = adapter.getSelectedFlower(position);
                Intent intent = new Intent(MainFlowerListActivity.this, FlowersDetail.class);
                intent.putExtra("flower", selectedFlower);
                startActivity(intent);
            }
        });
    }

}