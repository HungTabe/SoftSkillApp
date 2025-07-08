package com.example.softskillapp.viewmodel;

import android.content.Context;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.softskillapp.data.repository.UserRepository;

public class MainViewModel extends ViewModel {

    private final UserRepository userRepository;
    private final MutableLiveData<Boolean> _isConnected = new MutableLiveData<>();
    public LiveData<Boolean> isConnected = _isConnected;

    public MainViewModel(Context context) {
        this.userRepository = new UserRepository(context);
    }

    public void checkSupabaseConnection() {
        userRepository.checkConnectionWithCallback(isConnected -> _isConnected.setValue(isConnected));
    }
}