package com.example.ahaag.peoplr;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
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
import com.google.gson.annotations.SerializedName;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

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
import java.util.concurrent.ExecutionException;


public class MyProfile extends Activity implements AdapterView.OnItemClickListener {
    final String drawerTitle = "Navigation";
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle drawerToggle;
    String[] fragmentNames;
    ListView drawerList;
    UserProfile cr;
    int id;
    //user currUser;
    List<NameValuePair> params;
    User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        //USED TO GET CORRECT USER
        id = startUp.getUserId();
        params = new ArrayList<NameValuePair>();

        //String st="{\"id\":10,\"name\":\"Dipper Pines\",\"blurb\":null,\"fb_access_token\":\"222\",\"created_at\":\"2015-05-04T19:14:06.421Z\",\"updated_at\":\"2015-05-05T21:59:45.375Z\",\"latitude\":40.0,\"longitude\":30.1,\"photo_url\":\"http://vignette2.wikia.nocookie.net/gravityfalls/images/c/cb/S1e16_dipper_will_take_room.png/revision/latest/scale-to-width/250?cb=20130406215813\"}";
        params.add(new BasicNameValuePair("user_id", Integer.toString(startUp.getUserId()))); //TODO MAKE THIS THE REAL USER
//=======
//        startUp s=((startUp)getApplicationContext());
//        //USED TO GET CORRECT USER
//        id=s.getUserId();
//        String st="{\"id\":10,\"name\":\"Dipper Pines\",\"blurb\":null,\"fb_access_token\":\"222\",\"created_at\":\"2015-05-04T19:14:06.421Z\",\"updated_at\":\"2015-05-05T21:59:45.375Z\",\"latitude\":40.0,\"longitude\":30.1,\"photo_url\":\"http://vignette2.wikia.nocookie.net/gravityfalls/images/c/cb/S1e16_dipper_will_take_room.png/revision/latest/scale-to-width/250?cb=20130406215813\"}";
//        Gson gson = new Gson();
//        currUser= gson.fromJson(st, User.class);
//        TextView username = (TextView) findViewById(R.id.username);
//        username.setText(currUser.getName());
////    TextView contactInfo=(TextView) findViewById(R.id.contactInfo);
////    contactInfo.setText(cr.getContactInfo());
//        TextView description=(TextView) findViewById(R.id.description);
//        //if not null!
//        if (currUser.getBlurb()!=null)
//            description.setText(currUser.getBlurb());
//>>>>>>> 01d21f2afb5724abf03433376cfba50484557c91

        new UserDownloadTask(this).execute();
//    Gson gson = new Gson();
//     currUser= gson.fromJson(st, user.class);
//    TextView username = (TextView) findViewById(R.id.username);
//    username.setText(currUser.name);
////    TextView contactInfo=(TextView) findViewById(R.id.contactInfo);
////    contactInfo.setText(cr.getContactInfo());
//    TextView description=(TextView) findViewById(R.id.description);
//    //if not null!
//    if (currUser.blurb!=null)
//    description.setText(currUser.blurb);


//<<<<<<< HEAD
        // ImageView im=(ImageView) findViewById(R.id.imageView1);

        //new ImageLoadTask(currUser.photo_url, im, true).execute();

        ImageView imageview = (ImageView) findViewById(R.id.imageView1);
        startUp.loadProfilePhoto(imageview);

       // new ImageLoadTask(currUser.getPhoto_url(), im, true).execute();
//>>>>>>> 01d21f2afb5724abf03433376cfba50484557c91


        //im.setImageDrawable(d);

        Button button = (Button) findViewById(R.id.Button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent nextScreen = new Intent(getApplicationContext(), EditInfo.class);
                startActivity(nextScreen);
            }
        });
        // Set the drawer toggle as the DrawerListener
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.drawer_open, R.string.drawer_close) {

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
        fragmentNames = getResources().getStringArray(R.array.fragment_names);
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

        if (position == 0) {
            Intent nextScreen = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(nextScreen);
        }
        if (position == 1) {
            Intent nextScreen = new Intent(getApplicationContext(), MyProfile.class);
            startActivity(nextScreen);
        }
        if (position == 2) {
            Intent nextScreen = new Intent(getApplicationContext(), Matches.class);
            startActivity(nextScreen);
        }
        if (position == 3) {
            Intent nextScreen = new Intent(getApplicationContext(), fblogin.class);
            startActivity(nextScreen);
        }
    }
    protected void onUserResponse(String response){

        Gson gson = new Gson();

        String jsonOutput = response.trim();
        //Type listType = new TypeToken<List<User>>(){}.getType();
        user = (User) gson.fromJson(jsonOutput, User.class);

        //final ArrayList<String> list = new ArrayList<String>();
        final ArrayList<String> imageUrls = new ArrayList<String>();
//        for (User u : users) {
//            list.add(u.getName());
          imageUrls.add(user.getPhoto_url());
//        }

        try {
            new ImageDownloadTask(imageUrls).execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
//=======
//        if (position==0){
//            Intent nextScreen = new Intent(getApplicationContext(), MainActivity.class);
//            startActivity(nextScreen);
//        }
//        if (position==1){
//            Intent nextScreen = new Intent(getApplicationContext(), MyProfile.class);
//            startActivity(nextScreen);
//        }
//        if (position==2){
//            Intent nextScreen = new Intent(getApplicationContext(), Matches.class);
//            startActivity(nextScreen);
//        }
//        if (position==3){
//            Intent nextScreen = new Intent(getApplicationContext(), fblogin.class);
//            startActivity(nextScreen);
//>>>>>>> 01d21f2afb5724abf03433376cfba50484557c91
        }
    }

