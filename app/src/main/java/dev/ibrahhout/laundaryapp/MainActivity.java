package dev.ibrahhout.laundaryapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import dev.ibrahhout.laundaryapp.Adapters.MainViewPagerAdapter;
import dev.ibrahhout.laundaryapp.Fragments.FeedBackFragment;
import dev.ibrahhout.laundaryapp.Fragments.ServicesFragment;
import dev.ibrahhout.laundaryapp.Fragments.ServicesHistoryFragment;
import dev.ibrahhout.laundaryapp.Utils.Constants;
import dev.ibrahhout.laundaryapp.Utils.MotherActivity;

public class MainActivity extends MotherActivity {

    FirebaseAuth mFirebaseAuth;
    @BindView(R.id.mainToolBar)
    Toolbar mainToolBar;

    @BindView(R.id.viewPager_mainChat)
     ViewPager viewPagerMainChat;

    @BindView(R.id.mainAppBar)
    AppBarLayout mainAppBar;
    @BindView(R.id.tablatout)
    TabLayout tablayoutMainChat;

    MainViewPagerAdapter mainViewPagerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mFirebaseAuth = FirebaseAuth.getInstance();


        if (mFirebaseAuth.getCurrentUser() == null) {
            singOutAndUpdateUI();
        } else {
//            Toast.makeText(this, "User id is" + mFirebaseAuth.getCurrentUser().getUid(), Toast.LENGTH_SHORT).show();


            FirebaseDatabase.getInstance().getReference().child(Constants.FIREBASE_USERS_NODE).keepSynced(true);


            setSupportActionBar(mainToolBar);
            getSupportActionBar().setTitle(R.string.appname);
            mainToolBar.setTitleTextColor(Color.WHITE);

            mainToolBar.setNavigationIcon(R.drawable.ic_menu);
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, mainToolBar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.addDrawerListener(toggle);
            toggle.syncState();


            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);


            View navHeaderView = navigationView.getHeaderView(0);

            final TextView textViewHeader = navHeaderView.findViewById(R.id.textView);


            FirebaseDatabase.getInstance().getReference().child(Constants.FIREBASE_USERS_NODE)
                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    String name = dataSnapshot.child(Constants.FIREBASE_USERS_USERNAME).getValue(String.class);
//                    Toast.makeText(MainActivity.this, "okayyyyyy" + "  " + name, Toast.LENGTH_SHORT).show();
                    textViewHeader.setText(name);

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

            navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    int id = item.getItemId();

                    if (id == R.id.nav_profile) {
                        // Handle the camera action

                        Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                        startActivity(intent);
                    }else if (id == R.id.nav_howItWorks) {
                        // Handle the camera action

                        Intent intent = new Intent(MainActivity.this, HowItWorksActivity.class);
                        startActivity(intent);

                    } else if (id == R.id.nav_logout) {
                        singOutAndUpdateUI();

                    }

                    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                    drawer.closeDrawer(GravityCompat.START);
                    return true;
                }
            });


            mainViewPagerAdapter = new MainViewPagerAdapter(getSupportFragmentManager());
//        mainViewPagerAdapter.addFragment(new FeedsFragment(), "How it Works ");
            mainViewPagerAdapter.addFragment(new ServicesFragment(), "Services");
            mainViewPagerAdapter.addFragment(new ServicesHistoryFragment(), "History");
            mainViewPagerAdapter.addFragment(new FeedBackFragment(), "Feedback");


            tablayoutMainChat.setupWithViewPager(viewPagerMainChat);
            viewPagerMainChat.setAdapter(mainViewPagerAdapter);
        }

    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public ViewPager getViewPagerMainChat(){
        return viewPagerMainChat;
    }


    ///TODO menu
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main2, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    @Override
    protected void onResume() {
        super.onResume();


        if (mFirebaseAuth.getCurrentUser() == null) {
            singOutAndUpdateUI();
        } else {
//            Toast.makeText(this, "User id is" + mFirebaseAuth.getCurrentUser().getUid(), Toast.LENGTH_SHORT).show();
        }
    }
}
