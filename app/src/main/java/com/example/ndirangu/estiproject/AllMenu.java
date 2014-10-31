package com.example.ndirangu.estiproject;

import android.app.ActionBar;
import android.app.Activity;


import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;


import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.ByteArrayBuffer;
import org.apache.http.util.EntityUtils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import static com.example.ndirangu.estiproject.R.id.content_frame;


public class AllMenu extends android.support.v4.app.FragmentActivity implements ActionBar.TabListener {
    TextView txtQuery;
   static  String emaili,Music,LoginTime,userBirthday;

    //ActionBar
    ActionBar bar;
    //Navigation Drawer
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
   static String[] mPlanetTitles;
    //Navigation drawer


    //SwipeViews
    ViewPager mViewPager;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_menu);
        //ActionBar
       bar= getActionBar();
        bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        //Swipe Views
        CollectionPagerAdapter mCollectionPagerAdapter;


        mCollectionPagerAdapter = new CollectionPagerAdapter(
                getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mCollectionPagerAdapter);
        mViewPager
                .setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {

                    @Override

                    public void onPageSelected(int position) {
                        bar.setSelectedNavigationItem(position);
                    }

                });
        for (int i = 0; i < mCollectionPagerAdapter.getCount(); i++) {
            bar.addTab(bar.newTab()
                    .setText(mCollectionPagerAdapter.getPageTitle(i))
                    .setTabListener(this));
        }

        //Swipe Views
        //Items variables from database
        TextView text=(TextView)findViewById(R.id.database);
