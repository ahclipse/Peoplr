package com.example.ahaag.peoplr;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;



public class MyAdapter extends FragmentStatePagerAdapter {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    public MyAdapter (FragmentManager fragmentManager){
        super(fragmentManager);
    }
    public int getCount() {
        //change this
        return 1;
    }

    public Fragment getItem(int position) {
        //change this 2
        return null;
    }

//    static Fragment createNewFragmentToDisplay(){
//        //I think you add backend into here????
//        Fragment displayFragment = new Fragment();
//        return displayFragment;
//    }

}
