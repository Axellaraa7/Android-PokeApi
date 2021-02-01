package com.computomovil.pokeapi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.computomovil.pokeapi.infoPokemon.AdapterPk;
import com.computomovil.pokeapi.infoPokemon.Pokemon;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView lvPokemones;
    protected ArrayList<Pokemon> pokemones=new ArrayList<>();

    private String url;
    private JsonObjectRequest jsonObject;
    private RequestQueue queue;
    private JSONArray jsonArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvPokemones=findViewById(R.id.lvPokemones);
        conexion();
    }

    private void conexion(){
        queue= Volley.newRequestQueue(this);
        url=getResources().getString(R.string.urlApi);
        jsonObject=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    jsonArray=response.getJSONArray(getResources().getString(R.string.results));
                    for(int i=0;i<jsonArray.length();i++){
                        String name=jsonArray.getJSONObject(i).getString(getResources().getString(R.string.name));
                        String urlPk=jsonArray.getJSONObject(i).getString(getResources().getString(R.string.url));
                        Pokemon pokemon=new Pokemon(name,i,urlPk);
                        pokemones.add(pokemon);
                    }
                } catch (JSONException e) { e.printStackTrace();
                }
                finally{
                    AdapterPk adapterPk=new AdapterPk(MainActivity.this,pokemones);
                    lvPokemones.setAdapter(adapterPk);

                    lvPokemones.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent description=new Intent(MainActivity.this,PokemonView.class);
                            Bundle bundle=new Bundle();
                            bundle.putString(getResources().getString(R.string.url),pokemones.get(position).getUrl());
                            description.putExtras(bundle);
                            startActivity(description);
                        }
                    });
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) { error.printStackTrace(); }
        });
        queue.add(jsonObject);
    }

}