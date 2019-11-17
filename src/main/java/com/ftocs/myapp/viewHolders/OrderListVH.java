package com.ftocs.myapp.viewHolders;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ftocs.myapp.R;


public class OrderListVH extends RecyclerView.ViewHolder {
    public CardView card_city;
    public TextView tv_OrderNo;
    public TextView tv_ProductName;
    public TextView tv_Price;
    public TextView tv_Status;
    public TextView  tv_Date;
    public TextView  tv_Time;
    public OrderListVH(View itemView) {
        super(itemView);
//        card_city = itemView.findViewById(R.id.card_city);
        tv_OrderNo = itemView.findViewById(R.id.tv_OrderNo);
        tv_ProductName = itemView.findViewById(R.id.tv_ProductName);
        tv_Price = itemView.findViewById(R.id.tv_Price);
        tv_Status = itemView.findViewById(R.id.tv_Status);
        tv_Date = itemView.findViewById(R.id.tv_Date);
        tv_Time = itemView.findViewById(R.id.tv_Time);

    }
}
