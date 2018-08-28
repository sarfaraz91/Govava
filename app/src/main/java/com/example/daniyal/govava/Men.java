package com.example.daniyal.govava;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.daniyal.govava.activity.FilterActivity;
import com.example.daniyal.govava.activity.ItemDetails;

public class Men extends AppCompatActivity {


    ImageView MenBack , Menimg1 , imageView3;
    TextView MenHeading, t1 , t2 , t3 ,t4 ,t5, t6;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_men);
        MenHeading = (TextView) findViewById(R.id.MenHeading);
        t1 = (TextView) findViewById(R.id.MenText1);
        t2 = (TextView) findViewById(R.id.MenText2);
        t3 = (TextView) findViewById(R.id.MenText3);
        t4 = (TextView) findViewById(R.id.MenText4);
        t5 = (TextView) findViewById(R.id.MenText5);
        t6 = (TextView) findViewById(R.id.MenText6);
        MenBack = (ImageView) findViewById(R.id.MenBack);
        Menimg1 = (ImageView) findViewById(R.id.Menimg1);
        imageView3 = (ImageView) findViewById(R.id.MenFilterButton);

        Typeface typeface2 = Typeface.createFromAsset(getAssets(), "fonts/RobotoCondensed-Regular.ttf");
        Typeface typeface3 = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Regular.ttf");
        MenHeading.setTypeface(typeface3);


        t1.setTypeface(typeface2);
        t2.setTypeface(typeface2);
        t3.setTypeface(typeface2);
        t4.setTypeface(typeface2);
        t5.setTypeface(typeface2);
        t6.setTypeface(typeface2);

        MenBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();

            }
        });

        Menimg1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Men.this , ItemDetails.class);
                startActivity(intent);
            }
        });

        imageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Men.this , FilterActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            // finish the activity
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
