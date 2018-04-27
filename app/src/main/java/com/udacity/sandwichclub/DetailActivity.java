package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = null;
        try {
            sandwich = JsonUtils.parseSandwichJson(json);
        } catch (Exception e) {
            Log.d("Detail Activity", "onCreate: " + e);
        }
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        ImageView ingredientsIv = findViewById(R.id.image_iv);
        TextView originTextView = findViewById(R.id.origin_tv);
        TextView descriptionTextView = findViewById(R.id.description_tv);
        TextView ingredientsTextView = findViewById(R.id.ingredients_tv);
        TextView alsoKnowAsTextView = findViewById(R.id.also_known_tv);

        originTextView.setText(sandwich.getPlaceOfOrigin());
        descriptionTextView.setText(sandwich.getDescription());

        StringBuilder sandwichIngredientsStringBuilder = new StringBuilder();
        List<String> sandwichIngredientsList = sandwich.getIngredients();
        for(int i = 0; i < sandwichIngredientsList.size(); i++) {
            sandwichIngredientsStringBuilder.append(sandwichIngredientsList.get(i));
            sandwichIngredientsStringBuilder.append("\n");
        }
        ingredientsTextView.setText(sandwichIngredientsStringBuilder.toString());

        StringBuilder sandwichNamesStringBuilder = new StringBuilder();
        List<String> sandwichNameList = sandwich.getAlsoKnownAs();
        for(int i = 0; i < sandwichNameList.size(); i++) {
            sandwichNamesStringBuilder.append(sandwichNameList.get(i));
            sandwichNamesStringBuilder.append("\n");
        }
        alsoKnowAsTextView.setText(sandwichNamesStringBuilder.toString());

        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());


    }
}
