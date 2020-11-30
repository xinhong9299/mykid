package com.example.databasewithimageanddate;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.databasewithimageanddate.DataModel.DataConverter;
import com.example.databasewithimageanddate.DataModel.ShowUsersActivity;
import com.example.databasewithimageanddate.DataModel.User;
import com.example.databasewithimageanddate.DataModel.UserDAO;
import com.example.databasewithimageanddate.DataModel.UserDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class MainActivity extends AppCompatActivity {

    ImageView imageView;
    Bitmap bmpImage;
    EditText name, uName, pas, dob;

    UserDAO userDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.userImage);
        bmpImage = null;

        name = findViewById(R.id.fullName);
        uName = findViewById(R.id.userName);
        pas = findViewById(R.id.userPassword);
        dob = findViewById(R.id.userDob);

        userDAO = UserDatabase.getDBInstance(this).userDao();
    }

    // Can receive the image that taken by the camera into our program
    final int CAMERA_INTENT = 51;

    // Calling the camera inside our phone
    public void takePicture(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(intent.resolveActivity(getPackageManager()) != null){
            startActivityForResult(intent, CAMERA_INTENT);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case CAMERA_INTENT:
    //            if (requestCode == Activity.RESULT_OK){
                    bmpImage = (Bitmap) data.getExtras().get("data"); //converting into bitmap
                    if(bmpImage != null){
                        imageView.setImageBitmap(bmpImage); // put the bitmap into our image
                    }else{
                        Toast.makeText(
                                this,
                                "Bitmap is NULL",
                                Toast.LENGTH_SHORT
                        ).show();
                    }
//                }else{
//                    Toast.makeText(
//                            this,
//                            "Result not OK",
//                            Toast.LENGTH_SHORT
//                    ).show();
//                }
                break;
        }
    }

    public void saveUser(View view) {
        // if one of  the details is missing will show the text out
        if(name.getText().toString().isEmpty() || uName.getText().toString().isEmpty()
                || pas.getText().toString().isEmpty() || dob.getText().toString().isEmpty()
                || bmpImage == null){
            Toast.makeText(
                    this,
                    "user Data is missing",
                    Toast.LENGTH_SHORT
            ).show();
        }else{
            User user = new User(); // if user all information are there
            user.setFullName(name.getText().toString());
            user.setUserName(uName.getText().toString());
            user.setPassword(pas.getText().toString());
            user.setImage(DataConverter.convertImage2ByteArray(bmpImage));
            try {
                user.setDob(new SimpleDateFormat("dd/MM/yyyy").parse(dob.getText().toString()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            userDAO.insertUser(user);
            Toast.makeText(
                    this,
                    "Insertion successful",
                    Toast.LENGTH_SHORT
            ).show();
        }
    }

    public void showUsers(View view) {
        Intent intent = new Intent(this, ShowUsersActivity.class);
        startActivity(intent);
    }
}