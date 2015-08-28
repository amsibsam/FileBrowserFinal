package com.example.open.qiscusfilebrowser.ui.activity;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.open.qiscusfilebrowser.R;
import com.example.open.qiscusfilebrowser.application.QiscusExplorerApplication;
import com.example.open.qiscusfilebrowser.infrastructure.client.QiscusClient;
import com.example.open.qiscusfilebrowser.model.Room;
import com.example.open.qiscusfilebrowser.model.Topic;
import com.example.open.qiscusfilebrowser.model.User;
import com.example.open.qiscusfilebrowser.model.event.ClientCallback;
import com.example.open.qiscusfilebrowser.model.event.NavigationDrawerCallbacks;
import com.example.open.qiscusfilebrowser.model.event.OnGetRoomListSucceeded;
import com.example.open.qiscusfilebrowser.model.event.RecyclerItemClickListener;
import com.example.open.qiscusfilebrowser.ui.adapter.RoomAdapterRecycler;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.Subscribe;

/**
 * A simple {@link Fragment} subclass.
 */
public class DrawerFragment extends android.support.v4.app.Fragment implements NavigationDrawerCallbacks, ClientCallback<Room> {


    public static final String PREF_FILE_NAME = "testpref";
    public static final String KEY_USER_LEARNED_DRAWER = "user_learned_drawer";
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    RoomAdapterRecycler adapter;
    NavigationDrawerCallbacks mCallbacks;
    private boolean mUserLearnedDrawer;
    private boolean mFromSavedInstanceState;
    private int mCurrentSelectedPosition;
    private View containerView;
    private List<Room> rooms;
    public static List<Topic> topics;
    private RecyclerView recyclerView;
    private User user;
    private int mSelectedPosition;
    public static int idRoom;

    public DrawerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUserLearnedDrawer=Boolean.valueOf(readFromPreferences(getActivity(), KEY_USER_LEARNED_DRAWER, "false"));
        if(savedInstanceState!=null){
            mFromSavedInstanceState=true;
        }



    }

    public void closeDrawer() {
        mDrawerLayout.closeDrawer(containerView);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_drawer, container, false);
        TextView Home = (TextView) layout.findViewById(R.id.textViewHome);




        Home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), RecentFilesActivity.class);
//                startActivity(intent);
                RecentFilesActivity.toolbar.setTitle("Qiscus File Browser");

                Load(-1);
                closeDrawer();
                
            }
        });
        QiscusExplorerApplication app = (QiscusExplorerApplication) getActivity().getApplication();
        user = app.getUser();

        rooms = QiscusClient.getRoomlist(user.getToken(), DrawerFragment.this);
        recyclerView = (RecyclerView) layout.findViewById(R.id.room_list);
        adapter = new RoomAdapterRecycler(getActivity(), rooms);
        recyclerView.setAdapter(adapter);
        adapter.setmNavigationDrawerCallbacks(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                idRoom = rooms.get(position).id;
                System.out.println("ini item ke " + position + " dengan id " + idRoom);
                System.out.println("token di Drawer " + QiscusClient.token);
                RecentFilesActivity.toolbar.setTitle(rooms.get(position).name);
//                topics = QiscusClient.getTopicList(idRoom, QiscusClient.token);
                selectItem(position);
                Load(position);
                closeDrawer();

                Load(position);

            }
        }));


        return layout;
    }

    public void Load(int position) {

        android.support.v4.app.Fragment newFragment = null;
        FragmentTransaction transaction = getFragmentManager().beginTransaction();

        if(position >= 0){
            newFragment = new TopicListFragment();

        }else if(position == -1){
            newFragment = new RecentFilesFragment();
        }

        if (newFragment != null) {
            transaction.replace(R.id.container, newFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }

//        switch (position) {
//            case 1:
//                newFragment = new HomeFragment();
//                transaction.replace(R.id.container, newFragment);
//                transaction.addToBackStack(null);
//                transaction.commit();
//                break;
//            default:
//                break;
//
//        }
        //DrawerList.setItemChecked(position, true);
        closeDrawer();
    }



    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mCallbacks = (NavigationDrawerCallbacks) activity;
        }catch (ClassCastException e){
            throw new ClassCastException("hrs implement");
        }
    }

    public  List<Room> getData(){
        List<Room> data = new ArrayList<>();
        String[] nama = {"room 1", "room 2", "room 3"};
        int[] id = {123, 321, 432};
        for(int i=0;i<nama.length && i<id.length; i++){
            Room current = new Room(id[i], nama[i]);
            data.add(current);


        }
        return data;
    }
//    public List<Information> getData(){
//        List<Information> data = new ArrayList<>();
//        String[] nama = {"data 1", "data 2", "data 3", "data 4"};
//        for(int i=0; i<nama.length;i++){
//            Information current = new Information();
//            current.title = nama[i];
//            data.add(current);
//        }
//        return data;
//    }
    public boolean isDrawerOpen() {
        return mDrawerLayout != null && mDrawerLayout.isDrawerOpen(containerView);
    }


    public void setUp(int fragmentId, final DrawerLayout drawerLayout, Toolbar toolbar) {
        containerView = getView().findViewById(fragmentId);
        if(containerView.getParent() instanceof ScrimInsetsFrameLayout){
            containerView = (View) containerView.getParent();
        }
        mDrawerLayout=drawerLayout;
        mDrawerToggle=new ActionBarDrawerToggle(getActivity(), drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                if(mUserLearnedDrawer){
                    if (!isAdded()) return;
                    mUserLearnedDrawer=true;
                    saveToPreferences(getActivity(), KEY_USER_LEARNED_DRAWER, mUserLearnedDrawer+"");
                }
                System.out.println(QiscusClient.token);
//                adapter.notifyDataSetChanged();
//                System.out.println("notify");
                getActivity().invalidateOptionsMenu();
            }


            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                if (!isAdded()) return;
                getActivity().invalidateOptionsMenu();

            }
        };
//        if(!mUserLearnedDrawer && !mFromSavedInstanceState){
//            mDrawerLayout.openDrawer(containerView);
//        }


        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });

        mDrawerLayout.setDrawerListener(mDrawerToggle);

    }
    public static void saveToPreferences(Context context, String preferenceName, String preferenceValue){
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(preferenceName, preferenceValue);
        editor.apply();
    }

    public static String readFromPreferences(Context context, String preferenceName, String defaultValue){
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(preferenceName, defaultValue);
        
    }

    @Subscribe
    public void onEvent(OnGetRoomListSucceeded e){
        adapter.notifyDataSetChanged();

    }

    void selectItem(int position) {
        mCurrentSelectedPosition = position;
        if (mDrawerLayout != null) {
            mDrawerLayout.closeDrawer(containerView);
        }
        if (mCallbacks != null) {
            mCallbacks.onNavigationDrawerItemSelected(position);
        }
        ((RoomAdapterRecycler) recyclerView.getAdapter()).selectPosition(position);
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        selectItem(position);
    }

    @Override
    public void onSucceeded(Room result) {
        adapter.notifyDataSetChanged();
        System.out.println("notify di drawer");
    }

    @Override
    public void onFailed() {

    }

    @Override
    public void onNoConnection() {

    }
}
