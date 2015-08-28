package com.example.open.qiscusfilebrowser.ui.activity;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.*;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.open.qiscusfilebrowser.R;
import com.example.open.qiscusfilebrowser.application.QiscusExplorerApplication;
import com.example.open.qiscusfilebrowser.model.QiscusRecentFile;
import com.example.open.qiscusfilebrowser.model.User;
import com.example.open.qiscusfilebrowser.model.event.NavigationDrawerCallbacks;
import com.example.open.qiscusfilebrowser.model.event.OnRecentFilesSucceeded;
import com.example.open.qiscusfilebrowser.ui.adapter.RecentFileAdapter;

import java.util.List;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;

/**
 * Created by Rahardyan on 8/5/2015.
 */
public class RecentFilesActivity extends AppCompatActivity implements NavigationDrawerCallbacks {
    private ListView listView;
    private RecentFileAdapter adapter;
    private List<QiscusRecentFile> recentFiles;
    public static User user;
    public static android.support.v7.widget.Toolbar toolbar;
    DrawerFragment drawerFragment;
    private TextView username;




    @Override
    public void onCreate(Bundle savedInstanceState) {
        System.out.println("ini recent activity");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recent_files);
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        drawerFragment = (DrawerFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_nav_drawer);
        drawerFragment.setUp(R.id.fragment_nav_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), toolbar);

        if(findViewById(R.id.container) != null)
        {
            // if we are being restored from a previous state, then we dont need to do anything and should
            // return or else we could end up with overlapping fragments.
            if(savedInstanceState != null)
                return;

            // Create an instance of editorFrag
            RecentFilesFragment recentFilesFragment = new RecentFilesFragment();

            // add fragment to the fragment container layout
            getSupportFragmentManager().beginTransaction().add(R.id.container, recentFilesFragment).commit();
        }

        username = (TextView) findViewById(R.id.etUsername);


        EventBus.getDefault().register(this);

        QiscusExplorerApplication app = (QiscusExplorerApplication) getApplication();
        user = app.getUser();




        username.setText(user.getUsername());
        System.out.println("di Recent" + user.toString());
//
//        recentFiles = user.recentFiles;
//
//
//
//        listView = (ListView) findViewById(R.id.listView);
//
//        adapter = new RecentFileAdapter(this, R.layout.item_recent_file, recentFiles);
//        listView.setAdapter(adapter);
//        user.getRecentFiles();


    }

//    private void displayView(int position) {
//        // update the main content by replacing fragments
//        android.app.Fragment fragment = null;
//        switch (position) {
//            case 0:
//                fragment = new HomeFragment();
//                break;
//
//            default:
//                break;
//        }
//
//        if (fragment != null) {
//            FragmentManager fragmentManager = getFragmentManager();
//            fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();
//
//            // update selected item and title, then close the drawer
////            mDrawerList.setItemChecked(position, true);
////            mDrawerList.setSelection(position);
////            setTitle(navMenuTitles[position]);
////            mDrawerLayout.closeDrawer(mDrawerList);
//        } else {
//            // error in creating fragment
//            Log.e("MainActivity", "Error in creating fragment");
//        }
//    }



    @Subscribe
    public void onEvent(OnRecentFilesSucceeded e){
        adapter.notifyDataSetInvalidated();

    }



    @Override
    public void onNavigationDrawerItemSelected(int position) {
        Toast.makeText(this, "Menu item selected -> " + position, Toast.LENGTH_SHORT).show();


    }

    @Override
    public void onBackPressed() {
        if(drawerFragment.isDrawerOpen()){
            drawerFragment.closeDrawer();
        }else{
            moveTaskToBack(true);
        }
    }


}
