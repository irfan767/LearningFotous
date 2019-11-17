package com.ftocs.myapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ftocs.myapp.R;
import com.ftocs.myapp.dataModels.ListItem;
import com.ftocs.myapp.dataModels.OrderListHistoryItem;
import com.ftocs.myapp.viewHolders.OrderListVH;
import com.bumptech.glide.Glide;

import java.util.List;

public class OrderListAdap extends RecyclerView.Adapter<OrderListVH> {
    private Context context;
    private List<ListItem> citiesItemList;

    public OrderListAdap(Context context, List<ListItem> citiesItemList) {
        this.context = context;
        this.citiesItemList = citiesItemList;
    }

    @Override
    public OrderListVH onCreateViewHolder(ViewGroup parent, int viewType) {

        //Called by the layoutManger to attach the view to adapter
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.temp_order_history, parent, false);
        OrderListVH latestJobVH = new OrderListVH(view);
        return latestJobVH;

    }

    @Override
    public void onBindViewHolder(OrderListVH holder,final int position) {



        holder.tv_Date.setText(citiesItemList.get(position).getDate()+"");
        holder.tv_OrderNo.setText("ORDER NO: "+citiesItemList.get(position).getOrderNo()+"");
        holder.tv_Price.setText("Total Price "+citiesItemList.get(position).getPrice());
        holder.tv_ProductName.setText("Product");
        holder.tv_Status.setText("STATUS:"+citiesItemList.get(position).getStatus()+"");
        holder.tv_Time.setText(citiesItemList.get(position).getTime()+"");




    }

    @Override
    public int getItemCount() {

        return (null != citiesItemList ? citiesItemList.size() : 0);
    }

//    public void loadDATA(List<OrderListHistoryItem> stringList) {
//        this.citiesItemList = stringList;
//        notifyDataSetChanged();
//    }
}
