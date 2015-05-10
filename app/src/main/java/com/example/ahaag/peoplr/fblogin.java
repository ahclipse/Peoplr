package com.example.ahaag.peoplr;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
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

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

//<<<<<<< HEAD
//import android.content.Intent;
////
//>>>>>>> f3ae491dc09f1f5f25efeed4a5fbe07a39ab3bd8


public class fblogin extends Activity {

    CallbackManager callbackManager;
    List<NameValuePair> params;
    fblogin activity;
    double longitude;
    double latitude;
    String first_name;
    String last_name;
    String picture;
    String id;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        LocationManager lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        try {
             longitude = location.getLongitude();
             latitude = location.getLatitude();
        }
        catch (NullPointerException e){
             longitude = 89.4;
             latitude = 43.0667;
        }
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(this.getApplicationContext());
        setContentView(R.layout.activity_fblogin);
        final Context currContext = this;

        activity = this;
        callbackManager = CallbackManager.Factory.create();
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 10, locationListener);
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                       Log.e("FB INFO", loginResult.toString());
                        String accessToken= loginResult.getAccessToken().getToken();
                        GraphRequest request = GraphRequest.newMeRequest(
                                AccessToken.getCurrentAccessToken(),
                                new GraphRequest.GraphJSONObjectCallback() {
                                    @Override
                                    public void onCompleted(JSONObject object,
                                                            GraphResponse response) {
                                        // Application code String id = Profile.getCurrentProfile().getId();
                                        String FBid = Profile.getCurrentProfile().getId();
                                        String FBfirst_name = Profile.getCurrentProfile().getFirstName();
                                        String FBlast_name = Profile.getCurrentProfile().getLastName();
                                        String FBpicture = Profile.getCurrentProfile().getProfilePictureUri(50, 50).toString();
                                        Log.w("STUFF", "HERE: " + FBid + " " + " " + FBfirst_name + " " + FBlast_name + " " + FBpicture);
                                        id = FBid;
                                        first_name = FBfirst_name;
                                        last_name = FBlast_name;
                                        picture = FBpicture;
                                    }
                                });
                        request.executeAsync();
                                //I think this works... Idk atm

                        params = new ArrayList<NameValuePair>();
                        params.add(new BasicNameValuePair("fb_access_token", id));
                        params.add(new BasicNameValuePair("name", first_name + " " + last_name)); //TODO MAKE THIS THE REAL USER
                        params.add(new BasicNameValuePair("photo_url", picture)); //ProfPic




                        //Progress from the Login to the MainActivity
                        //Intent intent = new Intent(currContext, MainActivity.class);
                        //startActivity(intent);


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

                    }
                });

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




    class UserCreateTask extends AsyncTask<Void, Void, String> {

        Context context;
        fblogin activity;
        int swipeNum;

        int streamLength = 0;

        public UserCreateTask(fblogin activity){
            this.activity = activity;
            this.context = activity;

        }

        @Override
        protected String doInBackground(Void... args) {
            try {
                return loadFromNetwork("http://peoplr-eisendrachen00-4.c9.io/create_user");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(activity.getApplicationContext(), result, Toast.LENGTH_SHORT).show();
        }

        /** Initiates the fetch operation. */
        private String loadFromNetwork(String url) throws IOException {
            InputStream stream = null;
            String str ="";
            try{
                stream = postRequest(url);
                str = readIt(stream, streamLength); //TODO ENSURE THAT THIS WORKS FOR ALL LENGTHS YA DUMB
            } finally {
                if (stream != null) {
                    stream.close();
                }
            }
            return str;
        }

        private InputStream postRequest(String urlString) throws IOException {
            // BEGIN_INCLUDE(get_inputstream)
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(20000 /* milliseconds */);
            conn.setConnectTimeout(20000 /* milliseconds */);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write(getQuery(params));
            writer.flush();
            writer.close();

            // Start the query
            conn.connect();
            InputStream stream = conn.getInputStream();
            streamLength = conn.getContentLength();

            return stream;

            // END_INCLUDE(get_inputstream)
        }

        private String getQuery(List<NameValuePair> params) throws UnsupportedEncodingException
        {
            StringBuilder result = new StringBuilder();
            boolean first = true;

            for (NameValuePair pair : params)
            {
                if (first)
                    first = false;
                else
                    result.append("&");

                result.append(URLEncoder.encode(pair.getName(), "UTF-8"));
                result.append("=");
                result.append(URLEncoder.encode(pair.getValue(), "UTF-8"));
            }

            return result.toString();
        }

        // END NEW GET AND POST STUFF  ---------------------------------------------------------------->

        /** Reads an InputStream and converts it to a String.
         * @param stream InputStream containing HTML from targeted site.
         * @param len Length of string that this method returns.
         * @return String concatenated according to len parameter.
         * @throws java.io.IOException
         * @throws java.io.UnsupportedEncodingException
         */
        private String readIt(InputStream stream, int len) throws IOException, UnsupportedEncodingException {
            Reader reader = null;
            reader = new InputStreamReader(stream, "UTF-8");
            char[] buffer = new char[len];
            reader.read(buffer);
            return new String(buffer);
        }
    }

    class TagUpdateTask extends AsyncTask<Void, Void, String>{

        Context context;
        TinderProfile activity;
        int streamLength = 0;

        public TagUpdateTask(TinderProfile activity){
            this.activity = activity;
            this.context = activity;

        }

        @Override
        protected String doInBackground(Void... args) {
            try {
                return loadFromNetwork("http://peoplr-eisendrachen00-4.c9.io/add_user_to_tag");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(activity.getApplicationContext(), result, Toast.LENGTH_SHORT).show();
        }

        /** Initiates the fetch operation. */
        private String loadFromNetwork(String url) throws IOException {
            InputStream stream = null;
            String str ="";
            try{
                stream = postRequest(url);
                str = readIt(stream, streamLength); //TODO ENSURE THAT THIS WORKS FOR ALL LENGTHS YA DUMB
            } finally {
                if (stream != null) {
                    stream.close();
                }
            }
            return str;
        }

        private InputStream postRequest(String urlString) throws IOException {
            // BEGIN_INCLUDE(get_inputstream)
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(20000 /* milliseconds */);
            conn.setConnectTimeout(20000 /* milliseconds */);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write(getQuery(params));
            writer.flush();
            writer.close();

            // Start the query
            conn.connect();
            InputStream stream = conn.getInputStream();
            streamLength = conn.getContentLength();

            return stream;

            // END_INCLUDE(get_inputstream)
        }

        private String getQuery(List<NameValuePair> tagUpdate) throws UnsupportedEncodingException
        {
            StringBuilder result = new StringBuilder();
            boolean first = true;

            for (NameValuePair pair : tagUpdate)
            {
                if (first)
                    first = false;
                else
                    result.append("&");

                result.append(URLEncoder.encode(pair.getName(), "UTF-8"));
                result.append("=");
                result.append(URLEncoder.encode(pair.getValue(), "UTF-8"));
            }

            return result.toString();
        }

        // END NEW GET AND POST STUFF  ---------------------------------------------------------------->

        /** Reads an InputStream and converts it to a String.
         * @param stream InputStream containing HTML from targeted site.
         * @param len Length of string that this method returns.
         * @return String concatenated according to len parameter.
         * @throws java.io.IOException
         * @throws java.io.UnsupportedEncodingException
         */
        private String readIt(InputStream stream, int len) throws IOException, UnsupportedEncodingException {
            Reader reader = null;
            reader = new InputStreamReader(stream, "UTF-8");
            char[] buffer = new char[len];
            reader.read(buffer);
            return new String(buffer);
        }
    }

}
