package com.example.ahaag.peoplr;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import org.json.JSONObject;

public class fblogin extends Activity {

    CallbackManager callbackManager;
    fblogin activity;
    double longitude;
    double latitude;
    Context context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        LocationManager lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        try {
            longitude = location.getLongitude();
            latitude = location.getLatitude();
        }
        catch (NullPointerException e){
            longitude = -89.4;
            latitude = 43.0667;
        }
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(this.getApplicationContext());
        setContentView(R.layout.activity_fblogin);
        context = this;

        activity = this;
        callbackManager = CallbackManager.Factory.create();
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 10, locationListener);
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        Log.e("FB INFO", loginResult.toString());
                        String accessToken= loginResult.getAccessToken().getToken();
                        Log.w("ACCESS TOKEN: ", accessToken);
                        GraphRequest request = GraphRequest.newMeRequest(
                                AccessToken.getCurrentAccessToken(),
                                new GraphRequest.GraphJSONObjectCallback() {
                                    @Override
                                    public void onCompleted(JSONObject object,
                                                            GraphResponse response) {
                                        String fbId = Profile.getCurrentProfile().getId();
                                        String name = Profile.getCurrentProfile().getFirstName();
                                        name = name + " " + Profile.getCurrentProfile().getLastName();
                                        String picture = "https://graph.facebook.com/" + fbId + "/picture?type=large";
                                        startUp.createUser(fbId, name, latitude, longitude, picture, context);
                                        // to log in as a demo user, replace fbId w/ Integer.toString(THEIR FB ID)
                                    }
                                });
                        request.executeAsync();

                        //I think this works... Idk atm

                        //Progress from the Login to the MainActivity
                        Intent intent = new Intent(context, MainActivity.class);
                        startActivity(intent);

                    }

                    // TODO BETTER ERROR MESSAGES?
                    @Override
                    public void onCancel() {
                        Toast.makeText(getApplicationContext(),
                                "Cancelling Login", Toast.LENGTH_LONG)
                                .show();
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        Toast.makeText(getApplicationContext(),
                                "There has been an error", Toast.LENGTH_LONG)
                                .show();

                    }
                });


//        Intent intent = new Intent(currContext, MainActivity.class);
//        startActivity(intent);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private final LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            longitude = location.getLongitude();
            latitude = location.getLatitude();

        }
        @Override
        public void onStatusChanged(String a, int b,Bundle bundle ){
            //IDK WHAT GOES HERE???
        }

        @Override
        public void onProviderEnabled(String provider) {
            //IDK WHAT GOES HERE???
        }

        @Override
        public void onProviderDisabled(String provider) {
            //IDK WHAT GOES HERE???
        }
    };
}
