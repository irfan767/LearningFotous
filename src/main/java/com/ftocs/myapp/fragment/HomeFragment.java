package com.ftocs.myapp.fragment;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.ftocs.myapp.R;
import com.ftocs.myapp.dataModels.DashboardDM;
import com.ftocs.myapp.retrofit.ApiClient;
import com.ftocs.myapp.retrofit.ApiInterface;
import com.ftocs.myapp.uiActivities.ProductDetailInfoActivity;
import com.ftocs.myapp.utils.Constants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    SharedPreferences sharedPreferences;
    private String prefUserId;

    private ProgressBar progressBar;
    private ApiInterface apiInterface;
  

    private Call<DashboardDM> categoryDMCall;

    private TextView tv_PendingOrders;
    private TextView tv_DeliverdOrders;
    private TextView tv_CancelOrders;
    private TextView tv_ConfirmedOrders;
    ScrollView screen;



    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_home, container, false);

        tv_PendingOrders =view.findViewById(R.id.tv_PendingOrders);
        tv_DeliverdOrders =view.findViewById(R.id.tv_DeliverdOrders);
        tv_CancelOrders =view.findViewById(R.id.tv_CancelOrders);
        tv_ConfirmedOrders =view.findViewById(R.id.tv_ConfirmedOrders);
        progressBar =view.findViewById(R.id.progressBar);
        screen =view.findViewById(R.id.screen);

        sharedPreferences = getActivity().getSharedPreferences(Constants.MY_PREF_LOGIN, Context.MODE_PRIVATE);
        prefUserId = sharedPreferences.getString(Constants.PREF_USER_ID_KEY, Constants.NULL);
        getDashboardInfo(Integer.parseInt(prefUserId));

        return view;
    }

    public void getDashboardInfo(int id) {
        progressBar.setVisibility(View.VISIBLE);


        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        categoryDMCall = apiInterface.getdashboard(id);
        categoryDMCall.enqueue(new Callback<DashboardDM>() {
            @Override
            public void onResponse(Call<DashboardDM> call, Response<DashboardDM> response) {
                if (response.isSuccessful()) {
                    DashboardDM productInfoDM = response.body();
                    tv_PendingOrders.setText(productInfoDM.getDataHome().getPendingOrders()+"");
                    tv_DeliverdOrders.setText(productInfoDM.getDataHome().getDeliverdOrders()+"");
                    tv_CancelOrders.setText(productInfoDM.getDataHome().getCancelOrders()+"");
                    tv_ConfirmedOrders.setText(productInfoDM.getDataHome().getConfirmedOrders()+"");
                    screen.setVisibility(View.VISIBLE);


                    progressBar.setVisibility(View.GONE);

                } else {
                    Toast.makeText(getActivity(), "No Found", Toast.LENGTH_LONG).show();

                    progressBar.setVisibility(View.GONE);

                }

            }

            @Override
            public void onFailure(Call<DashboardDM> call, Throwable t) {
                Toast.makeText(getActivity(), "Network Problem", Toast.LENGTH_LONG).show();

                progressBar.setVisibility(View.GONE);

            }
        });
    }


}
