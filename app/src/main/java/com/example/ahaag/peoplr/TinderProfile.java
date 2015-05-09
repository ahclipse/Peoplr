package com.example.ahaag.peoplr;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.andtinder.model.CardModel;
import com.andtinder.model.Orientations;
import com.andtinder.view.CardContainer;
import com.andtinder.view.SimpleCardStackAdapter;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class TinderProfile extends Activity implements AdapterView.OnItemClickListener {
    final String drawerTitle = "Navigation";
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle drawerToggle;
    String[] fragmentNames;
    ListView drawerList;
    CardContainer mCardContainer;
    startUp s;
    int tagID;
    int[] u;
    user u2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tinder_profile);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
        setContentView(R.layout.activity_tinder_profile);

        Toast.makeText(getApplicationContext(),
                "Swipe right to like and left to dislike", Toast.LENGTH_LONG)
                .show();

        TextView tagtext = (TextView) findViewById(R.id.tagtext);
        Intent i = getIntent();
        String tag = i.getStringExtra("tag");
        tagtext.setText(tag);
        s = ((startUp) getApplicationContext());
        //THis is the tag id!!1
        tagID = i.getIntExtra("id", 0);
        String st = "[9,10,11,12,13,14,15,16]";//Get users on tag with id above
        Gson gson = new Gson();
        u = gson.fromJson(st, int[].class);

        //delete when image url works
        Resources r=getResources();

        mCardContainer = (CardContainer) findViewById(R.id.layoutview);
        mCardContainer.setOrientation(Orientations.Orientation.Disordered);
        SimpleCardStackAdapter adapter = new SimpleCardStackAdapter(this);
        for (int j = 0; j < u.length; j++) {
            //get user with id u.ids[j]
            String st2 = "{\"id\":10,\"name\":\"Dipper Pines\",\"blurb\":null,\"fb_access_token\":\"222\",\"created_at\":\"2015-05-04T19:14:06.421Z\",\"updated_at\":\"2015-05-05T21:59:45.375Z\",\"latitude\":40.0,\"longitude\":30.1,\"photo_url\":\"http://vignette2.wikia.nocookie.net/gravityfalls/images/c/cb/S1e16_dipper_will_take_room.png/revision/latest/scale-to-width/250?cb=20130406215813\"}";
            Gson gson2 = new Gson();
            u2 = gson2.fromJson(st2, user.class);
            ImageView i2=(ImageView)findViewById(R.id.imageView1);
            Bitmap bmap=null;
            try {
                 bmap = new ImageLoadTask(u2.photo_url, i2, false).execute().get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            CardModel card = new CardModel(u2.name, u2.blurb,bmap);//Must add actual picture
//
            card.setOnCardDimissedListener(new CardModel.OnCardDimissedListener() {
                @Override
                public void onLike() {
                    //Log.i("Swipeable Cards","I like the card");
                    Toast.makeText(getApplicationContext(),
                            "Liked", Toast.LENGTH_LONG)
                            .show();

                    //udate swiping liked with id u[j] and s.getUserId()
                }

                @Override
                public void onDislike() {
                    // Log.i("Swipeable Cards","I dislike the card");
                    Toast.makeText(getApplicationContext(),
                            "Disliked", Toast.LENGTH_LONG)
                            .show();
                    //udate swiping disliked with id u[j] and s.getUserId()
                }
            });
            adapter.add(card);
        }


        mCardContainer.setAdapter(adapter);


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
//            s.setCurrUser(cr);
//            s.setTags(tagID,tags);//changed from pos
            startActivity(nextScreen);
        }
        if (position == 1) {
            Intent nextScreen = new Intent(getApplicationContext(), MyProfile.class);
//            s.setCurrUser(cr);
//            s.setTags(tagID,tags);
            startActivity(nextScreen);
        }
        if (position == 2) {
            Intent nextScreen = new Intent(getApplicationContext(), Matches.class);
//            s.setCurrUser(cr);
//            s.setTags(tagID,tags);
            // Bundle b = new Bundle();
//            b.putParcelable("currUser", cr);
//            b.putParcelableArrayList("tag1", tag1);
//            b.putParcelableArrayList("tag2", tag2);
//            b.putParcelableArrayList("tag3", tag3);
//            nextScreen.putExtras(b);
            startActivity(nextScreen);
        }
        if (position == 3) {
            Intent nextScreen = new Intent(getApplicationContext(), fblogin.class);
//            s.setCurrUser(cr);
//            s.setTags(tagID,tags);
            startActivity(nextScreen);
        }
    }

}
//class userID{
//    int[] ids;
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