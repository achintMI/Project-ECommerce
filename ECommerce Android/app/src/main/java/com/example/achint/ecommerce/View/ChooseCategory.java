package com.example.achint.ecommerce.View;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;

import com.example.achint.ecommerce.R;

public class ChooseCategory extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        CardView catWomen = findViewById(R.id.women_clothing);
        CardView catMen = findViewById(R.id.men_clothing);
        CardView catPhone = findViewById(R.id.cat_phone);
        CardView catMenShoe = findViewById(R.id.men_shoe);
        CardView catWomenShoe = findViewById(R.id.women_shoe);
        CardView catHeadPhone = findViewById(R.id.headphone);

        catWomen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent categoryIntent = new Intent(ChooseCategory.this, CategoryActivity.class);
                categoryIntent.putExtra("category", "women_clothing");
                startActivity(categoryIntent);
            }
        });

        catMen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent categoryIntent = new Intent(ChooseCategory.this, CategoryActivity.class);
                categoryIntent.putExtra("category", "men_clothing");
                startActivity(categoryIntent);
            }
        });

        catPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent categoryIntent = new Intent(ChooseCategory.this, CategoryActivity.class);
                categoryIntent.putExtra("category", "phone");
                startActivity(categoryIntent);
            }
        });
        catMenShoe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent categoryIntent = new Intent(ChooseCategory.this, CategoryActivity.class);
                categoryIntent.putExtra("category", "men_shoe");
                startActivity(categoryIntent);
            }
        });

        catWomenShoe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent categoryIntent = new Intent(ChooseCategory.this, CategoryActivity.class);
                categoryIntent.putExtra("category", "women_shoe");
                startActivity(categoryIntent);
            }
        });
        catHeadPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent categoryIntent = new Intent(ChooseCategory.this, CategoryActivity.class);
                categoryIntent.putExtra("category", "headphone");
                startActivity(categoryIntent);
            }
        });
    }
}
