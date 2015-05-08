package com.example.ahaag.peoplr;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;


public class MatchesProfile extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matches_profile);

        Intent i=getIntent();
        int ID=i.getIntExtra("userID", 0);
        //Get user with ID
        String st="{\"id\":10,\"name\":\"Dipper Pines\",\"blurb\":null,\"fb_access_token\":\"222\",\"created_at\":\"2015-05-04T19:14:06.421Z\",\"updated_at\":\"2015-05-05T21:59:45.375Z\",\"latitude\":40.0,\"longitude\":30.1,\"photo_url\":\"http://vignette2.wikia.nocookie.net/gravityfalls/images/c/cb/S1e16_dipper_will_take_room.png/revision/latest/scale-to-width/250?cb=20130406215813\"}";
        Gson gson2 = new Gson();
       user u2 = gson2.fromJson(st, user.class);

        TextView na = (TextView) findViewById(R.id.username);
        TextView de = (TextView) findViewById(R.id.description);
        TextView co= (TextView) findViewById(R.id.contactInfo);
        ImageView im=(ImageView) findViewById(R.id.imageView1);
        na.setText(u2.name);
//        if (!u2.blurb.equals(null))
        de.setText(u2.blurb);
       // co.setText(c);
        new ImageLoadTask(u2.photo_url, im,true).execute();

        final Button button = (Button) findViewById(R.id.butt);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent nextScreen = new Intent(getApplicationContext(), Matches.class);
                startActivity(nextScreen);
            }
        });
    }




}
