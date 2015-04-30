//package com.example.ahaag.peoplr;
//
//import android.os.Bundle;
//import android.os.Parcel;
//import android.os.Parcelable;
//
//import java.util.ArrayList;
//
///**
// * Created by Josephine on 4/21/2015.
// */
//public class TagLists implements Parcelable{
//     ArrayList<ArrayList<UserProfile>> tags;
//
//    public TagLists(){
//        tags=new ArrayList<ArrayList<UserProfile>>();
//    }
//    public int describeContents() {
//
//        return 0;
//
//    }
//
//    public void writeToParcel(Parcel dest, int flag) {
//       // Bundle bundle = new Bundle();
//        dest.writeList(tags);
//       // bundle.putParcelableArrayList("tags", tags);
//
//    }
//    public static final Parcelable.Creator<TagLists> CREATOR = new Creator<TagLists>() {
//
//        @Override
//        public TagLists createFromParcel(Parcel source) {
//            // read the bundle containing key value pairs from the parcel
//           // Bundle bundle = source.readBundle();
//
//            // instantiate a person using values from the bundle
//            TagLists t=new TagLists();
//            t.tags=source.readList();
//            return t;
//        }
//
//        @Override
//        public TagLists[] newArray(int size) {
//            return new TagLists[size];
//        }
//
//    };
//}
