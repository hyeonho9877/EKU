package com.kyonggi.eku;

import android.content.Context;

public class UserInformation {
    String email="";
    private Context getApplicationContext;
    String password ="";
    boolean postLogin=false;
    boolean verify =false;
    String student_no = "";
    String department ="";



    public UserInformation(Context context){
        getApplicationContext = context;
        email = PreferenceManagers.getString(getApplicationContext,"email");
        password = PreferenceManagers.getString(getApplicationContext,"password");
        postLogin = PreferenceManagers.getBoolean(getApplicationContext,"postLogin");
        verify = PreferenceManagers.getBoolean(getApplicationContext,"verify");
        student_no = PreferenceManagers.getString(getApplicationContext,"student_no");
        department = PreferenceManagers.getString(getApplicationContext,"department");
    }

    public UserInformation() {

    }

    public void toPhone(Context getAppliactionContext,String email,String password,String student_no,String department,boolean postLogin,boolean verify){
        PreferenceManagers.setString(getAppliactionContext,"email",email);
        PreferenceManagers.setString(getAppliactionContext,"password",password);
        PreferenceManagers.setBoolean(getAppliactionContext,"postLogin",postLogin);
        PreferenceManagers.setBoolean(getAppliactionContext,"verify",verify);
        PreferenceManagers.setString(getAppliactionContext,"student_no",student_no);
        PreferenceManagers.setString(getAppliactionContext,"department",department);
    }

    public String fromPhoneEmail(Context getApplicationContext) {
        if (email.equals(""))
        {
            email = PreferenceManagers.getString(getApplicationContext,"email");
        }
        return email;
    }

    public String fromPhonePassword(Context getApplicationContext){
        if(password.equals(""))
        {
            password = PreferenceManagers.getString(getApplicationContext,"password");
        }
        return password;
    }

    public boolean fromPhonePostLogin(Context getApplicationContext){
        postLogin = PreferenceManagers.getBoolean(getApplicationContext,"postLogin");
        return postLogin;
    }
    public boolean fromPhoneVerify(Context getApplicationContext){
        verify = PreferenceManagers.getBoolean(getApplicationContext,"verify");
        return verify;
    }

    public String fromPhoneStudentNo(Context getApplicationContext){
        if(student_no.equals(""))
        {
        student_no = PreferenceManagers.getString(getApplicationContext,"student_no");
        }
        return student_no;
    }
    public String fromPhoneDepartment(Context getApplicationContext){
        if(department.equals(""))
        {
            department = PreferenceManagers.getString(getApplicationContext,"department");
        }
        return department;
    }





    /*
    *   세션 체크
    *   다음과 같이 복붙 할것!
    *
        UserInformation userInfo = new UserInformation();
        String check = userInfo.sessionCheck(getApplicationContext());
        if(check.equals("needLogin"))
        {
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
            finish();
        }
        else if(check.equals("needVerify")){
            Intent intent = new Intent(getApplicationContext(), VerfityActivity.class);
            startActivity(intent);
            finish();
        }
    *
    * 다시체크
     */
    public String sessionCheck(Context getApplicationContext){
        if (!(PreferenceManagers.getBoolean(getApplicationContext,"postLogin"))){
            return "needLogin";
        }
        else if(!(PreferenceManagers.getBoolean(getApplicationContext,"postLogin"))) {
            return "needVerify";
        }
        else{
            return "success";
        }


    }
}