//<<<<<<< HEAD
//       // Toast.makeText(getApplicationContext(), (String) list.toString(), Toast.LENGTH_LONG).show();
//        //TODO
//    }

    protected void onLoadImages(List<Bitmap> images){

    TextView username = (TextView) findViewById(R.id.username);
    username.setText(user.getName());
//    TextView contactInfo=(TextView) findViewById(R.id.contactInfo);
//    contactInfo.setText(cr.getContactInfo());
    TextView description=(TextView) findViewById(R.id.description);
    //if not null!
    if (user.getBlurb()!=null)
    description.setText(user.getBlurb());


        ImageView im=(ImageView) findViewById(R.id.imageView1);



        im.setImageBitmap(images.get(0));
    }

    class ImageDownloadTask extends AsyncTask<Void, Void, Void> {

        List<Bitmap> images;
        ArrayList<String> imageUrls;

        public ImageDownloadTask(ArrayList<String> imageUrls) {
            this.imageUrls = imageUrls;
            images = new ArrayList<Bitmap>();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                for (String url : imageUrls){
                    URL urlConnection = new URL(url);
                    HttpURLConnection connection = (HttpURLConnection) urlConnection
                            .openConnection();
                    connection.setDoInput(true);
                    connection.connect();
                    InputStream input = connection.getInputStream();
                    Bitmap myBitmap = BitmapFactory.decodeStream(input);
                    images.add(myBitmap);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            onLoadImages(images);
        }
    }
    //}
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
    public class User {

        @SerializedName("updated_at")
        private String updated_at;

        @SerializedName("created_at")
        private String created_at;

        @SerializedName("id")
        private String id;

        @SerializedName("name")
        private String name;

        @SerializedName("blurb")
        private String blurb;

        @SerializedName("fb_access_token")
        private String fb_access_token;

        @SerializedName("latitude")
        private String latitude;

        @SerializedName("longitude")
        private String longitude;

        @SerializedName("photo_url")
        private String photo_url;

        public final Integer getId() {
            return Integer.parseInt(this.id);
        }

        public final String getName() {
            return this.name;
        }

        public final String getBlurb() {
            return this.blurb;
        }

        public final String getFb_access_token() {
            return this.fb_access_token;
        }

        public final String getLatitude() {
            return this.latitude;
        }

        public final String getLongitude() {
            return this.longitude;
        }

        public final String getPhoto_url() {
            return this.photo_url;
        }


    }

//TODO GET SOME USERRRRRS

    class UserDownloadTask extends AsyncTask<Void, Void, String> {

        ProgressDialog dialog;
        Context context;
        MyProfile activity;

        int streamLength = 0;

        // http://stackoverflow.com/questions/23267345/how-to-use-spinning-or-wait-icon-when-asynctask-is-being-performed-in-android
        // http://stackoverflow.com/questions/1270760/passing-a-string-by-reference-in-java?rq=1

        public UserDownloadTask(MyProfile activity) {

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
                return loadFromNetwork("https://peoplr-eisendrachen00-4.c9.io/get_profile");
            } catch (IOException e) {
                return ("Connection error!");
            }
        }

        @Override
        protected void onPostExecute(String result) {

            Toast.makeText(activity.getApplicationContext(), (String) result, Toast.LENGTH_LONG).show();
            onUserResponse(result);
            dialog.dismiss();
        }

        /**
         * Initiates the fetch operation.
         */
        private String loadFromNetwork(String url) throws IOException {
            InputStream stream = null;
            String str = "";
            try {
                stream = postRequest(url, params);
                str = readIt(stream, streamLength); //TODO ENSURE THAT THIS WORKS FOR ALL LENGTHS YA DUMB
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

        private String getQuery(List<NameValuePair> params) throws UnsupportedEncodingException {
            StringBuilder result = new StringBuilder();
            boolean first = true;

            for (NameValuePair pair : params) {
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

        /**
         * Reads an InputStream and converts it to a String.
         *
         * @param stream InputStream containing HTML from targeted site.
         * @param len    Length of string that this method returns.
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

