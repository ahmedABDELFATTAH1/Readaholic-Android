package com.example.android.readaholic;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.android.readaholic.BookSearch.Search;

import com.example.android.readaholic.books.BookReview;
import com.example.android.readaholic.books.Creviewdata;
import com.example.android.readaholic.books.ReviewActivity;
import com.example.android.readaholic.contants_and_static_data.Urls;
import com.example.android.readaholic.contants_and_static_data.UserInfo;
import com.example.android.readaholic.explore.ExploreActivity;
import com.example.android.readaholic.home.HomeFragment;
import com.example.android.readaholic.home.NotificationFragment;
import com.example.android.readaholic.home.ViewPageAdapter;
import com.example.android.readaholic.myshelves.ShelvesFragment;
import com.example.android.readaholic.profile_and_profile_settings.FollowersAndFollowingFragment;
import com.example.android.readaholic.profile_and_profile_settings.Profile;
import com.example.android.readaholic.settings.Settings;
import com.example.android.readaholic.sign_in_up.Start;

import com.example.android.readaholic.contants_and_static_data.UserInfo;

import com.example.android.readaholic.myshelves.ShelvesFragment;
import com.pusher.client.Pusher;
import com.pusher.client.PusherOptions;
import com.pusher.client.channel.Channel;
import com.pusher.client.channel.SubscriptionEventListener;
import com.pusher.client.channel.User;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.concurrent.atomic.AtomicBoolean;


