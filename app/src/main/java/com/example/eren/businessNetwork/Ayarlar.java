package com.example.eren.businessNetwork;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.eren.uyeol.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Ayarlar extends AppCompatActivity {

    EditText etKadi,etCeptel,etEvtel,etEposta;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    FirebaseDatabase database;
    DatabaseReference myRef;
    String kullanici_adi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ayarlar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        etKadi=(EditText)findViewById(R.id.etAyarKadi);
        etCeptel=(EditText)findViewById(R.id.etAyarCeptel);
        etEvtel=(EditText)findViewById(R.id.etAyarCeptel);
        etEposta=(EditText)findViewById(R.id.etAyarEposta);

        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        editor = preferences.edit();
        kullanici_adi=preferences.getString("kullanıcı adı","Default");

    }



    public void Guncelle(View view) {
        String a;
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference(kullanici_adi);
        try {
            myRef.setValue("");
            myRef.push().setValue(etKadi.getText().toString());
            myRef.push().setValue(etCeptel.getText().toString());
            myRef.push().setValue(etEvtel.getText().toString());
            myRef.push().setValue(etEposta.getText().toString());
            Toast.makeText(getApplicationContext(),"Bilgileriniz Güncellendi",Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            Toast.makeText(getApplicationContext(),"Güncelleme İşlemi Başarısız Bir Hata Oluştu",Toast.LENGTH_SHORT).show();
        }

    }
}
