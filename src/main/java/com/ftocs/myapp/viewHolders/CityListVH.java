package com.ftocs.myapp.viewHolders;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ftocs.myapp.R;


public class CityListVH extends RecyclerView.ViewHolder {
    public CardView card_city;
    public TextView tv_Name;
    public ImageView imageView;
    public CityListVH(View itemView) {
        super(itemView);
        card_city = itemView.findViewById(R.id.card_city);
        tv_Name = itemView.findViewById(R.id.tv_Name);
        imageView = itemView.findViewById(R.id.img_Url);
    }
}
