package com.example.daniyal.govava.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.daniyal.govava.R;
import com.yahoo.mobile.client.android.util.rangeseekbar.RangeSeekBar;

public class FilterActivity extends AppCompatActivity {


   // RangeSeekBar rangeSeekBar;
    public RelativeLayout close_filter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
       // rangeSeekBar = (RangeSeekBar) findViewById(R.id.rangeSeekbar);
        close_filter = (RelativeLayout)findViewById(R.id.close_filter);
        RangeSeekBar<Integer> rangeSeekBar = new RangeSeekBar<Integer>(FilterActivity.this);
        rangeSeekBar.setRangeValues(0, 100);


        rangeSeekBar.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener<Integer>() {
            @Override
            public void onRangeSeekBarValuesChanged(RangeSeekBar<?> bar, Integer minValue, Integer maxValue) {
                //Now you have the minValue and maxValue of your RangeSeekbar
                Toast.makeText(getApplicationContext(), minValue + "-" + maxValue, Toast.LENGTH_LONG).show();
            }
        });

        // Get noticed while dragging
        rangeSeekBar.setNotifyWhileDragging(true);

        close_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}
