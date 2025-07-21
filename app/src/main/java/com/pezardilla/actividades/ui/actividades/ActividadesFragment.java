package com.pezardilla.actividades.ui.actividades;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pezardilla.actividades.databinding.FragmentActividadesBinding;
import com.pezardilla.actividades.model.Actividad;

import java.util.ArrayList;
import java.util.List;

public class ActividadesFragment extends Fragment {

    private FragmentActividadesBinding binding;
    private ActividadAdapter adapter;
    private final List<Actividad> allActividades = new ArrayList<>();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentActividadesBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adapter = new ActividadAdapter(new ArrayList<>());
        binding.rvActividades.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.rvActividades.setAdapter(adapter);

        // Escucha cambios en la base de datos
        loadActividades();

        binding.etSearchActividades.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int st, int c, int a) {}
            @Override public void onTextChanged(CharSequence s, int st, int b, int c) {
                filterActividades(s.toString().trim());
            }
            @Override public void afterTextChanged(Editable s) {}
        });
    }

    private void loadActividades() {
        FirebaseDatabase db = FirebaseDatabase.getInstance();

        db.getReference("actividades/puntuales").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot item : snapshot.getChildren()) {
                    Actividad act = item.getValue(Actividad.class);
                    if (act != null) {
                        allActividades.add(act);
                    }
                }

                db.getReference("actividades/regulares").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot item : snapshot.getChildren()) {
                            Actividad act = item.getValue(Actividad.class);
                            if (act != null) {
                                allActividades.add(act);
                            }
                        }

                        // Mostrar todo
                        adapter.updateData(allActividades);
                    }

                    @Override public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(requireContext(), "Error cargando regulares", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(requireContext(), "Error cargando puntuales", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void filterActividades(String query) {
        List<Actividad> filtered = new ArrayList<>();
        for (Actividad a : allActividades) {
            if (a.getTitulo().toLowerCase().contains(query.toLowerCase())) {
                filtered.add(a);
            }
        }
        adapter.updateData(filtered);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}