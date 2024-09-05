package com.example.dog_care_android.interfaces;

import com.example.dog_care_android.models.Family;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface FamilyService {

    @POST("family")
    Call<Family> createFamily(@Body Family family);

    @GET("family/{id}")
    Call<Family> getFamilyById(@Path("id") long id);

    @PUT("family/{id}")
    Call<Family> updateFamilyName(@Path("id") long id, @Body Map<String, String> requestBody);
}

