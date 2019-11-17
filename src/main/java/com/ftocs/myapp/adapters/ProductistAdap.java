package com.ftocs.myapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ftocs.myapp.R;
import com.ftocs.myapp.dataModels.ListProductItem;
import com.ftocs.myapp.viewHolders.CityListVH;
import com.bumptech.glide.Glide;

import java.util.List;

public class ProductistAdap extends RecyclerView.Adapter<CityListVH> {
    private Context context;
    private List<ListProductItem> citiesItemList;

    public ProductistAdap(Context context, List<ListProductItem> citiesItemList) {
        this.context = context;
        this.citiesItemList = citiesItemList;
    }

    @Override
    public CityListVH onCreateViewHolder(ViewGroup parent, int viewType) {

        //Called by the layoutManger to attach the view to adapter
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.temp_product_category, parent, false);
        CityListVH latestJobVH = new CityListVH(view);
        return latestJobVH;

    }

    @Override
    public void onBindViewHolder(CityListVH holder,final int position) {

        ListProductItem citiesItem = citiesItemList.get(position);



        holder.tv_Name.setText(Html.fromHtml(citiesItem.getDescription()).toString());



            Glide.with(context).load(citiesItem.getImage())
                    .thumbnail(0.5f)
                    .into(holder.imageView);







    }

    @Override
    public int getItemCount() {

        return (null != citiesItemList ? citiesItemList.size() : 0);
    }

    public void loadDATA(List<ListProductItem> stringList) {
        this.citiesItemList = stringList;
        notifyDataSetChanged();
    }
}
