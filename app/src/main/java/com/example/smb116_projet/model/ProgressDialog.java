package com.example.smb116_projet.model;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;

import com.example.smb116_projet.R;

public class ProgressDialog {
    Activity activity;
    AlertDialog dialog;

    public ProgressDialog(Activity myActivity) { activity = myActivity; }

    public void startLoading() {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        LayoutInflater inflater = activity.getLayoutInflater();

        builder.setView(inflater.inflate(R.layout.progress_dialog, null));
        builder.setCancelable(true);

        dialog = builder.create();
        dialog.show();

    }

    public void dismissLoading() { dialog.dismiss(); }
}