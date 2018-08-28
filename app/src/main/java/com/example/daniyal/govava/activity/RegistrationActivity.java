package com.example.daniyal.govava.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.daniyal.govava.R;
import com.example.daniyal.govava.models.User_Info;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

public class RegistrationActivity extends Activity {

    TextView RegName , RegEmail , RegSign , RegHaveAccount;
    EditText FullName , EmailAddress ,Password ,ConfirmPassword ,Adress ,PhoneNumber;
    ImageView RegImgSign;
    public ImageView upload_image;
    private FirebaseAuth mAuth;
    public Uri mCapturedImageURI;
    DatabaseReference databaseReference;
    private Uri selectedImageUri;
    public CircleImageView user_image;
    ProgressDialog progressDialog;
    private static final String TAG = "";
    private StorageReference rootStorageRef, imageRef, folderRef, fileStorageRef;
    public ProgressDialog mProgressDialog;
    public User_Info user_info;
    public String image_Url = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
      //  RegName = (TextView) findViewById(R.id.RegName);
      //  RegEmail = (TextView) findViewById(R.id.RegEmail);
        RegSign = (TextView) findViewById(R.id.RegSign);
        RegHaveAccount = (TextView) findViewById(R.id.RegHaveAccount);
        RegImgSign = (ImageView) findViewById(R.id.RegImgSign);
        FullName = (EditText) findViewById(R.id.FullName);
        EmailAddress = (EditText) findViewById(R.id.EmailAddress);
        Password = (EditText) findViewById(R.id.Password);
        ConfirmPassword = (EditText) findViewById(R.id.ConfirmPassword);
        Adress = (EditText) findViewById(R.id.Adress);
        PhoneNumber = (EditText) findViewById(R.id.PhoneNumber);
        mAuth = FirebaseAuth.getInstance();
        user_image = (CircleImageView)findViewById(R.id.user_image);
        upload_image = (ImageView)findViewById(R.id.upload_image);
        rootStorageRef = FirebaseStorage.getInstance().getReference();
        RegHaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegistrationActivity.this,LoginActivity.class));
            }
        });

        upload_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    builder = new AlertDialog.Builder(RegistrationActivity.this, android.R.style.Theme_Material_Dialog_Alert);
                } else {
                    builder = new AlertDialog.Builder(RegistrationActivity.this);
                }
                builder.setTitle("Select Photo Method")
                        .setPositiveButton("Camera", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                openCamera();
                            }
                        })
                        .setNegativeButton("Gallery", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                openGallery();
                            }
                        })
                        .setIcon(android.R.drawable.ic_menu_camera)
                        .show();
            }
        });


        progressDialog = new ProgressDialog(this);


        Typeface typeface3 = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Medium.ttf");

     //   RegName.setTypeface(typeface3);
     //   RegEmail.setTypeface(typeface3);
        RegSign.setTypeface(typeface3);
        RegHaveAccount.setTypeface(typeface3);
        FullName.setTypeface(typeface3);
        EmailAddress.setTypeface(typeface3);
        Password.setTypeface(typeface3);
        ConfirmPassword.setTypeface(typeface3);
        Adress.setTypeface(typeface3);
        PhoneNumber.setTypeface(typeface3);

        RegImgSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(FullName.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "Enter FullName!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(EmailAddress.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "Enter EmailAddress!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(Password.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "Enter Password!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(Adress.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "Enter Adress!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(PhoneNumber.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "Enter PhoneNumber!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(ConfirmPassword.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "Enter Confrim Password!", Toast.LENGTH_SHORT).show();
                    return;
                }


                if(!Password.getText().toString().equals(ConfirmPassword.getText().toString()))
                {
                    Toast.makeText(getApplicationContext(), "Password not match", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(image_Url.equals("")){
                    Toast.makeText(getApplicationContext(), "Please upload image", Toast.LENGTH_SHORT).show();
                    return;
                }

                createAccount(EmailAddress.getText().toString(),Password.getText().toString());

            }
        });


    }

    public void openCamera() {
        if (ContextCompat.checkSelfPermission(RegistrationActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(RegistrationActivity.this,
                android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(RegistrationActivity.this,
                    new String[]{
                            android.Manifest.permission.CAMERA, android.Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    2);
        } else {
            if (Build.VERSION.SDK_INT > 20) {
                File imagesFolder = new File(Environment.getExternalStorageDirectory(), "Images");
                imagesFolder.mkdirs();
                File image = new File(imagesFolder.getPath(), "MyImage_.jpg");
                String fileName = "temp.jpg";
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.TITLE, image.getAbsolutePath());
                mCapturedImageURI = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            }


            Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);

            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, mCapturedImageURI);
            startActivityForResult(cameraIntent, 1);

        }
    }

    public void openGallery() {
        if (ContextCompat.checkSelfPermission(RegistrationActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(RegistrationActivity.this,
                android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(RegistrationActivity.this,
                    new String[]{
                            android.Manifest.permission.CAMERA, android.Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    2);
        } else {


            Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(i, 4);


        }
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //updateUI(currentUser);
    }

    public void createAccount(String email,String password)
    {
        progressDialog.setMessage("Please wait...");
        progressDialog.show();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.hide();

                        if (task.isSuccessful()) {


                            // Sign in success, update UI with the signed-in user's information
                            Log.d("", "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            String uid = user.getUid();
                            //task.getResult().getUser().getUid();
                            Log.d("register", "uid : "+uid);

                            FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                            databaseReference = FirebaseDatabase.getInstance().getReference("users");

                            //user_info.uid = databaseReference.push().getKey();

                            User_Info user_info = new User_Info(FirebaseAuth.getInstance().getUid(),FullName.getText().toString(),EmailAddress.getText().toString(),
                                    Password.getText().toString(),Adress.getText().toString(),PhoneNumber.getText().toString(),image_Url
                            );


                            databaseReference.child(uid).setValue(user_info);
                            Log.d("98","name : "+user_info.getFullName());



                            Toast.makeText(RegistrationActivity.this, "Registered Successfully",
                                    Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(RegistrationActivity.this , HomeScreen.class);
                            startActivity(intent);
                            //updateUI(user);
                        } else {
                          //  String errorCode = ((FirebaseAuthException) task.getException()).getErrorCode();
                            Toast.makeText(RegistrationActivity.this, task.getException()+"", Toast.LENGTH_LONG).show();

//                            switch (errorCode) {
//
//                                case "ERROR_INVALID_CUSTOM_TOKEN":
//                                    Toast.makeText(RegistrationActivity.this, "The custom token format is incorrect. Please check the documentation.", Toast.LENGTH_LONG).show();
//                                    break;
//
//                                case "ERROR_CUSTOM_TOKEN_MISMATCH":
//                                    Toast.makeText(RegistrationActivity.this, "The custom token corresponds to a different audience.", Toast.LENGTH_LONG).show();
//                                    break;
//
//                                case "ERROR_INVALID_CREDENTIAL":
//                                    Toast.makeText(RegistrationActivity.this, "The supplied auth credential is malformed or has expired.", Toast.LENGTH_LONG).show();
//                                    break;
//
//                                case "ERROR_INVALID_EMAIL":
//                                    Toast.makeText(RegistrationActivity.this, "The email address is badly formatted.", Toast.LENGTH_LONG).show();
//                                    break;
//
//                                case "ERROR_WRONG_PASSWORD":
//                                    Toast.makeText(RegistrationActivity.this, "The password is invalid or the user does not have a password.", Toast.LENGTH_LONG).show();
//                                    break;
//
//                                case "ERROR_USER_MISMATCH":
//                                    Toast.makeText(RegistrationActivity.this, "The supplied credentials do not correspond to the previously signed in user.", Toast.LENGTH_LONG).show();
//                                    break;
//
//                                case "ERROR_REQUIRES_RECENT_LOGIN":
//                                    Toast.makeText(RegistrationActivity.this, "This operation is sensitive and requires recent authentication. Log in again before retrying this request.", Toast.LENGTH_LONG).show();
//                                    break;
//
//                                case "ERROR_ACCOUNT_EXISTS_WITH_DIFFERENT_CREDENTIAL":
//                                    Toast.makeText(RegistrationActivity.this, "An account already exists with the same email address but different sign-in credentials. Sign in using a provider associated with this email address.", Toast.LENGTH_LONG).show();
//                                    break;
//
//                                case "ERROR_EMAIL_ALREADY_IN_USE":
//                                    Toast.makeText(RegistrationActivity.this, "The email address is already in use by another account.   ", Toast.LENGTH_LONG).show();
//                                    break;
//
//                                case "ERROR_CREDENTIAL_ALREADY_IN_USE":
//                                    Toast.makeText(RegistrationActivity.this, "This credential is already associated with a different user account.", Toast.LENGTH_LONG).show();
//                                    break;
//
//                                case "ERROR_USER_DISABLED":
//                                    Toast.makeText(RegistrationActivity.this, "The user account has been disabled by an administrator.", Toast.LENGTH_LONG).show();
//                                    break;
//
//                                case "ERROR_USER_TOKEN_EXPIRED":
//                                    Toast.makeText(RegistrationActivity.this, "The user\\'s credential is no longer valid. The user must sign in again.", Toast.LENGTH_LONG).show();
//                                    break;
//
//                                case "ERROR_USER_NOT_FOUND":
//                                    Toast.makeText(RegistrationActivity.this, "There is no user record corresponding to this identifier. The user may have been deleted.", Toast.LENGTH_LONG).show();
//                                    break;
//
//                                case "ERROR_INVALID_USER_TOKEN":
//                                    Toast.makeText(RegistrationActivity.this, "The user\\'s credential is no longer valid. The user must sign in again.", Toast.LENGTH_LONG).show();
//                                    break;
//
//                                case "ERROR_OPERATION_NOT_ALLOWED":
//                                    Toast.makeText(RegistrationActivity.this, "This operation is not allowed. You must enable this service in the console.", Toast.LENGTH_LONG).show();
//                                    break;
//
//                                case "ERROR_WEAK_PASSWORD":
//                                    Toast.makeText(RegistrationActivity.this, "The given password is invalid.", Toast.LENGTH_LONG).show();
//                                    break;
//
//                            }

                            // If sign in fails, display a message to the user.
                            /*Log.w("", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(RegistrationActivity.this, "Registration failed.",
                                    Toast.LENGTH_SHORT).show();*/
                            //updateUI(null);
                        }

                        // ...
                    }
                });
    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == this.RESULT_OK) {

            //for meal camera image
            if (requestCode == 1) {
                try {
                    selectedImageUri = data.getData();
                    Log.d("ImageStatus", selectedImageUri.toString());

                } catch (Exception e) {
                    Log.d("Exception", e.getMessage());
                }
                if (resultCode == Activity.RESULT_CANCELED) {
                    //Write your code if there's no result
                }
                Glide.with(RegistrationActivity.this)
                        .load(selectedImageUri)
                        .asBitmap()
                        .into(user_image);

                uploadImage(selectedImageUri);

            } else if (requestCode == 4) {
                if (data != null) {
                    Uri photo = data.getData();
                    selectedImageUri = photo;

                } else {

                    selectedImageUri = mCapturedImageURI;
                }

                Glide.with(RegistrationActivity.this)
                        .load(selectedImageUri)
                        .asBitmap()
                        .into(user_image);

                uploadImage(selectedImageUri);
            } else {
                Toast.makeText(RegistrationActivity.this, "Nothing Selected !", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void uploadImage(Uri imgPath) {
        try {
            //   File fileRef = new File(imgPath);
            //   if (meal.equals("mealcamera") || meal.equals("mealgallery")) {
            //      folderRef = rootStorageRef.child("meal_image");
            //   } else if (meal.equals("recipecamera") || meal.equals("recipegallery")) {
            //       folderRef = rootStorageRef.child("recipe_image");
            //   } else if (meal.equals("restcamera") || meal.equals("restgallery")) {
            folderRef = rootStorageRef.child("owner_image");
            //   }

            final Date date = new Date(System.currentTimeMillis());
            //   final String filenew = fileRef.getName();
            //    AppLogs.d("fileNewName", filenew);
            //    int dot = filenew.lastIndexOf('.');
            //     String base = (dot == -1) ? filenew : filenew.substring(0, dot);
            //     final String extension = (dot == -1) ? "" : filenew.substring(dot + 1);
            //     AppLogs.d("extensionsss", extension);
            mProgressDialog = ProgressDialog.show(RegistrationActivity.this, "Uploading Image", "loading...", true, false);
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            UploadTask uploadTask;
            imageRef = folderRef.child(String.valueOf(date) + ".png");

            //   imgPath.setDrawingCacheEnabled(true);
            //   imgPath.buildDrawingCache();
            // //   Bitmap b = imgPath.getDrawingCache();
            //     ByteArrayOutputStream baos = new ByteArrayOutputStream();
            //     b.compress(Bitmap.CompressFormat.PNG, 100, baos);
            //    byte[] bytes = baos.toByteArray();
            //  UploadTask uploadTask = ref.child(id + ".png").putBytes(bytes);
            //  Uri file = Uri.fromFile(new File(imgPath.toString()));
            final File file = new File(imgPath.getPath());

            uploadTask = imageRef.putFile(imgPath);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle unsuccessful uploads
                    Toast.makeText(RegistrationActivity.this, "UPLOAD FAILED", Toast.LENGTH_LONG).show();
                    mProgressDialog.dismiss();
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(final UploadTask.TaskSnapshot taskSnapshot) {
                    // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                    String downloadUrl = taskSnapshot.getDownloadUrl().toString();
                    image_Url = downloadUrl;
                    Log.e("Image ka URL", "" + downloadUrl);
                    //   if (meal.equals("mealcamera") || meal.equals("mealgallery")) {
                    //      meal_picture = downloadUrl;
                    //   } else if (meal.equals("recipecamera") || meal.equals("recipegallery")) {
                    //       recipe_picture = downloadUrl;
                    //  }  if (meal.equals("restcamera") || meal.equals("restgallery")) {

//                    databaseReference.child("owner").child(user.getUid())
//                            .child("picture").setValue(downloadUrl);
//                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
//                            .setDisplayName(user.firstName + " " + user.lastName)
//                            .setPhotoUri(Uri.parse(downloadUrl))
//                            .build();
//                    FirebaseAuth.getInstance().getCurrentUser().updateProfile(profileUpdates)
//                            .addOnCompleteListener(new OnCompleteListener<Void>() {
//                                @Override
//                                public void onComplete(@NonNull Task<Void> task) {
//                                    if (task.isSuccessful()) {
//                                        mProgressDialog.dismiss();
//                                        //       Intent intent = new Intent(SignupActivity_Customer.this,
//                                        //              SearchRestaurantsActivity.class);
//                                        //     startActivity(intent);
//                                    }
//                                }
//                            });

                    mProgressDialog.dismiss();
                    //  }
                    //    imageUrl = downloadUrl;

                    Toast.makeText(RegistrationActivity.this, "UPLOAD SUCCESS", Toast.LENGTH_LONG).show();


                }
            });
        } catch (Exception e) {
            mProgressDialog.dismiss();
            Toast.makeText(RegistrationActivity.this, e.getMessage()+"", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

}
