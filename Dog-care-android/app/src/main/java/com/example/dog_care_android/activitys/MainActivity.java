package com.example.dog_care_android.activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dog_care_android.R;
import com.example.dog_care_android.interfaces.FamilyService;
import com.example.dog_care_android.models.Family;
import com.example.dog_care_android.retrofit.RetrofitClientInstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button buttonAceptar;
    private EditText editTextFamilyName;
    private FamilyService familyService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonAceptar = findViewById(R.id.buttonAceptar);
        editTextFamilyName = findViewById(R.id.editTextFamilyName);

        buttonAceptar.setOnClickListener(this);
        editTextFamilyName.setOnClickListener(this);


        //TODO Configurar Retrofir
        Retrofit retrofit = RetrofitClientInstance.getRetrofitInstance();
        familyService = retrofit.create(FamilyService.class);

        // Obtener el ID de la familia desde SharedPreferences
        SharedPreferences prefs = getSharedPreferences("dogcare", MODE_PRIVATE);
        long familyId = prefs.getLong("FAMILY_ID", -1);



        if (familyId != -1) {
            Intent intent = new Intent(MainActivity.this, MenuDog.class);
            intent.putExtra("FAMILY_ID", familyId);
            startActivity(intent);
            finish(); // Opcional: Terminar la actividad actual
        }


    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.buttonAceptar:
                // Obtengo el nombre de la familia
                String familyName = editTextFamilyName.getText().toString().trim();

                // Validar que no esté vacío
                if (familyName.isEmpty()) {
                    Toast.makeText(MainActivity.this, "El nombre de la familia no puede estar vacío", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Crear objeto family con el nombre ingresado
                Family newFamily = new Family();
                newFamily.setName(familyName);

                // Llamar al endpoint para crear la familia
                Call<Family> call = familyService.createFamily(newFamily);
                call.enqueue(new Callback<Family>() {
                    @Override
                    public void onResponse(Call<Family> call, Response<Family> response) {
                        if (response.isSuccessful()) {
                            Family createdFamily = response.body();// Obtén la familia creada
                            long familyId = createdFamily.getId(); // Obtén el ID directamente
                            Toast.makeText(MainActivity.this, "Familia creada con éxito", Toast.LENGTH_SHORT).show();

                            //Guardar el ID de la familia en SharedPreferences
                            SharedPreferences prefs = getSharedPreferences("dogcare", MODE_PRIVATE);
                            SharedPreferences.Editor editor = prefs.edit();
                            editor.putLong("FAMILY_ID", familyId);
                            editor.apply();

                            // Inicia la siguiente actividad y pasa el ID de la familia
                            Intent intent = new Intent(MainActivity.this, AnhadirOwners.class);
                            intent.putExtra("FAMILY_ID", familyId);
                            intent.putExtra("nombreFamilia", familyName);
                            startActivity(intent);
                            finish();

                        } else {
                            Toast.makeText(MainActivity.this, "Error al crear la familia", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Family> call, Throwable t) {
                        Toast.makeText(MainActivity.this, "Error de red: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

                break;
        }
    }
}