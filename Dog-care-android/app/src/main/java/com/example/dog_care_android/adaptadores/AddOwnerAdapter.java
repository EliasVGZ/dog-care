package com.example.dog_care_android.adaptadores;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dog_care_android.R;
import com.example.dog_care_android.models.Owner;

import java.util.List;

public class AddOwnerAdapter extends RecyclerView.Adapter<AddOwnerAdapter.OwnerViewHolder> {

    private List<Owner> ownerList;

    public AddOwnerAdapter(List<Owner> ownerList) {
        this.ownerList = ownerList;
    }

    @NonNull
    @Override
    public OwnerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_add_owner_adapter, parent, false);
        return new OwnerViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull OwnerViewHolder holder, int position) {
        Owner owner = ownerList.get(position);
        holder.nombreDuenho.setText(owner.getName());
    }

    @Override
    public int getItemCount() {
        return ownerList.size();
    }

    public static class OwnerViewHolder extends RecyclerView.ViewHolder {
        public TextView nombreDuenho;

        public OwnerViewHolder(View itemView) {
            super(itemView);
            nombreDuenho = itemView.findViewById(R.id.nombreOwner);
        }
    }
}
