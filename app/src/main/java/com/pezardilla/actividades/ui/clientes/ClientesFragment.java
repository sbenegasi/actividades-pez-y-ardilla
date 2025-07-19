package com.pezardilla.actividades.ui.clientes;

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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pezardilla.actividades.databinding.FragmentClientesBinding;
import com.pezardilla.actividades.model.Client;

import java.util.ArrayList;
import java.util.List;

public class ClientesFragment extends Fragment {
    private FragmentClientesBinding binding;
    private ClientAdapter adapter;
    private DatabaseReference dbRef;
    private List<Client> allClients = new ArrayList<>();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentClientesBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adapter = new ClientAdapter(new ArrayList<>());
        binding.rvClientes.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.rvClientes.setAdapter(adapter);

        dbRef = FirebaseDatabase.getInstance().getReference("clientes");

        binding.etSearch.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int st, int c, int a) {}
            @Override public void onTextChanged(CharSequence s, int st, int b, int c) {
                filterClients(s.toString().trim());
            }
            @Override public void afterTextChanged(Editable s) {}
        });

        fetchClients();
    }

    private void fetchClients() {
        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                allClients.clear();
                for (DataSnapshot child : snapshot.getChildren()) {
                    Client client = child.getValue(Client.class);
                    if (client != null) {
                        allClients.add(client);
                    }
                }
                adapter.updateData(new ArrayList<>(allClients));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Error: " + error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void filterClients(String filter) {
        List<Client> filtered = new ArrayList<>();
        for (Client c : allClients) {
            if (c.getName() != null && c.getName().toLowerCase().contains(filter.toLowerCase())) {
                filtered.add(c);
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
