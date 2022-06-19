package com.example.smb116_projet.ui.recherche;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.Navigator;
import androidx.navigation.ui.NavigationUI;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.smb116_projet.MainActivity;
import com.example.smb116_projet.R;
import com.example.smb116_projet.databinding.ActivityMainBinding;
import com.example.smb116_projet.databinding.FragmentRechercheBinding;

public class RechercheFragment extends Fragment {

    private FragmentRechercheBinding binding;
    private View view;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentRechercheBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final Button btnGo = binding.getRoute;

        btnGo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Bundle bundle = new Bundle();
               bundle.putString("pointDepart", binding.pointDepart.getText().toString());
               bundle.putString("pointArrive", binding.pointArrive.getText().toString());

               Navigation.findNavController(root).navigate(R.id.navigateToResultRecherche, bundle);
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}