package com.example.jsondatawithrestfullapi;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    TextView page,per_page,total,total_page;

    RecyclerView recyclerView;
    Adapter adapter;

    ProgressDialog progressDialog;

    List<String>IdList = new ArrayList<>();
    List<String>NameList = new ArrayList<>();
    List<String>YearList = new ArrayList<>();
    List<String>ColorList = new ArrayList<>();
    List<String>ValueList = new ArrayList<>();

    HttpURLConnection connection = null;
    StringBuilder builder = null;

    String jsonString = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        page = findViewById(R.id.page_id);
        per_page = findViewById(R.id.per_page_id);
        total = findViewById(R.id.total_id);
        total_page = findViewById(R.id.total_page_id);

        recyclerView = findViewById(R.id.main_rcv_id);

        recyclerView.setHasFixedSize(true);
        adapter =  new Adapter();
        recyclerView.setAdapter(adapter);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(manager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setNestedScrollingEnabled(false);


        new FetchJsonData().execute();


    }

    public class FetchJsonData extends AsyncTask<String,Integer,Boolean>{
        Boolean asdf = false;

        @Override
        protected void onPreExecute() {
//            super.onPreExecute();
            progressDialog = ProgressDialog.show(MainActivity.this,"Fetching JsonData","Please Wait",true);
        }


        @Override
        protected Boolean doInBackground(String... strings) {


            try {
                urlConnection();

                if (jsonString!=null){

                    JSONObject jsonObject = new JSONObject(jsonString);

                    page.setText(jsonObject.getString("page"));
                    per_page.setText(jsonObject.getString("per_page"));
                    total.setText(jsonObject.getString("total"));
                    total_page.setText(jsonObject.getString("total_pages"));


                    JSONArray array = jsonObject.getJSONArray("data");


                    for (int i=0; i<array.length();i++){

                        JSONObject object = array.getJSONObject(i);

                        IdList.add(object.getString("id"));
                        NameList.add(object.getString("name"));
                        YearList.add(object.getString("year"));
                        ColorList.add(object.getString("color"));
                        ValueList.add(object.getString("pantone_value"));

                        System.out.println("id:"+IdList);

                    }



                }


            } catch (Exception e) {
                e.printStackTrace();
            }


            return asdf;
        }


        @Override
        protected void onPostExecute(Boolean aBoolean) {
//            super.onPostExecute(aBoolean);
            progressDialog.dismiss();

            adapter.notifyDataSetChanged();
        }


    }

    public void urlConnection(){

        try {
            URL url = new URL("https://reqres.in/api/unknown");
            connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoInput(true);

            InputStream inputStream = connection.getInputStream();
            BufferedReader reader =new BufferedReader(new InputStreamReader(inputStream));

            builder = new StringBuilder();
            String line = "";

            while ((line = reader.readLine())!=null){
                    builder.append(line);
                System.out.println("line:"+line);
            }

            inputStream.close();

            jsonString = builder.toString();

            System.out.println("jsonData:"+jsonString);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public class Adapter extends RecyclerView.Adapter<Adapter.Holder>{
        @NonNull
        @Override
        public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.main_recycler_layout,viewGroup,false);
            Holder holder = new Holder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull Holder holder, int i) {

            holder.id.setText(IdList.get(i));
            holder.name.setText(NameList.get(i));
            holder.year.setText(YearList.get(i));
            holder.color.setText(ColorList.get(i));
            holder.value.setText(ValueList.get(i));

        }

        @Override
        public int getItemCount() {
            return NameList.size();
        }

        public class Holder extends RecyclerView.ViewHolder{

            TextView id,name,year,color,value;


            public Holder(@NonNull View itemView) {
                super(itemView);

                id =  findViewById(R.id.mrcv_id);
                name =  findViewById(R.id.mrcv_name);
                year =  findViewById(R.id.mrcv_year);
                color =  findViewById(R.id.mrcv_color);
                value =  findViewById(R.id.mrcv_value);

            }
        }
    }
}
