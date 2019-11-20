package com.psyex.application;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class UserInfo extends AppCompatActivity {

    private static final String TAG = "UserInfo";
    static String username,gender;
    static int age;
    static String userId;

    userInfoAdapter mAdapter;
    RecyclerView recyclerView;
    static List<String> userfields;
    static List<String> userInfos;


    //    TextInputLayout usertil, agetil,gendtil;
    Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

//        usertil = (TextInputLayout) findViewById(R.id.textInputLayout_name);
//        gendtil = (TextInputLayout) findViewById(R.id.textInputLayout_gender);
//        agetil = (TextInputLayout) findViewById(R.id.textInputLayout_age);
        submit = (Button) findViewById(R.id.button_submit_userInfo);

        userfields = Arrays.asList(getResources().getStringArray(R.array.user_field_names));
        mAdapter = new userInfoAdapter(userfields);
        recyclerView = (RecyclerView) findViewById(R.id.rv_userfields);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
//        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userInfoAdapter.getInputs();
                List<String> responses = userInfoAdapter.responses;
                Log.d(TAG, responses.toString());
//                if(usertil.getEditText().getText().toString().equals())
                if(!responses.get(0).equals("") && !responses.get(2).equals("")) {
                    try {

                        username = responses.get(0);
                        Log.d(TAG, "username done: "+username);
                        age = Integer.parseInt(responses.get(1));
                        Log.d(TAG, "age done: "+age);
                        gender = responses.get(2);
                        Log.d(TAG, "gender done: "+gender);
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss", Locale.getDefault());
                        String currentDateandTime = sdf.format(new Date());
                        Log.d(TAG, "date done: "+currentDateandTime);
                        userId = username + currentDateandTime;
                        Log.d(TAG, "id done: "+userId);
//                    userId = currentDateandTime;
//                    finish();
//                        saveResponses(responses);
                        Log.d(TAG, "saving done");
                        userInfos = responses;
                        finish();
                        startActivity(new Intent(UserInfo.this, ChooseMode.class));
                    } catch (Exception e) {
                        Toast.makeText(UserInfo.this, "Something is wrong with the inputs" +
                                "!", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(UserInfo.this, "Please enter the name, age and gender correctly!", Toast.LENGTH_SHORT).show();
                }
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
