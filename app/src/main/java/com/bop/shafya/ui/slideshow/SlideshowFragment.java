package com.bop.shafya.ui.slideshow;

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
import com.bop.shafya.databinding.FragmentSlideshowBinding;

public class SlideshowFragment extends Fragment {
  
  private FragmentSlideshowBinding binding;
  
  private void goToNewFileAction() {
    binding.searchButtonAction.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        NavController navController = Navigation.findNavController(v);
        navController.navigate(R.id.nav_new_file);
      }
    });
  }
  
  public View onCreateView(@NonNull LayoutInflater inflater,
                           ViewGroup container, Bundle savedInstanceState) {
    SlideshowViewModel slideshowViewModel =
      new ViewModelProvider(this).get(SlideshowViewModel.class);
    
    binding = FragmentSlideshowBinding.inflate(inflater, container, false);
    View root = binding.getRoot();
    
    final TextView textView = binding.textSlideshow;
    slideshowViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
    
    goToNewFileAction();
    
    return root;
  }
  
  @Override
  public void onDestroyView() {
    super.onDestroyView();
    binding = null;
  }
}
