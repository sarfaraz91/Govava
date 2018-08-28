package com.example.daniyal.govava.activity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Daniyal on 10/6/2017.
 */

public class SplashActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    FirebaseAuth firebaseAuth;
    private final int SPLASH_DISPLAY_LENGTH = 2000;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();

        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.example.daniyal.govava",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));

                //74:B8:EF:A4:C7:CD:FD:37:5D:B4:93:84:D9:D4:74:C3:2E:49:A4:75

                byte[] sha1 = {
                        0x74, (byte)0xB8, (byte)0xEF, (byte)0xA4, (byte)0xC7, (byte)0xCD, (byte)0xFD, (byte)0x37, (byte)0x5D, (byte)0xB4, (byte)0x93, (byte)0x84, (byte)0xD9, (byte)0xD4, 0x74, (byte)0xC3, 0x2E, 0x49, (byte)0xA4, (byte)0x75
                };
                System.out.println("keyhashGooglePlaySignIn:"+ Base64.encodeToString(sha1, Base64.NO_WRAP));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }


        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull final FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() != null) {
                        new Handler().postDelayed(new Runnable() {
                           @Override
                           public void run() {
                    Intent intent = new Intent(SplashActivity.this, HomeScreen.class);
                    startActivity(intent);
                    finish();

                          }
                      }, SPLASH_DISPLAY_LENGTH);

                }else{

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }, SPLASH_DISPLAY_LENGTH);
                }
            }
        });

    }

  /*  @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if(currentUser != null)
        {
startActivity(new Intent(SplashActivity.this,HomeScreen.class));        }

        //startActivity(new Intent(LoginActivity.this,HomeScreen.class));
    }
*/

}
