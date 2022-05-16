package com.kyonggi.eku.view.signUp.fragment;

import static com.kyonggi.eku.view.signUp.dialog.SignUpErrorDialogFragment.ALL_FINE;
import static com.kyonggi.eku.view.signUp.dialog.SignUpErrorDialogFragment.NOT_FILLED_FIELD;
import static com.kyonggi.eku.view.signUp.dialog.SignUpErrorDialogFragment.PASSWORD_NOT_MATCHING;
import static com.kyonggi.eku.view.signUp.dialog.SignUpErrorDialogFragment.PASSWORD_NOT_VALID;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.kyonggi.eku.databinding.FargmentSignupInfoBinding;
import com.kyonggi.eku.model.OCRForm;
import com.kyonggi.eku.model.SignUpForm;
import com.kyonggi.eku.utils.exceptions.NoExtraDataException;
import com.kyonggi.eku.view.signUp.OnConfirmedListener;
import com.kyonggi.eku.view.signUp.activity.ActivityInputSignUpInfo;
import com.kyonggi.eku.view.signUp.dialog.SignUpErrorDialogFragment;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class FragmentSignupInfo extends Fragment {
    private FargmentSignupInfoBinding binding;
    private OnConfirmedListener listener;
    private ActivityInputSignUpInfo activity;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FargmentSignupInfoBinding.inflate(inflater, container, false);
        activity = (ActivityInputSignUpInfo) getActivity();
        initListeners();

        try {
            autoFillElements(activity.getForm());
        } catch (NoExtraDataException ignored) {

        };

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (OnConfirmedListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnArticleSelectedListener");
        }
    }

    private void initListeners() {
        binding.buttonConfirm.setOnClickListener(v -> {
            switch (activity.isAllFieldsAppropriate(binding)) {
                case NOT_FILLED_FIELD:
                    new SignUpErrorDialogFragment(NOT_FILLED_FIELD).show(activity.getSupportFragmentManager(), "alert");
                    break;
                case PASSWORD_NOT_VALID:
                    new SignUpErrorDialogFragment(PASSWORD_NOT_VALID).show(activity.getSupportFragmentManager(), "alert");
                    break;
                case PASSWORD_NOT_MATCHING:
                    new SignUpErrorDialogFragment(PASSWORD_NOT_MATCHING).show(activity.getSupportFragmentManager(), "alert");
                    break;
                case ALL_FINE:
                    String name = binding.editName.getText().toString();
                    String department = binding.editDept.getText().toString();
                    Long studNo = Long.parseLong(binding.editStudNo.getText().toString());
                    String email = binding.editEmail.getText().toString();
                    String password = binding.editPassword.getText().toString();

                    SignUpForm signUpForm = new SignUpForm(name, studNo, password, department, email);
                    listener.onConfirmed(signUpForm);
            }
            ;
        });

        binding.editPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {

            }
        });
    }

    private void autoFillElements(OCRForm form) {
        binding.editStudNo.setText(form.getStudNo());
        binding.editDept.setText(form.getDepartment());
        binding.editName.setText(form.getName());
    }
}