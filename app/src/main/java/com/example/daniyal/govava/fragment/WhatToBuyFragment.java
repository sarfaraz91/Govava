package com.example.daniyal.govava.fragment;

import android.content.Context;
import android.media.Image;
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


public class WhatToBuyFragment extends Fragment {


    ImageView continueNext,abc;
    RelativeLayout rl_QuestionCross;
    public Spinner Question1Edit;
    public String[] old = {"0-1(Infant)",
            "2-9",
            "10-12",
            "13-15",
            "16-24",
            "25-32",
            "33-45",
            "46-55",
            "56-65",
            "65-older",
    };



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_what_to_buy,
                container, false);

        continueNext = (ImageView) view.findViewById(R.id.continueNext);
        Question1Edit = (Spinner) view.findViewById(R.id.Question1Edit);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(),R.layout.custom_text, old);
        Question1Edit.setAdapter(arrayAdapter);
        rl_QuestionCross = (RelativeLayout) view.findViewById(R.id.rl_QuestionCross);

        abc = (ImageView) view.findViewById(R.id.abc);
        abc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewPager viewPager = ((HomeFragments)getActivity()).mPager;
                viewPager.setCurrentItem(viewPager.getCurrentItem()-1);
            }
        });

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
                WhatToBuyFragment2 nextFrag= new WhatToBuyFragment2();
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

        return Question1Edit.getSelectedItem().toString();
    }

}
