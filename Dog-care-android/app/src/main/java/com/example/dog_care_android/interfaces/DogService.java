package com.example.dog_care_android.interfaces;

import com.example.dog_care_android.models.Dog;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.POST;

public interface DogService {

    // Buscar perro por ID
    @GET("dog/{id}")
    Call<Dog> buscarPerro(@Path("id") Long id);

    // Crear perro en una familia
    @POST("dog/family/{familyId}")
    Call<Dog> createDog(@Path("familyId") Long familyId, @Body Dog dog);

    // Borrar perro por ID
    @DELETE("dog/{id}")
    Call<Void> borrarPerro(@Path("id") Long id);
}
