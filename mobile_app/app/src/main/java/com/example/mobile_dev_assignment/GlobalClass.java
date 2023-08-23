package com.example.mobile_dev_assignment;

import android.app.Application;

public class GlobalClass extends Application {

    private static int userID = -1 ; //None value within database to stop incorrect access

    public GlobalClass() {
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

}
