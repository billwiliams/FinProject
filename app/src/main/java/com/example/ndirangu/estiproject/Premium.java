package com.example.ndirangu.estiproject;

/**
 * Created by NDIRANGU on 10/27/2014.
 */

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Premium extends Fragment {

    static ListView list;

    View viewcart;
    String[] premiumoffers = {
            "\nPremium Mens' Watch @15% discount\n",
            "\nMens Ties\nColors blue,white,red @1000/Kshs ",
            "\nLeather handbag for 6699.00Kshs",
            "\nduffle bags " +
                    "10% discount"
    } ;
    Integer[] premiumimageId = {
            R.drawable.premium_watch,
            R.drawable.premium_tie,
            R.drawable.premium_bag,
            R.drawable.premium_handbag

    };
    static  Integer[] Amount = {
            20000,
            1000,
            6699,
            15356


    };



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        // Inflate the layout for this fragment
        if (AllMenu.PremiumUser==false) {
            return inflater.inflate(R.layout.unpremium, container, false);
        }
        else {
            return inflater.inflate(R.layout.premium, container, false);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //TextView PremiumTextView=(TextView)getActivity().findViewById(R.id.premiumTextView);
        if (AllMenu.PremiumUser==false) {
            TextView premiumTextview= (TextView)getActivity().findViewById(R.id.premiumTextview);

            premiumTextview.setText("become a premium member and enjoy premium offers just tailored for you");

        }
        else{

Log.i("Unable", "Image setting");
            CustomList adapter = new
                    CustomList(getActivity(), premiumoffers, premiumimageId);
            list=(ListView)getActivity().findViewById(R.id.premiumlist);
            list.setAdapter(adapter);
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {



                    Toast.makeText(getActivity(), "The Item " + premiumoffers[+position] + " has been added to cart", Toast.LENGTH_SHORT).show();



                    viewcart=list.getAdapter().getView(position,null,list);


                    Cart.cartListView.addFooterView(viewcart);

                    AllMenu.mPlanetTitles[4]= "Cart Items: "+String.valueOf( Cart.cartListView.getCount()-1);

                    AllMenu.ShoppingAmount+=Amount[+position];
                    AllMenu.mPlanetTitles[5]="Cart Items Amount:"+String.valueOf(AllMenu.ShoppingAmount);






                }
            });

        }
    }
}