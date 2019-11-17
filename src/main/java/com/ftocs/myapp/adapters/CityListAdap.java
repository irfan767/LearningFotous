package com.ftocs.myapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ftocs.myapp.R;
import com.ftocs.myapp.dataModels.ListItem;
import com.ftocs.myapp.viewHolders.CityListVH;
import com.bumptech.glide.Glide;

import java.util.List;

public class CityListAdap extends RecyclerView.Adapter<CityListVH> {
    private Context context;
    private List<ListItem> citiesItemList;

    public CityListAdap(Context context, List<ListItem> citiesItemList) {
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

        ListItem citiesItem = citiesItemList.get(position);



        holder.tv_Name.setText(Html.fromHtml(citiesItem.getCategoryName()).toString());



            Glide.with(context).load(citiesItem.getCategoryIcon())
                    .thumbnail(0.5f)
                    .into(holder.imageView);







    }

    @Override
    public int getItemCount() {

        return (null != citiesItemList ? citiesItemList.size() : 0);
    }

    public void loadDATA(List<ListItem> stringList) {
        this.citiesItemList = stringList;
        notifyDataSetChanged();
    }
}
