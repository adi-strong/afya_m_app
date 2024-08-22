package com.bop.shafya.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.bop.shafya.R;
import com.bop.shafya.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {
  
  private FragmentHomeBinding binding;
  
  private void goToNewRdvAction() {
    binding.searchButtonAction.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        // Obtenez le texte saisi par l'utilisateur
        String searchText = binding.searchBar.getText().toString();
        
        // Créez un bundle avec les arguments
        Bundle bundle = new Bundle();
        bundle.putString("keyword", searchText);
        
        // Obtenez le NavController
        NavController navController = Navigation.findNavController(v);
        
        // Naviguez vers le fragment de destination avec le bundle
        navController.navigate(R.id.nav_new_rdv, bundle);
      }
    });
  }
  
  
  
  public View onCreateView(
    @NonNull LayoutInflater inflater,
    ViewGroup container,
    Bundle savedInstanceState) {
    
    // HomeViewModel homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
    
    binding = FragmentHomeBinding.inflate(inflater, container, false);
    View root = binding.getRoot();
    
    goToNewRdvAction();
    
    return root;
    
  }
  
  @Override
  public void onDestroyView() {
    super.onDestroyView();
    binding = null;
  }
}
