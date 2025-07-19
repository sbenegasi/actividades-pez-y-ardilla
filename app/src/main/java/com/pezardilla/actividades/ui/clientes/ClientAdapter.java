package com.pezardilla.actividades.ui.clientes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pezardilla.actividades.R;
import com.pezardilla.actividades.model.Client;

import java.util.List;

public class ClientAdapter extends RecyclerView.Adapter<ClientAdapter.ClientViewHolder> {

    private final List<Client> clientList;

    public ClientAdapter(List<Client> clientList) {
        this.clientList = clientList;
    }

    public void updateData(List<Client> newClients) {
        clientList.clear();
        clientList.addAll(newClients);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ClientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_client, parent, false);
        return new ClientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ClientViewHolder holder, int position) {
        Client client = clientList.get(position);
        holder.tvName.setText(client.getName());
        holder.tvEmail.setText(client.getEmail());
        holder.tvPhone.setText(client.getPhone());
        holder.tvBirth.setText(client.getBirthDate());
    }

    @Override
    public int getItemCount() {
        return clientList.size();
    }

    static class ClientViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvEmail, tvPhone, tvBirth;

        public ClientViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvClientName);
            tvEmail = itemView.findViewById(R.id.tvClientEmail);
            tvPhone = itemView.findViewById(R.id.tvClientPhone);
            tvBirth = itemView.findViewById(R.id.tvClientBirth);
        }
    }
}
