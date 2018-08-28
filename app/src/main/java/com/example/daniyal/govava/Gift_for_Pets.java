package com.example.daniyal.govava;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class Gift_for_Pets extends AppCompatActivity {


    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gift_for__pets);
        imageView = (ImageView) findViewById(R.id.GiftForPetimg1);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(Gift_for_Pets.this , Pets.class);
//                startActivity(intent);
            }
        });

        //getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        //getSupportActionBar().setCustomView(R.layout.custom_searchbar);



//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarCustom);
//        setSupportActionBar(toolbar);

    }
}
