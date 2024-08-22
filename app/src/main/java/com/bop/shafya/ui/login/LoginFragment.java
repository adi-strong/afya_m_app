package com.bop.shafya.ui.login;

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

import com.bop.shafya.MainActivity;
import com.bop.shafya.R;
import com.bop.shafya.databinding.FragmentLoginBinding;
import com.bop.shafya.ui.register.RegisterActivity;

import java.util.Objects;

public class LoginFragment extends Fragment {
  
  private LoginViewModel loginViewModel;
  private FragmentLoginBinding binding;
  
  public LoginFragment() {
    // Required empty public constructor
  }
  
  @Override
  public View onCreateView(
    @NonNull LayoutInflater inflater,
    ViewGroup container,
    Bundle savedInstanceState) {
    
    binding = FragmentLoginBinding.inflate(inflater, container, false);
    loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
    
    onSubmit();
    goToRegisterNewUser();
    
    return binding.getRoot();
    
  }
  
  public void onSubmit() {
    binding.loginButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        binding.errorTextView.setText("");
        String username = binding.username.getText().toString();
        String password = binding.password.getText().toString();
        setUiEnabled(false);
        
        // binding.loginProgressBar.setVisibility(View.VISIBLE);
        binding.loginButton.setText("Veuillez patienter");
        loginViewModel.login(username, password);
      }
    });
    
    loginViewModel.getLoginSuccess().observe(getViewLifecycleOwner(), success -> {
      // binding.loginProgressBar.setVisibility(View.VISIBLE);
      binding.loginButton.setText("Veuillez patienter");
      setUiEnabled(true);
      
      if (success) {
        binding.loginButton.setText("Se connecter");
        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);
        getActivity().finish();
        Toast.makeText(getActivity(), "Vous êtes connecté", Toast.LENGTH_SHORT).show();
      }
    });
    
    loginViewModel.getErrorMessage().observe(getViewLifecycleOwner(), error -> {
      // binding.loginProgressBar.setVisibility(View.VISIBLE);
      setUiEnabled(true);
      
      binding.loginButton.setText("Se connecter");
      binding.errorTextView.setText(error);
    });
  }
  
  private void setUiEnabled(Boolean enabled) {
    binding.username.setEnabled(enabled);
    binding.password.setEnabled(enabled);
    binding.loginButton.setEnabled(enabled);
    binding.biometricButton.setEnabled(enabled);
    binding.createAccountButton.setEnabled(enabled);
  }
  
  private void goToRegisterNewUser() {
    binding.createAccountButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(getActivity(), RegisterActivity.class);
        startActivity(intent);
        getActivity().finish();
      }
    });
  }
  
}
