package com.example.myapplication;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class AddFragment extends Fragment {
    FragmentActivity listener;
    ListView listView;
    EditText name,fName;
    String fullName=null;
    Button bAppoint;
    TextView number,text,number2;
    String value=null;
    String valueAr=null;
    SqliteHelper sqliteHelper;
    FirebaseFirestore db;
    int count=0;
    String d;


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

        return inflater.inflate(R.layout.fragment_add,container,false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String currentUserId = getActivity().getIntent().getStringExtra("currentUserNameKey");
        sqliteHelper = new SqliteHelper(getContext());
        db=FirebaseFirestore.getInstance();
        listView=view.findViewById(R.id.listView);
        name=view.findViewById(R.id.name);
        fName=view.findViewById(R.id.Fname);
        number2=view.findViewById(R.id.number2);
        bAppoint=view.findViewById(R.id.bAppoint);
        text=view.findViewById(R.id.text);
        number=view.findViewById(R.id.number);

        ArrayList<String> arrayList=new ArrayList<>();
        ArrayAdapter adapter=new ArrayAdapter<String>(getContext(),R.layout.calendar_list_item,arrayList);
        listView.setAdapter(adapter);
        arrayList.add("عيادة أ");
        arrayList.add("عيادة ب");
        arrayList.add("عيادة ج");
        arrayList.add("عيادة د");
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        d=dateFormat.format(date);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                valueAr= listView.getItemAtPosition(position).toString();
                value=hopAr(valueAr);
                CollectionReference appRef = db.collection(value);
                Query query=appRef.whereEqualTo("date", d);
                query.addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        count=value.size();

                    }
                });

            }
        });



        bAppoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                number.setVisibility(View.GONE);
                Map<String,Object> map=new HashMap<>();

                if(value==null){
                    Toast.makeText(getContext(),"اختر عيادة",Toast.LENGTH_LONG).show();
                }


                if(!fName.getText().toString().isEmpty() && !name.getText().toString().isEmpty() && value!=null){
                    fullName=name.getText().toString().toLowerCase(Locale.ROOT).trim()+" "+fName.getText().toString().toLowerCase(Locale.ROOT).trim();

                    if(!sqliteHelper.isDateExists(d,currentUserId,value)){
                        if(isInternetAvailable()){
                            map.put("fullname",fullName);
                            map.put("date",d);
                            if(count==0){
                                count=1;
                            }else{
                                count=count+1;
                            }
                            map.put("number",count);

                            db.collection(value).add(map).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentReference> task) {
                                    sqliteHelper.updateTurn(value,currentUserId);
                                    Log.v("tag user", currentUserId);
                                    sqliteHelper.addApp(valueAr,fullName,d,currentUserId,String.valueOf(count));

                                    number2.setVisibility(View.VISIBLE);
                                    number2.setText("رقمك هو: "+String.valueOf(count));
                                    Toast.makeText(getContext(),"أراك غدا!",Toast.LENGTH_LONG).show();
                                }
                            });}else{
                            Toast.makeText(getContext(),"تحقق من الإنترنت الخاص بك!",Toast.LENGTH_LONG).show();

                        }


                    }else{
                        Toast.makeText(getContext(),"لقد قمت بتحديد موعد اليوم!",Toast.LENGTH_LONG).show();
                    }

                }else{
                    Toast.makeText(getContext(),"أدخل اسمك واسم عائلتك!",Toast.LENGTH_LONG).show();
                }

            }
        });

    }

        public boolean isInternetAvailable() {
            String command = "ping -c 1 google.com";
            try {
                return Runtime.getRuntime().exec(command).waitFor() == 0;
            } catch (InterruptedException e) {
                Toast.makeText(getContext(),"check conn",Toast.LENGTH_LONG).show();

            } catch (IOException e) {
                Toast.makeText(getContext(),"check conn",Toast.LENGTH_LONG).show();

            }
            return false;
        }
    public  String hopAr(String valueAr){
        String val=null;
        switch(valueAr){
            case "عيادة أ":
                val="A";
                break;
            case "عيادة ب":
                val="B";
                break;
            case "عيادة ج":
                val="C";
                break;
            case "عيادة د":
                val="D";
                break;

        }
        return val;

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
