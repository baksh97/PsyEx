package com.psyex.application;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;

public class ChooseMode extends AppCompatActivity {

    private static final String TAG = "ChooseMode";
    //    static boolean isPractice=false;
    static public int mode =1;
    static File mainFolder;// = new File(Environment.getExternalStorageDirectory() + File.separator + "PsyEx");
    static public File userfolder;
    Button mode1,mode2;

//    public static File getUserFolder(){
//        return userfolder;
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_mode);

//        Environment.getex

//        mainFolder = new File(Environment.getExternalStorageDirectory() + File.separator + "PsyEx");
        mainFolder = this.getExternalFilesDir(null);
//        mainFolder = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS) + File.separator + "PsyEx");

//        Environment.

//        Environment.
//

        Log.d(TAG, "main folder: "+mainFolder.getAbsolutePath());


        boolean success = true;
        if (!mainFolder.exists()) {
            success = mainFolder.mkdirs();
            if (success) {
//                Toast.makeText(this, "App folder created successfully!", Toast.LENGTH_SHORT).show();

            }
            else{
                success = mainFolder.mkdir();
                if(!success)
                Toast.makeText(this, "Could not create folder in local storage", Toast.LENGTH_SHORT).show();
            }
        }

//        mainFolder = new File(mainFolder,String.valueOf(mode));
//        if (!mainFolder.exists()) {
//            success = mainFolder.mkdirs();
//            if (success) {
////                Toast.makeText(this, "App folder created successfully!", Toast.LENGTH_SHORT).show();
//
//            }
//            else{
//                Toast.makeText(this, "Could not create folder in local storage", Toast.LENGTH_SHORT).show();
//            }
//        }
//        mainFolder = new
        mainFolder = new File(mainFolder,UserInfo.userId);

        Log.d(TAG, "main folder: "+mainFolder.getAbsolutePath());

//        File folder = new File(Environment.getExternalStorageDirectory() +
//                File.separator + userID);
        success = true;
        if (!mainFolder.exists()) {
            success = mainFolder.mkdirs();
        }


//        saveResponses(UserInfo.userInfos);
        Log.d(TAG, "main folder: "+mainFolder.getAbsolutePath());



//        practiceBtn = (Button) findViewById(R.id.button_practice);
        mode1 = (Button) findViewById(R.id.button_mode1);
        mode2 = (Button) findViewById(R.id.button_mode2);

        DecideOrder.init();

//        DecideOrder.currAct=0;
//        DecideOrder.trialNum=0;

//        practiceBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                isPractice=true;
//                finish();
//                startActivity(new Intent(ChooseMode.this, decideOrder.class));
//            }
//        });

        mode1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                isPractice=false;
                mode=1;
                userfolder = new File(mainFolder,String.valueOf(mode));
                boolean success = true;
                if (!userfolder.exists()) {
                    success = userfolder.mkdir();
                }
                if (success) {
//                Toast.makeText(this, "App folder created successfully!", Toast.LENGTH_SHORT).show();

                }
                else{
                    Toast.makeText(ChooseMode.this, "Could not create folder in local storage", Toast.LENGTH_SHORT).show();
                }

                Log.d(TAG, "user folder: "+userfolder.getAbsolutePath());

                finish();
                startActivity(new Intent(ChooseMode.this, DecideOrder.class));
            }
        });

        mode2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                isPractice=false;
                mode=2;
                userfolder = new File(mainFolder,String.valueOf(mode));
                boolean success = true;
                if (!userfolder.exists()) {
                    success = userfolder.mkdirs();
                }
                if (success) {
//                Toast.makeText(this, "App folder created successfully!", Toast.LENGTH_SHORT).show();

                }
                else{
                    Toast.makeText(ChooseMode.this, "Could not create folder in local storage", Toast.LENGTH_SHORT).show();
                }

                Log.d(TAG, "user folder: "+userfolder.getAbsolutePath());

                finish();
                startActivity(new Intent(ChooseMode.this, DecideOrder.class));
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
