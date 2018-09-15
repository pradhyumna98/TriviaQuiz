package com.example.pradhyumna.triviaquiz.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pradhyumna.triviaquiz.Interface.ItemClickListener;
import com.example.pradhyumna.triviaquiz.R;

/**
 * Created by pradhyumna on 6/30/2018.
 */

public class RankingViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView txt_name , txt_score;
    public ImageView iv;
    private ItemClickListener itemclicklistener;
    public RankingViewHolder(View itemView) {
        super(itemView);
        txt_name = itemView.findViewById(R.id.txt_name);
        txt_score = itemView.findViewById(R.id.txt_score);
        iv=itemView.findViewById(R.id.imageView3);
        itemView.setOnClickListener(this);
    }

    public void setItemclicklistener(ItemClickListener itemclicklistener) {
        this.itemclicklistener = itemclicklistener;
    }

    @Override
    public void onClick(View view) {

        itemclicklistener.onclick(view , getAdapterPosition() , false);
    }
}
