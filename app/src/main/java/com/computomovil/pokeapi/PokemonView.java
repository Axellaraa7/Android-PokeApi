package com.computomovil.pokeapi;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.computomovil.pokeapi.infoPokemon.Pokemon;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class PokemonView extends AppCompatActivity {

    Bundle bundle=new Bundle();
    private String url;
    ImageView ivPokemon;
    TextView tvNamePokemon,tvExp,tvHeight,tvWeight;

    private RequestQueue queue;
    private JsonObjectRequest jsonObject;
    private JSONArray jsonArray;

    private ImageView[] types;
    private LinearLayout llTypes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemon);

        tvNamePokemon=findViewById(R.id.tvNamePokemon);
        tvExp=findViewById(R.id.tvExp);
        tvHeight=findViewById(R.id.tvHeight);
        tvWeight=findViewById(R.id.tvWeight);
        ivPokemon=findViewById(R.id.ivPokemon);
        llTypes=findViewById(R.id.llTypes);


        bundle=getIntent().getExtras();
        if(bundle!=null) {
            url = bundle.getString(getResources().getString(R.string.url));
        }

        Conexion();
    }

    //Obtención datos especializados de pokemones.
    public void Conexion(){
        queue= Volley.newRequestQueue(this);
        jsonObject=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                String nombre="",exp="",alt="",peso="",img="";
                String tipos[];
                try {
                    nombre=response.getJSONObject(getResources().getString(R.string.species)).getString(getResources().getString(R.string.name));
                    exp=response.getString(getResources().getString(R.string.base_experience));
                    alt=response.getString(getResources().getString(R.string.height));
                    peso=response.getString(getResources().getString(R.string.weight));
                    tipos=getType(response);
                    img=getImg(response);
                    setImageView(img);
                    setTextViews(nombre,exp,alt,peso);
                    setTypes(tipos);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(jsonObject);
    }
    //Obtención tipos del pokemon
    private String[] getType(JSONObject response){
        String datos[]=null;
        try {
            jsonArray=response.getJSONArray(getResources().getString(R.string.types));
            datos=new String[jsonArray.length()];
            for(int i=0;i<jsonArray.length();i++){
                datos[i]=jsonArray.getJSONObject(i).getJSONObject(getResources().getString(R.string.type)).getString(getResources().getString(R.string.name));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }finally{
            return datos;
        }

    }
    //Obtención de la url de la img
    private String getImg(JSONObject response){
        String imgUrl=getResources().getString(R.string.vacio);
        try {
            imgUrl=response.getJSONObject(getResources().getString(R.string.sprites)).getJSONObject(getResources().getString(R.string.other)).getJSONObject(getResources().getString(R.string.artwork)).getString(getResources().getString(R.string.img));
        } catch (JSONException e) {
            e.printStackTrace();
        }finally{
            return imgUrl;
        }

    }

    //Setter del ImageView con la url de la img
    private void setImageView(String img){
        Picasso.with(PokemonView.this).load(img).into(ivPokemon);
    }

    //Setter de los TextViews
    private void setTextViews(String nombre, String exp, String alt, String peso){
        tvNamePokemon.setText(nombre);
        tvExp.setText(exp);
        tvHeight.setText(alt);
        tvWeight.setText(peso);
    }

    //Setter de las ImageViews de los tipos del pokemon
    private void setTypes(String[] tipos){
        llTypes.removeAllViews();
        types=new ImageView[tipos.length];
        for(int i=0;i<tipos.length;i++){
            types[i]=new ImageView(this);
            types[i].setLayoutParams(new ViewGroup.LayoutParams(llTypes.getWidth()/getResources().getInteger(R.integer.weightDiv), ViewGroup.LayoutParams.WRAP_CONTENT));
            selectType(i,tipos);
            llTypes.addView(types[i]);
        }
    }

    private void selectType(int position,String[] tipos) {
        if (getResources().getString(R.string.bug).equals(tipos[position])) {
            types[position].setImageDrawable(getResources().getDrawable(R.drawable.bug));
        } else if (getResources().getString(R.string.dark).equals(tipos[position])) {
            types[position].setImageDrawable(getResources().getDrawable(R.drawable.dark));
        } else if (getResources().getString(R.string.dragon).equals(tipos[position])) {
            types[position].setImageDrawable(getResources().getDrawable(R.drawable.dragon));
        } else if (getResources().getString(R.string.electric).equals(tipos[position])) {
            types[position].setImageDrawable(getResources().getDrawable(R.drawable.electric));
        } else if (getResources().getString(R.string.fairy).equals(tipos[position])) {
            types[position].setImageDrawable(getResources().getDrawable(R.drawable.fairy));
        } else if (getResources().getString(R.string.fighting).equals(tipos[position])) {
            types[position].setImageDrawable(getResources().getDrawable(R.drawable.fighting));
        } else if (getResources().getString(R.string.fire).equals(tipos[position])) {
            types[position].setImageDrawable(getResources().getDrawable(R.drawable.fire));
        } else if (getResources().getString(R.string.flying).equals(tipos[position])) {
            types[position].setImageDrawable(getResources().getDrawable(R.drawable.flying));
        } else if (getResources().getString(R.string.ghost).equals(tipos[position])) {
            types[position].setImageDrawable(getResources().getDrawable(R.drawable.ghost));
        } else if (getResources().getString(R.string.grass).equals(tipos[position])) {
            types[position].setImageDrawable(getResources().getDrawable(R.drawable.grass));
        } else if (getResources().getString(R.string.ground).equals(tipos[position])) {
            types[position].setImageDrawable(getResources().getDrawable(R.drawable.ground));
        } else if (getResources().getString(R.string.ice).equals(tipos[position])) {
            types[position].setImageDrawable(getResources().getDrawable(R.drawable.ice));
        } else if (getResources().getString(R.string.normal).equals(tipos[position])) {
            types[position].setImageDrawable(getResources().getDrawable(R.drawable.normal));
        } else if (getResources().getString(R.string.poison).equals(tipos[position])) {
            types[position].setImageDrawable(getResources().getDrawable(R.drawable.poison));
        } else if (getResources().getString(R.string.psychic).equals(tipos[position])) {
            types[position].setImageDrawable(getResources().getDrawable(R.drawable.psychic));
        } else if (getResources().getString(R.string.rock).equals(tipos[position])) {
            types[position].setImageDrawable(getResources().getDrawable(R.drawable.rock));
        } else if (getResources().getString(R.string.steel).equals(tipos[position])) {
            types[position].setImageDrawable(getResources().getDrawable(R.drawable.steel));
        } else if (getResources().getString(R.string.water).equals(tipos[position])) {
            types[position].setImageDrawable(getResources().getDrawable(R.drawable.water));
        }
    }

}