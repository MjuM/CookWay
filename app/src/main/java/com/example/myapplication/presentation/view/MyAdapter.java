package com.example.myapplication.presentation.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Random;


import android.view.View.OnClickListener;

import com.example.myapplication.R;
import com.example.myapplication.presentation.model.Food;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private  List<Food> values;
    private OnItemClickListener listener;
    public interface OnItemClickListener{
        void onItemClick(Food item);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView txtHeader;
        public TextView txtFooter;
        public View layout;
        public ImageView imageView;
        public ViewHolder(View v) {
            super(v);
            layout = v;
            txtHeader = (TextView) v.findViewById(R.id.firstLine);
            txtFooter = (TextView) v.findViewById(R.id.secondLine);
            imageView = (ImageView) v.findViewById(R.id.icon);
        }
    }

    public void add(int position, Food item) {
        values.add(position, item);
        notifyItemInserted(position);
    }

    public void remove(int position) {
        values.remove(position);
        notifyItemRemoved(position);
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(List<Food> myDataset, OnItemClickListener listener) {
        this.values = myDataset;
        this.listener = listener;
    }
    public MyAdapter(List<Food> myDataset){
        this.values = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(
                parent.getContext());
        View v =
                inflater.inflate(R.layout.row_layout, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        final Food currentIngr = values.get(position);

        if(currentIngr.getId() < 6 ) {

            holder.txtHeader.setText(currentIngr.getName());


            holder.txtFooter.setText(Integer.toString(currentIngr.getCost()) + " €");
            String image;
            int ModImage;
            int i;


            if (currentIngr.getId() == 1) {
                holder.imageView.setImageResource(R.mipmap.ic_tab1);
            }
            if (currentIngr.getId() == 2) {
                holder.imageView.setImageResource(R.mipmap.ic_tab2);
            }
            if (currentIngr.getId() == 3) {
                holder.imageView.setImageResource(R.mipmap.ic_tab3);
            }
            if (currentIngr.getId() == 4) {
                holder.imageView.setImageResource(R.mipmap.ic_tab4);
            }
            if (currentIngr.getId() == 5) {
                holder.imageView.setImageResource(R.mipmap.ic_tab5);
            }
            holder.itemView.setOnClickListener(new View.OnClickListener(){
                @Override public void onClick(View v){
                    listener.onItemClick(currentIngr);
                }
            });

        }else{
            holder.txtHeader.setText(currentIngr.getName());
            holder.txtFooter.setText("Repas aleatoire");


            holder.imageView.setImageResource(R.mipmap.ic_random);
            int i;



            holder.itemView.setOnClickListener(new View.OnClickListener(){
                @Override public void onClick(View v){
                    Food RandomIngr = new Food();
                    Random random = new java.util.Random();
                    int randomNumber = random.nextInt(5+ 1);
                    int i;
                    for(i = 0 ; i < 5 ; i++){
                        if(values.get(i).getId() == randomNumber){
                            RandomIngr = values.get(i);
                            break;
                        }
                    }
                    listener.onItemClick(RandomIngr);
                }
            });

        }



    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return values.size();
    }











}