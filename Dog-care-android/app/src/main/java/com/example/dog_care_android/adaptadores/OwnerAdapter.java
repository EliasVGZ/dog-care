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

public class OwnerAdapter extends RecyclerView.Adapter<OwnerAdapter.OwnerViewHolder> {

    private List<Owner> ownerList;


    public static class OwnerViewHolder extends RecyclerView.ViewHolder {
        public TextView ownerNameTextView;

        public OwnerViewHolder(View itemView) {
            super(itemView);
            ownerNameTextView = itemView.findViewById(R.id.ownerNameTextView);
        }
    }

    public OwnerAdapter(List<Owner> ownerList) {
        this.ownerList = ownerList;
    }

    @Override
    public OwnerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_owner, parent, false);
        return new OwnerViewHolder(itemView);
    }



    @Override
    public void onBindViewHolder(OwnerViewHolder holder, int position) {
        Owner owner = ownerList.get(position);
        holder.ownerNameTextView.setText(owner.getName());
    }

    @Override
    public int getItemCount() {
        return ownerList.size();
    }

    public void addOwner(Owner owner) {
        ownerList.add(owner);
        notifyItemInserted(ownerList.size() - 1);
    }
}

