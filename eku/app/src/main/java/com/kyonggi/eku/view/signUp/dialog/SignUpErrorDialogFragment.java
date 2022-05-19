package com.kyonggi.eku.view.signUp.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.kyonggi.eku.R;

public class SignUpErrorDialogFragment extends DialogFragment {

    private final int code;

    public SignUpErrorDialogFragment(int code) {
        this.code = code;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder
                .setMessage(R.string.signUp_fill_fields)
                .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
        switch (code) {
            case NOT_FILLED_FIELD:
                builder.setMessage(R.string.signUp_fill_fields);
                break;
            case PASSWORD_NOT_VALID:
                builder.setMessage(R.string.signUp_password_not_valid);
                break;
            case PASSWORD_NOT_MATCHING:
                builder.setMessage(R.string.signUp_password_not_matching);
                break;
            case DUPLICATED_ACCOUNT:
                builder.setMessage(R.string.signUp_duplicated_account);
                break;
            case NAME_NOT_VALID:
                builder.setMessage(R.string.name_guide);
                break;
        }
        return builder.create();
    }

    public static final int ALL_FINE = 0x00;
    public static final int NOT_FILLED_FIELD = 0x01;
    public static final int PASSWORD_NOT_VALID = 0x02;
    public static final int PASSWORD_NOT_MATCHING = 0x03;
    public static final int DUPLICATED_ACCOUNT = 0x04;
    public static final int NAME_NOT_VALID = 0x05;
}
