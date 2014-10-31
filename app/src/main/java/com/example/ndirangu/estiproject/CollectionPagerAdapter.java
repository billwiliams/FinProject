package com.example.ndirangu.estiproject;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by NDIRANGU on 10/25/2014.
 */
public class CollectionPagerAdapter extends FragmentStatePagerAdapter {

    final int NUM_ITEMS = 6; // number of tabs

    public CollectionPagerAdapter(FragmentManager fm) {
        super(fm);

    }

    @Override

    public Fragment getItem(int i) {
         final String ARG_OBJECT = "object";
        Fragment fragment=null;
        Bundle args = new Bundle();
        args.putInt(ARG_OBJECT, i);
        switch(i){
            case 0:
                fragment= new Home();

                break;
            case 1:
                fragment= new Offers();


                break;
            case 2:
                fragment= new Cart();

                break;
            case 3:
                fragment= new Wishlist();

                break;
            case 4:
                fragment= new Me();

                break;
            case 5:
                fragment= new Premium();

                break;


        }

        return fragment;

    }

    @Override
    public int getCount() {

        return NUM_ITEMS;

    }

    @Override
    public CharSequence getPageTitle(int position) {
        String tabLabel = null;
        switch (position) {
            case 0:
                tabLabel = "HOME";

                break;
            case 1:
                tabLabel = "Offers";
                break;
            case 2:
                tabLabel = "Cart";
                break;
            case 3:
                tabLabel = "WishList";
                break;
            case 4:
                tabLabel = "Me";
                break;
            case 5:
                tabLabel = "Premium";
                break;


        }

        return tabLabel;

    }
}


