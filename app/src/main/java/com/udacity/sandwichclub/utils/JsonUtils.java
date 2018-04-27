package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) throws JSONException {

        JSONObject sandwichJsonObject = new JSONObject(json);
        JSONObject sandwichNameJsonObject = sandwichJsonObject.getJSONObject("name");
        String sandwichName = sandwichNameJsonObject.getString("mainName");
        JSONArray sandwichAlsoKnownAsArray = sandwichNameJsonObject.getJSONArray("alsoKnownAs");

        List<String> sandwichAlsoKnownAs = new ArrayList<>();
        for (int i = 0; i < sandwichAlsoKnownAsArray.length(); i++ ) {
            sandwichAlsoKnownAs.add(sandwichAlsoKnownAsArray.getString(i));
        }

        String sandwichPlaceOfOrigin = "";
        if (sandwichJsonObject.has("placeOfOrigin")) {
            sandwichPlaceOfOrigin = sandwichJsonObject.getString("placeOfOrigin");
        }
        
        String sandwichDescription = sandwichJsonObject.getString("description");
        String sandwichImageUrl = sandwichJsonObject.getString("image");

        JSONArray sandwichIngredientsArray = sandwichJsonObject.getJSONArray("ingredients");
        List<String> sandwichIngredients = new ArrayList<>();
        for (int i = 0; i < sandwichIngredientsArray.length(); i++) {
            sandwichIngredients.add(sandwichIngredientsArray.getString(i));
        }

        Sandwich sandwich = new Sandwich(sandwichName, sandwichAlsoKnownAs, sandwichPlaceOfOrigin, sandwichDescription, sandwichImageUrl, sandwichIngredients);
        return sandwich;
    }
}
