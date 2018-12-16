package com.example.nishat.weatherforcaster.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;

import com.example.nishat.weatherforcaster.R;

/**
 * Created by Nishat on 3/18/2017.
 */

public class AlertFragment extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Context context = getActivity();
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setTitle(R.string.error_title)
                .setMessage(R.string.error_message)
                .setPositiveButton("Ok", null);
        AlertDialog dialog = builder.create();
        return dialog;
    }
}
