package com.example.ahaag.peoplr;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.andtinder.model.CardModel;
import com.andtinder.model.Orientations;
import com.andtinder.view.CardContainer;
import com.andtinder.view.SimpleCardStackAdapter;

import java.util.ArrayList;

//CHANGE IT TO FRAGMENT
public class TinderProfile extends Activity implements AdapterView.OnItemClickListener{
    final String drawerTitle= "Navigation";
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle drawerToggle;
    String[] fragmentNames;
    ListView drawerList;
    CardContainer mCardContainer;
     ///ADDED THESE BEAST
//    MyAdapter mAdapter;
//    ViewPager mPager;

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
        Intent i=getIntent();
        String tag=i.getStringExtra("tag");
        tagtext.setText(tag);

        mCardContainer = (CardContainer) findViewById(R.id.layoutview);
        mCardContainer.setOrientation(Orientations.Orientation.Disordered);
        SimpleCardStackAdapter adapter = new SimpleCardStackAdapter(this);
        adapter.add(new CardModel("Bobby Smith", "Hello, my name is Bobby. I just moved from " +
                "Chicago and I don't know many people in the area. This is an activity I enjoy doing" +
                "with lots of people so please don't hesitate to contact me if you are interested." +
                "I look forward to meeting you.", getDrawable(R.drawable.picture1)));
        adapter.add(new CardModel("Mary Miller", "Hey, how it going? I did this a lot as a kid but none of " +
                "my friends are interested in doing it with me. I am a friendly person who loves to have " +
                "fun.", getDrawable(R.drawable.picture2)));
        adapter.add(new CardModel("Nathan Simin", "I am very shy but I am quite good at this. I am very quiet and calm" +
                "and a nice person to hang out with. Please contact me.", getDrawable(R.drawable.picture3)));
        adapter.add(new CardModel("Susan Bouda", "Hey! I am senior at uw madison. I am studying wildlife ecology" +
                "and I love being outside. I love to laugh and hang out with my friends. I can be loud sometimes" +
                "but I can be quiet too.", getDrawable(R.drawable.picture2)));
        adapter.add(new CardModel("Mark Laeney", "Hello. My name is Mark. I am a freshmen who just moved here" +
                "from Arizona. I love Wisconsin so far but I don't know a lot of people. Let me know if" +
                "you want to meet me. I love forward to meeting you.", getDrawable(R.drawable.picture3)));
        adapter.add(new CardModel("Charlie Martin", "This is just one of the many things I love to do. I also love" +
                "to swim and dance and play the harmonica. ", getDrawable(R.drawable.picture1)));
        adapter.add(new CardModel("George Thomas", "My name is George. I am a nice guy.", getDrawable(R.drawable.picture2)));
        adapter.add(new CardModel("Nick Boots", "Hello everyone! My name is Nick. I was born in Idaho" +
                "and moved here when I was six. I am trying to branch out and try new things and meet new" +
                "people.", getDrawable(R.drawable.picture1)));
        mCardContainer.setAdapter(adapter);


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
