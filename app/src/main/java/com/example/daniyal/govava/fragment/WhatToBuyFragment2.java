package com.example.daniyal.govava.fragment;

import android.content.Context;
import android.net.Uri;
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
import com.example.daniyal.govava.R;



public class WhatToBuyFragment2 extends Fragment {

    ImageView continueNext,continueback;
    RelativeLayout rl_QuestionCross;
    public String gender[] = {"Male","Female"};
    public ArrayAdapter<String> adapter;
    public Spinner Question2Edit;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_question_2,
                container, false);
        continueNext = (ImageView)view.findViewById(R.id.continueNext);
        continueback = (ImageView)view.findViewById(R.id.continueback);

        continueback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              ViewPager viewPager = ((HomeFragments)getActivity()).mPager;
              viewPager.setCurrentItem(viewPager.getCurrentItem()-1);
            }
        });

        Question2Edit = (Spinner)view.findViewById(R.id.Question2Edit);
        rl_QuestionCross = (RelativeLayout)view.findViewById(R.id.rl_QuestionCross);

        rl_QuestionCross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();

            }
        });

        adapter = new ArrayAdapter<String>(getActivity(),R.layout.custom_text,gender);
        Question2Edit.setAdapter(adapter);

        /*continueNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WhatToBuyFragment3 nextFrag= new WhatToBuyFragment3();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fl_forFragment, nextFrag,"findThisFragment")
                        .addToBackStack(null)
                        .commit();

            }
        });*/

        // Inflate the layout for this fragment
        return view;    }

    public String getText(){

        return Question2Edit.getSelectedItem().toString();
    }




}
