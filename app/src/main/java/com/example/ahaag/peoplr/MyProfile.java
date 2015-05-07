package com.example.ahaag.peoplr;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;


public class MyProfile extends Activity implements AdapterView.OnItemClickListener{
final String drawerTitle= "Navigation";
        DrawerLayout drawerLayout;
        ActionBarDrawerToggle drawerToggle;
        String[] fragmentNames;
        ListView drawerList;
        UserProfile cr;
        int id;
        user currUser;

@Override
protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

    startUp s=((startUp)getApplicationContext());
    //USED TO GET CORRECT USER
    id=s.getUserId();
    String st="{\"id\":10,\"name\":\"Dipper Pines\",\"blurb\":null,\"fb_access_token\":\"222\",\"created_at\":\"2015-05-04T19:14:06.421Z\",\"updated_at\":\"2015-05-05T21:59:45.375Z\",\"latitude\":40.0,\"longitude\":30.1,\"photo_url\":\"http://vignette2.wikia.nocookie.net/gravityfalls/images/c/cb/S1e16_dipper_will_take_room.png/revision/latest/scale-to-width/250?cb=20130406215813\"}";
    Gson gson = new Gson();
     currUser= gson.fromJson(st, user.class);
    TextView username = (TextView) findViewById(R.id.username);
    username.setText(currUser.name);
//    TextView contactInfo=(TextView) findViewById(R.id.contactInfo);
//    contactInfo.setText(cr.getContactInfo());
    TextView description=(TextView) findViewById(R.id.description);
    //if not null!
    //if (!currUser.blurb.equals(""))
    description.setText(currUser.blurb);



       ImageView im=(ImageView) findViewById(R.id.imageView1);

    new ImageLoadTask(currUser.photo_url, im).execute();


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
        Intent nextScreen = new Intent(getApplicationContext(), MapsActivity.class);
        startActivity(nextScreen);
        }
        }

  }
class user{
    int id;
    String name;
    String blurb;
    String fb_access_token;
    String created_at;
    String updated_at;
    double longitude;
    double latitude;
    String photo_url;
}
