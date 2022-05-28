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
import com.kyonggi.eku.utils.EditDistance;
import com.kyonggi.eku.utils.SendTool;
import com.kyonggi.eku.view.signUp.activity.ActivityInputSignUpInfo;

import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

public class SignUpInfoPresenter {

    private static final String TAG = "SignUpInfoPresenter";
    private Handler signUpHandler;
    private Handler deptHandler;
    private final ActivityInputSignUpInfo activity;
    private final Context context;

    public SignUpInfoPresenter(ActivityInputSignUpInfo activity, Context context) {
        this.activity = activity;
        this.context = context;
    }

    public void processForm(OCRForm form, List<String> deptList) {
        String studNo = form.getStudNo();
        String name = form.getName();
        String department = form.getDepartment();

        if (Objects.equals(studNo, "0")) {
            form.setStudNo(NO_NOT_FOUND);
        }
        if (name == null) {
            form.setName(NAME_NOT_FOUND);
        }
        if (department == null) {
            form.setDepartment(DEPT_NOT_FOUND);
        } else {
            String dept="";
            int min=Integer.MAX_VALUE;
            for (String deptName : deptList) {
                int calculate = EditDistance.calculate(deptName, form.getDepartment());
                if (calculate < min) {
                    min = calculate;
                    dept = deptName;
                }
            }
            form.setDepartment(dept);
        }
    }

    public int isAllFieldsAppropriate(FargmentSignupInfoBinding binding) {
        String name = binding.editName.getText().toString();
        String department = binding.textDeptShow.getText().toString();
        String studNo = binding.editStudNo.getText().toString();
        String email = binding.editEmail.getText().toString();
        String password = binding.editPassword.getText().toString();
        String passwordConfirm = binding.editPasswordConfirm.getText().toString();

        String kyonggiMail = "[a-zA-Z\\d]+(@kyonggi.ac.kr)";
        String digitSpecialPattern = "[`~!@#$%^&*()\\-_=+|\\[\\]{};:'\",.<>/?\\d]";
        String incompleteNamePattern = "[ㄱ-ㅎㅏ-ㅣ]";
        String passwordContainsNumber = "[0-9]";
        Pattern mailCompile = Pattern.compile(kyonggiMail);
        Pattern digitSpecialCompile = Pattern.compile(digitSpecialPattern);
        Pattern incompleteCompile = Pattern.compile(incompleteNamePattern);
        Pattern passwordCompile = Pattern.compile(passwordContainsNumber);

        if (name.equals("") || department.equals("") || studNo.equals("") || email.equals("") || password.equals("") || passwordConfirm.equals("")) {
            return NOT_FILLED_FIELD;
        } else if (password.length() < 8 || !passwordCompile.matcher(passwordConfirm).find()) {
            return PASSWORD_NOT_VALID;
        } else if (!password.equals(passwordConfirm)) {
            return PASSWORD_NOT_MATCHING;
        } else if (digitSpecialCompile.matcher(name).find() || incompleteCompile.matcher(name).find() || name.length() == 0) {
            return NAME_NOT_VALID;
        } else if (email.length() == 0 || !mailCompile.matcher(email).matches()) {
            return EMAIL_NOT_VALID;
        } else {
            return ALL_FINE;
        }
    }

    public void signUp(SignUpForm form) {
        SendTool.requestForJson2("/signUp", form, getSignUpHandler());
    }

    private Handler getSignUpHandler() {
        if (signUpHandler == null) {
            this.signUpHandler = new Handler(Looper.getMainLooper()) {
                public void handleMessage(@NonNull Message msg) {
                    int code = msg.what;
                    String response = (String) msg.obj;
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
        return signUpHandler;
    }

    private Handler getDeptHandler() {
        if (deptHandler == null) {
            this.deptHandler = new Handler(Looper.getMainLooper()) {
                public void handleMessage(@NonNull Message msg) {
                    int code = msg.what;
                    String response = (String) msg.obj;
                    switch (code) {
                        case SendTool.CONNECTION_FAILED | SendTool.HTTP_BAD_REQUEST | SendTool.HTTP_INTERNAL_SERVER_ERROR:
                            Toast.makeText(context, "네트워크 연결에 실패하였습니다.", Toast.LENGTH_LONG).show();
                            break;
                        case SendTool.HTTP_OK:
                            List<String> deptList = SendTool.parseToString(response);
                            activity.onDeptResponseSuccess(deptList);
                            break;
                    }
                }
            };
        }
        return deptHandler;
    }

    public void getDept() {
        SendTool.request("/signUp/dept", getDeptHandler());
    }

    public static final String NAME_NOT_FOUND = "이름을 인식하지 못하였습니다.";
    public static final String DEPT_NOT_FOUND = "학과를 인식하지 못하였습니다.";
    public static final String NO_NOT_FOUND = "학번을 인식하지 못하였습니다.";

}
