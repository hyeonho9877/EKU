package com.kyonggi.eku.view.signUp.fragment;

import static com.kyonggi.eku.presenter.signUp.SignUpInfoPresenter.DEPT_NOT_FOUND;
import static com.kyonggi.eku.presenter.signUp.SignUpInfoPresenter.NAME_NOT_FOUND;
import static com.kyonggi.eku.presenter.signUp.SignUpInfoPresenter.NO_NOT_FOUND;
import static com.kyonggi.eku.view.signUp.dialog.SignUpErrorDialogFragment.ALL_FINE;
import static com.kyonggi.eku.view.signUp.dialog.SignUpErrorDialogFragment.EMAIL_NOT_VALID;
import static com.kyonggi.eku.view.signUp.dialog.SignUpErrorDialogFragment.NAME_NOT_VALID;
import static com.kyonggi.eku.view.signUp.dialog.SignUpErrorDialogFragment.NOT_FILLED_FIELD;
import static com.kyonggi.eku.view.signUp.dialog.SignUpErrorDialogFragment.PASSWORD_NOT_MATCHING;
import static com.kyonggi.eku.view.signUp.dialog.SignUpErrorDialogFragment.PASSWORD_NOT_VALID;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.kyonggi.eku.R;
import com.kyonggi.eku.databinding.FargmentSignupInfoBinding;
import com.kyonggi.eku.model.OCRForm;
import com.kyonggi.eku.model.SignUpForm;
import com.kyonggi.eku.presenter.signUp.SignUpInfoPresenter;
import com.kyonggi.eku.utils.EditDistance;
import com.kyonggi.eku.utils.callbacks.OnConfirmedListener;
import com.kyonggi.eku.utils.exceptions.NoExtraDataException;
import com.kyonggi.eku.view.signUp.OnDeptSelectedListener;
import com.kyonggi.eku.view.signUp.activity.ActivityInputSignUpInfo;
import com.kyonggi.eku.view.signUp.dialog.DialogDepartmentPicker;
import com.kyonggi.eku.view.signUp.dialog.SignUpErrorDialogFragment;

