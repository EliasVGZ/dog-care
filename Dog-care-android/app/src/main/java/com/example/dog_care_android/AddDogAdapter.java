package com.example.dog_care_android;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dog_care_android.R;
import com.example.dog_care_android.models.Dog;

import java.util.List;

public class AddDogAdapter extends RecyclerView.Adapter<AddDogAdapter.DogViewHolder> {

    private List<Dog> dogList;

    public AddDogAdapter(List<Dog> DogList) {
        this.dogList = DogList;
    }

    @NonNull
    @Override
    public DogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_add_dog_adapter, parent, false);
        return new DogViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull DogViewHolder holder, int position) {
        Dog Dog = dogList.get(position);
        holder.nombreDog.setText(Dog.getName());
    }

    @Override
    public int getItemCount() {
        return dogList.size();
    }

    public static class DogViewHolder extends RecyclerView.ViewHolder {
        public TextView nombreDog;

        public DogViewHolder(View itemView) {
            super(itemView);
            nombreDog = itemView.findViewById(R.id.nombreDog);
        }
    }
}
