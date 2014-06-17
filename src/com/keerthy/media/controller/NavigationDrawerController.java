package com.keerthy.media.controller;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.keerthy.media.MediaApplication;
import com.keerthy.music.R;

public class NavigationDrawerController extends BaseController {

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;

    public NavigationDrawerController(DrawerLayout drawerLayout) {
        this.mDrawerLayout = drawerLayout;
    }

    @Override
    public void onCreate() {
        mDrawerList = (ListView) mDrawerLayout.findViewById(R.id.left_drawer);

        // Set the adapter for the list view
        String[] navItems = MediaApplication.getAppContext().getResources()
            .getStringArray(R.array.nav_items);
        mDrawerList.setAdapter(new ArrayAdapter<String>(MediaApplication.getAppContext(),
            R.layout.drawer_list_item, navItems));
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    /** Swaps fragments in the main content view */
    private void selectItem(int position) {
        // Create a new fragment based on the position.

    }

    @Override
    public void onStart() {
        // TODO Auto-generated method stub
    }

    @Override
    public void onStop() {
        // TODO Auto-generated method stub
    }

}
