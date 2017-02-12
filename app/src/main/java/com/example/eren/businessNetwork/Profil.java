package com.example.eren.businessNetwork;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eren.uyeol.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Profil extends AppCompatActivity {

    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    FirebaseDatabase database;
    DatabaseReference myRef;
    String kullanici_adi;
    TextView tvKadi,tvCeptel,tvEvtel,tvEposta;
    HashMap<String, Object> userMap = new HashMap<String, Object>();
    int sayac=0;
        @Override
        protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

            preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            editor = preferences.edit();
            kullanici_adi=preferences.getString("kullanıcı adı","Default");
           // Toast.makeText(getApplicationContext(),kullanici_adi,Toast.LENGTH_SHORT).show();
            tvKadi=(TextView)findViewById(R.id.tvProfilKadi);
            tvCeptel=(TextView)findViewById(R.id.tvProfilCeptel);
            tvEvtel=(TextView)findViewById(R.id.tvProfilEvtel);
            tvEposta=(TextView)findViewById(R.id.tvProfilEposta);
            acilis();

            Toast.makeText(getApplicationContext(),"Add "+userMap.get("kadi"),Toast.LENGTH_SHORT).show();

        }


        public void acilis(){

            database = FirebaseDatabase.getInstance();
            myRef = database.getReference(kullanici_adi);
            //   myRef.push().setValue(etMesaj.getText().toString());


            myRef.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                    sayac=sayac+1;
                    if(sayac==1){
                        userMap.put("kadi",dataSnapshot.getValue());
                    }else if(sayac==2){
                        userMap.put("ceptel",dataSnapshot.getValue());
                    }else if(sayac==3){
                        userMap.put("evtel",dataSnapshot.getValue());
                    }else if(sayac==4){
                        userMap.put("eposta",dataSnapshot.getValue());
                        goster();
                    }
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }

    public void goster(){
            tvKadi.setText(userMap.get("kadi").toString());
            tvCeptel.setText(userMap.get("ceptel").toString());
            tvEvtel.setText(userMap.get("evtel").toString());
            tvEposta.setText(userMap.get("eposta").toString());
    }


}
