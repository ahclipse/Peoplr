package com.example.ahaag.peoplr;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Parcelable;
//import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;


public class startUp extends Application {

   // @Override
   // protected void onCreate(Bundle savedInstanceState) {
        //super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_start_up


       // Resources r=getResources();

        //Create necessary objects
        UserProfile currUser=new UserProfile("Jane Doe", "example@gmail.com", "Description-none", new ArrayList<UserProfile>());// , r.getDrawable(R.drawable.picture1));
        ArrayList<UserProfile> tag1 = new ArrayList<UserProfile>(Arrays.asList(new UserProfile("Mary Miller", "example@gmail.com", "Hey, how it going? I did this a lot as a kid but none of " +
                                     "my friends are interested in doing it with me. I am a friendly person who loves to have " +
                                    "fun.", new ArrayList()),new UserProfile("Bobby Smith","example@gmail.com", "Hello, my name is Bobby. I just moved from " +
                    "Chicago and I don't know many people in the area. This is an activity I enjoy doing" +
                    "with lots of people so please don't hesitate to contact me if you are interested." +
                    "I look forward to meeting you.", new ArrayList()),new UserProfile("Charlie Pence", "example@gmail.com", "This is just one of the many things I love to do. I also love" +
                   "to swim and dance and play the harmonica. ", new ArrayList()),new UserProfile("Nick Boots", "example@gmail.com", "Hello everyone! My name is Nick. I was born in Idaho" +
                    "and moved here when I was six. I am trying to branch out and try new things and meet new" +
                   "people.", new ArrayList())));//, r.getDrawable(R.drawable.picture2)));
//            tag1.add(new UserProfile("Bobby Smith","example@gmail.com", "Hello, my name is Bobby. I just moved from " +
//                    "Chicago and I don't know many people in the area. This is an activity I enjoy doing" +
//                    "with lots of people so please don't hesitate to contact me if you are interested." +
//                    "I look forward to meeting you.", new ArrayList()));//, r.getDrawable(R.drawable.picture1)));
//            tag1.add(new UserProfile("Charlie Martin", "example@gmail.com", "This is just one of the many things I love to do. I also love" +
//                    "to swim and dance and play the harmonica. ", new ArrayList()));//, r.getDrawable(R.drawable.picture1)));
//            tag1.add(new UserProfile("Nick Boots", "example@gmail.com", "Hello everyone! My name is Nick. I was born in Idaho" +
//                    "and moved here when I was six. I am trying to branch out and try new things and meet new" +
//                    "people.", new ArrayList()));// r.getDrawable(R.drawable.picture1)));
                ArrayList < UserProfile > tag2 = new ArrayList<UserProfile>(Arrays.asList(new UserProfile("Daisy Song", "email@email.com", "I am very shy but I am quite good at this. I am very quiet and calm" +
                   "and a nice person to hang out with. Please contact me.", new ArrayList()),new UserProfile("Susan Bouda", "example@gmail.com","Hey! I am senior at uw madison. I am studying wildlife ecology" +               "and I love being outside. I love to laugh and hang out with my friends. I can be loud sometimes" +
        "but I can be quiet too.", new ArrayList())));
//            tag2.add(new UserProfile("Nathan Simin", "email@email.com", "I am very shy but I am quite good at this. I am very quiet and calm" +
//                    "and a nice person to hang out with. Please contact me.", new ArrayList()));// r.getDrawable(R.drawable.picture3)));
//            tag2.add(new UserProfile("Susan Bouda", "example@gmail.com","Hey! I am senior at uw madison. I am studying wildlife ecology" +
//                    "and I love being outside. I love to laugh and hang out with my friends. I can be loud sometimes" +
//                    "but I can be quiet too.", new ArrayList()));// r.getDrawable(R.drawable.picture2)));
//            tag2.add(new UserProfile("Susan Bouda", "example@gmail.com", "Hey! I am senior at uw madison. I am studying wildlife ecology" +
//                    "and I love being outside. I love to laugh and hang out with my friends. I can be loud sometimes" +
//                    "but I can be quiet too.", new ArrayList()));// r.getDrawable(R.drawable.picture2)));
         ArrayList<UserProfile> tag3= new ArrayList<UserProfile>(Arrays.asList(new UserProfile("Mark Laeney", "example@email.com", "Hello. My name is Mark. I am a freshmen who just moved here" +
                    "from Arizona. I love Wisconsin so far but I don't know a lot of people. Let me know if" +
      "you want to meet me. I love forward to meeting you.", new ArrayList()),new UserProfile("George Thomas", "example@email.com", "My name is George. I am a nice guy.",
                  new ArrayList())));
//            tag3.add(new UserProfile("Mark Laeney", "example@email.com", "Hello. My name is Mark. I am a freshmen who just moved here" +
//                    "from Arizona. I love Wisconsin so far but I don't know a lot of people. Let me know if" +
//                    "you want to meet me. I love forward to meeting you.", new ArrayList()));// r.getDrawable(R.drawable.picture3)));
//            tag3.add(new UserProfile("George Thomas", "example@email.com", "My name is George. I am a nice guy.",
//                    new ArrayList()));//, r.getDrawable(R.drawable.picture2)));
       // TagLists lists=new TagLists();
//        lists.tags.add(tag1);
//        lists.tags.add(tag2);
//        lists.tags.add(tag3);
//        Intent nextScreen = new Intent(this, MainActivity.class);//changes fromm app Content
//        Bundle b = new Bundle();
//        b.putParcelable("currUser", currUser);
//        b.putParcelableArrayList("tag1", tag1);
//        b.putParcelableArrayList("tag2", tag2);
//        b.putParcelableArrayList("tag3", tag3);
//        nextScreen.putExtras(b);
//        startActivity(nextScreen);
    public UserProfile getCurrUser(){
        return currUser;
    }
    public void setCurrUser(UserProfile p){
        currUser=p;
    }
    public ArrayList<UserProfile> getTagList(int pos){
        if (pos==0){
            return tag1;
        }
        else if (pos==1){
            return tag2;
        }
        else{
            return tag3;
        }

    }
    public void setTags(int pos, ArrayList<UserProfile> t){
        if (pos==0){
            tag1=t;
        }
        else if (pos==1){
            tag2=t;
        }
        else{
            tag3=t;
        }

    }
//    }

}
