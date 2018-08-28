package com.example.daniyal.govava.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.daniyal.govava.R;
import com.example.daniyal.govava.models.User_Info;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookAuthorizationException;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class LoginActivity extends BaseActivity {

    TextView t1 , t2 , t3 , t4 ,t5;
    EditText et_userName , et_passwrd;
    TextView tv_GoToRegistation;
    ImageView logo_fb,img_gmail;
    Button sigin_btn;
    private static final String TAG = "login";

    private FirebaseAuth mAuth;
    ProgressDialog progressDialog;

    CallbackManager mCallbackManager;

    GoogleSignInClient mGoogleSignInClient;

    String uid;

    DatabaseReference databaseReference;
    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setupComponents();
        initializeComponents();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        /*mAuth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                if(firebaseAuth.getCurrentUser().getUid() != null)
                {
                    startActivity(new Intent(LoginActivity.this,HomeScreen.class));
                }
            }
        });
*/

        img_gmail = (ImageView)findViewById(R.id.img_gmail);

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        //SignInButton signInButton = (SignInButton) findViewById(R.id.sign_in_button);
        img_gmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.setMessage("Please wait...");
                progressDialog.show();
                signInGmail();

            }
        });


        mCallbackManager = CallbackManager.Factory.create();

        logo_fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "btn clicked");
                progressDialog.setMessage("Please wait...");
                progressDialog.show();
                LoginManager.getInstance().logInWithReadPermissions(LoginActivity.this, Arrays.asList("email", "public_profile"));
                LoginManager.getInstance().registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {

                    @Override
                    public void onSuccess(LoginResult loginResult) {

                        System.out.println("onSuccess");
                        String accessToken = loginResult.getAccessToken().getToken();
                        handleFacebookAccessToken(loginResult.getAccessToken());
                        Log.i(TAG,"accessToken :" +accessToken);


                    }

                    @Override
                    public void onCancel() {
                        System.out.println("onCancel");
                       // progressDialog.hide();

                    }

                    @Override
                    public void onError(FacebookException e) {
                        Log.d(TAG,"FacebookException : "+e);

                        if (e instanceof FacebookAuthorizationException) {
                            if (AccessToken.getCurrentAccessToken() != null) {
                                LoginManager.getInstance().logOut();
                            }
                        }
                    }
                });

            }
        });

        tv_GoToRegistation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(LoginActivity.this , RegistrationActivity.class);
                startActivity(intent);


            }
        });


        sigin_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //isConnected(getApplicationContext());

                if (TextUtils.isEmpty(et_userName.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(et_passwrd.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }
                Log.d(TAG,"listener reached");

                signIn(et_userName.getText().toString(),et_passwrd.getText().toString());
/*
                startActivity(new Intent(LoginActivity.this,HomeScreen.class));
*/

            }
        });

        PackageInfo info;

        try {
            info = getPackageManager().getPackageInfo("com.example.daniyal.govava", PackageManager.GET_SIGNATURES);
            Log.e(TAG, "succes key1 ");

            for (Signature signature : info.signatures) {
                MessageDigest md;
                md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.e(TAG, "succes key2 ");

                String something = new String(Base64.encode(md.digest(), 0));
                //String something = new String(Base64.encodeBytes(md.digest()));
                Log.e(TAG, "succes key3 ");

                Log.e(TAG, something);
            }
        } catch (PackageManager.NameNotFoundException e1) {
            Log.e("name not found", e1.toString());
        } catch (NoSuchAlgorithmException e) {
            Log.e("no such an algorithm", e.toString());
        } catch (Exception e) {
            Log.e("exception", e.toString());
        }

    }

    private void signInGmail() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, 10);

    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
       /* if(currentUser != null)
        {
            updateUI();
        }*/
    }

   /* @Override
    public void onDestroy() {
        super.onDestroy();
        if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }
*/
    public void updateUI() {

        startActivity(new Intent(getApplicationContext(),HomeScreen.class));
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Pass the activity result back to the Facebook SDK
        mCallbackManager.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == 10) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
                // ...
            }
        }

    }

    private void firebaseAuthWithGoogle(final GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                          //  startActivity(new Intent(LoginActivity.this,HomeScreen.class));
                            progressDialog.hide();

                            if (acct != null) {
                                String personName = acct.getDisplayName();
                                String personGivenName = acct.getGivenName();
                                String personFamilyName = acct.getFamilyName();
                                String personEmail = acct.getEmail();
                                String personId = acct.getId();
                                Uri personPhoto = acct.getPhotoUrl();
                                User_Info user_info;
                                if(personPhoto != null)
                                {
                                    user_info = new User_Info(personName,personEmail,personPhoto.toString());

                                }
                                else{

                                    user_info = new User_Info(personName,personEmail,"");

                                }


                                Log.d(TAG,"uid "+uid);
                                databaseReference.child("users").child(user.getUid()).setValue(user_info);

                            }

                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            //Snackbar.make(findViewById(R.id.main_layout), "Authentication Failed.", Snackbar.LENGTH_SHORT).show();
                            //updateUI(null);
                        }

                        // ...
                    }
                });
    }


    private User_Info getFacebookData(JSONObject object) {

        try {
            Bundle bundle = new Bundle();
            String id = object.getString("id");
            URL profile_pic;
            try {
                profile_pic = new URL("https://graph.facebook.com/" + id + "/picture?width=200&height=150");
                Log.i("profile_pic", profile_pic + "");
                //bundle.putString("profile_pic", profile_pic.toString());

            } catch (MalformedURLException e) {
                e.printStackTrace();
                return null;
            }
            User_Info user_info = new User_Info(object.getString("first_name"),object.getString("email"),profile_pic.toString());
            //  databaseReference.child("users").child(uid).setValue(bFacebookData);


           /* bundle.putString("idFacebook", id);
            if (object.has("first_name"))
                bundle.putString("first_name", object.getString("first_name"));
            if (object.has("last_name"))
                bundle.putString("last_name", object.getString("last_name"));
            if (object.has("email"))
                bundle.putString("email", object.getString("email"));
            if (object.has("gender"))
                bundle.putString("gender", object.getString("gender"));
            if (object.has("birthday"))
                bundle.putString("birthday", object.getString("birthday"));
            if (object.has("location"))
                bundle.putString("location", object.getJSONObject("location").getString("name"));*/

            return user_info;
        }
        catch(JSONException e) {
            Log.d(TAG,"Error parsing JSON");
        }
        return null;
    }


    private void handleFacebookAccessToken(final AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);

    /*    progressDialog.setMessage("Please wait...");
        progressDialog.show();*/

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                       // progressDialog.hide();

                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            firebaseUser = mAuth.getCurrentUser();

                            GraphRequest request = GraphRequest.newMeRequest(token, new GraphRequest.GraphJSONObjectCallback() {

                                @Override
                                public void onCompleted(JSONObject object, GraphResponse response) {
                                    Log.d(TAG,"rsponse : "+ response.toString());
                                    // Get facebook data from login
                                    User_Info bFacebookData = getFacebookData(object);
                                    databaseReference.child("users").child(firebaseUser.getUid()).setValue(bFacebookData);
                                    //Log.d(TAG,"getFacebookData : "+ bFacebookData.getFullName());

                                }
                            });
                            Bundle parameters = new Bundle();
                            parameters.putString("fields", "id, first_name, last_name, email,gender, birthday, location"); // Parámetros que pedimos a facebook
                            request.setParameters(parameters);
                            request.executeAsync();

                         //   Intent intent = new Intent(LoginActivity.this,HomeScreen.class);
                        //    startActivity(intent);
                            progressDialog.hide();

                            Log.d(TAG,"home intent");

                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }

                        // ...
                    }
                });
    }



    @Override
    public void initializeComponents() {

        progressDialog = new ProgressDialog(this);

        logo_fb = (ImageView)findViewById(R.id.logo_fb);

        et_userName = (EditText) findViewById(R.id.et_userName);
        et_passwrd = (EditText) findViewById(R.id.et_passwrd);
        sigin_btn = (Button) findViewById(R.id.sigin_btn);

        tv_GoToRegistation = (TextView) findViewById(R.id.tv_GoToRegistation);
     /*   t1 = (TextView) findViewById(R.id.HeadingSgn);
        t2 = (TextView) findViewById(R.id.Signup);
        t4 = (TextView) findViewById(R.id.orSigninWith);
        t5 = (TextView) findViewById(R.id.ForgotPassword);



*/
    }

    public boolean isConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobileInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if ((wifiInfo != null && wifiInfo.isConnected()) || (mobileInfo != null && mobileInfo.isConnected())) {
            return true;
        } else {
            showDialog();
            return false;
        }
    }
    private void showDialog()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Connect to wifi or quit")
                .setCancelable(false)
                .setPositiveButton("Connect to WIFI", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                    }
                })
                .setNegativeButton("Quit", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    public void signIn(String email, String password)
    {
        progressDialog.setMessage("Please wait...");
        progressDialog.show();

        mAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        progressDialog.hide();
                        Log.d(TAG,"singIn reached");

                        if (task.isSuccessful()) {
                            Log.d(TAG,"login succeed");

                            // Sign in success, update UI with the signed-in user's information
                            Log.d("", "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            String firebaseUid = user.getUid();

                         //   startActivity(new Intent(LoginActivity.this,HomeScreen.class).putExtra("firebaseUid",firebaseUid));
                            //updateUI(user);
                        } else {
                            Log.d(TAG,"login failed");
                            Toast.makeText(LoginActivity.this, task.getException().getMessage()+"", Toast.LENGTH_LONG).show();


//                            switch (errorCode) {
//
//                                case "ERROR_INVALID_CUSTOM_TOKEN":
//                                    Toast.makeText(LoginActivity.this, "The custom token format is incorrect. Please check the documentation.", Toast.LENGTH_LONG).show();
//                                    break;
//
//                                case "ERROR_CUSTOM_TOKEN_MISMATCH":
//                                    Toast.makeText(LoginActivity.this, "The custom token corresponds to a different audience.", Toast.LENGTH_LONG).show();
//                                    break;
//
//                                case "ERROR_INVALID_CREDENTIAL":
//                                    Toast.makeText(LoginActivity.this, "The supplied auth credential is malformed or has expired.", Toast.LENGTH_LONG).show();
//                                    break;
//
//                                case "ERROR_INVALID_EMAIL":
//                                    Toast.makeText(LoginActivity.this, "The email address is badly formatted.", Toast.LENGTH_LONG).show();
//                                    break;
//
//                                case "ERROR_WRONG_PASSWORD":
//                                    Toast.makeText(LoginActivity.this, "The password is invalid or the user does not have a password.", Toast.LENGTH_LONG).show();
//                                    break;
//
//                                case "ERROR_USER_MISMATCH":
//                                    Toast.makeText(LoginActivity.this, "The supplied credentials do not correspond to the previously signed in user.", Toast.LENGTH_LONG).show();
//                                    break;
//
//                                case "ERROR_REQUIRES_RECENT_LOGIN":
//                                    Toast.makeText(LoginActivity.this, "This operation is sensitive and requires recent authentication. Log in again before retrying this request.", Toast.LENGTH_LONG).show();
//                                    break;
//
//                                case "ERROR_ACCOUNT_EXISTS_WITH_DIFFERENT_CREDENTIAL":
//                                    Toast.makeText(LoginActivity.this, "An account already exists with the same email address but different sign-in credentials. Sign in using a provider associated with this email address.", Toast.LENGTH_LONG).show();
//                                    break;
//
//                                case "ERROR_EMAIL_ALREADY_IN_USE":
//                                    Toast.makeText(LoginActivity.this, "The email address is already in use by another account.   ", Toast.LENGTH_LONG).show();
//                                    break;
//
//                                case "ERROR_CREDENTIAL_ALREADY_IN_USE":
//                                    Toast.makeText(LoginActivity.this, "This credential is already associated with a different user account.", Toast.LENGTH_LONG).show();
//                                    break;
//
//                                case "ERROR_USER_DISABLED":
//                                    Toast.makeText(LoginActivity.this, "The user account has been disabled by an administrator.", Toast.LENGTH_LONG).show();
//                                    break;
//
//                                case "ERROR_USER_TOKEN_EXPIRED":
//                                    Toast.makeText(LoginActivity.this, "The user\\'s credential is no longer valid. The user must sign in again.", Toast.LENGTH_LONG).show();
//                                    break;
//
//                                case "ERROR_USER_NOT_FOUND":
//                                    Toast.makeText(LoginActivity.this, "There is no user record corresponding to this identifier. The user may have been deleted.", Toast.LENGTH_LONG).show();
//                                    break;
//
//                                case "ERROR_INVALID_USER_TOKEN":
//                                    Toast.makeText(LoginActivity.this, "The user\\'s credential is no longer valid. The user must sign in again.", Toast.LENGTH_LONG).show();
//                                    break;
//
//                                case "ERROR_OPERATION_NOT_ALLOWED":
//                                    Toast.makeText(LoginActivity.this, "This operation is not allowed. You must enable this service in the console.", Toast.LENGTH_LONG).show();
//                                    break;
//
//                                case "ERROR_WEAK_PASSWORD":
//                                    Toast.makeText(LoginActivity.this, "The given password is invalid.", Toast.LENGTH_LONG).show();
//                                    break;
//
//                            }
                            /*// If sign in fails, display a message to the user.
                            Log.w("", "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Login failed.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);*/
                        }

                        // ...
                    }
                });
    }
    @Override
    public void setupListeners() {


  /*      Typeface typeface3 = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Medium.ttf");

        t1.setTypeface(typeface3);
        t2.setTypeface(typeface3);
//        t3.setTypeface(typeface3);
        t4.setTypeface(typeface3);
        t5.setTypeface(typeface3);
        et_userName.setTypeface(typeface3);
        et_passwrd.setTypeface(typeface3);

        GoToRegistration.setTypeface(typeface3);
*/

    }
}
