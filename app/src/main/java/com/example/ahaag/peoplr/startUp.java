package com.example.ahaag.peoplr;


import android.app.Application;



public class startUp extends Application {



    private int userId = -1;


    public int getUserId(){
        return userId;
    }

    public void setUserId(int userId){
        this.userId=userId;
    }

// // set
    //((MyApplication) this.getApplication()).setSomeVariable("foo");

    // get
    //String s = ((MyApplication) this.getApplication()).getSomeVariable();   }

}
