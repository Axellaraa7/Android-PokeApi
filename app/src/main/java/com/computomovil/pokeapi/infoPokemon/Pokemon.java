package com.computomovil.pokeapi.infoPokemon;

public class Pokemon {

        private String name;
        private int id;
        private String url;

        public Pokemon(String name,int id,String url){
            this.name=name;
            this.id=id;
            this.url=url;
        }

        public String getName(){
            return this.name;
        }

        public int getId(){
            return this.id;
        }

        public String getUrl(){
            return this.url;
        }
}
