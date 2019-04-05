package com.example.onboarding;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import static com.example.onboarding.FragmentContact.isInActionMode;

public class InsideActivity extends AppCompatActivity {
    public static final String TAG = "InsideActivity";

    //private Toolbar mToolbar;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private ViewPagerAdapter mViewPagerAdapter;

    private String[] titles = {"Call Logs","Contacts","Favorites"};
    FragmentContact fragmentContact;
    FragmentCall mFragmentCall;
    FragmentFav mFragmentFav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inside);

        mTabLayout =  findViewById(R.id.tabLayout);
        mViewPager = findViewById(R.id.viewpager);
        mViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

        //Add Fragments Here
        mFragmentCall = new FragmentCall();
        mFragmentFav =  new FragmentFav();
        fragmentContact = new FragmentContact();
        mViewPagerAdapter.addFragment(fragmentContact,"");
        mViewPagerAdapter.addFragment(mFragmentCall,"");
        mViewPagerAdapter.addFragment(mFragmentFav,"");
        mViewPager.setAdapter(mViewPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);

        mTabLayout.getTabAt(0).setIcon(R.drawable.ic_call_white_24dp);
        mTabLayout.getTabAt(1).setIcon(R.drawable.ic_group_black_24dp);
        mTabLayout.getTabAt(2).setIcon(R.drawable.ic_star_black_24dp);

        getSupportActionBar().setTitle(getString(R.string.app_name));
        mTabLayout.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                changeMenuState(position,false,-1);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        getSupportActionBar().setElevation(0);
    }

    private void setActionBarTitle(int position) {
        getSupportActionBar().setTitle(titles[position]);
    }

    private boolean mode = false;

    void changeMenuState(int tab,boolean mode,int count){
        this.mode = mode;
        invalidateOptionsMenu();
        switch (tab){
            case 0:
                resetContacts();
                break;
            case 1:
                changeItemCount(count);
                fragmentContact.recyclerViewAdapter.notifyDataSetChanged();
                break;
            case 2:
                resetContacts();
                break;
        }
    }

    private void resetContacts(){
        isInActionMode = false;
        mode = false;
        fragmentContact.count_item_selected =0;
        fragmentContact.item_selected.clear();
        fragmentContact.recyclerViewAdapter.notifyDataSetChanged();
    }

    void changeItemCount(int count){
        Log.d(TAG, "changeItemCount: starts");
        if(count!=0){
            getSupportActionBar().setTitle(count+" items selected");
        }else {
            mode = false;
            isInActionMode = false;
            invalidateOptionsMenu();
        }
        Log.d(TAG, "changeItemCount: ends");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d(TAG, "onCreateOptionsMenu: called");
        if(mode){
            Log.d(TAG, "onCreateOptionsMenu: inflating menu 1");
            getMenuInflater().inflate(R.menu.menu_action_mode,menu);
        }else {
            Log.d(TAG, "onCreateOptionsMenu: inflating menu 2");
            getSupportActionBar().setTitle(getString(R.string.app_name));
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getMenuInflater().inflate(R.menu.menu_activity_main,menu);
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        if(isInActionMode){
            resetContacts();
            invalidateOptionsMenu();
        }else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(isInActionMode){
            switch (item.getItemId()){
                case android.R.id.home:
                    resetContacts();
                    invalidateOptionsMenu();
                    break;
                case  R.id.item_delete:
                    Toast.makeText(this,"Delete Button Pressed",Toast.LENGTH_SHORT).show();
                    fragmentContact.recyclerViewAdapter.notifyItemRemoved(0);
            }
        }else {
            switch (item.getItemId()){

            }
        }
        return true;
    }
}
