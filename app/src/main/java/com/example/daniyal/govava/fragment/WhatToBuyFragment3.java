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


public class WhatToBuyFragment3 extends Fragment {


    ImageView continueNext,continueback;
    RelativeLayout rl_QuestionCross;
    public ArrayAdapter<String> adapter;
    public Spinner Question3Edit;
    public String[] range = {
            "$2-25",
            "$30-40",
            "$45-50",
            "$75-100",
            "More"

    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_question_3,
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



        Question3Edit = (Spinner) view.findViewById(R.id.Question3Edit);
        adapter = new ArrayAdapter<String>(getActivity(),R.layout.custom_text,range);
        Question3Edit.setAdapter(adapter);
        rl_QuestionCross = (RelativeLayout) view.findViewById(R.id.rl_QuestionCross);

        rl_QuestionCross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //getActivity().getFragmentManager().popBackStack();
                getActivity().onBackPressed();

            }
        });

       /* continueNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WhatToBuyFragment4 nextFrag= new WhatToBuyFragment4();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fl_forFragment, nextFrag,"findThisFragment")
                        .addToBackStack(null)
                        .commit();
            }
        });*/

        // Inflate the layout for this fragment
        return view;
    }

    public String getText(){

        return Question3Edit.getSelectedItem().toString();
    }


}
