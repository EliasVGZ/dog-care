package com.example.dog_care_android.interfaces;

import com.example.dog_care_android.models.Dog;
import com.example.dog_care_android.models.Owner;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

import java.util.List;
import java.util.Map;

// Interfaz de Retrofit para OwnerService
public interface OwnerService {

    // Crear un nuevo dueño (Owner)
    @POST("/owner/family/{familyId}")
    Call<Owner> createOwner(@Path("familyId") Long familyId, @Body Owner owner);

    // Eliminar un dueño por ID
    @DELETE("/owner/{ownerId}")
    Call<Void> eliminarDueno(@Path("ownerId") Long id);

    // Buscar un dueño por ID
    // Buscar dueño por family_id
    @GET("/owner/family/{familyId}")
    Call<List<Owner>> getOwnerByFamilyId(@Path("familyId") Long familyId);




    // Actualizar el nombre de un dueño
    @PUT("/owner/{ownerId}/name")
    Call<Owner> updateOwnerName(@Path("ownerId") Long ownerId, @Body RequestBody newName);



    // Registrar un paseo con un perro
    @POST("/owner/{ownerId}/dog/{dogId}/paseo")
    Call<Dog> registrarPaseo(@Path("ownerId") Long ownerId, @Path("dogId") Long dogId);

    // Registrar un baño realizado por un dueño
    @POST("/owner/{ownerId}/dog/{dogId}/bano")
    Call<Dog> registrarBano(@Path("ownerId") Long ownerId, @Path("dogId") Long dogId);

    @GET("/owner/{ownerId/actividades}")
    Call<Owner> getActividades(@Path("ownerId") Long ownerId);
}

