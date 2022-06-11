package com.mycompany.maw3id_app;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;


import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class ShowDbChanges  {
    String d;
    
    
    public ShowDbChanges(){
           try {
            new FirestoreClient(new FirebaseService().getDb());
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
    public List<QueryDocumentSnapshot> ShowDbChanges1() throws InterruptedException, ExecutionException {
       
     
        LocalDate today = LocalDate.now();
        String yesterday = (today.minusDays(1)).format(DateTimeFormatter.ISO_DATE);
        System.out.println(yesterday);


         DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
       String d=dateFormat.format(date);
        
        

        CollectionReference ref = FirestoreClient.getFirestore().collection("A");
        ApiFuture<QuerySnapshot> future =ref.whereEqualTo("date", yesterday).get();
        

        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        return documents;
 
    }
        public List<QueryDocumentSnapshot> ShowApp(String name) throws InterruptedException, ExecutionException {
 
        CollectionReference ref = FirestoreClient.getFirestore().collection("A");
        ApiFuture<QuerySnapshot> future =
    FirestoreClient.getFirestore().collection("A").whereEqualTo("fullname", name).get();

        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        return documents;
 
    }
       public void updatePatient(String tour){
           Map<String,Object> map=new HashMap<>();
           map.put("tour",tour);
           FirestoreClient.getFirestore().collection("AT").document("tour").update(map);
           
       }
}