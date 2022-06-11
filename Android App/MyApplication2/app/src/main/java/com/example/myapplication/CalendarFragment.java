package com.example.myapplication;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import java.util.List;

public class CalendarFragment extends Fragment implements SettingSelection{

    private ListView listView;
    FragmentActivity listener;
    AlertDialog.Builder reset_alert;
    LayoutInflater layoutInflater;
    SqliteHelper sqliteHelper;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Activity){
            this.listener = (FragmentActivity) context;
        }
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_calendar,container,false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sqliteHelper = new SqliteHelper(getContext());
        reset_alert=new AlertDialog.Builder(getContext());
        layoutInflater=getLayoutInflater();
        String currentUserId = getActivity().getIntent().getStringExtra("currentUserNameKey");
        listView=view.findViewById(R.id.listView);
        List<String> list=sqliteHelper.displayAllApps(currentUserId);
        ArrayAdapter adapter=new ArrayAdapter<String>(getContext(),R.layout.calendar_list_item,list);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.listener = null;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onSettingSelected(int position) {
        if(position==0){

        }
        if(position==1){

        }
        if (position==2){


        }
    }


}



