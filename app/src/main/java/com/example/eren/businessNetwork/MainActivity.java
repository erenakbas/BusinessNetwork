package com.example.eren.businessNetwork;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.eren.uyeol.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    EditText etAd,etSoyad,etEposta,etSifre,etKullaniciAdi;
    String url_kaydet="http://xxx.xxx.xx.x";
    RequestQueue requestQueue;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    FirebaseDatabase database;
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etAd=(EditText) findViewById(R.id.etAd);
        etSoyad=(EditText) findViewById(R.id.etSoyad);
        etEposta=(EditText) findViewById(R.id.etEposta);
        etSifre=(EditText) findViewById(R.id.etSifre);
        etKullaniciAdi=(EditText) findViewById(R.id.etKullaniciAdi);

        requestQueue= Volley.newRequestQueue(getApplicationContext());

        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        editor = preferences.edit();
        if(preferences.getBoolean("login", false)){
//            Intent intent = new Intent(getApplicationContext(),Index.class);
//            startActivity(intent);

            Intent intent = new Intent(getApplicationContext(), menu.class);
//            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(intent);
         //  this.finish();
        }

    }

    public void grisGetirOnclick(View view) {
        Intent intent = new Intent(getApplicationContext(),Giris.class);
        startActivity(intent);
    }

    public void uyeOlClick(View view) {

        StringRequest request = new StringRequest(Request.Method.POST, url_kaydet, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                try {
                    JSONObject obje = new JSONObject(response);
                    boolean deger=obje.getBoolean("error");

                    if(!deger){
                        Toast.makeText(getApplicationContext()," Kayıt İşlemi Başarrılı",Toast.LENGTH_SHORT).show();
                        run();
                    }
                    else {
                        Toast.makeText(getApplicationContext()," Zaten Üyesin Giriş Yapmayı Dene :)",Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("ad",etAd.getText().toString());
                params.put("soyad",etSoyad.getText().toString());
                params.put("kAdi",etKullaniciAdi.getText().toString());
                params.put("eposta",etEposta.getText().toString());
                params.put("sifre",etSifre.getText().toString());
                return params;
            }
        };

        requestQueue.add(request);




    }
    public void run(){
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference(etKullaniciAdi.getText().toString());
        myRef.push().setValue(etKullaniciAdi.getText().toString());




    }
}
