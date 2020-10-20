package com.demo.myflower.adapter;
/* Created by Ihor Bochkor on 18.10.2020.
 */

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.demo.myflower.R;
import com.demo.myflower.pojo.Flower;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FlowerAdapter extends RecyclerView.Adapter<FlowerAdapter.FlowerViewHolder> {

    private List<Flower> flowers;
    private FlowerClickListener flowerClickListener;

    private FlowerClickListener getFlowerClickListener() {
        return flowerClickListener;
    }

    public void setFlowerClickListener(FlowerClickListener flowerClickListener) {
        this.flowerClickListener = flowerClickListener;
    }

    public List<Flower> getFlowers() {
        return flowers;
    }

    public void setFlowers(List<Flower> flowers) {
        this.flowers = flowers;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FlowerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_flower_layout, parent, false);
        return new FlowerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FlowerViewHolder holder, int position) {
        Flower flower = flowers.get(position);
        holder.textName.setText(flower.getName());
        holder.textCategory.setText(flower.getCategory());
        holder.textPrice.setText(flower.getPrice() + "$");
        Picasso.with(holder.itemView.getContext()).load("https://app-demo-web-august.web.app/pictures/" + flower.getPhoto())
                .into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return flowers.size();
    }

    class FlowerViewHolder extends RecyclerView.ViewHolder {

        private TextView textName;
        private TextView textCategory;
        private TextView textPrice;
        private ImageView imageView;

        public FlowerViewHolder(@NonNull View itemView) {
            super(itemView);
            textName = itemView.findViewById(R.id.textViewName);
            textCategory = itemView.findViewById(R.id.textCategory);
            textPrice = itemView.findViewById(R.id.textPrice);
            imageView = itemView.findViewById(R.id.imageFlower);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (flowerClickListener != null) {
                        flowerClickListener.onClick(getLayoutPosition());
                    }
                }
            });

        }
    }

    public Flower getSelectedFlower(int position) {
        return flowers.get(position);
    }

    public interface FlowerClickListener {
        void onClick(int position);
    }
}
