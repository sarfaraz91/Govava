package com.example.daniyal.govava.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.example.daniyal.govava.HomeFragments;
import com.example.daniyal.govava.activity.HomeCategory;
import com.example.daniyal.govava.activity.HomeScreen;
import com.example.daniyal.govava.R;


public class WhatToBuyFragment4 extends Fragment {

    ImageView continueNext,continueback;
    RelativeLayout rl_QuestionCross;
    public String occasions[] = {
            "Birthday",
            "Bridal",
            "Thanksgiving",
            "Christmas",
            "Easter",
            "Hanuka",
            "New Born",
            "Baby Shower",
            "Bachelorette/Bachelor Party",
            "Promotion",
            "Sympathy",
            "Graduation",
            "Passed Big Exam",
            "Weight loss",
            "Get Well Soon/Welcome home from hospital",
            "Apology",
            "Thank you/Appreciation",
            "Grand Opening",
    };

    public Spinner Question4Edit;
    public ArrayAdapter<String> adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_question_4,
                container, false);
        continueNext = (ImageView) view.findViewById(R.id.continueNext);
        continueback = (ImageView)view.findViewById(R.id.continueback);
        continueback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewPager viewPager = ((HomeFragments)getActivity()).mPager;
                viewPager.setCurrentItem(viewPager.getCurrentItem()-1);
            }
        });

        Question4Edit = (Spinner)view.findViewById(R.id.Question4Edit);
        adapter = new ArrayAdapter<String>(getActivity(),R.layout.custom_text,occasions);
        Question4Edit.setAdapter(adapter);

        rl_QuestionCross = (RelativeLayout) view.findViewById(R.id.rl_QuestionCross);

        rl_QuestionCross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // getActivity().getFragmentManager().popBackStack();
                getActivity().onBackPressed();
            }
        });


        // Inflate the layout for this fragment
        return view;
    }

    public String getText(){

        return Question4Edit.getSelectedItem().toString();
    }


}
