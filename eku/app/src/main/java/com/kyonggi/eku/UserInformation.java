package com.kyonggi.eku;

import android.content.Context;

public class UserInformation {
    String email="";
    private Context getApplicationContext;
    String password ="";
    boolean postLogin=false;
    boolean verify =false;
    public void toPhone(Context getAppliactionContext,String email,String password){
        PreferenceManagers.setString(getAppliactionContext,"email",email);
        PreferenceManagers.setString(getAppliactionContext,"password",password);
        PreferenceManagers.setBoolean(getAppliactionContext,"postLogin",postLogin);
        PreferenceManagers.setBoolean(getAppliactionContext,"verify",verify);
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
}
