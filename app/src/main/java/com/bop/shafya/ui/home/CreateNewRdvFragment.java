package com.bop.shafya.ui.home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.bop.shafya.R;
import com.bop.shafya.databinding.FragmentCreateNewRdvBinding;

public class CreateNewRdvFragment extends Fragment {
  
  private static final String ARG_PARAM1 = "param1";
  private static final String ARG_PARAM2 = "param2";
  
  private FragmentCreateNewRdvBinding binding;
  
  public CreateNewRdvFragment() {
    // Required empty public constructor
  }
  
  public static CreateNewRdvFragment newInstance(String param1, String param2) {
    CreateNewRdvFragment fragment = new CreateNewRdvFragment();
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
  
  private void backToHomeAction() {
    binding.backButtonAction.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        NavController navController = Navigation.findNavController(v);
        navController.navigate(R.id.nav_home);
      }
    });
  }
  
  @Override
  public View onCreateView(
    @NonNull LayoutInflater inflater,
    ViewGroup container,
    Bundle savedInstanceState) {
    // return inflater.inflate(R.layout.fragment_create_new_rdv, container, false);
    binding = FragmentCreateNewRdvBinding.inflate(inflater, container, false);
    
    AutoCompleteTextView doctorAutoCompleteTextView = binding.doctor;
    
    String[] doctorOptions = getResources().getStringArray(R.array.doctor_options);
    ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_dropdown_item_1line, doctorOptions);
    
    doctorAutoCompleteTextView.setAdapter(adapter);
    
    backToHomeAction();
    
    return binding.getRoot();
  }
}
