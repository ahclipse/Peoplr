package com.example.ahaag.peoplr;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import org.apache.http.NameValuePair;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

//MOAR BUTTS
public class MainActivity extends Activity implements AdapterView.OnItemClickListener{

    final String drawerTitle= "Navigation";
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle drawerToggle;
    String[] fragmentNames;
    ListView drawerList;
    TextView textview;
    ListView listview;
    UserProfile cr;
    ArrayList tag1;
    ArrayList tag2;
    ArrayList tag3;
    //tags[] myTags;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        listview = (ListView) findViewById(R.id.fragmentContainer);

        MainActivity activity = this;
        new TagDownloadTask(3, activity).execute(); // listview? null will be params eventually...

        //DOES THIS WORK THE WAY ITS SUPPOSED TO???
        //DO I NEED TO PUT ANYTHING IN THE APPLICATION CODE
//        GraphRequest request = GraphRequest.newMeRequest(
//                AccessToken.getCurrentAccessToken(),
//                new GraphRequest.GraphJSONObjectCallback() {
//                    @Override
//                    public void onCompleted(
//                            JSONObject object,
//                            GraphResponse response) {
//                        // Application code
//                    }
//                });
//        Bundle parameters = new Bundle();
//        parameters.putString("fields", "id,name,email,picture");//access_token?
//       //I THINK ^^^^ IS ALL I NEED
//        request.setParameters(parameters);
//        request.executeAsync();


        // TODO THIS FIXES THE RUSHING BUG FIGURE OUT A LESS HACKY SOLUTION
        try {
            Thread.sleep(30);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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

        // TODO THIS MAKES NO SENSE, NEEDS TO BE DYNAMIC???

        if (position==0){
            Intent nextScreen = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(nextScreen);
        }
        if (position==1){
            Intent nextScreen = new Intent(this, MyProfile.class);
//            Bundle b = new Bundle();
//            b.putParcelable("currUser", cr);
//            nextScreen.putExtras(b);
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

    public class Tag {

        @SerializedName("updated_at")
        private String updated_at;

        @SerializedName("created_at")
        private String created_at;

        @SerializedName("id")
        private String id;

        @SerializedName("name")
        private String name;

        public final Integer getId() {
            return Integer.parseInt(this.id);
        }

        public final String getName() {
            return this.name;
        }

    }

    protected void onTagResponse(String response){

        Gson gson = new Gson();

        String jsonOutput = response.trim();
        Type listType = new TypeToken<List<Tag>>(){}.getType();
        final List<Tag> tags = (List<Tag>) gson.fromJson(jsonOutput, listType);

        final ArrayList<String> list = new ArrayList<String>();
        for (Tag t : tags) {
            list.add(t.getName());
        }

        ArrayAdapter adapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1, list);
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                String tagName = list.get(position);
                Intent nextScreen = new Intent(getApplicationContext(), TinderProfile.class);
                nextScreen.putExtra("tag", tagName);
                nextScreen.putExtra("id", tags.get(position).getId());
                startActivity(nextScreen);
            }
        });
    }

    class TagDownloadTask extends AsyncTask<Void, Void, String> {

        int type;
        List<NameValuePair> params;

        ProgressDialog dialog;
        Context context;
        MainActivity activity;

        int streamLength = 0;

        // http://stackoverflow.com/questions/23267345/how-to-use-spinning-or-wait-icon-when-asynctask-is-being-performed-in-android
        // http://stackoverflow.com/questions/1270760/passing-a-string-by-reference-in-java?rq=1

        public TagDownloadTask(int type, MainActivity activity){

            this.type = type;

            this.activity = activity;
            this.context = activity;
            dialog = new ProgressDialog(context);
            //dialog.setTitle("title");
            //dialog.setMessage("message");
        }

        protected void onPreExecute() {
            this.dialog.show();
        }

        @Override
        protected String doInBackground(Void... args) {
            try {
                return loadFromNetwork("http://peoplr-eisendrachen00-4.c9.io/all_tags", false, params);
            } catch (IOException e) {
                return ("Connection error!");
            }
        }

        @Override
        protected void onPostExecute(String result) {

            Toast.makeText(activity.getApplicationContext(), (String) result, Toast.LENGTH_LONG).show();
            onTagResponse(result);
            dialog.dismiss();
        }

        /** Initiates the fetch operation. */
        private String loadFromNetwork(String url, Boolean isPOST, List<NameValuePair> params) throws IOException {
            InputStream stream = null;
            String str ="";
            try{
                stream = getRequest(url);
                str = readIt(stream, streamLength); //TODO ENSURE THAT THIS WORKS FOR ALL LENGTHS YA DUMB
            } finally {
                if (stream != null) {
                    stream.close();
                }
            }
            return str;
        }

        // ADD NEW GET AND POST STUFF  ---------------------------------------------------------------->

        private InputStream getRequest(String urlString) throws IOException {
            // BEGIN_INCLUDE(get_inputstream)
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(20000 /* milliseconds */);
            conn.setConnectTimeout(20000 /* milliseconds */);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
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
