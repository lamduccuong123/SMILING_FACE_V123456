package com.example.vitinhtt2.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SignUpActivity extends AppCompatActivity {

    @BindView(R.id.btn_camera) Button _cameraButton;
    @BindView(R.id.btn_getimage) Button _getimageButton;

    @BindView(R.id.edt_email) EditText _edt_email;
    @BindView(R.id.edt_password) EditText _edt_password;
    @BindView(R.id.btn_signup) Button _btn_signup;
    @BindView(R.id.btn_back_login) Button _btn_back_login;
    @BindView(R.id.edt_name) EditText _edt_name;
    @BindView(R.id.edt_mobile) EditText _edt_mobile;
    @BindView(R.id.spn_dep) Spinner _spn_dep;
    @BindView(R.id.spn_role) Spinner _spn_role;
    @BindView(R.id.img_image) ImageView _imgImage;



    private File imageFile;
    private int PICK_IMAGE_REQUEST = 1;

    //Authentication
    private FirebaseAuth mAuth;
    // ...
    FirebaseStorage storage = FirebaseStorage.getInstance();
    // Create a storage reference from our app
    final StorageReference storageRef = storage.getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);

        mAuth = FirebaseAuth.getInstance();

        _btn_back_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenActivity();
            }
        });
        _cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,0);
            }
        });

        _getimageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                // Show only images, no videos or anything else
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                // Always show the chooser (if there are multiple options available)
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
            }
        });

        _btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(_edt_name.getText().length()==0)
                {
                    _edt_name.setError("Please enter your name!");
                } else if(_edt_email.getText().length()==0){
                        _edt_email.setError("Please enter your email!");
                        }
                        else if(_edt_password.getText().length()==0)
                            {
                                _edt_password.setError("Please enter your password!");
                            }
                            else {Register();}


            }
        });
    }
    private void Register(){
        final String email = _edt_email.getText().toString();
        String password = _edt_password.getText().toString();
        final String name = _edt_name.getText().toString();
        final String mobile = _edt_mobile.getText().toString();
        final String dep = _spn_dep.getSelectedItem().toString();
        final String role = _spn_role.getSelectedItem().toString();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            //Upload image and get link
                            Calendar calendar = Calendar.getInstance();

                            // Create a reference to "mountains.jpg"
                            StorageReference mountainsRef = storageRef.child(_edt_email.getText().toString() + ".png");

                            // Create a reference to 'images/mountains.jpg'
                            StorageReference mountainImagesRef = storageRef.child("images");

                            // While the file names are the same, the references point to different files
                            mountainsRef.getName().equals(mountainImagesRef.getName());    // true
                            mountainsRef.getPath().equals(mountainImagesRef.getPath());    // false
                            // Get the data from an ImageView as bytes
                            _imgImage.setDrawingCacheEnabled(true);
                            _imgImage.buildDrawingCache();
                            Bitmap bitmap = ((BitmapDrawable) _imgImage.getDrawable()).getBitmap();
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                            byte[] data = baos.toByteArray();

                            UploadTask uploadTask = mountainsRef.putBytes(data);

                            uploadTask.addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception exception) {
                                    // Handle unsuccessful uploads
                                    Toast.makeText(SignUpActivity.this, "Error Tien!", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                    Task<Uri> result = taskSnapshot.getMetadata().getReference().getDownloadUrl();
                                    result.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            String photoLink = uri.toString();
                                            //Toast.makeText(SignUpActivity.this, photoLink, Toast.LENGTH_SHORT).show();
                                            //we will store the additional fields in firebase database
                                            User user = new User(
                                                    name, role, dep, mobile, photoLink , email
                                            );
                                            FirebaseDatabase.getInstance().getReference("Users")
                                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if(task.isSuccessful()){
                                                        Toast.makeText(SignUpActivity.this, "Register Successfully", Toast.LENGTH_SHORT).show();
                                                        _edt_name.setText("");
                                                        _edt_name.requestFocus();
                                                        _edt_email.setText("");
                                                        _edt_mobile.setText("");
                                                        _edt_password.setText("");

                                                    }else{
                                                        Toast.makeText(SignUpActivity.this, "Failure! please check internet connection!", Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            });
                                        }
                                    });

                                    // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                                    //Toast.makeText(SignUpActivity.this, "Successfully Upload Im!", Toast.LENGTH_SHORT).show();
                                }
                            });


                        }
                        else{
                            Toast.makeText(SignUpActivity.this, "Unsuccessful!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    private void OpenActivity(){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==0){
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");//image bitmap file
            Bitmap resizeBitmap = Bitmap.createScaledBitmap(bitmap,680,500,false);
            _imgImage.setImageBitmap(resizeBitmap);
        }
        else if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null){
            Uri uri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                // Log.d(TAG, String.valueOf(bitmap));

                //ImageView imageView = (ImageView) findViewById(R.id.imageView);
                Bitmap resizeBitmap = Bitmap.createScaledBitmap(bitmap,680,500,false);
                _imgImage.setImageBitmap(resizeBitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private void UploadImage(){

    }
}
