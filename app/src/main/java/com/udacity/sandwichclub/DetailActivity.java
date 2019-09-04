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

    private TextView tvAlsoKnownAs;
    private TextView tvIngredients;
    private TextView tvOrigin;
    private TextView tvDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);
        tvAlsoKnownAs = findViewById(R.id.also_known_tv);
        tvIngredients = findViewById(R.id.ingredients_tv);
        tvOrigin = findViewById(R.id.origin_tv);
        tvDescription = findViewById(R.id.description_tv);


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
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
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

    //Checks and handles if there is an empty array or string.
    private void populateUI(Sandwich sandwich) {
        if (sandwich.getAlsoKnownAs().isEmpty())
            tvAlsoKnownAs.setText("-none-");
        else
            tvAlsoKnownAs.setText(listToString(sandwich.getAlsoKnownAs()));

        if (sandwich.getPlaceOfOrigin().equals(""))
            tvOrigin.setText("-none-");
        else
            tvOrigin.setText(sandwich.getPlaceOfOrigin());
        tvIngredients.setText(listToString(sandwich.getIngredients()));

        tvDescription.setText(sandwich.getDescription());
    }

    //Creates a better looking bulleted string.
    private static String listToString(List<String> list) {
        String str = "";
        for (int i = 0; i < list.size(); i++) {
            str += "-" + list.get(i) + "\n";
        }
        return str;
    }
}
