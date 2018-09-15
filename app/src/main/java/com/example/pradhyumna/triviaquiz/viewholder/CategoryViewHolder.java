package com.example.pradhyumna.triviaquiz.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pradhyumna.triviaquiz.Interface.ItemClickListener;
import com.example.pradhyumna.triviaquiz.R;

/**
 * Created by pradhyumna on 6/24/2018.
 */

public class CategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public TextView category_name;
    public ImageView category_image;

    private ItemClickListener itemcl;

     public CategoryViewHolder(View itemView) {
        super(itemView);

        category_image = itemView.findViewById(R.id.imgcategory);
        category_name = itemView.findViewById(R.id.category);

        itemView.setOnClickListener(this);

    }

    public void setItemcl(ItemClickListener itemcl) {
        this.itemcl = itemcl;
    }

    @Override
    public void onClick(View view) {
        itemcl.onclick(view , getAdapterPosition() , false);
    }
}
