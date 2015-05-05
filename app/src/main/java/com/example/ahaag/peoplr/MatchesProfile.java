package com.example.ahaag.peoplr;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MatchesProfile extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matches_profile);

        Intent i=getIntent();
        String name=i.getStringExtra("name");
        String d=i.getStringExtra("description");
        String c=i.getStringExtra("contactInfo");
        TextView na = (TextView) findViewById(R.id.username);
        TextView de = (TextView) findViewById(R.id.description);
        TextView co= (TextView) findViewById(R.id.contactInfo);
        na.setText(name);
        de.setText(d);
        co.setText(c);

        final Button button = (Button) findViewById(R.id.butt);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent nextScreen = new Intent(getApplicationContext(), Matches.class);
                startActivity(nextScreen);
            }
        });
    }




}
