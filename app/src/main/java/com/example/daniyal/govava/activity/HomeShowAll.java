package com.example.daniyal.govava.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.daniyal.govava.Men;
import com.example.daniyal.govava.R;

public class HomeShowAll extends AppCompatActivity {

    TextView textView, Apparels, Camera;
    ImageView ShowAllBck,img_filter;


    Context context =this;

    ImageView image1,image3,From2,imag_camera1,img_camera2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_show_all);
        Apparels = (TextView) findViewById(R.id.HomeShowApparels);
        Camera = (TextView) findViewById(R.id.HomeShowCamera);

        imag_camera1 = (ImageView) findViewById(R.id.imag_camera1);
        imag_camera1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context,
                        "Under Progress !", Toast.LENGTH_LONG).show();
            }
        });

        img_camera2 = (ImageView) findViewById(R.id.img_camera2);
        img_camera2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context,
                        "Under Progress !", Toast.LENGTH_LONG).show();
            }
        });


        image1 = (ImageView) findViewById(R.id.image1);
        image1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context,
                        "Under Progress !", Toast.LENGTH_LONG).show();
            }
        });
        From2 = (ImageView) findViewById(R.id.From2);
        From2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context,
                        "Under Progress !", Toast.LENGTH_LONG).show();
            }
        });
        image3 = (ImageView) findViewById(R.id.image3);
        image3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context,
                        "Under Progress !", Toast.LENGTH_LONG).show();
            }
        });






        img_filter = (ImageView) findViewById(R.id.img_filter);


        ShowAllBck = (ImageView) findViewById(R.id.ShowAllBck);
        textView = (TextView) findViewById(R.id.Showmore);

        Typeface typeface3 = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Black.ttf");

        Apparels.setTypeface(typeface3);
        Camera.setTypeface(typeface3);
        textView.setTypeface(typeface3);


        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent  intent = new Intent(HomeShowAll.this , Men.class);
                startActivity(intent);
            }
        });

        ShowAllBck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent  intent = new Intent(HomeShowAll.this , HomeScreen.class);
                startActivity(intent);
            }
        });

        img_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent  intent = new Intent(HomeShowAll.this , FilterActivity.class);
                startActivity(intent);
            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the HomeScreen/Up button, so long
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
