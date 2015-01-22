package com.example.mk.rcwindy.Data;

/**
 * Created by MK on 22.12.2014.
 */
public class User {

    int userID;
    String userName;
    String password;

    public User(){

    }

    public User( String userName, String password){

        this.userName=userName;
        this.password=password;
    }

    public User(int userID, String userName, String password){
        this.userID=userID;
        this.userName=userName;
        this.password=password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public  String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }


}
