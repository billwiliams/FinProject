package com.example.ndirangu.estiproject;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.TextView;


public class AllMenu extends Activity {
    TextView txtQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_menu);
TextView text=(TextView)findViewById(R.id.database);
//start a new instant of database class mysqlitehelper
        MySQLiteHelper me=new MySQLiteHelper(getApplicationContext());
        //show the text that the user queries
        txtQuery=(TextView)findViewById(R.id.textquery);
//set the emaili string to the email of the user
        String emaili=me.findEmail();
        //display the artists the user likes
        text.setText(me.findUser(emaili));
        //handle the search intent
        handleIntent(getIntent());

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.all_menu, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));


        handleIntent(getIntent());
        return true;
    }
    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        handleIntent(intent);
    }



    //handling intent data
    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);

            txtQuery.setText("Search Query: " + query);
            Log.i("query is  ",query);

        }
    }





    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        if (id == R.id.search) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
