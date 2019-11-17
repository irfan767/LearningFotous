package com.ftocs.myapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.ftocs.myapp.R;
import com.ftocs.myapp.dataModels.ListOrderProductsItem;
import com.ftocs.myapp.dataModels.OrderListHistoryItem;

import java.util.List;

public class OrderHistoryAdapter extends RecyclerView.Adapter<OrderHistoryAdapter.ViewHolder>{
    private Context context;
    List<ListOrderProductsItem> listItem;

    public OrderHistoryAdapter(Context context, List<ListOrderProductsItem> listHistoryItems) {
        this.context = context;
        this.listItem = listHistoryItems;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_order_detail, viewGroup, false);
        return new OrderHistoryAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.et_qty.setText(listItem.get(i).getQuantity()+"");
        viewHolder.tv_price.setText(listItem.get(i).getPrice()+"");
        viewHolder.spinner_city.setText(listItem.get(i).getSize()+"");


        if (listItem.get(i).isHasDandi()) {
            viewHolder.linear_without_dundee.setVisibility(View.VISIBLE);
            viewHolder.linear_dundee.setVisibility(View.VISIBLE);
        } else {
            viewHolder.linear_without_dundee.setVisibility(View.GONE);
            viewHolder.linear_dundee.setVisibility(View.GONE);
        }

        if (listItem.get(i).isIsDandi()) {
            viewHolder.linear_dundee.setBackgroundResource(R.drawable.bg_border);
            viewHolder.linear_without_dundee.setBackgroundResource(0);


        } else {
            viewHolder.linear_without_dundee.setBackgroundResource(R.drawable.bg_border);
            viewHolder.linear_dundee.setBackgroundResource(0);
        }


    }

    @Override
    public int getItemCount() {
        return listItem.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_price;
        LinearLayout linear_without_dundee;
        LinearLayout linear_dundee;
        Button btn_submit;
        TextView et_qty;
        TextView spinner_city;
        ImageView remove_img;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_price = itemView.findViewById(R.id.tv_price);
            spinner_city = itemView.findViewById(R.id.spinner_city);
            et_qty = itemView.findViewById(R.id.et_qty);
            linear_without_dundee = itemView.findViewById(R.id.linear_without_dundee);
            linear_dundee = itemView.findViewById(R.id.linear_dundee);
            remove_img = itemView.findViewById(R.id.remove_order);
        }
    }
}
