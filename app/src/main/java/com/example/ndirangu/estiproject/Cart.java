package com.example.ndirangu.estiproject;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;

/**
 * Created by NDIRANGU on 10/27/2014.
 */
public class Cart extends Fragment  {

    static ListView cartListView,listView;
   static CustomList mAdapter;
    //initialize cart

    String [] cartString={"Cart"};
    Integer[] cartImage={R.drawable.ic_launcher};





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.cart, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
       cartListView=(ListView)getActivity().findViewById(R.id.cart_list);

        mAdapter =new CustomList(getActivity(),cartString,cartImage);
        cartListView.setAdapter(mAdapter);
         listView=cartListView;
        SwipeDismissListViewTouchListener touchListener =
                new SwipeDismissListViewTouchListener(
                        listView,
                        new SwipeDismissListViewTouchListener.DismissCallbacks() {
                            @Override
                            public boolean canDismiss(int position) {
                                return true;
                            }
                            @Override
                            public void onDismiss(ListView listView, int[] reverseSortedPositions) {
                                for (int position : reverseSortedPositions) {
                                 //just getting the position swipped
                                   Log.e("Swipe",""+position );
                                    //remove the view at the position swipped
                                   listView.removeFooterView(cartListView.getChildAt(position));
                                    AllMenu.mPlanetTitles[4]= "Cart Items: "+String.valueOf( Cart.cartListView.getCount()-1);

                                }
                                mAdapter.notifyDataSetChanged();

                            }
                        });
        listView.setOnTouchListener(touchListener);
// Setting this scroll listener is required to ensure that during ListView scrolling,
// we don't look for swipes.
        listView.setOnScrollListener(touchListener.makeScrollListener());
// Set up normal ViewGroup example
        final ViewGroup dismissableContainer = (ViewGroup)getActivity().findViewById(R.id.dismissable_container);
        for (int i = 0; i <cartString.length; i++) {
            final Button dismissableButton = new Button(getActivity());
            dismissableButton.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            dismissableButton.setText("Button " + (i + 1));
            dismissableButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getActivity(),
                            "Clicked " + ((Button) view).getText(),
                            Toast.LENGTH_SHORT).show();
                }
            });
// Create a generic swipe-to-dismiss touch listener.
            dismissableButton.setOnTouchListener(new SwipeDismissTouchListener(
                    dismissableButton,
                    null,
                    new SwipeDismissTouchListener.DismissCallbacks() {
                        @Override
                        public boolean canDismiss(Object token) {
                            return true;
                        }
                        @Override
                        public void onDismiss(View view, Object token) {
                            dismissableContainer.removeView(dismissableButton);
                        }
                    }));
            dismissableContainer.addView(dismissableButton);
        }
    }






    }














