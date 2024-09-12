package com.example.dog_care_android.adaptadores;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dog_care_android.R;
import com.example.dog_care_android.interfaces.DogService;
import com.example.dog_care_android.interfaces.FamilyService;
import com.example.dog_care_android.interfaces.OwnerService;
import com.example.dog_care_android.models.Dog;
import com.example.dog_care_android.models.Family;
import com.example.dog_care_android.models.Owner;
import com.example.dog_care_android.retrofit.RetrofitClientInstance;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class OwnerAdapter extends RecyclerView.Adapter<OwnerAdapter.OwnerViewHolder> {

    private List<Owner> ownerList;
    private List<Dog> dogList;
    private OwnerService ownerService;

    private Owner owner;

    private Long dogId;

    private DogsAdapter dogsAdapter; // Añade esta variable




    public static class OwnerViewHolder extends RecyclerView.ViewHolder {
        public TextView ownerNameTextView;
        public Button walkButton, bathButton;

        public OwnerViewHolder(View itemView) {
            super(itemView);
            ownerNameTextView = itemView.findViewById(R.id.ownerNameTextView);
            walkButton = itemView.findViewById(R.id.walkButton);
            bathButton = itemView.findViewById(R.id.bathButton);
        }
    }

    public OwnerAdapter(List<Owner> ownerList, Long dogId, DogsAdapter dogsAdapter) {
        this.ownerList = ownerList;
        this.dogId = dogId;
        this.dogsAdapter = dogsAdapter;
    }
    public Long getDogId() {
        return dogId;
    }

    @Override
    public OwnerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_owner, parent, false);
        return new OwnerViewHolder(itemView);
    }



    @Override
    public void onBindViewHolder(OwnerViewHolder holder, int position) {
        owner = ownerList.get(position);
        holder.ownerNameTextView.setText(owner.getName());

        holder.walkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = holder.itemView.getContext();

                getDogName(dogId, context);
            }
        });

        holder.bathButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerBath(owner.getId(), holder.itemView.getContext());
            }
        });

    }

    @Override
    public int getItemCount() {
        return ownerList.size();
    }

    public void addOwner(Owner owner) {
        ownerList.add(owner);
        notifyItemInserted(ownerList.size() - 1);
    }

    private void registerWalk(Long ownerId, Context context) {
        Retrofit retrofit = RetrofitClientInstance.getRetrofitInstance();
        OwnerService ownerService = retrofit.create(OwnerService.class);

        Call<Dog> call = ownerService.registrarPaseo(ownerId, getDogId());
        call.enqueue(new Callback<Dog>() {
            @Override
            public void onResponse(Call<Dog> call, Response<Dog> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(context, "Paseo registrado!", Toast.LENGTH_SHORT).show();
                    //Actualizar los paseos del perro



                } else {
                    Toast.makeText(context, "No se pudo registrar el paseo", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Dog> call, Throwable t) {
                Toast.makeText(context, "Fallo en la solicitud", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //Registrar Baño
    private void registerBath(Long ownerId, Context contexto){

        Retrofit retrofit = RetrofitClientInstance.getRetrofitInstance();
        OwnerService ownerService = retrofit.create(OwnerService.class);

        Call<Dog> call = ownerService.registrarBano(ownerId, getDogId());

        call.enqueue(new Callback<Dog>() {
            @Override
            public void onResponse(Call<Dog> call, Response<Dog> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(contexto, "Baño registrado!", Toast.LENGTH_SHORT).show();
                    //Actualizar los baños del perro
                } else {
                    Toast.makeText(contexto, "No se pudo registrar el baño", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Dog> call, Throwable t) {
                Toast.makeText(contexto, "Fallo en la solicitud", Toast.LENGTH_SHORT).show();
            }
        });

    }

    //Buscar nombre de perro por ID
    private void getDogName(Long dogId, Context context) {
        Retrofit retrofit = RetrofitClientInstance.getRetrofitInstance();
        DogService dogService = retrofit.create(DogService.class);

        Call<Dog> call = dogService.buscarPerro(dogId);
        call.enqueue(new Callback<Dog>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(Call<Dog> call, Response<Dog> response) {
                if (response.isSuccessful()) {
                    Dog dog = response.body();
                    if (dog != null) {
                        String mensaje = "¿Paseaste a " + dog.getName() + "?";

                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setMessage(mensaje)
                                .setPositiveButton("Sí", (dialog, which) -> {
                                    registerWalk(owner.getId(), context);

                                    //actualizar los datos en dogsadapter
                                    dogsAdapter.notifyDataSetChanged();
                                })
                                .setNegativeButton("No", (dialog, which) -> {
                                    Toast.makeText(context, "No se registró el paseo", Toast.LENGTH_SHORT).show();
                                })
                                .show();
                    } else {
                        Toast.makeText(context, "Error: Perro no encontrado", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(context, "Error al obtener el nombre del perro", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Dog> call, Throwable t) {
                Toast.makeText(context, "Fallo en la solicitud", Toast.LENGTH_SHORT).show();
            }
        });
    }





















}

