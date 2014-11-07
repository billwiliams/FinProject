package com.example.ndirangu.estiproject;

/**
 * Created by NDIRANGU on 10/27/2014.
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class Premium extends Fragment {




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.premium, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        TextView PremiumTextView=(TextView)getActivity().findViewById(R.id.premiumTextView);
        PremiumTextView.setText("become a premium member and listen to your favorite music as you shop");

    }
}