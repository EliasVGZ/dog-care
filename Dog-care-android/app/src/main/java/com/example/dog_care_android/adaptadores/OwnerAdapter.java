package com.example.dog_care_android.adaptadores;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
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

import okhttp3.MediaType;
import okhttp3.RequestBody;
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
    private Long ownerId;

    private DogsAdapter dogsAdapter; // Añade esta variable

    private String ownerName;

    private Long familyId;




    public static class OwnerViewHolder extends RecyclerView.ViewHolder {
        public TextView ownerNameTextView, ownerNameLabel;
        public ImageView opc_owner;
        public Button walkButton, bathButton;

        public OwnerViewHolder(View itemView) {
            super(itemView);
            ownerNameTextView = itemView.findViewById(R.id.ownerNameTextView);
            walkButton = itemView.findViewById(R.id.walkButton);
            bathButton = itemView.findViewById(R.id.bathButton);
            opc_owner = itemView.findViewById(R.id.opc_owner);
            ownerNameLabel = itemView.findViewById(R.id.ownerNameLabel);

        }
    }

    public OwnerAdapter(List<Owner> ownerList,Long dogId, Long ownerId,Long familyId, DogsAdapter dogsAdapter) {
        this.ownerList = ownerList;
        this.dogId = dogId;
        this.dogsAdapter = dogsAdapter;
        this.ownerId = ownerId;
        this.familyId = familyId;
    }
    public Long getDogId() {
        return dogId;
    }
    public Long getOwnerId(){
        return ownerId;
    }


    @Override
    public OwnerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_owner, parent, false);
        return new OwnerViewHolder(itemView);
    }



    @Override
    public void onBindViewHolder(OwnerViewHolder holder, @SuppressLint("RecyclerView") int position) {
        owner = ownerList.get(position);
        holder.ownerNameTextView.setText(owner.getName());



        holder.walkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = holder.itemView.getContext();

                registrarPaseo(dogId, context);
            }
        });

        holder.bathButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Owner selectedOwner = ownerList.get(holder.getAdapterPosition());

                // Obtener el ID y nombre del Owner
                ownerId = selectedOwner.getId();

                registerBath(ownerId, holder.itemView.getContext());
            }
        });

        //Logica para mostrar opciones de dueño
        holder.opc_owner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtener el Owner correspondiente de la lista usando la posición
                Owner selectedOwner = ownerList.get(holder.getAdapterPosition());

                // Obtener el ID y nombre del Owner
                ownerId = selectedOwner.getId();
                ownerName = selectedOwner.getName();

                // Log para verificar que obtenemos el ID y el nombre
                Log.d("Owner", "El ID del owner es: " + ownerId);
                Log.d("Owner", "El nombre del owner es: " + ownerName);

                // Mostrar el diálogo
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.itemView.getContext());
                builder.setTitle(ownerName)
                        .setItems(R.array.opciones_owner, (dialog, which) -> {
                            switch (which) {
                                case 0:
                                    modifyOwnerName(selectedOwner,ownerId, holder.itemView.getContext());
                                    break;
                                case 1:
                                    deleteOwner(ownerId,ownerName, holder.itemView.getContext());
                                    break;
                                default:
                                    break;
                            }
                        })
                        .show();
            }
        });


    }

    private void deleteOwner(Long ownerId, String ownerName, Context context) {

        new AlertDialog.Builder(context)
                .setTitle("Eliminar dueño ")
                .setMessage("¿Estás seguro de que deseas eliminar a "+ownerName+" ?")
                .setPositiveButton("Sí", (dialog, which) -> {
                    Retrofit retrofit = RetrofitClientInstance.getRetrofitInstance();
                    OwnerService ownerService = retrofit.create(OwnerService.class);

                    Call<Void> call = ownerService.eliminarDueno(ownerId);
                    call.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            if (response.isSuccessful()) {
                                ownerList.remove(owner);
                                notifyItemRemoved(ownerList.indexOf(owner));
                                Toast.makeText(context, "Dueño eliminado", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(context, "No se pudo eliminar el dueño", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            Toast.makeText(context, "Fallo en la solicitud", Toast.LENGTH_SHORT).show();
                        }
                    });
                })
                .setNegativeButton("No", (dialog, which) -> {
                    dialog.dismiss();
                })
                .show();

    }

    @Override
    public int getItemCount() {
        return ownerList.size();
    }



    //Metodo para modificar nombre del dueño, se le pide el nombre en un dialog y se actualiza
    public void modifyOwnerName(Owner owner,Long ownerId, Context context) {
        Retrofit retrofit = RetrofitClientInstance.getRetrofitInstance();
        OwnerService ownerService = retrofit.create(OwnerService.class);

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_modify_owner_name, null);
        builder.setView(view);

        TextView ownerNameTextView = view.findViewById(R.id.ownerNameTextView);
        ownerNameTextView.setHint(owner.getName()); // Mostrar el nombre actual como hint

        builder.setPositiveButton("Guardar", (dialog, which) -> {
            // Obtener el nuevo nombre del EditText
            String newName = ownerNameTextView.getText().toString().trim();

            if (!newName.isEmpty()) { // Verifica que no esté vacío
                // Crear el RequestBody
                RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), newName);

                // Hacer la llamada para actualizar el nombre
                Call<Owner> call = ownerService.updateOwnerName(ownerId, requestBody);
                call.enqueue(new Callback<Owner>() {
                    @Override
                    public void onResponse(Call<Owner> call, Response<Owner> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            owner.setName(newName); // Actualiza el nombre del objeto local
                            notifyItemChanged(ownerList.indexOf(owner)); // Actualiza la vista
                            Toast.makeText(context, "Nombre actualizado", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "No se pudo actualizar el nombre", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Owner> call, Throwable t) {
                        Toast.makeText(context, "Fallo de conexión: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                Toast.makeText(context, "El nombre no puede estar vacío", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Cancelar", (dialog, which) -> {
            dialog.dismiss();
        });

        builder.show();
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
    private void registrarPaseo(Long dogId, Context context) {
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
                                    dog.setPaseosDiarios(dog.getPaseosDiarios() + 1);
                                    //dogsAdapter.notifyDataSetChanged();
                                    dogsAdapter.updateDog(dog);
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

