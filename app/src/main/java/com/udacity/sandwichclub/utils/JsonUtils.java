package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {

        Sandwich sandwich = new Sandwich();

        try {
            JSONObject jsonSandwich = new JSONObject(json);
            JSONObject jsonName = jsonSandwich.getJSONObject("name");

            sandwich.setMainName(jsonName.getString("mainName"));
            sandwich.setAlsoKnownAs(listHelper(jsonName.getJSONArray("alsoKnownAs")));
            sandwich.setPlaceOfOrigin(jsonSandwich.getString("placeOfOrigin"));
            sandwich.setDescription(jsonSandwich.getString("description"));
            sandwich.setImage(jsonSandwich.getString("image"));
            sandwich.setIngredients(listHelper(jsonSandwich.getJSONArray("ingredients")));
        }
        catch (JSONException e) {
            e.printStackTrace();
        }

        return sandwich;
    }

    // This method helps at converting a JSONArray to an ArrayList for easier manipulation.
    private static List<String> listHelper(JSONArray arr) throws JSONException {
        ArrayList<String> list = new ArrayList<>();

        for (int i = 0; i < arr.length(); i++) {
            list.add((String) arr.get(i));
        }

        return list;
    }
}
