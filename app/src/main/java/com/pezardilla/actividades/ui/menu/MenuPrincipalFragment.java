package com.pezardilla.actividades.ui.menu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.pezardilla.actividades.R;

public class MenuPrincipalFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_menu_principal, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FrameLayout btnClientes = view.findViewById(R.id.btnClientes);
        FrameLayout btnActividades = view.findViewById(R.id.btnActividades);

        btnClientes.setOnClickListener(v ->
                Navigation.findNavController(v).navigate(R.id.clientesFragment)
        );

        btnActividades.setOnClickListener(v ->
                Navigation.findNavController(v).navigate(R.id.actividadesFragment)
        );
    }
}
