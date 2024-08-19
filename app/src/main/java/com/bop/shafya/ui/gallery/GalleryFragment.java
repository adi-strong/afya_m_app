package com.bop.shafya.ui.gallery;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.bop.shafya.R;
import com.bop.shafya.databinding.FragmentGalleryBinding;

public class GalleryFragment extends Fragment {
  
  private FragmentGalleryBinding binding;
  
  private void goToNewLabAction() {
    binding.searchButtonAction.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        NavController navController = Navigation.findNavController(v);
        navController.navigate(R.id.nav_new_lab);
      }
    });
  }
  
  public View onCreateView(@NonNull LayoutInflater inflater,
                           ViewGroup container, Bundle savedInstanceState) {
    GalleryViewModel galleryViewModel =
      new ViewModelProvider(this).get(GalleryViewModel.class);
    
    binding = FragmentGalleryBinding.inflate(inflater, container, false);
    View root = binding.getRoot();
    
    final TextView textView = binding.textGallery;
    galleryViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
    
    goToNewLabAction();
    
    return root;
  }
  
  @Override
  public void onDestroyView() {
    super.onDestroyView();
    binding = null;
  }
}