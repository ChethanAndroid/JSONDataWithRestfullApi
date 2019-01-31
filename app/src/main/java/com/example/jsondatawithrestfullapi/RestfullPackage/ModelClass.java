package com.example.jsondatawithrestfullapi.RestfullPackage;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ModelClass {

    @SerializedName("page")
    public String Page;

    @SerializedName("per_page")
    public String Per_page;

    @SerializedName("total")
    public String Total;

    @SerializedName("total_pages")
    public String Total_pages;

    @SerializedName("data")
    public List<ArrayValue>data = null;

    public class ArrayValue{

        @SerializedName("id")
        public String Id;

        @SerializedName("name")
        public String Name;

        @SerializedName("year")
        public String Year;

        @SerializedName("pantone_value")
        public String Pantone_value;

        @SerializedName("color")
        public String Color;
    }

}
