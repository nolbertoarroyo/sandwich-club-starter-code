package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import org.json.JSONException;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

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
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    //added Sandwich object as parameter to extract data and populate views
    private void populateUI(Sandwich sandwich) {

        TextView sandwichDescriptionTv = findViewById(R.id.description_tv);
        TextView sandwichAlsoKnownAsTv = findViewById(R.id.also_known_tv);
        TextView sandwichIngredientsTv = findViewById(R.id.ingredients_tv);
        TextView sandwichPlaceOfOriginTv = findViewById(R.id.origin_tv);

        // if statements check return data and displays in textView if not empty, sets default value if empty
        if (sandwich.getDescription().isEmpty()) {
            sandwichDescriptionTv.setText(R.string.default_string_value);
        } else {
            sandwichDescriptionTv.setText(sandwich.getDescription());

        }
        if (sandwich.getPlaceOfOrigin().isEmpty()) {
            sandwichPlaceOfOriginTv.setText(R.string.default_string_value);
        } else {
            sandwichPlaceOfOriginTv.setText(sandwich.getPlaceOfOrigin());

        }

        // check if arrays are not empty if so, set default value to text views
        //using TextUtils.join to concatenate string values for textViews
        if (sandwich.getAlsoKnownAs().isEmpty()) {
            sandwichAlsoKnownAsTv.setText(R.string.default_string_value);
        } else {

            sandwichAlsoKnownAsTv.setText(TextUtils.join(", ", sandwich.getAlsoKnownAs()));
        }


        if (sandwich.getIngredients().isEmpty()) {
            sandwichIngredientsTv.setText(R.string.default_string_value);
        } else {
            sandwichIngredientsTv.setText(TextUtils.join(", ", sandwich.getIngredients()));

        }


    }
}