import java.util.Collections;
import java.util.List;
import java.util.OptionalInt;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class FragmentSignupInfo extends Fragment implements OnDeptSelectedListener {
    private static final String TAG = "FragmentSignupInfo";
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

        }
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
        String kyonggiMail = "(@kyonggi.ac.kr)";
        String digitSpecialPattern = "[`~!@#$%^&*()\\-_=+|\\[\\]{};:'\",.<>/?\\d]";
        String incompleteNamePattern = "[ㄱ-ㅎㅏ-ㅣ]";
        String passwordContainsNumber = "[0-9]";
        Pattern mailCompile = Pattern.compile(kyonggiMail);
        Pattern digitSpecialCompile = Pattern.compile(digitSpecialPattern);
        Pattern incompleteCompile = Pattern.compile(incompleteNamePattern);
        Pattern passwordCompile = Pattern.compile(passwordContainsNumber);

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
                case NAME_NOT_VALID:
                    new SignUpErrorDialogFragment(NAME_NOT_VALID).show(activity.getSupportFragmentManager(), "alert");
                    break;
                case EMAIL_NOT_VALID:
                    new SignUpErrorDialogFragment(EMAIL_NOT_VALID).show(activity.getSupportFragmentManager(), "alert");
                    break;
                case ALL_FINE:
                    String name = binding.editName.getText().toString();
                    String department = binding.textDeptShow.getText().toString();
                    Long studNo = Long.parseLong(binding.editStudNo.getText().toString());
                    String email = binding.editEmail.getText().toString();
                    String password = binding.editPassword.getText().toString();

                    SignUpForm signUpForm = new SignUpForm(name, studNo, password, department, email);
                    listener.onConfirmed(signUpForm);
            }
            ;
        });

        binding.editPassword.setOnFocusChangeListener((view, b) -> binding.textPasswordGuide.setVisibility(View.VISIBLE));
        binding.editPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (binding.editPasswordConfirm.getText().toString().equals(charSequence.toString())) {
                    binding.textPasswordConfirmGuide.setText(R.string.password_confirm_right);
                    binding.textPasswordConfirmGuide.setTextColor(Color.parseColor("#2A5189"));
                } else {
                    binding.textPasswordConfirmGuide.setText(R.string.password_confirm_wrong);
                    binding.textPasswordConfirmGuide.setTextColor(Color.parseColor("#F7941E"));
                }
                if (binding.editPassword.getText().length() < 8 || !passwordCompile.matcher(charSequence).find()) {
                    binding.textPasswordGuide.setText(R.string.password_guide);
                    binding.textPasswordGuide.setTextColor(Color.parseColor("#F7941E"));
                } else {
                    binding.textPasswordGuide.setText(R.string.password_guide_right);
                    binding.textPasswordGuide.setTextColor(Color.parseColor("#2A5189"));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        binding.editEmail.setOnFocusChangeListener((view, b) -> binding.textEmailGuide.setVisibility(View.VISIBLE));
        binding.editEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() == 0 || !mailCompile.matcher(charSequence).find()) {
                    binding.textEmailGuide.setText("올바른 이메일을 입력해주세요.");
                    binding.textEmailGuide.setTextColor(Color.parseColor("#F7941E"));
                } else {
                    binding.textEmailGuide.setText("올바른 이메일입니다.");
                    binding.textEmailGuide.setTextColor(Color.parseColor("#2A5189"));
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        binding.editPasswordConfirm.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                binding.textPasswordConfirmGuide.setVisibility(View.VISIBLE);
                String password = binding.editPassword.getText().toString();
                if (password.length() != 0 && password.equals(charSequence.toString())) {
                    binding.textPasswordConfirmGuide.setText(R.string.password_confirm_right);
                    binding.textPasswordConfirmGuide.setTextColor(Color.parseColor("#2A5189"));
                } else {
                    binding.textPasswordConfirmGuide.setText(R.string.password_confirm_wrong);
                    binding.textPasswordConfirmGuide.setTextColor(Color.parseColor("#F7941E"));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        binding.editName.setOnFocusChangeListener((view, b) -> {
            binding.textNameGuide.setVisibility(View.VISIBLE);
        });
        binding.editName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (digitSpecialCompile.matcher(charSequence).find()) {
                    binding.textNameGuide.setText("이름에는 숫자나 특수문자가 포함될 수 없습니다.");
                    binding.textNameGuide.setTextColor(Color.parseColor("#F7941E"));
                } else {
                    if (incompleteCompile.matcher(charSequence).find() || charSequence.length() == 0) {
                        binding.textNameGuide.setText("정확한 이름을 입력해주세요.");
                        binding.textNameGuide.setTextColor(Color.parseColor("#F7941E"));
                    } else {
                        binding.textNameGuide.setText("사용할 수 있는 이름입니다.");
                        binding.textNameGuide.setTextColor(Color.parseColor("#2A5189"));
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        binding.buttonSelectDept.setOnClickListener(v->{
            selectDept();
        });
    }

    private void autoFillElements(OCRForm form) {
        if (form.getStudNo().equals(NO_NOT_FOUND)) binding.editStudNo.setHint(NO_NOT_FOUND);
        else binding.editStudNo.setText(form.getStudNo());
        if (form.getDepartment().equals(DEPT_NOT_FOUND)) binding.textDeptShow.setHint(DEPT_NOT_FOUND);
        else binding.textDeptShow.setText(form.getDepartment());
        if(form.getName().equals(NAME_NOT_FOUND)) binding.editName.setHint(NAME_NOT_FOUND);
        else binding.editName.setText(form.getName());
    }

    public void selectDept(){
        DialogDepartmentPicker picker = new DialogDepartmentPicker(activity.getDeptList(), this);
        picker.show(getActivity().getSupportFragmentManager(), "picker");
    }

    @Override
    public void onSelected(String item) {
        binding.textDeptShow.setText(item);
    }
}