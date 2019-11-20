package com.psyex.application;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;

public class FinalAct extends AppCompatActivity {

    private static final String TAG = "finalAct";
    StorageReference storageRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("users/new");

        FirebaseStorage storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();
//        myRef = myRef.

        TextView finaltext = (TextView) findViewById(R.id.textView_final_string);

//        boolean isPractice

        finaltext.setText(R.string.final_string);
        myRef.push().setValue(UserInfo.userId);
//        myRef.


        for (String i: DecideOrder.s) {
            uploadFile(i, UserInfo.userId);
        }
//        }
//        else{
//            finaltext.setText(R.string.final_string_practice);
//            finish();
//            startActivity(new Intent(this, chooseMode.class));
//        }

    }

    void uploadFile(final String fileName, String username){
        Uri file = Uri.fromFile(new File(fileName));
        StorageReference riversRef = storageRef.child(username+"/"+String.valueOf(ChooseMode.mode)+"/"+file.getLastPathSegment());
        UploadTask uploadTask = riversRef.putFile(file);

// Register observers to listen for when the download is done or if it fails
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
                Log.d(TAG, "could not save file: "+fileName);
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                // ...
            }
        });
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        new AlertDialog.Builder(this)
                .setTitle("Close Application")
                .setMessage("Are you sure you want to close the application?")

                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Continue with delete operation
                        finish();
                    }
                })

                // A null listener allows the button to dismiss the dialog and take no further action.
                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

}
