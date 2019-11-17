package com.ftocs.myapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import com.ftocs.myapp.R;
import com.ftocs.myapp.utils.PriceListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class BookingsAdapter extends RecyclerView.Adapter<BookingsAdapter.ViewHolder> {
    PriceListener priceListener;
    private Context context;
    String selectedCityName;
    private int cittyIDSelected;
    private String price="0";
    float total;
    HashMap<String, String> intituteHashMap ;
    HashMap<String, String> priceHashMap ;
    ArrayList<String> list;
    ArrayList<Integer> countlist;
    boolean isHasDandi;
    public List<Boolean> booleans = new ArrayList<>();
    public List<Float> totalprice = new ArrayList<>();
    boolean IsDandi;
    public float grandtotal = 0;

    public BookingsAdapter(Context context, boolean isHasDandi, ArrayList<String> list,ArrayList<Integer> countlist,HashMap<String, String> intituteHashMap,
                           HashMap<String, String> priceHashMap,PriceListener priceListener) {
        this.context = context;
        this.list = list;
        this.countlist = countlist;
        this.intituteHashMap = intituteHashMap;
        this.priceHashMap = priceHashMap;
        this.isHasDandi=isHasDandi;
        this.priceListener = priceListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.custom_layout_rc, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        viewHolder.linear_without_dundee.setBackgroundResource(R.drawable.bg_border);
        IsDandi=false;
        booleans.add(i,IsDandi);
        if (isHasDandi) {
            viewHolder.linear_without_dundee.setVisibility(View.VISIBLE);
            viewHolder.linear_dundee.setVisibility(View.VISIBLE);
        } else {
            viewHolder.linear_without_dundee.setVisibility(View.GONE);
            viewHolder.linear_dundee.setVisibility(View.GONE);
        }

        viewHolder.linear_without_dundee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewHolder.linear_without_dundee.setBackgroundResource(R.drawable.bg_border);
                viewHolder.linear_dundee.setBackgroundResource(0);
                IsDandi = false;
                booleans.add(i,IsDandi);
            }
        });
        viewHolder.linear_dundee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewHolder.linear_without_dundee.setBackgroundResource(0);
                viewHolder.linear_dundee.setBackgroundResource(R.drawable.bg_border);
                IsDandi = true;
                booleans.add(i,IsDandi);

            }
        });


        viewHolder.remove_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeItem(i);
            }
        });

        setDataCitySpinner(list,viewHolder);

        // spinner work here
        viewHolder.spinner_city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                selectedCityName = viewHolder.spinner_city.getItemAtPosition(position).toString();
                for (String key : intituteHashMap.keySet()) {

                    if (intituteHashMap.get(key).equals(selectedCityName)) {
                        cittyIDSelected = Integer.parseInt(key);
                    }
                }
                for (String key : priceHashMap.keySet()) {

                    if (priceHashMap.get(key).equals(selectedCityName)) {
                        price = key;
                    }
                    viewHolder.tv_price.setText(price);
                    Log.d("dataaaaaaaa","data"+price);
                }
                // calculation price
                if (price!=null && !price.isEmpty() && viewHolder.et_qty.getText().toString()!=null && !viewHolder.et_qty.getText().toString().isEmpty()){
                    total = Float.parseFloat(price) * Float.parseFloat(viewHolder.et_qty.getText().toString());
                    grandtotal = grandtotal+total;
                }

                Log.d("priceend",""+grandtotal);
                priceListener.totalPrice(grandtotal);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return countlist.size();
    }

    private void removeItem(int position){
        countlist.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position,countlist.size());
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_price;
        LinearLayout linear_without_dundee;
        LinearLayout linear_dundee;
        Button btn_submit;
        EditText et_qty;
        Spinner spinner_city;
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

    private void setDataCitySpinner(ArrayList<String> stringArrayListCityName,ViewHolder view) {
        ArrayAdapter<String> classArrayadapter = new ArrayAdapter<String>(context, android.R.layout.simple_dropdown_item_1line, stringArrayListCityName);
        view.spinner_city.setAdapter(classArrayadapter);
    }

}
