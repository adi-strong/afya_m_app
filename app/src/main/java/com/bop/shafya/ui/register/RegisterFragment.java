package com.bop.shafya.ui.register;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bop.shafya.R;
import com.bop.shafya.databinding.FragmentRegisterBinding;
import com.bop.shafya.ui.login.LoginActivity;

public class RegisterFragment extends Fragment {
  
  private FragmentRegisterBinding binding;
  private RegisterViewModel registerViewModel;
  
  public RegisterFragment() {
    // Required empty public constructor
  }
  
  @Override
  public View onCreateView(
    @NonNull LayoutInflater inflater,
    ViewGroup container,
    Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    binding = FragmentRegisterBinding.inflate(inflater, container, false);
    registerViewModel = new ViewModelProvider(this).get(RegisterViewModel.class);
    
    goToLogin();
    onRegister();
    
    return binding.getRoot();
  }
  
  private void setUiEnabled(Boolean enabled) {
    binding.username.setEnabled(enabled);
    binding.password.setEnabled(enabled);
    binding.loginButton.setEnabled(enabled);
    binding.biometricButton.setEnabled(enabled);
    binding.registerButton.setEnabled(enabled);
  }
  
  private void goToLogin() {
    binding.loginButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Log.d("TEST", "Go To LoginActivity");
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        getActivity().startActivity(intent);
        getActivity().finish();
      }
    });
  }
  
  private void onRegister() {
    binding.registerButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        binding.errorTextView.setText("");
        String username = binding.username.getText().toString();
        String password = binding.password.getText().toString();
        String phone = binding.phone.getText().toString();
        String email = binding.email.getText().toString();
        String fullName = binding.fullName.getText().toString();
        String repeatPassword = binding.repeatPassword.getText().toString();
        setUiEnabled(false);
        
        // binding.loginProgressBar.setVisibility(View.VISIBLE);
        binding.registerButton.setText("Veuillez patienter");
        registerViewModel.register(username, password, phone, email, fullName, null);
      }
    });
    
    registerViewModel.getRegisterSuccess().observe(getViewLifecycleOwner(), success -> {
      setUiEnabled(true);
      if (success) {
        binding.registerButton.setText("Créer un compte");
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
        getActivity().finish();
        Toast.makeText(getActivity(), "Compte bien enregistré", Toast.LENGTH_SHORT).show();
      }
    });
    
    registerViewModel.getErrorMessage().observe(getViewLifecycleOwner(), error -> {
      // binding.loginProgressBar.setVisibility(View.VISIBLE);
      setUiEnabled(true);
      binding.errorTextView.setText(error);
      binding.registerButton.setText("Créer un compte");
    });
  }
}
