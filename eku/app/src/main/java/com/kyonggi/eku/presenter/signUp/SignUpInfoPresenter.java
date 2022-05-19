package com.kyonggi.eku.presenter.signUp;

import static com.kyonggi.eku.view.signUp.dialog.SignUpErrorDialogFragment.ALL_FINE;
import static com.kyonggi.eku.view.signUp.dialog.SignUpErrorDialogFragment.EMAIL_NOT_VALID;
import static com.kyonggi.eku.view.signUp.dialog.SignUpErrorDialogFragment.NAME_NOT_VALID;
import static com.kyonggi.eku.view.signUp.dialog.SignUpErrorDialogFragment.NOT_FILLED_FIELD;
import static com.kyonggi.eku.view.signUp.dialog.SignUpErrorDialogFragment.PASSWORD_NOT_MATCHING;
import static com.kyonggi.eku.view.signUp.dialog.SignUpErrorDialogFragment.PASSWORD_NOT_VALID;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.kyonggi.eku.databinding.FargmentSignupInfoBinding;
import com.kyonggi.eku.model.OCRForm;
import com.kyonggi.eku.model.SignUpForm;
import com.kyonggi.eku.utils.SendTool;
import com.kyonggi.eku.view.signUp.activity.ActivityInputSignUpInfo;

import java.util.Objects;
import java.util.regex.Pattern;

public class SignUpInfoPresenter {

    private static final String TAG = "SignUpInfoPresenter";
    private Handler handler;
    private final ActivityInputSignUpInfo activity;
    private final Context context;

    public SignUpInfoPresenter(ActivityInputSignUpInfo activity, Context context) {
        this.activity = activity;
        this.context = context;
    }

    public void processForm(OCRForm form) {
        String studNo = form.getStudNo();
        String name = form.getName();
        String department = form.getDepartment();

        if (Objects.equals(studNo, "0")) {
            form.setStudNo("학번을 인식하지 못하였습니다.");
        }
        if (name == null) {
            form.setName("이름을 인식하지 못하였습니다.");
        }
        if (department == null) {
            form.setDepartment("학과를 인식하지 못하였습니다.");
        }
    }

    public int isAllFieldsAppropriate(FargmentSignupInfoBinding binding) {
        String name = binding.editName.getText().toString();
        String department = binding.editDept.getText().toString();
        String studNo = binding.editStudNo.getText().toString();
        String email = binding.editEmail.getText().toString();
        String password = binding.editPassword.getText().toString();
        String passwordConfirm = binding.editPasswordConfirm.getText().toString();

        String digitSpecialPattern = "[`~!@#$%^&*()\\-_=+|\\[\\]{};:'\",.<>/?\\d]";
        String incompleteNamePattern = "[ㄱ-ㅎㅏ-ㅣ]";
        Pattern digitSpecialCompile = Pattern.compile(digitSpecialPattern);
        Pattern incompleteCompile = Pattern.compile(incompleteNamePattern);

        if (name.equals("") || department.equals("") || studNo.equals("") || email.equals("") || password.equals("") || passwordConfirm.equals("")) {
            return NOT_FILLED_FIELD;
        } else if (password.length() <= 8) {
            return PASSWORD_NOT_VALID;
        } else if (!password.equals(passwordConfirm)) {
            return PASSWORD_NOT_MATCHING;
        } else if (digitSpecialCompile.matcher(name).find() || incompleteCompile.matcher(name).find() || name.length()==0){
            return NAME_NOT_VALID;
        } else if (email.length() == 0 || email.contains("@")) {
            return EMAIL_NOT_VALID;
        } else {
            return ALL_FINE;
        }
    }

    public void signUp(SignUpForm form) {
        SendTool.requestForJson2("/signUp", form, getHandler());
    }

    private Handler getHandler() {
        if (handler == null) {
            this.handler = new Handler(Looper.getMainLooper()) {
                public void handleMessage(@NonNull Message msg) {
                    int code = msg.what;
                    String response = (String) msg.obj;
                    Log.d(TAG, "handleMessage: " + code);
                    switch (code) {
                        case SendTool.CONNECTION_FAILED:
                            Toast.makeText(context, "네트워크 연결에 실패하였습니다.", Toast.LENGTH_LONG).show();
                            break;
                        case SendTool.HTTP_OK:
                            activity.onSignUpResponseSuccess();
                            break;
                        case SendTool.HTTP_BAD_REQUEST:
                            activity.onSignUpResponseFailed();
                            break;
                        case SendTool.HTTP_INTERNAL_SERVER_ERROR:
                            Toast.makeText(context, "서버 에러가 발생하였습니다.", Toast.LENGTH_LONG).show();
                        default:
                            Log.e(TAG, "handleMessage: Unknown Error");
                            break;
                    }
                }
            };
        }
        return handler;
    }
}
