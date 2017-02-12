package com.example.eren.businessNetwork;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.eren.uyeol.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Giris extends AppCompatActivity {


    boolean kontrol=false;
    EditText etGrsKadi,etGrsSifre;
    String url_getir="http://xxx.xxx.xx.x";
    RequestQueue requestQueue;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giris);

        etGrsKadi=(EditText) findViewById(R.id.etGrsKadi);
        etGrsSifre=(EditText) findViewById(R.id.etGrsSifre);
        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        editor = preferences.edit();


        requestQueue= Volley.newRequestQueue(getApplicationContext());

    }

    public void GrsYapOnclick(View view) {

        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.POST, url_getir, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONArray uyeler=response.getJSONArray("uyeler");

                    for (int i=0;i<uyeler.length();i++){

                        JSONObject uye=uyeler.getJSONObject(i);

                        String eposta=uye.getString("eposta").toString();
                        String kAdi=uye.getString("kAdi").toString();
                        String sifre=uye.getString("sifre").toString();

                        if((etGrsKadi.getText().toString().equals(eposta) ||etGrsKadi.getText().toString().equals(kAdi)) && etGrsSifre.getText().toString().equals(sifre)){
                            Toast.makeText(getApplicationContext(),"Bilgiler Doğru",Toast.LENGTH_SHORT).show();
                            editor.putBoolean("login",true);
                            editor.putString("Eposta",eposta);
                            editor.putString("kullanıcı adı",kAdi);
                            editor.putString("Şifre",sifre);
                            editor.commit();
                            kontrol=true;
                            ac();

                        }

                    }
                    if(kontrol!=true){
                        Toast.makeText(getApplicationContext(),"Bilgiler Yanlış",Toast.LENGTH_SHORT).show();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        requestQueue.add(jsonObjectRequest);

    }

    public void dene(View view) {

        editor.remove("login");
        editor.remove("eposta");
        editor.remove("kullanıcı adı");
        editor.remove("Şifre");
        editor.commit();

    }
    public void ac(){

            Intent intent = new Intent(getApplicationContext(), menu.class);
           // intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(intent);
//            this.finish();

    }
}
