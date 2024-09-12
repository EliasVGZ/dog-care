package com.example.dog_care_android.adaptadores;

import android.annotation.SuppressLint;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dog_care_android.R;
import com.example.dog_care_android.models.DateUtils;
import com.example.dog_care_android.models.Dog;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.TimeZone;


public class DogsAdapter extends RecyclerView.Adapter<DogsAdapter.DogViewHolder> {

    private List<Dog> dogs;

    public DogsAdapter(List<Dog> dogs) {
        this.dogs = dogs;
    }

    @Override
    public DogViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_dog, parent, false);
        return new DogViewHolder(view);
    }



    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(DogViewHolder holder, int position) {
        Dog dog = dogs.get(position);

        // Si las fechas son null, se muestra "No disponible", de lo contrario se muestra la fecha directamente
        String formattedLastWalkDate = dog.getLastWalkDate() != null ? convertUTCToLocal(dog.getLastWalkDate()) : "No disponible";
        String formattedBathDate = dog.getBathDate() != null ? dog.getBathDate() : "No disponible";

        // Usar Html.fromHtml para mostrar en párrafos separados
        String details = "<p>Paseos hoy: " + dog.getPaseosDiarios() + "</p>" +
                "<p>Último paseo: " + formattedLastWalkDate + "</p>" +
                "<p>Último baño: " + formattedBathDate + "</p>";
        Spanned htmlText = Html.fromHtml(details);

        holder.dogNameTextView.setText(dog.getName());
        holder.dogDetailsTextView.setText(htmlText);
    }

    public String convertUTCToLocal(String utcDateString) {
        SimpleDateFormat utcFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        utcFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

        SimpleDateFormat localFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        localFormat.setTimeZone(TimeZone.getDefault());

        try {
            // Parsear la fecha UTC
            Date date = utcFormat.parse(utcDateString);

            // Crear una instancia de Calendar y añadir 2 horas
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.HOUR_OF_DAY, 2);

            // Convertir a formato local
            Date newDate = calendar.getTime();
            return localFormat.format(newDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }



    @Override
    public int getItemCount() {
        return dogs.size();
    }

    public static class DogViewHolder extends RecyclerView.ViewHolder {
        TextView dogNameTextView;
        TextView dogDetailsTextView;

        public DogViewHolder(View itemView) {
            super(itemView);
            dogNameTextView = itemView.findViewById(R.id.dogNameTextView);
            dogDetailsTextView = itemView.findViewById(R.id.dogDetailsTextView);
        }
    }

    // Método para actualizar la lista de perros
    public void updateDogList(List<Dog> newDogList) {
        this.dogs = newDogList;
        notifyDataSetChanged();
    }





}

