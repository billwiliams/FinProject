package com.example.ndirangu.estiproject;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;


import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class Offers extends Fragment {
    static ListView list;
    ProgressDialog progress;
    View viewcart;
    String[] web = {
            "\nladies  Dress @ 10% discount\n",
            "\nMens Suit   for 150,000/=KSHS ",
            "\ncool Kids   Hoodie @25% discount",
            "\nfull womens  collection " +
            "10% discount",
            "\nHandbag complementary with shoes",
            "\nshoes complementary with " +
                    " Handbag ",
            "\nFull women's Outfit @10000KShs"
    } ;
    Integer[] imageId = {
            R.drawable.dress_shoes,
            R.drawable.suit,
            R.drawable.hoodie,
            R.drawable.womens_outfit,
            R.drawable.handbag_shoes,
            R.drawable.womens_shoes,
            R.drawable.womens_fashion
    };



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_offers, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        CustomList adapter = new
                CustomList(getActivity(), web, imageId);
        list=(ListView)getActivity().findViewById(R.id.list);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {



                Toast.makeText(getActivity(), "The Item " + web[+position] +" has been added to cart", Toast.LENGTH_SHORT).show();



                viewcart=list.getAdapter().getView(position,null,list);


                Cart.cartListView.addFooterView(viewcart);
                AllMenu.mPlanetTitles[4]= "Cart Items: "+String.valueOf( Cart.cartListView.getCount()-1);






            }
        });



    }



}

