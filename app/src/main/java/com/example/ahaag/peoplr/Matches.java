package com.example.ahaag.peoplr;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
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
import java.util.ArrayList;


public class Matches extends Activity implements AdapterView.OnItemClickListener{
    final String drawerTitle= "Navigation";
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle drawerToggle;
    String[] fragmentNames;
    ListView drawerList;
    ListView listview;
    UserProfile cr;
    ArrayList tag1;
    ArrayList tag2;
    ArrayList tag3;
    ArrayList<String> list;
    startUp s;
    ArrayList<UserProfile> m;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matches);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        s=((startUp)getApplicationContext());
        cr=s.getCurrUser();

//        Intent j=getIntent();
//
//        cr =j.getParcelableExtra("currUser");
//        tag1=j.getParcelableArrayListExtra("tag1");
//        tag2=j.getParcelableArrayListExtra("tag2");
//        tag3=j.getParcelableArrayListExtra("tag3");

         list=new ArrayList<>();
//        list.add("Bob");
//        list.add("Joan");

         m=cr.getMatches();

        for (int i=0; i<m.size(); i++){
            UserProfile p=m.get(i);
            list.add(p.getName());
        }
//
        listview = (ListView) findViewById(R.id.fragmentContainer);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
//                Toast.makeText(getApplicationContext(),
//                        "Click ListItem Number " + position, Toast.LENGTH_LONG)
//                        .show();

                UserProfile u=m.get(position);
                Intent nextScreen = new Intent(getApplicationContext(), MatchesProfile.class);
                nextScreen.putExtra("name", u.getName());
                nextScreen.putExtra("description", u.getDescription());
                nextScreen.putExtra("contactInfo", u.getContactInfo());
//                Bundle b = new Bundle();
//                b.putParcelable("currUser", cr);
//                b.putParcelableArrayList("tag1", tag1);
//                b.putParcelableArrayList("tag2", tag2);
//                b.putParcelableArrayList("tag3", tag3);
//                nextScreen.putExtras(b);
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