//start a new instant of database class mysqlitehelper
        MySQLiteHelper me=new MySQLiteHelper(getApplicationContext());
        //show the text that the user queries
        txtQuery=(TextView)findViewById(R.id.textquery);
        try {
//set the emaili string to the email of the user
            emaili = me.findEmail();
            //display the artists the user likes
            Music = me.findUser(emaili);
            Log.i("Music", Music);
            text.setText(me.findUser(emaili));
            userBirthday = me.findBirthday(emaili);
            LoginTime=getCurrentTimeStamp();
        }catch (Exception e){
            emaili="unknown";
            Music="unknown";
            userBirthday="unknown";
        }
        mPlanetTitles =new String [6];
        mPlanetTitles[0]="    About Me ";
        mPlanetTitles[1]="Email: "+emaili;
        mPlanetTitles[2]="Last entered store: "+LoginTime;
        mPlanetTitles[3]="My Birthday: "+userBirthday;
        mPlanetTitles[4]="Cart items:" ;
        mPlanetTitles[5]="My points";









        //Item variables from database
        //Navigation Drawer
        mTitle = mDrawerTitle = getTitle();

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        // set a custom shadow that overlays the main content when the drawer opens
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        // set up the drawer's list view with items and click listener
        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_list_item, mPlanetTitles));
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
        // enable ActionBar app icon to behave as action to toggle nav drawer
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        // ActionBarDrawerToggle ties together the the proper interactions
        // between the sliding drawer and the action bar app icon
        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                R.drawable.ic_drawer,  /* nav drawer image to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description for accessibility */
                R.string.drawer_close  /* "close drawer" description for accessibility */
        ) {
            public void onDrawerClosed(View view) {
                getActionBar().setTitle(mTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                getActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
//if no item is selected
        if (savedInstanceState == null) {

        }
        //Navigation Drawer



        //Navigation drawer





        // we are going to use asynctask to prevent network on main thread exception while sending music likes to music.myshoppingmate.com
        new PostDataAsyncTask().execute();


    }

    public void onTabSelected(ActionBar.Tab tab,FragmentTransaction ft) {


        mViewPager.setCurrentItem(tab.getPosition());

    }
    public void onTabUnselected (ActionBar.Tab tab, FragmentTransaction ft){


    }
    public void onTabReselected (ActionBar.Tab tab,FragmentTransaction ft){
        mViewPager.setCurrentItem(tab.getPosition());
    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.all_menu, menu);


        // Associate searchable configuration with the SearchView
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        final SearchView searchView =
                (SearchView) menu.findItem(R.id.search).getActionView();
        if(null!=searchView) {
            searchView.setSearchableInfo(
                    searchManager.getSearchableInfo(getComponentName()));
        }
        SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
            public boolean onQueryTextChange(String newText) {
                // this is your adapter that will be filtered
                return true;
            }

            public boolean onQueryTextSubmit(String query) {
                //Here u can get the value "query" which is entered in the search box.
                Log.e("String query","" + query);
                Toast.makeText(AllMenu.this, "your  search  for " +query + "  has been forwarded and you will be notified when in range", Toast.LENGTH_SHORT).show();

                // call your request, do some stuff..
                searchView.onActionViewCollapsed();  //collapse your ActionView
                searchView.setQuery("",false);       //clears your query without submit

                // collapse the action view

                return true;

            }
        };
        searchView.setOnQueryTextListener(queryTextListener);



        return true;
    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        //Nav Drawer
        // The action bar home/up action should open or close the drawer.
        // ActionBarDrawerToggle will take care of this.
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        //Nav Drawer
        int id = item.getItemId();


        if (id == R.id.search) {


            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public class PostDataAsyncTask extends AsyncTask<String, String, String> {

        protected void onPreExecute() {
            super.onPreExecute();
            // do stuff before posting data
        }

        @Override
        protected String doInBackground(String... strings) {
            try {

                // 1 = post text data, 2 = any other post apart from text
                int actionChoice = 1;

                // post a text data
                if(actionChoice==1){
                    postText();
                }

                // any other post apart from text
                else{

                }

            } catch (NullPointerException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String lenghtOfFile) {
            // do stuff after posting data
        }
    }
    private void postText(){
        try{
            // url where the data will be posted this data will be stored in a database
            String postReceiverUrl = "http://music.myshoppingmate.com/post.php";
            Log.v("PHP", "postURL: " + postReceiverUrl);

            // HttpClient
            HttpClient httpClient = new DefaultHttpClient();

            // post header
            HttpPost httpPost = new HttpPost(postReceiverUrl);

            // add your data which includes Music, email and Login time
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
            nameValuePairs.add(new BasicNameValuePair("music",Music ));
            nameValuePairs.add(new BasicNameValuePair("user",emaili ));
            nameValuePairs.add(new BasicNameValuePair("date",LoginTime ));


            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            // execute HTTP post request
            HttpResponse response = httpClient.execute(httpPost);
            HttpEntity resEntity = response.getEntity();

            if (resEntity != null) {

                String responseStr = EntityUtils.toString(resEntity).trim();
                Log.v("PHP", "Response: " +  responseStr);

                // you can add an if statement here and do other actions based on the response
            }

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //Method to get the current date inoder to store it in database
    public static String getCurrentTimeStamp(){
        try {

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String currentTimeStamp = dateFormat.format(new Date()); // Find todays date

            return currentTimeStamp;
        } catch (Exception e) {
            e.printStackTrace();
Log.e("Date","couldn't retrieve date" + e);
            return null;
        }
    }


    /* The click listener for ListView in the navigation drawer */
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }

//for selecting the navigation drawer items
    private void selectItem(int position) {
        // update the main content by replacing fragments


        FragmentManager fragmentManager = getFragmentManager();
        switch (position){

            case 0:
            {

                //should redirect to Offers



            }
            case 1:
            {
                //should redirect to requests

                              break;

            }
            case 2:
            {
                //should redirect to WishList


            }
            case 4:
            {
                //should redirect to cart


            }
            case 5:
            {
                //should redirect to shopping history

            }
            case 6:
            {
            //should redirect to me
            }
            case 7:
            {
                //should redirect to premium
            }
            default:
            {
                //should do nothing
                break;
            }
        }


        // update selected item and title, then close the drawer
        mDrawerList.setItemChecked(position, true);
        //setTitle(mPlanetTitles[position]);
        mDrawerLayout.closeDrawer(mDrawerList);
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getActionBar().setTitle(mTitle);
    }



    public android.support.v4.app.Fragment getVisibleFragment(){
       android.support.v4.app.FragmentManager fragmentManager = AllMenu.this.getSupportFragmentManager();
        List<android.support.v4.app.Fragment> fragments = fragmentManager.getFragments();
        for(android.support.v4.app.Fragment fragment : fragments){
            if(fragment != null && fragment.getUserVisibleHint())
                return (android.support.v4.app.Fragment)fragment;
        }
        return null;
    }

    public  void AddtoWishList(View view){
        Toast.makeText(this,"Added to WishList" ,Toast.LENGTH_SHORT).show();





    }


}
