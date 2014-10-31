package com.example.ndirangu.estiproject;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by NDIRANGU on 10/27/2014.
 */
public class Me extends Fragment {
    public  static TextView Fromdb;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.me, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Fromdb=(TextView)getActivity().findViewById(R.id.me_from_db);

        Fromdb.setText("My Email: " +AllMenu.emaili +"\n\n"

        + "Last Entered Store: " + AllMenu.LoginTime +"\n\n"
        + "Account Type: " + "Regular" +"\n\n"
        +"Birthday: "+ AllMenu.userBirthday +"\n\n"
        +"Points: " +"\n\n"
        +"Total Shopping Amount: " +"\n\n"
        + "Favourite Musicians: " + AllMenu.Music + "\n\n");



    }
}
