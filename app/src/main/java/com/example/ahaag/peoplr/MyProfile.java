package com.example.ahaag.peoplr;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;


public class MyProfile extends Activity implements AdapterView.OnItemClickListener{
    final String drawerTitle= "Navigation";
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle drawerToggle;
    String[] fragmentNames;
    ListView drawerList;
    UserProfile cr;
    int id;
    User currUser;
    Context currContext;
    startUp s;
    MyProfile activity;
    ImageView imageView;

    List<NameValuePair> params;

    String photoUrl = "";
    String blurb = "";
    String name = "";
    String contact = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        currContext = this;
        s = ((startUp) getApplicationContext());
        activity = this;

        params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("user_id", Integer.toString(s.getUserId()))); //TODO MAKE THIS THE REAL USER

        //imageView = (ImageView) findViewById(R.id.imageView1);

        new UserProfileLoadTask(activity).execute();

        //im.setImageDrawable(d);

        // Set the drawer toggle as the DrawerListener
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout,R.string.drawer_open,R.string.drawer_close) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getActionBar().setTitle(drawerTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        drawerLayout.setDrawerListener(drawerToggle);

        //Setting up the values of the Sidebar menu (Home, My Profile, Matches, Settings)
        fragmentNames=getResources().getStringArray(R.array.fragment_names);
        drawerList = (ListView) findViewById(R.id.left_drawer);

        //Sets the adapter of the list view for the side Drawer
        drawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_list_item, fragmentNames));

        //Listener for the drawer objects
        drawerList.setOnItemClickListener(this);
    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle your other action bar items...

        return super.onOptionsItemSelected(item);
    }

    //onItemClick to handle placement of title on drawer
    @Override
    public void onItemClick(AdapterView parent, View view, int position, long id) {
        getActionBar().setTitle(fragmentNames[position]);
        drawerLayout.closeDrawer(drawerList);
        if (position==0){
            Intent nextScreen = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(nextScreen);
        }
        if (position==1){
            Intent nextScreen = new Intent(getApplicationContext(), MyProfile.class);
            startActivity(nextScreen);
        }
        if (position==2){
            Intent nextScreen = new Intent(getApplicationContext(), Matches.class);
            startActivity(nextScreen);
        }
        if (position==3){
            Intent nextScreen = new Intent(getApplicationContext(), fblogin.class);
            startActivity(nextScreen);
        }
    }

    protected void onUserProfileResponse(String result){
        //set user id

        Gson gson = new Gson();
        User user = null;

        String jsonOutput = result.trim();
        Type userType = new TypeToken<User>(){}.getType();
        user = (User) gson.fromJson(jsonOutput, userType);

        photoUrl = user.getPhoto_url();
        new ImageDownloadTask().execute();

        name = user.getName();
        if ((user.getBlurb() != null) && !user.getBlurb().equals("")) { blurb = user.getBlurb(); }
//        contact = "";

//        TextView username = (TextView) findViewById(R.id.username);
//        username.setText(name);
//
//        if ((users.get(0).getBlurb() != null) && !users.get(0).getBlurb().equals("")) {
//            TextView description=(TextView) findViewById(R.id.description);
//            description.setText(currUser.getBlurb());
//            blurb = users.get(0).getBlurb();
//        }

//        TODO
//        contact = "";
//        TextView contactInfo=(TextView) findViewById(R.id.contactInfo);
//        contactInfo.setText(cr.getContactInfo());

    }

    protected void onLoadImages(Bitmap image) {
        //TODO STUFF for real this is very broken

        imageView = (ImageView) findViewById(R.id.imageView1);
        Drawable d = new BitmapDrawable(getResources(), image);
        //imageView.setImageBitmap(image);
        //imageView.setImageDrawable(d);

        ImageView imageview = (ImageView) findViewById(R.id.imageView1);
        imageview.setImageBitmap(image);
        imageview.setAdjustViewBounds(true);
        Drawable drawable= new BitmapDrawable(getResources(),image);
        imageview.setImageDrawable(drawable);


        Log.w("onLoadImages:  ", "at least we tried...");

        TextView username = (TextView) findViewById(R.id.username);
        username.setText(name);

        TextView description=(TextView) findViewById(R.id.description);
        description.setText(blurb);

        Button button = (Button) findViewById(R.id.Button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent nextScreen = new Intent(getApplicationContext(), EditInfo.class);
                startActivity(nextScreen);
            }
        });
    }

    class ImageDownloadTask extends AsyncTask<Void, Void, Bitmap> {

        Bitmap image;
        ArrayList<String> imageUrl;

        public ImageDownloadTask() {
            image = null;
        }

        @Override
        protected Bitmap doInBackground(Void... params) {

            Bitmap image = null;
            HttpClient client = new DefaultHttpClient();
            HttpGet request = new HttpGet(photoUrl);
            HttpResponse response;

            try {
                response = (HttpResponse)client.execute(request);
                HttpEntity entity = response.getEntity();
                BufferedHttpEntity bufferedEntity = new BufferedHttpEntity(entity);
                InputStream inputStream = bufferedEntity.getContent();
                image = BitmapFactory.decodeStream(inputStream);
                Log.w("Image downloaded: ", image.toString());
                if(image == null) Log.w("Image is null: ", "YOU SUCK!");
//            try {
//                    URL urlConnection = new URL(photoUrl);
//                    HttpURLConnection connection = (HttpURLConnection) urlConnection
//                            .openConnection();
//                    connection.setDoInput(true);
//                    connection.setReadTimeout(100000 /* milliseconds */);
//                    connection.setConnectTimeout(100000 /* milliseconds */);
//                    connection.connect();
//                    InputStream input = connection.getInputStream();
////                    input.reset(); // TODO HACKY FIX?
//                    image = BitmapFactory.decodeStream(input);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return image;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            //super.onPostExecute(result);
            Log.w("onPostExecute: ", "YAY");
            onLoadImages(image);
        }
    }


    class UserProfileLoadTask extends AsyncTask<Void, Void, String> {

        ProgressDialog dialog;
        Context context;
        MyProfile activity;

        int streamLength = 0;

        // http://stackoverflow.com/questions/23267345/how-to-use-spinning-or-wait-icon-when-asynctask-is-being-performed-in-android
        // http://stackoverflow.com/questions/1270760/passing-a-string-by-reference-in-java?rq=1

        public UserProfileLoadTask(MyProfile activity){

            this.activity = activity;
            this.context = activity;
            dialog = new ProgressDialog(context);
            //dialog.setTitle("Loading");
            //dialog.setMessage("message");
        }

        protected void onPreExecute() {
            this.dialog.show();
        }

        @Override
        protected String doInBackground(Void... args) {
            try {
                return loadFromNetwork("http://peoplr-eisendrachen00-4.c9.io/get_profile");
            } catch (IOException e) {
                return ("Connection error!");
            }
        }

        @Override
        protected void onPostExecute(String result) {

            Toast.makeText(activity.getApplicationContext(), (String) result, Toast.LENGTH_LONG).show();
            onUserProfileResponse(result);
            dialog.dismiss();
        }

        /** Initiates the fetch operation. */
        private String loadFromNetwork(String url) throws IOException {
            InputStream stream = null;
            String str ="";
            try{
                stream = postRequest(url, params);
                str = readIt(stream, 2 * streamLength); //TODO ENSURE THAT THIS WORKS FOR ALL LENGTHS YA DUMB
            } finally {
                if (stream != null) {
                    stream.close();
                }
            }
            return str;
        }

        // ADD NEW GET AND POST STUFF  ---------------------------------------------------------------->

        private InputStream postRequest(String urlString, List<NameValuePair> params) throws IOException {
            // BEGIN_INCLUDE(get_inputstream)
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(50000 /* milliseconds */);
            conn.setConnectTimeout(50000 /* milliseconds */);
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

}
//class user{
//    int id;
//    String name;
//    String blurb;
//    String fb_access_token;
//    String created_at;
//    String updated_at;
//    double longitude;
//    double latitude;
//    String photo_url;
//}
