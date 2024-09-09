package com.example.dog_care_android.activitys;

import static android.provider.Settings.System.DATE_FORMAT;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dog_care_android.AddDogAdapter;
import com.example.dog_care_android.R;
import com.example.dog_care_android.adaptadores.AddOwnerAdapter;
import com.example.dog_care_android.adaptadores.OwnerAdapter;
import com.example.dog_care_android.interfaces.DogService;
import com.example.dog_care_android.interfaces.FamilyService;
import com.example.dog_care_android.interfaces.OwnerService;
import com.example.dog_care_android.models.Dog;
import com.example.dog_care_android.models.Family;
import com.example.dog_care_android.models.Owner;
import com.example.dog_care_android.retrofit.RetrofitClientInstance;
import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AnhadirOwners extends AppCompatActivity implements View.OnClickListener{


    private static final SimpleDateFormat BACKEND_DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

    private TextView tvFamilyName, tvSelectedBirthDate;
    private EditText etOwnerName, etDogName;
    private Button btnAccept, btnAddDog, btnFinalize, btnSelectBirthDate;
    private LinearLayout llAddOwner;
    private RecyclerView rvOwners, rvDogs;
    private long familyId;
    private OwnerService ownerService;
    private DogService dogService;
    private AddOwnerAdapter addOwnerAdapter;
    private AddDogAdapter addDogAdapter;
    private List<Owner> ownerList;
    private List<Dog> dogList;
    private String selectedDate;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anhadir_owners);

        tvFamilyName = findViewById(R.id.tvFamilyName);
        btnAccept = findViewById(R.id.btnAccept);
        llAddOwner = findViewById(R.id.llAddOwner);
        etOwnerName = findViewById(R.id.etOwnerName);
        rvOwners = findViewById(R.id.rvOwners);
        rvDogs = findViewById(R.id.rvDogs);
        btnAddDog = findViewById(R.id.btnAddDog);
        btnFinalize = findViewById(R.id.btnFinalize);
        tvSelectedBirthDate = findViewById(R.id.tvSelectedBirthDate);
        btnSelectBirthDate = findViewById(R.id.btnSelectBirthDate);
        etDogName = findViewById(R.id.etDogName);

        btnAccept.setOnClickListener(this);
        btnAddDog.setOnClickListener(this);
        btnFinalize.setOnClickListener(this);
        btnSelectBirthDate.setOnClickListener(this);

        String nombreFamilia =  getIntent().getStringExtra("nombreFamilia");
        tvFamilyName.setText("Familia "+nombreFamilia);

        familyId = getIntent().getLongExtra("FAMILY_ID", -1);
        if (familyId == -1) {
            // Manejar el caso donde no se ha recibido el ID de la familia
            Toast.makeText(this, "Error al recibir el ID de la familia", Toast.LENGTH_SHORT).show();
            return;
        }



        //TODO Configurar Retrofir
        Retrofit retrofit = RetrofitClientInstance.getRetrofitInstance();
        ownerService = retrofit.create(OwnerService.class);
        dogService = retrofit.create(DogService.class);

        ownerList = new ArrayList<>();
        dogList = new ArrayList<>();
        addOwnerAdapter = new AddOwnerAdapter(ownerList);
        addDogAdapter = new AddDogAdapter(dogList);

        rvOwners.setLayoutManager(new LinearLayoutManager(this));
        rvDogs.setLayoutManager(new LinearLayoutManager(this));
        rvOwners.setAdapter(addOwnerAdapter);
        rvDogs.setAdapter(addDogAdapter);

        fetchFamilyDetails(familyId);

    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAccept:
                String ownerName = etOwnerName.getText().toString();
                if (!ownerName.isEmpty()) {
                    // Crear un nuevo objeto Owner con el nombre proporcionado
                    Owner newOwner = new Owner();
                    newOwner.setName(ownerName);

                    ownerList.add(newOwner);
                    addOwnerAdapter.notifyDataSetChanged();
                    etOwnerName.setText("");

                    // Enviar la solicitud para crear el dueño
                    createOwner(familyId, newOwner);
                } else {
                    Toast.makeText(AnhadirOwners.this, "Ingrese el nombre del dueño", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.btnAddDog:

                String dogName = etDogName.getText().toString();
                String birthDate = tvSelectedBirthDate.getText().toString();

                if(dogName.isEmpty() || birthDate.isEmpty()){
                    Toast.makeText(AnhadirOwners.this, "Ingrese el nombre y la fecha de nacimiento del perro", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    try {
                        // Convertir la fecha de String a Date
                        SimpleDateFormat inputFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                        Date newBirthDate = inputFormat.parse(birthDate);

                        // Mostrar la fecha formateada en el formato deseado
                        SimpleDateFormat displayFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                        String formattedDate = displayFormat.format(newBirthDate);
                        Log.d("DOG", "NACIMIENTO PERRO: " + formattedDate);

                        // Crear el objeto Dog con la fecha en formato Date
                        Dog newDog = new Dog();
                        newDog.setName(dogName);
                        newDog.setBirthDate(formattedDate);

                        // Log JSON payload
                        Gson gson = new Gson();
                        String json = gson.toJson(newDog);
                        Log.d("DOG_JSON", "JSON Enviado: " + json);

                        dogList.add(newDog);
                        addDogAdapter.notifyDataSetChanged();
                        etDogName.setText("");
                        tvSelectedBirthDate.setText("");
                        createDog(familyId, newDog);

                    } catch (ParseException e) {
                        e.printStackTrace();
                        Toast.makeText(AnhadirOwners.this, "Error al parsear la fecha", Toast.LENGTH_SHORT).show();
                    }
                }
                break;



            case R.id.btnSelectBirthDate:
                showDatePicker();
                break;

           case R.id.btnFinalize:

               if(ownerList.isEmpty() || dogList.isEmpty()){
                   Toast.makeText(AnhadirOwners.this, "Añada al menos un dueño y un perro", Toast.LENGTH_SHORT).show();
                   return;
               }else{
                   Intent intent = new Intent(AnhadirOwners.this, MenuDog.class);
                   intent.getLongExtra("FAMILY_ID", familyId);
                   startActivity(intent);
               }
                break;
        }
    }




    private void createDog(long familyId, Dog dog) {

        // Asegúrate de que `dog.getBirthDate()` es un Date, no una String
        Call<Dog> call = dogService.createDog(familyId, dog);
        call.enqueue(new Callback<Dog>() {
            @Override
            public void onResponse(Call<Dog> call, Response<Dog> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(AnhadirOwners.this, "Perro añadido correctamente", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(AnhadirOwners.this, "Error al añadir el perro", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Dog> call, Throwable t) {
                Toast.makeText(AnhadirOwners.this, "Error en la conexión", Toast.LENGTH_SHORT).show();
            }
        });
    }




    private void createOwner(long familyId, Owner owner) {

        // Enviar solicitud para crear el dueño
        Call<Owner> call = ownerService.createOwner(familyId, owner);
        call.enqueue(new Callback<Owner>() {
            @Override
            public void onResponse(Call<Owner> call, Response<Owner> response) {
                if (response.isSuccessful()) {
                    // El dueño se creó exitosamente
                    Toast.makeText(AnhadirOwners.this, "Dueño añadido correctamente", Toast.LENGTH_SHORT).show();
                } else {
                    // Manejar errores
                    Toast.makeText(AnhadirOwners.this, "Error al añadir el dueño", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Owner> call, Throwable t) {
                // Manejar fallos de la red
                Toast.makeText(AnhadirOwners.this, "Error en la conexión", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showDatePicker() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
                        // Formatear el día y el mes con ceros a la izquierda si es necesario
                        String formattedDay = String.format("%02d", selectedDay);
                        String formattedMonth = String.format("%02d", selectedMonth + 1);
                        selectedDate = formattedDay + "/" + formattedMonth + "/" + selectedYear;
                        tvSelectedBirthDate.setText(selectedDate);


                    }
                },
                year,
                month,
                day
        );

        datePickerDialog.show();
    }




    // Obtener detalles de la familia para poder rellenar los recyclverview
    private void fetchFamilyDetails(long familyId) {
        Retrofit retrofit = RetrofitClientInstance.getRetrofitInstance();
        FamilyService familyService = retrofit.create(FamilyService.class);

        Call<Family> call = familyService.getFamilyById(familyId);
        call.enqueue(new Callback<Family>() {
            @Override
            public void onResponse(Call<Family> call, Response<Family> response) {
                if (response.isSuccessful()) {
                    Family family = response.body();
                    if (family != null) {
                        if (family.getOwners() != null) {
                            ownerList.clear();
                            ownerList.addAll(family.getOwners());
                            addOwnerAdapter.notifyDataSetChanged();
                        }
                        if (family.getDogs() != null) {
                            dogList.clear();
                            dogList.addAll(family.getDogs());
                            addDogAdapter.notifyDataSetChanged();
                        }
                    } else {
                        Toast.makeText(AnhadirOwners.this, "Error: Familia no encontrada", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(AnhadirOwners.this, "Error al obtener detalles de la familia", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Family> call, Throwable t) {
                Toast.makeText(AnhadirOwners.this, "Error en la conexión", Toast.LENGTH_SHORT).show();
            }
        });
    }
}