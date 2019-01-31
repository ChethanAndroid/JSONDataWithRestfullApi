package com.example.jsondatawithrestfullapi.RestfullPackage;

import retrofit2.Call;
import retrofit2.http.GET;

public interface InterfaceClass {

        @GET("/api/unknown")
    Call<ModelClass>getModel();
}
