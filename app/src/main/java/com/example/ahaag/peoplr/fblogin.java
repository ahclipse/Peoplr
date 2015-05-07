package com.example.ahaag.peoplr;

import android.app.Activity;
//<<<<<<< HEAD
//import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
////
import android.content.Context;
import android.content.Intent;
//>>>>>>> f3ae491dc09f1f5f25efeed4a5fbe07a39ab3bd8
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

//import com.facebook.login.LoginClient;


public class fblogin extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(this.getApplicationContext());
        setContentView(R.layout.activity_fblogin);
        final Context currContext = this;

        CallbackManager callbackManager = CallbackManager.Factory.create();
        //doesnt' redirect to mainActivity (check AndroidManifest)
        //NEEDS TO REDIRECT
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        Log.e("FB INFO", loginResult.toString());
                        // App code
                        String accessToken= loginResult.getAccessToken().getToken();


                        //setContentView(R.layout.activity_main);

                        //Remeber to uncomment
//                        Intent nextScreen = new Intent(getApplicationContext(), MainActivity.class);
//                        startActivity(nextScreen);
                        //setContentView(R.layout.activity_main);
                        //TODO:
//                        Intent intent = new Intent(currContext, MainActivity.class);
//                        //intent.putExtra(NAME, VALUE);
//
//                        startActivity(intent);


                    }

                    @Override
                    public void onCancel() {
                        Toast.makeText(getApplicationContext(),
                                "Canceling the idiot!", Toast.LENGTH_LONG)
                                .show();
                        // App code

                    }

                    @Override
                    public void onError(FacebookException exception) {
                        Toast.makeText(getApplicationContext(),
                                "Error of death!", Toast.LENGTH_LONG)
                                .show();
                        // App code
                    }
                });
    //delete this
        Intent nextScreen = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(nextScreen);

    }




//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_fblogin, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
//

}
