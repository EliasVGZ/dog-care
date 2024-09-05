package com.example.dog_care_android.interfaces;

import com.example.dog_care_android.models.Dog;
import com.example.dog_care_android.models.Owner;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import java.util.Map;

// Interfaz de Retrofit para OwnerService
public interface OwnerService {

    // Crear un nuevo dueño (Owner)
    @POST("/owner/family/{familyId}")
    Call<Owner> createOwner(@Path("familyId") Long familyId, @Body Owner owner);

    // Eliminar un dueño por ID
    @DELETE("/owner/{id}")
    Call<Void> eliminarDueno(@Path("id") Long id);

    // Buscar un dueño por ID
    @GET("/owner/{id}")
    Call<Owner> buscarDueno(@Path("id") Long id);

    // Actualizar el nombre de un dueño
    @PUT("/owner/{id}")
    Call<Owner> updateOwnerName(@Path("id") long id, @Body Map<String, String> requestBody);

    // Registrar un paseo con un perro
    @POST("/owner/{ownerId}/dog/{dogId}/paseo")
    Call<Dog> registrarPaseo(@Path("ownerId") Long ownerId, @Path("dogId") Long dogId);

    // Registrar un baño realizado por un dueño
    @POST("/owner/{ownerId}/dog/{dogId}/bano")
    Call<Dog> registrarBano(@Path("ownerId") Long ownerId, @Path("dogId") Long dogId);

    @GET("/owner/{ownerId/actividades}")
    Call<Owner> getActividades(@Path("ownerId") Long ownerId);
}

