package com.example.daniyal.govava.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.daniyal.govava.R;
import com.example.daniyal.govava.models.User_Info;
import com.example.daniyal.govava.Utils.CircleTransform;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditProfile extends AppCompatActivity {


    TextView UpdName , UpdEmail , UpdSign , RegHaveAccount;
    EditText FullName , EmailAddress ,Password ,ConfirmPassword ,Adress ,PhoneNumber;
   // ImageView img_profile;
    public Button UpdImgSign;
    public Uri mCapturedImageURI;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    private Uri selectedImageUri;
    FirebaseUser firebaseUser;
    ProgressDialog mProgressDialog;
    public final static String TAG = "editprofile";
    public ImageView upload_image;
    public CircleImageView img_profile;
    String getFullName,getEmail,getPassword,getAddress,getphone;
    private StorageReference rootStorageRef, imageRef, folderRef, fileStorageRef;
    public String image_Url = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        setContentView(R.layout.activity_edit_profile);
        upload_image = (ImageView)findViewById(R.id.upload_image);
        img_profile = (CircleImageView)findViewById(R.id.img_profile);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        rootStorageRef = FirebaseStorage.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        String email = firebaseUser.getEmail();

        UpdName = (TextView) findViewById(R.id.UpdName);

        UpdEmail = (TextView) findViewById(R.id.UpdEmail);
        UpdEmail.setText(firebaseUser.getEmail());

      //  UpdSign = (TextView) findViewById(R.id.UpdSign);
        RegHaveAccount = (TextView) findViewById(R.id.RegHaveAccount);
        UpdImgSign = (Button) findViewById(R.id.UpdImgSign);
        FullName = (EditText) findViewById(R.id.FullName);
        EmailAddress = (EditText) findViewById(R.id.EmailAddress);
        Password = (EditText) findViewById(R.id.Password);
        ConfirmPassword = (EditText) findViewById(R.id.ConfirmPassword);
        Adress = (EditText) findViewById(R.id.Adress);
        PhoneNumber = (EditText) findViewById(R.id.PhoneNumber);

     //   img_profile = (ImageView) findViewById(R.id.img_profile);

        databaseReference.child("users").child(firebaseUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                User_Info user = dataSnapshot.getValue(User_Info.class);

               /* getFullName = user.getFullName();
                getEmail = user.getEmail();
                getPassword = user.getPassword();
                getAddress =user.getAddress();
                getphone = user.getphone().toString();*/

                if(!TextUtils.isEmpty(user.getPhotoUrl())){
                    Glide.with(getApplicationContext()).load(user.getPhotoUrl()).crossFade()
                            .thumbnail(0.5f)
                            .bitmapTransform(new CircleTransform(getApplicationContext()))
                            .diskCacheStrategy(DiskCacheStrategy.ALL).into(img_profile);
                }else{
                    Glide.with(getApplicationContext()).load(R.mipmap.contactlist_img_four)
                            .crossFade()
                            .thumbnail(0.5f)
                            .bitmapTransform(new CircleTransform(getApplicationContext()))
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(img_profile);

                }
                image_Url = user.getPhotoUrl();
                if(!TextUtils.isEmpty(user.getFullName()))
                {
                    FullName.setText(user.getFullName());
                    UpdName.setText(user.getFullName());
                }
                else{
                    FullName.setText("");
                    UpdName.setText("");
                }
                if(!TextUtils.isEmpty(user.getEmail()))
                {
                    EmailAddress.setText(user.getEmail());
                }else{
                    EmailAddress.setText("");
                }
                if(!TextUtils.isEmpty(user.getPassword()))
                {
                    Password.setText(user.getPassword());
                    ConfirmPassword.setText(user.getPassword());
                }else{
                    Password.setText("");
                    ConfirmPassword.setText("");
                }
                if(!TextUtils.isEmpty(user.getAddress()))
                {
                    Adress.setText(user.getAddress());
                }else{
                    Adress.setText("");

                }
                if(!TextUtils.isEmpty(user.getphone()))
                {
                    PhoneNumber.setText(user.getphone());
                }else{
                    PhoneNumber.setText("");

                }


                //Log.d("", "User name: " + user.getFullName() + ", email " + user.getEmail());
                Log.d(TAG, "user_info : "+user);


            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("", "Failed to read value.", error.toException());
            }
        });

        upload_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    builder = new AlertDialog.Builder(EditProfile.this, android.R.style.Theme_Material_Dialog_Alert);
                } else {
                    builder = new AlertDialog.Builder(EditProfile.this);
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



        UpdImgSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(FullName.getText().toString().equals("")){
                    FullName.setError("Enter valid name");
                }else if(EmailAddress.getText().toString().equals("")){
                    EmailAddress.setError("Enter valid address");
                }else if(Password.getText().toString().equals("")){
                    Password.setError("Enter valid password");
                }else if(Adress.getText().toString().equals("")){
                    Adress.setError("Enter valid address");
                }else if(PhoneNumber.getText().toString().equals("")){
                    PhoneNumber.setError("Enter valid number");
                }



                User_Info user_info = new User_Info(firebaseUser.getUid(),FullName.getText().toString(),EmailAddress.getText().toString(),
                        Password.getText().toString(),Adress.getText().toString(),PhoneNumber.getText().toString(),image_Url
                );

                user_info.setPhotoUrl(image_Url);
                if(!Password.getText().toString().equals(ConfirmPassword.getText().toString()))
                {
                    Toast.makeText(getApplicationContext(), "Password not match", Toast.LENGTH_SHORT).show();
                    return;
                }

                databaseReference.child("users").child(firebaseUser.getUid()).setValue(user_info, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                        Toast.makeText(getApplicationContext(),"Profile Updated Successfully!",Toast.LENGTH_SHORT).show();
                        onBackPressed();
                    }
                });
            }
        });


    }

    public void openCamera() {
        if (ContextCompat.checkSelfPermission(EditProfile.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(EditProfile.this,
                android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(EditProfile.this,
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
        if (ContextCompat.checkSelfPermission(EditProfile.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(EditProfile.this,
                android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(EditProfile.this,
                    new String[]{
                            android.Manifest.permission.CAMERA, android.Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    2);
        } else {


            Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(i, 4);


        }
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
                Glide.with(EditProfile.this)
                        .load(selectedImageUri)
                        .asBitmap()
                        .into(img_profile);

                uploadImage(selectedImageUri);

            } else if (requestCode == 4) {
                if (data != null) {
                    Uri photo = data.getData();
                    selectedImageUri = photo;

                } else {

                    selectedImageUri = mCapturedImageURI;
                }

                Glide.with(EditProfile.this)
                        .load(selectedImageUri)
                        .asBitmap()
                        .into(img_profile);

                uploadImage(selectedImageUri);
            } else {
                Toast.makeText(EditProfile.this, "Nothing Selected !", Toast.LENGTH_LONG).show();
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
            mProgressDialog = ProgressDialog.show(EditProfile.this, "Uploading Image", "loading...", true, false);
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
                    Toast.makeText(EditProfile.this, "UPLOAD FAILED", Toast.LENGTH_LONG).show();
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

                    Toast.makeText(EditProfile.this, "UPLOAD SUCCESS", Toast.LENGTH_LONG).show();


                }
            });
        } catch (Exception e) {
//            mProgressDialog.dismiss();
            e.printStackTrace();
        }
    }

}
