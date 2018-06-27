package com.y2m.masrnanewstwo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by user on 3/28/2016.
 */
public class ParseNews {
    String json_string;

    public ParseNews (String json_string)
    {
        this.json_string=json_string;
    }
    public ArrayList<News> GetNewsArray()  {
        ArrayList<News> all_Recipes = new ArrayList<News>();
        try {
            JSONObject jsonobject = new JSONObject(json_string);
            JSONArray Recipes = jsonobject.getJSONArray("news");
            JSONObject json_recipe;
            News one_recipe;
            for (int i =0; i<Recipes.length() ; i++) {
                json_recipe = Recipes.getJSONObject(i);
                one_recipe = new News( json_recipe.getString("id"),json_recipe.getString("title"),
                        json_recipe.getString("body"), json_recipe.getString("link"),  json_recipe.getString("image"),
                        json_recipe.getString("video"),json_recipe.getString("type"),json_recipe.getString("time"),0);
                all_Recipes.add(one_recipe);
            }
            return all_Recipes;
        }
        catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
