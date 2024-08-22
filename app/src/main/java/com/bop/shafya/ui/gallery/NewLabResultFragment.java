package com.bop.shafya.ui.gallery;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bop.shafya.R;
import com.bop.shafya.databinding.FragmentNewLabResultBinding;

public class NewLabResultFragment extends Fragment {
  
  private FragmentNewLabResultBinding binding;
  
  public NewLabResultFragment() {
    // Required empty public constructor
  }
  
  public View onCreateView(
    @NonNull LayoutInflater inflater,
    ViewGroup container,
    Bundle savedInstanceState) {
    
    // Inflate the layout for this fragment
    binding = FragmentNewLabResultBinding.inflate(inflater, container, false);
    
    return binding.getRoot();
  }
  
}
