package com.example.open.qiscusfilebrowser.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.open.qiscusfilebrowser.R;
import com.example.open.qiscusfilebrowser.model.Room;
import com.example.open.qiscusfilebrowser.model.event.NavigationDrawerCallbacks;
import com.example.open.qiscusfilebrowser.ui.activity.DrawerFragment;
import com.example.open.qiscusfilebrowser.ui.activity.Information;

import java.util.Collections;
import java.util.List;

/**
 * Created by Rahardyan on 8/5/2015.
 */
public class RoomAdapterRecycler extends RecyclerView.Adapter<RoomAdapterRecycler.MyViewHolder>{
    private LayoutInflater inflater;
    private DrawerFragment drawerFragment;
    private NavigationDrawerCallbacks mNavigationDrawerCallbacks;
    List<Room> data = Collections.emptyList();
    private int mSelectedPosition;
    private int mTouchedPosition = -1;
    private SparseBooleanArray selectedItem;




    public RoomAdapterRecycler(Context context, List<Room> data) {
        inflater=LayoutInflater.from(context);
        this.data = data;

    }

    public void setmNavigationDrawerCallbacks(NavigationDrawerCallbacks navigationDrawerCallbacks){
        mNavigationDrawerCallbacks = navigationDrawerCallbacks;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, final int viewType) {
        View view=inflater.inflate(R.layout.item_room, parent, false);
        final MyViewHolder holder = new MyViewHolder(view);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("asdasdasd " + viewType);
                if (mNavigationDrawerCallbacks != null)
                    mNavigationDrawerCallbacks.onNavigationDrawerItemSelected(holder.getAdapterPosition());
            }
        });
        return holder;
    }

    public void selectPosition(int position) {
        int lastPosition = mSelectedPosition;
        mSelectedPosition = position;
        notifyItemChanged(lastPosition);
        notifyItemChanged(position);
    }



    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Room current = data.get(position);
        holder.textViewName.setText(current.name);


        if (mSelectedPosition == position || mTouchedPosition == position) {
            holder.itemView.setBackgroundColor(holder.itemView.getContext().getResources().getColor(R.color.selectedgrey));
        } else {
            holder.itemView.setBackgroundColor(Color.TRANSPARENT);
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView textViewName;
        LinearLayout itemRoom;
        public MyViewHolder(View itemView) {
            super(itemView);
            textViewName = (TextView) itemView.findViewById(R.id.textViewName);
            itemRoom = (LinearLayout) itemView.findViewById(R.id.itemRoomRoot);


        }

    }
}
