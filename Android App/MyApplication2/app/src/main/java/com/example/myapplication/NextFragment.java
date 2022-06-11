package com.example.myapplication;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.List;


public class NextFragment extends Fragment {

    FragmentActivity listener;
    FirebaseFirestore db;
    String tour=null;
    TextView textView,a,b,c,d;
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

        return inflater.inflate(R.layout.fragment_next,container,false);



    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        db=FirebaseFirestore.getInstance();
        String currentUserId = getActivity().getIntent().getStringExtra("currentUserNameKey");
        sqliteHelper=new SqliteHelper(getContext());
        textView=view.findViewById(R.id.text);
        a=view.findViewById(R.id.textA);
        b=view.findViewById(R.id.textB);
        c=view.findViewById(R.id.textC);
        d=view.findViewById(R.id.textD);

        List<String> list=sqliteHelper.getT(currentUserId);
        Log.v("tag",list.toString());

        for(int i=0;i<list.size();i++){
            switch (list.get(i)){
                case "A":
                    db.collection("AT").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            List<DocumentSnapshot> documents =task.getResult().getDocuments();
                            for (DocumentSnapshot document : documents) {
                                tour=document.getString("tour");


                                if(tour.equals("end")){
                                    sqliteHelper.update("A","no",currentUserId);
                                    Log.v("next",tour);
                                    a.setVisibility(View.GONE);

                                }else{
                                    sqliteHelper.update("A","A",currentUserId);
                                    a.setVisibility(View.VISIBLE);
                                    a.setText("دور المريض في العيادة أ: "+tour);

                                }
                            }
                        }
                    });
                    break;
                case "B":
                    db.collection("BT").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            List<DocumentSnapshot> documents =task.getResult().getDocuments();
                            for (DocumentSnapshot document : documents) {
                                tour=document.getString("tour");


                                if(tour.equals("end")){
                                    b.setVisibility(View.GONE);
                                    sqliteHelper.update("B","no",currentUserId);

                                }else{
                                    sqliteHelper.update("B","B",currentUserId);
                                    b.setVisibility(View.VISIBLE);
                                    b.setText("دور المريض في العيادة ب: "+tour);

                                }
                            }
                        }
                    });
                    break;
                case "C":
                    db.collection("CT").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            List<DocumentSnapshot> documents =task.getResult().getDocuments();
                            for (DocumentSnapshot document : documents) {
                                tour=document.getString("tour");


                                if(tour.equals("end")){
                                    c.setVisibility(View.GONE);
                                    sqliteHelper.update("C","no",currentUserId);

                                }else{
                                    sqliteHelper.update("C","C",currentUserId);
                                    c.setVisibility(View.VISIBLE);
                                    c.setText("دور المريض في العيادة ج: "+tour);

                                }
                            }
                        }
                    });
                    break;
                case "D":
                    db.collection("DT").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            List<DocumentSnapshot> documents =task.getResult().getDocuments();
                            for (DocumentSnapshot document : documents) {
                                tour=document.getString("tour");


                                if(tour.equals("end")){
                                    d.setVisibility(View.GONE);
                                    sqliteHelper.update("D","no",currentUserId);

                                }else{
                                    sqliteHelper.update("D","D",currentUserId);
                                    d.setVisibility(View.VISIBLE);
                                    d.setText("دور المريض في العيادة د: "+tour);

                                }
                            }
                        }
                    });
                    break;
            }
        }





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

}



