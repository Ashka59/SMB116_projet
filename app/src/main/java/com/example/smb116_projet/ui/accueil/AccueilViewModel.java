package com.example.smb116_projet.ui.accueil;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AccueilViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public AccueilViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is accueil fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}