public class Main extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private long mBackPressedTime;
    private DrawerLayout drawer;
    private ImageView ProfileImage;
    private TextView mUsername;
    private TabLayout mTabs;
    private ViewPager mPages;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final String[] textTitle = {"   "};
        final String[] textContent = {""};

        PusherOptions options = new PusherOptions();
        options.setCluster("eu");
        Pusher pusher = new Pusher("aa5ca7b55f8f7685a9cc", options);
        Channel channel = pusher.subscribe("user."+ Integer.toString(UserInfo.sID));
        String d;
        final int[] t = new int[1];
        final int[] actionid = new int[1];
        channel.bind("notify", new SubscriptionEventListener() {
            @Override
            public void onEvent(String channelName, String eventName, final String data) {
                System.out.println(data);
                try {
                    JSONObject i = new JSONObject(data);
                    JSONObject d = i.getJSONObject("message");
                    String user ;
                    t[0] = d.getInt("type");
                    String action;
                    if(t[0] == 0){
                            if(d.getInt("review_user_id") == UserInfo.sID){
                            user = "your";
                        }else{
                            user = d.getString("review_user_name");
                        }
                        action = " Commented on "+user + "'s review";
                            actionid[0] = d.getInt("review_id");
                    }else if(t[0] == 1){
                        if(d.getInt("review_user_id") == 0){
                            user = "your";
                        }else{
                            user = d.getString("review_user_name");
                        }
                        action = " Liked "+user + "'s review";
                        actionid[0] = d.getInt("review_id");
                    }
                    else{
                        action = " is now following you";
                        actionid[0] = d.getInt("user_id");
                    }
                    textContent [0]= d.getString("user_name")+action;
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                // Setup an intent for SecondActivity:
                Intent secondIntent = null;
                if(t[0] == 2){
                    secondIntent = new Intent (getBaseContext(), Profile.class);
                    secondIntent.putExtra("user-idFromFollowingList",actionid[0]);
                }else{
                    secondIntent = new Intent (getBaseContext(),ReviewActivity.class);
                    Creviewdata.INSTANCE.setReviewid(actionid[0]);
                }

                // Pass some information to SecondActivity:
                secondIntent.putExtra("message", "Greetings from MainActivity!");

                // Create a task stack builder to manage the back stack:
                TaskStackBuilder stackBuilder = TaskStackBuilder.create(getBaseContext());

                // Add all parents of SecondActivity to the stack:
                //stackBuilder.addParentStack (BookReview.class);

                // Push the intent that starts SecondActivity onto the stack:
                stackBuilder.addNextIntent (secondIntent);

                // Obtain the PendingIntent for launching the task constructed by
            // stackbuilder. The pending intent can be used only once (one shot):
             int pendingIntentId = 0;
                PendingIntent pendingIntent =
                        stackBuilder.getPendingIntent (pendingIntentId,9);

// Instantiate the builder and set notification elements, including
// the pending intent:
                Notification.Builder builder = new Notification.Builder (getBaseContext())
                        .setContentIntent (pendingIntent)
                        .setContentTitle ("Readaholic")
                        .setContentText (textContent[0])
                        .setSmallIcon (R.drawable.ic_notifications_black_24dp);

                // Build the notification:
                Notification notification = builder.build();
                notification.flags |=Notification.FLAG_AUTO_CANCEL;

                    // Get the notification manager:
                NotificationManager notificationManager =
                        (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

                    // Publish the notification:
                notificationManager.notify (0, notification);


            }
        });

        pusher.connect();



        Toolbar toolbar = (Toolbar) findViewById(R.id.Main_toolbar);
        setSupportActionBar(toolbar);
        mTabs = findViewById(R.id.Main_tabs_tablayout);
        mPages = findViewById(R.id.Main_views_viewpager);

        View Header = ((NavigationView)findViewById(R.id.Main_navView)).getHeaderView(0);
        ProfileImage = (ImageView)Header.findViewById(R.id.NavHeader_ProfilePhoto_ImageView);
        mUsername = (TextView) Header.findViewById(R.id.NavHeader_Profile_TextView);
        final AtomicBoolean loaded = new AtomicBoolean();
        Picasso.get().load(UserInfo.sImageUrl).transform(new CircleTransform()).into(ProfileImage, new Callback.EmptyCallback() {
            @Override public void onSuccess() {
                loaded.set(true);
            }
        });
        if (!loaded.get()) {
            // The image was immediately available.
            Picasso.get().load("https://s.gr-assets.com/assets/nophoto/user/u_111x148-9394ebedbb3c6c218f64be9549657029.png")
                    .transform(new CircleTransform()).into(ProfileImage);
        }


        //Picasso.get().load(UserInfo.sImageUrl).into(ProfileImage);
        mUsername.setText(UserInfo.sName);
        ProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profileIntent = new Intent(v.getContext(), Profile.class);
                profileIntent.putExtra("user-idFromFollowingList",0);
                startActivity(profileIntent);
            }
        });

        mUsername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profileIntent = new Intent(v.getContext(), Profile.class);
                profileIntent.putExtra("user-idFromFollowingList",0);
                startActivity(profileIntent);
            }
        });

        drawer = findViewById(R.id.Main_drawerlayout);
        NavigationView navigationView = findViewById(R.id.Main_navView);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawer,toolbar,
                R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if(savedInstanceState == null) {
            ViewPageAdapter adapter = new ViewPageAdapter(getSupportFragmentManager());
            navigationView.setCheckedItem(R.id.draw_home_menu);
            adapter.AddFragment(new HomeFragment(),"Updates");
            adapter.AddFragment(new NotificationFragment(),"notification");
            mPages.setAdapter(adapter);
            mTabs.setupWithViewPager(mPages);
            /*getSupportFragmentManager().beginTransaction().replace(R.id.Main_fragmentLayout,
                    new HomeFragment()).commit();*/
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mainsearch , menu);
        MenuItem item = menu.findItem(R.id.draw_search);
        SearchView searchView = (SearchView)item.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //when any genre is clicked I the clicked genre should be searched
                Intent intent = new Intent(getBaseContext(), Search.class);  //getting Search activity intent
                //setting the search data to true to indicate that he should search and check for more parameters
                intent.putExtra("search", true);
                //the key he will search for
                intent.putExtra("searchKey" ,query);
                //setting search type to Title
                intent.putExtra("searchType" , "Title");
                startActivity(intent); //starting the activity
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

           //region Listeners
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.draw_home_menu:
                mTabs.setVisibility(View.VISIBLE);
                mPages.setVisibility(View.VISIBLE);
                ViewPageAdapter adapter = new ViewPageAdapter(getSupportFragmentManager());
                adapter.AddFragment(new HomeFragment(),"Updates");
                adapter.AddFragment(new NotificationFragment(),"notification");
                mPages.setAdapter(adapter);
                mTabs.setupWithViewPager(mPages);

               /* getSupportFragmentManager().beginTransaction().replace(R.id.Main_fragmentLayout,
                        new HomeFragment()).commit();*/

                break;

            case R.id.draw_followers_menu:
                mTabs.setVisibility(View.GONE);
                mPages.setVisibility(View.GONE);
               /* Intent profileIntent = new Intent(getBaseContext(), Profile.class);
                profileIntent.putExtra("FromMenu",1);
                startActivity(profileIntent);
*/
                getSupportFragmentManager().beginTransaction().replace(R.id.Main_fragmentLayout,
                        new FollowersAndFollowingFragment(),"FollowersAndFollowings").addToBackStack("MainToFollowersAndFollowings").commit();
                break;

            case R.id.draw_settings_menu:
                mTabs.setVisibility(View.GONE);
                mPages.setVisibility(View.GONE);
                Intent intent = new Intent(this, Settings.class);
                startActivity(intent);
                break;
            case R.id.draw_logout_menu:

                if(UserInfo.IsMemic) {
                    Intent startIntent = new Intent(getBaseContext(),Start.class);
                    startActivity(startIntent);
                    finish();
                } else {
                    logoutrequest();
                }


                break;

            case R.id.draw_Myshelves_menu:
                mTabs.setVisibility(View.GONE);
                mPages.setVisibility(View.GONE);
                getSupportFragmentManager().beginTransaction().replace(R.id.Main_fragmentLayout,
                        new ShelvesFragment()).commit();
                break;
            case R.id.draw_explore_menu:
                Intent exploreIntent = new Intent(this, ExploreActivity.class);
                startActivity(exploreIntent);
                break;
            case R.id.draw_search_menu:
                Intent searchIntent = new Intent(this, Search.class);
                startActivity(searchIntent);
                break;



        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * should exit the app if back is pressed twice in two second
     * and if the drawer is opened it should be closed
     */
    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (mBackPressedTime + 2000 > System.currentTimeMillis()) {
                this.finishAffinity();
                return;
            } else {
                Toast.makeText(this, "Press back again to exit", Toast.LENGTH_SHORT).show();
            }
            mBackPressedTime = System.currentTimeMillis();

        }
    }
    //endregion

            //region requests
      /**
       * sending logout request
       */
      public void logoutrequest() {

                loading();
                Urls urlController = new Urls(this,this);
                RequestQueue queue = Volley.newRequestQueue(this);
                String url = Urls.ROOT + Urls.LOG_OUT + "?" +urlController.constructTokenParameters();
                int statusCode;
                // Request a string response from the provided URL.

            StringRequest stringRequest = new StringRequest(Request.Method.DELETE,
                    url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            UserInfo.removeUserInfo();
                            Intent intent = new Intent(getBaseContext(),Start.class);
                            startActivity(intent);
                            finish();
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            showLayout();
                            Toast.makeText(Main.this, "Logout failed Please try again", Toast.LENGTH_SHORT).show();
                        }
                    }) ;

            // Add the request to the RequestQueue.
                queue.add(stringRequest);

            }
            //endregion

            //region UI control

           /**
           * showing progress bar to indicate that logout is processing
           */
           public void loading()
            {

                DrawerLayout drawerLayout = (DrawerLayout)findViewById(R.id.Main_drawerlayout);
                drawerLayout.setVisibility(View.GONE);

                ProgressBar progressBar = (ProgressBar)findViewById(R.id.Main_progressBar);
                progressBar.setVisibility(View.VISIBLE);

            }
           /**
            * showing the original layout
            * used if the logout failed
            */
           public void showLayout()
            {
                ProgressBar progressBar = (ProgressBar)findViewById(R.id.Main_progressBar);
                progressBar.setVisibility(View.GONE);

                DrawerLayout drawerLayout = (DrawerLayout)findViewById(R.id.Main_drawerlayout);
                drawerLayout.setVisibility(View.VISIBLE);
            }
            //endregion





}
