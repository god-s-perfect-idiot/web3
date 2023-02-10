package com.entropyotc.web3;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.Calendar;

/**
 * Created by enyason on 7/28/18.
 */

public class HomeScreenFragment extends Fragment {

    private static BroadcastReceiver tickReceiver;
    ImageView imageViewDrawer;
    public HomeScreenFragment() {
    }

    public StringBuffer getTime() {
        StringBuffer str = new StringBuffer();
        str.append(String.valueOf(Calendar.getInstance().get(Calendar.HOUR)));
        str.append(" : ");
        str.append(String.valueOf(Calendar.getInstance().get(Calendar.MINUTE)));
        str.append(Calendar.getInstance().get(Calendar.AM) == 1 ? " PM" : " AM");
        return str;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_screen,container,false);
        tickReceiver = new BroadcastReceiver(){
            @Override
            public void onReceive(Context context, Intent intent) {
                if(intent.getAction().compareTo(Intent.ACTION_TIME_TICK)==0)
                {
                    ((TextView)view.findViewById(R.id.home_clock)).setText(getTime());
                }

            }
        };
        IntentFilter filter = new IntentFilter(Intent.ACTION_TIME_TICK);
        getActivity().registerReceiver(tickReceiver, filter);
        return view;
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        imageViewDrawer = view.findViewById(R.id.icon_drawer);
        ((TextView)getActivity().findViewById(R.id.home_clock)).setText(getTime());

        imageViewDrawer.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                loadFragment(new AppDrawerFragment());
            }
        });


    }

    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, fragment)
                    .commit();
            return true;
        }

        return false;
    }

}
