package com.example.dog_care_android.activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dog_care_android.R;
import com.example.dog_care_android.adaptadores.DogsAdapter;
import com.example.dog_care_android.adaptadores.OwnerAdapter;
import com.example.dog_care_android.interfaces.FamilyService;
import com.example.dog_care_android.interfaces.OwnerService;
import com.example.dog_care_android.models.Dog;
import com.example.dog_care_android.models.Family;
import com.example.dog_care_android.retrofit.RetrofitClientInstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MenuDog extends AppCompatActivity implements View.OnClickListener{

    private long familyId;
    private FamilyService familyService;
    private RecyclerView rvOwners, rvDogs;
    private DogsAdapter dogsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_dog);



        familyId = getIntent().getLongExtra("FAMILY_ID", -1);
        if (familyId == -1) {
            // Manejar el caso donde no se ha recibido el ID de la familia
            Toast.makeText(this, "Error al recibir el ID de la familia", Toast.LENGTH_SHORT).show();
            return;
        }

        rvOwners = findViewById(R.id.rvOwners);
        rvDogs = findViewById(R.id.rvDogs);

        //TODO Configurar Retrofir
        Retrofit retrofit = RetrofitClientInstance.getRetrofitInstance();
        familyService = retrofit.create(FamilyService.class);

        getFamilyData(familyId);
    }


    //Listar dueños y perros de una familia
    private void getFamilyData(long familyId) {
        Call<Family> call = familyService.getFamilyById(familyId);
        call.enqueue(new Callback<Family>() {
            @Override
            public void onResponse(Call<Family> call, Response<Family> response) {
                if (response.isSuccessful()) {
                    Family family = response.body();
                    if (family != null) {
                        displayFamilyData(family);
                    }
                } else {
                    Toast.makeText(MenuDog.this, "Error al obtener la información de la familia", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Family> call, Throwable t) {
                Toast.makeText(MenuDog.this, "Error de red: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void displayFamilyData(Family family) {
        TextView familyNameTextView = findViewById(R.id.tvFamilyName);
        familyNameTextView.setText(family.getName());

        Long dogId = family.getDogs().isEmpty() ? null : family.getDogs().get(0).getId();

        // Configurar RecyclerView para Perros
        rvDogs.setLayoutManager(new LinearLayoutManager(this));
        DogsAdapter dogsAdapter = new DogsAdapter(family.getDogs());
        rvDogs.setAdapter(dogsAdapter);

        // Configurar RecyclerView para Dueños y pasar el adaptador de perros
        rvOwners.setLayoutManager(new LinearLayoutManager(this));
        OwnerAdapter ownersAdapter = new OwnerAdapter(family.getOwners(), dogId, dogsAdapter);
        rvOwners.setAdapter(ownersAdapter);
    }






    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.walkButton:
                // Lógica para el botón de pasear
                break;
            case R.id.bathButton:
                // Lógica para el botón de bañar
                break;
        }
    }
}