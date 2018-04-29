package com.udacity.sandwichclub.utils;


import com.udacity.sandwichclub.model.Sandwich;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;


public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) throws JSONException {

        //parsing sandwich data
        JSONObject rootObject = new JSONObject(json);
        JSONObject parentObject = rootObject.getJSONObject("name");

        String mainName = parentObject.getString("mainName");
        String placeOfOrigin = rootObject.getString("placeOfOrigin");
        String image = rootObject.getString("image");
        String description = rootObject.getString("description");
        JSONArray ingredients = rootObject.getJSONArray("ingredients");

        ArrayList<String> sandwichIngredients = new ArrayList<>();

        for (int i = 0; i < ingredients.length(); i++) {
            sandwichIngredients.add(ingredients.getString(i));
        }

        JSONArray alsoKnownAs = parentObject.getJSONArray("alsoKnownAs");


        ArrayList<String> alsoKnownAsArray = new ArrayList<>();

        for (int i = 0; i < alsoKnownAs.length(); i++) {
            alsoKnownAsArray.add(alsoKnownAs.getString(i));
        }

        return new Sandwich(mainName, alsoKnownAsArray, placeOfOrigin, description, image, sandwichIngredients);
    }
}
