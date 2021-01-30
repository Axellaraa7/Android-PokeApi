package com.computomovil.pokeapi.infoPokemon;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.computomovil.pokeapi.R;

import java.util.ArrayList;

public class AdapterPk extends BaseAdapter {

    private Context main;
    private ArrayList<Pokemon> pokemones;
    protected TextView tvName,tvId;

    public AdapterPk(Context main,ArrayList<Pokemon> pokemones){
        this.main=main;
        this.pokemones=pokemones;
    }


    @Override
    public int getCount() {
        return pokemones.size();
    }

    @Override
    public Object getItem(int position) {
        return pokemones.get(position);
    }

    @Override
    public long getItemId(int position) {
        return pokemones.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Pokemon pokemon=pokemones.get(position);

        if(convertView==null) convertView= LayoutInflater.from(main).inflate(R.layout.prototype,null);

        tvName=convertView.findViewById(R.id.tvName);
        tvId=convertView.findViewById(R.id.tvId);

        tvName.setText(pokemon.getName());
        tvId.setText(parent.getResources().getString(R.string.number)+(pokemon.getId()+parent.getResources().getInteger(R.integer.uno)));

        return convertView;
    }
}
