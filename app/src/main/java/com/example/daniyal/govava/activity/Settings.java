package com.example.daniyal.govava.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.daniyal.govava.R;
import com.example.daniyal.govava.Utils.Recent_S_SharedPref;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class Settings extends BaseActivity {

    private FirebaseAuth mAuth;

    RelativeLayout rl_logOut,edit_profile;
    ProgressDialog progressDialog;
    public LinearLayout ll_back;
    public RelativeLayout clear_search;
    AlertDialog ad ;
    public ArrayList<String> stringArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        setupComponents();
        clear_search = (RelativeLayout)findViewById(R.id.clear_allsearch);
        stringArrayList = new ArrayList<>();
        ll_back = (LinearLayout)findViewById(R.id.ll_back);
        edit_profile = (RelativeLayout)findViewById(R.id.edit_profile);
        edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Settings.this,EditProfile.class));

            }
        });

        ll_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        rl_logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                progressDialog.setMessage("Please wait...");
                progressDialog.show();


                FirebaseAuth.getInstance().signOut();
                progressDialog.hide();
                //Toast.makeText(Settings.this, "Logout Successfully!", Toast.LENGTH_SHORT).show();
              //  startActivity(new Intent(Settings.this,LoginActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK));
                finish();
            }
        });

        clear_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater layoutInflater = LayoutInflater.from(Settings.this);
                View view1 = layoutInflater.inflate(R.layout.clear_dialog,null);
                TextView cancel_search = (TextView)view1.findViewById(R.id.cancel_search);
                TextView ok_clear = (TextView)view1.findViewById(R.id.ok_clear);
                final RadioButton check_yes = (RadioButton)view1.findViewById(R.id.check_yes);
                final RadioButton check_no = (RadioButton)view1.findViewById(R.id.check_no);
                AlertDialog.Builder alert  = new AlertDialog.Builder(Settings.this);
                alert.setTitle("Do you want to clear recent \n searches?");
                cancel_search.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ad.dismiss();
                    }
                });
                ok_clear.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (check_yes.isChecked()) {
                            stringArrayList.clear();
                            Recent_S_SharedPref.setRecentSearch(Settings.this, stringArrayList, "");
                            ad.dismiss();

                        }else if(check_no.isChecked()){
                            ad.dismiss();
                        }
                    }
                });
                alert.setView(view1);
                alert.create();
                alert.setCancelable(false);
                ad = alert.show();
            }
        });

    }

    @Override
    public void initializeComponents() {
        mAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(this);

        rl_logOut = (RelativeLayout)findViewById(R.id.rl_logOut);
    }

    @Override
    public void setupListeners() {

    }
}
