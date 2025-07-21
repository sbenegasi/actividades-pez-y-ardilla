package com.pezardilla.actividades.ui.actividades;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pezardilla.actividades.R;
import com.pezardilla.actividades.model.Actividad;

import java.util.List;

public class ActividadAdapter extends RecyclerView.Adapter<ActividadAdapter.ActividadViewHolder> {

    private List<Actividad> lista;

    public ActividadAdapter(List<Actividad> lista) {
        this.lista = lista;
    }

    @NonNull
    @Override
    public ActividadViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_actividad, parent, false);
        return new ActividadViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull ActividadViewHolder holder, int position) {
        Actividad a = lista.get(position);
        holder.tvTitulo.setText(a.getTitulo());
        holder.tvDescripcion.setText(a.getDescripcion());

        if (a.getFecha() != null && !a.getFecha().isEmpty()) {
            // Actividad puntual
            holder.tvHorario.setText(a.getDiaSemana() + " " + a.getHora() + " - " + a.getFecha());
        } else {
            // Actividad regular
            holder.tvHorario.setText(a.getDiaSemana() + " " + a.getHora() + " (" + a.getFrecuencia() + ")");
        }
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public void updateData(List<Actividad> nuevaLista) {
        this.lista = nuevaLista;
        notifyDataSetChanged();
    }

    static class ActividadViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitulo, tvDescripcion, tvHorario;

        public ActividadViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitulo = itemView.findViewById(R.id.tvTitulo);
            tvDescripcion = itemView.findViewById(R.id.tvDescripcion);
            tvHorario = itemView.findViewById(R.id.tvHorario);
        }
    }
}
