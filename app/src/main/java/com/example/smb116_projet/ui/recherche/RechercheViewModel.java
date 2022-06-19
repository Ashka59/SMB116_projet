package com.example.smb116_projet.ui.recherche;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class RechercheViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public RechercheViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is gallery fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}