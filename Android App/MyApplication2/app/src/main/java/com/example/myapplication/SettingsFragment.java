package com.example.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import java.util.Arrays;
import java.util.List;

public class SettingsFragment extends Fragment implements SettingSelection{

    private RecyclerView recyclerView;
    FragmentActivity listener;
    AlertDialog.Builder reset_alert;
    LayoutInflater layoutInflater;
    String currentUserId=null;
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

        return inflater.inflate(R.layout.fragment_settings,container,false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        reset_alert=new AlertDialog.Builder(getContext());
        layoutInflater=getLayoutInflater();
        sqliteHelper = new SqliteHelper(getContext());
        currentUserId = getActivity().getIntent().getStringExtra("currentUserNameKey");
        recyclerView=view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        List<String> settingsList= Arrays.asList(getResources().getStringArray(R.array.settings));
        SettingsAdapter settingsAdapter=new SettingsAdapter(settingsList,this);
        recyclerView.setAdapter(settingsAdapter);

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
            View view = View.inflate(getContext(), R.layout.reset_alert, null);
            reset_alert.setView(view);
            EditText input=view.findViewById(R.id.password);

            reset_alert.setPositiveButton("??????", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    if(input.getText().toString().isEmpty()){
                        input.setError("???????? ???????? ?????????? ????????????");
                        return;

                    }
                    if(currentUserId!=null && validate(input.getText().toString(),input)){
                        sqliteHelper.changepassword(currentUserId,input.getText().toString());
                        Toast.makeText(getContext(),"???? ?????????? ???????? ????????",Toast.LENGTH_LONG).show();
                    }
                }
            });
            reset_alert.setNegativeButton("??????????", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

           reset_alert.show();
        }

        if (position==1){
            reset_alert.setTitle("?????? ???????????? ???????? ????????").setMessage("???? ?????? ???????????? ?????????? ???????? ??????????????").setPositiveButton("??????", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if(!currentUserId.isEmpty()){
                        sqliteHelper.deleteUser(currentUserId);
                        Toast.makeText(getContext(),"?????? ????????????",Toast.LENGTH_LONG).show();
                        Intent intent=new Intent(getContext(),Login.class);
                        startActivity(intent);
                        getActivity().finish();
                    }

                }
            }).setNegativeButton("??????????",null).create().show();

        }
        }
        public boolean validate(String Password, EditText ed){
        boolean valid;
            if (Password.isEmpty()) {
                valid = false;
                ed.setError("???????????? ?????????? ???????? ???????? ??????????!");
            } else {
                if (Password.length() > 5) {
                    valid = true;
                    ed.setError(null);
                } else {
                    valid = false;
                    ed.setError("???????? ???????????? ??????????!");
                }
            }
            return valid;
        }


    }



