package com.bop.shafya.ui.slideshow;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.bop.shafya.R;
import com.bop.shafya.databinding.FragmentNewFileBinding;

public class NewFileFragment extends Fragment {
  
  // TODO: Rename parameter arguments, choose names that match
  // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
  private static final String ARG_PARAM1 = "param1";
  private static final String ARG_PARAM2 = "param2";
  
  private FragmentNewFileBinding binding;
  
  public NewFileFragment() {
    // Required empty public constructor
  }
  
  public static NewFileFragment newInstance(String param1, String param2) {
    NewFileFragment fragment = new NewFileFragment();
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
    binding = FragmentNewFileBinding.inflate(inflater, container, false);
    
    AutoCompleteTextView examsAutoCompleteTextView = binding.exams;
    
    String[] examOptions = getResources().getStringArray(R.array.exam_options);
    ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_dropdown_item_1line, examOptions);
    
    examsAutoCompleteTextView.setAdapter(adapter);
    
    return binding.getRoot();
  }
}
