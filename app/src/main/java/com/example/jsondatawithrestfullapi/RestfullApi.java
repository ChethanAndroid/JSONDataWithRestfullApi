package com.example.jsondatawithrestfullapi;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.jsondatawithrestfullapi.RestfullPackage.InstanceClass;
import com.example.jsondatawithrestfullapi.RestfullPackage.InterfaceClass;
import com.example.jsondatawithrestfullapi.RestfullPackage.ModelClass;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RestfullApi extends AppCompatActivity {


    RecyclerView rcv;
    RestAdapter restAdapter;


    TextView pageid,per_page,total,total_pages;

    InterfaceClass interfaceClass;

    List<ModelClass.ArrayValue> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restfull_api);

        pageid = findViewById(R.id.rest_page_id);
        per_page = findViewById(R.id.rest_per_page_id);
        total = findViewById(R.id.rest_total_id);
        total_pages = findViewById(R.id.total_page_id);

        rcv = findViewById(R.id.rest_rcv_layout);
        rcv.setHasFixedSize(true);
        restAdapter = new RestAdapter();
        rcv.setAdapter(restAdapter);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getApplicationContext());
        rcv.setLayoutManager(manager);
        rcv.setItemAnimator(new DefaultItemAnimator());
        rcv.setNestedScrollingEnabled(false);




        interfaceClass = InstanceClass.getRetrofit().create(InterfaceClass.class);

        Call<ModelClass>call = interfaceClass.getModel();

        call.enqueue(new Callback<ModelClass>() {
            @Override
            public void onResponse(Call<ModelClass> call, Response<ModelClass> response) {

                ModelClass modelClass = response.body();

                System.out.println("response:"+response);
                System.out.println("modelclass:"+modelClass);

                System.out.println("page:"+modelClass.Page);
                System.out.println("Per_page:"+modelClass.Per_page);
                System.out.println("Total:"+modelClass.Total);
                System.out.println("Total_pages:"+modelClass.Total_pages);

                pageid.setText(modelClass.Page);
                per_page.setText(modelClass.Per_page);
                total.setText(modelClass.Total);
                total_pages.setText(modelClass.Total_pages);


                list = modelClass.data;

                restAdapter.notifyDataSetChanged();


            }

            @Override
            public void onFailure(Call<ModelClass> call, Throwable t) {

            }
        });












    }


    public class RestAdapter extends RecyclerView.Adapter<RestAdapter.AdHolder>{
        @NonNull
        @Override
        public AdHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(RestfullApi.this).inflate(R.layout.activity_restfull_api,viewGroup,false);
            AdHolder adHolder = new AdHolder(view);
            return adHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull AdHolder adHolder, int i) {

            adHolder.id.setText(list.get(i).Id);
            adHolder.name.setText(list.get(i).Name);
            adHolder.year.setText(list.get(i).Year);
            adHolder.value.setText(list.get(i).Pantone_value);
            adHolder.color.setText(list.get(i).Color);



        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public class AdHolder extends RecyclerView.ViewHolder{

            TextView id,name,year,value,color;

            public AdHolder(@NonNull View itemView) {
                super(itemView);

                id =itemView.findViewById(R.id.rest_rcv_id);
                name = itemView.findViewById(R.id.rest_rcv_name);
                year = itemView.findViewById(R.id.rest_rcv_year);
                value = itemView.findViewById(R.id.rest_rcv_value);
                color = itemView.findViewById(R.id.rest_rcv_color);
            }
        }
    }

}
