package com.example.ahaag.peoplr;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

//<<<<<<< HEAD
//import android.content.Intent;
////
//>>>>>>> f3ae491dc09f1f5f25efeed4a5fbe07a39ab3bd8


public class fblogin extends Activity {
    CallbackManager callbackManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(this.getApplicationContext());
        setContentView(R.layout.activity_fblogin);
        final Context currContext = this;

         callbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                       Log.e("FB INFO", loginResult.toString());
                        String accessToken= loginResult.getAccessToken().getToken();

                        String id = Profile.getCurrentProfile().getId();
                        String first_name = Profile.getCurrentProfile().getFirstName();
                        String last_name = Profile.getCurrentProfile().getLastName();
                        String picture = Profile.getCurrentProfile().getProfilePictureUri(50, 50).toString();
                        Log.w("STUFF", "HERE: "+id+" "+" "+first_name + " " + last_name);

                        //Progress from the Login to the MainActivity
                        Intent intent = new Intent(currContext, MainActivity.class);
                        startActivity(intent);


                    }

                    @Override
                    public void onCancel() {
                        Toast.makeText(getApplicationContext(),
                                "Cancelling Login", Toast.LENGTH_LONG)
                                .show();
                        // App code

                    }

                    @Override
                    public void onError(FacebookException exception) {
                        Toast.makeText(getApplicationContext(),
                                "There has been an error", Toast.LENGTH_LONG)
                                .show();
                        // App code
                    }
                });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }



}
