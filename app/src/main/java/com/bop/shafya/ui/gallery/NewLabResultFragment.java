package com.bop.shafya.ui.gallery;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bop.shafya.R;
import com.bop.shafya.databinding.FragmentNewLabResultBinding;

public class NewLabResultFragment extends Fragment {
  
  private static final String ARG_PARAM1 = "param1";
  private static final String ARG_PARAM2 = "param2";
  
  private FragmentNewLabResultBinding binding;
  
  public NewLabResultFragment() {
    // Required empty public constructor
  }
  
  public static NewLabResultFragment newInstance(String param1, String param2) {
    NewLabResultFragment fragment = new NewLabResultFragment();
    Bundle args = new Bundle();
    args.putString(ARG_PARAM1, param1);
    args.putString(ARG_PARAM2, param2);
    fragment.setArguments(args);
    return fragment;
  }
  
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (getArguments() != null) {
      // TODO: Rename and change types of parameters
      String mParam1 = getArguments().getString(ARG_PARAM1);
      String mParam2 = getArguments().getString(ARG_PARAM2);
    }
  }
  
  @Override
  public View onCreateView(
    @NonNull LayoutInflater inflater,
    ViewGroup container,
    Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    binding = FragmentNewLabResultBinding.inflate(inflater, container, false);
    
    return binding.getRoot();
  }
}