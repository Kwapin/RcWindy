package com.example.mk.rcwindy;

import android.content.Context;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by MK on 28.12.2014.
 */
public class PasswordStrength  {

    public int getPasswordStrength(String haslodoTestu){

       // String haslodoTestu = repassword.getText().toString();
        int response = getRating(haslodoTestu);
       // Toast toast = Toast.makeText(getContext(), "siła hasła: "+response, Toast.LENGTH_LONG);
       // toast.show();
        return response;

    }

    private int getRating(String password) throws IllegalArgumentException {
        if (password == null) {throw new IllegalArgumentException();}
        int passwordStrength = 0;
        if (password.length() > 5) {passwordStrength++;} // minimal pw length of 6
        if (password.toLowerCase()!= password) {passwordStrength++;} // lower and upper case
        if (password.length() > 8) {passwordStrength++;} // good pw length of 9+
        int numDigits= getNumberDigits(password);
        if (numDigits > 0 && numDigits != password.length()) {passwordStrength++;} // contains digits and non-digits
        return passwordStrength;

    }

    public static int getNumberDigits(String inString){
        if (isEmpty(inString)) {
            return 0;
        }
        int numDigits= 0;
        int length= inString.length();
        for (int i = 0; i < length; i++) {
            if (Character.isDigit(inString.charAt(i))) {
                numDigits++;
            }
        }
        return numDigits;
    }

    public static boolean isEmpty(String inString) {
        return inString == null || inString.length() == 0;
    }






}
