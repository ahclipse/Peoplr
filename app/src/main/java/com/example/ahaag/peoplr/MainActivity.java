package com.example.ahaag.peoplr;


import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONObject;

import java.util.ArrayList;

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
    tags[] myTags;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);



        listview = (ListView) findViewById(R.id.fragmentContainer);
        //Get tags string
        String s="[{\"id\":9,\"name\":\"mystery twins\",\"created_at\":\"2015-05-04T19:10:35.030Z\",\"updated_at\":\"2015-05-04T19:10:35.030Z\"},{\"id\":10,\"name\":\"lumberjack stuff\",\"created_at\":\"2015-05-04T19:15:06.903Z\",\"updated_at\":\"2015-05-04T19:15:06.903Z\"},{\"id\":11,\"name\":\"making money\",\"created_at\":\"2015-05-04T19:15:12.365Z\",\"updated_at\":\"2015-05-04T19:15:12.365Z\"},{\"id\":12,\"name\":\"illuminati\",\"created_at\":\"2015-05-04T19:15:17.642Z\",\"updated_at\":\"2015-05-04T19:15:17.642Z\"},{\"id\":13,\"name\":\"glitter\",\"created_at\":\"2015-05-04T19:15:24.528Z\",\"updated_at\":\"2015-05-04T19:15:24.528Z\"},{\"id\":14,\"name\":\"fixing stuff\",\"created_at\":\"2015-05-04T19:15:30.946Z\",\"updated_at\":\"2015-05-04T19:15:30.946Z\"},{\"id\":15,\"name\":\"society of the blind eye\",\"created_at\":\"2015-05-04T22:19:48.857Z\",\"updated_at\":\"2015-05-04T22:19:48.857Z\"}]";

        Gson gson = new Gson();
         myTags = gson.fromJson(s, tags[].class);


        final ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < myTags.length; ++i) {
            list.add(myTags[i].name);
        }


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

         ArrayAdapter adapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1, list);
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
//
                   String tag=list.get(position);
                Intent nextScreen = new Intent(getApplicationContext(), TinderProfile.class);
                nextScreen.putExtra("tag", tag);
                nextScreen.putExtra("id", myTags[position].id);
                startActivity(nextScreen);
            }
        });




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
            Intent nextScreen = new Intent(getApplicationContext(), MapsActivity.class);
            startActivity(nextScreen);
        }
    }
  class tags{
           int id;
           String name;
            String created_at;
            String updated_at;
       }


}
