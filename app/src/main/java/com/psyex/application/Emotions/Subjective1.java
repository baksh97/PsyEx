package com.psyex.application.Emotions;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;
import com.psyex.application.ChooseMode;
import com.psyex.application.DecideOrder;
import com.psyex.application.R;

import java.io.File;

public class Subjective1 extends AppCompatActivity {

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_subjective1);
//    }

    private static final String TAG = "subjectiveCond2";
    int condition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subjective1);

        condition = getIntent().getIntExtra("condition",2);

        TextView heading = (TextView) findViewById(R.id.textView_cond_subj);
        heading.setText("CONDITION "+String.valueOf(condition));

        TextView tv_subjective = (TextView) findViewById(R.id.textView_subjective);
        final TextInputLayout til_subjective = (TextInputLayout) findViewById(R.id.textInputLayout_subjective);
        Button contSubjective = (Button) findViewById(R.id.button_subjective);

        if(condition==2){
            tv_subjective.setText(getResources().getString(R.string.condition2_subjective_start));
        }
        else{
            tv_subjective.setText(getResources().getString(R.string.condition3_subjective_start));
        }

        contSubjective.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fileName = "subjective_"+String.valueOf(condition)+".csv";
                File file = new File(ChooseMode.userfolder.getAbsolutePath(), fileName);

                DecideOrder.addFile(file.getAbsolutePath());
                DecideOrder.saveTextAsFile(til_subjective.getEditText().getText().toString(), file);
                if(condition==2){
                    finish();
                    startActivity(new Intent(Subjective1.this, Instructions7.class));
                }
                else{
                    Intent int1 = new Intent(Subjective1.this , Instructions9.class);
                    finish();
                    startActivity(int1);
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
