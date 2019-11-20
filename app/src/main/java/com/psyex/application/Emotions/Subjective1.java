package com.psyex.application.Emotions;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.SeekBar;
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

    TextInputLayout til1, til2;
    SeekBar rg1, rg2;
    boolean responded1=false, responded2=false;
    TextView chosen1,chosen2;

    String getResponses(){

        String resp = "";
        if(responded1)
            resp += String.valueOf(rg1.getProgress())+"\n";
        else resp += "-1\n";

        if(responded2)
            resp += String.valueOf(rg2.getProgress())+"\n";
        else resp += "-1\n";

        resp += til1.getEditText().getText().toString()+"\n";
        resp += til2.getEditText().getText().toString();
        return resp;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subjective1);

        condition = getIntent().getIntExtra("condition",2);

        TextView heading = (TextView) findViewById(R.id.textView_cond_subj);
        heading.setText("CONDITION "+String.valueOf(condition));
        chosen1 = (TextView) findViewById(R.id.textView_chosen_question1_subjective);
        chosen2 = (TextView) findViewById(R.id.textView_chosen_question2_subjective);

        Button contSubjective = (Button) findViewById(R.id.button_subjective);


        til1 = (TextInputLayout) findViewById(R.id.til_question3_subjective);
        til2 = (TextInputLayout) findViewById(R.id.til_question4_subjective);

        rg1 = (SeekBar) findViewById(R.id.seekBar1);
        rg2 = (SeekBar) findViewById(R.id.seekBar2);

        chosen1.setText("50");
        chosen2.setText("50");

        rg1.setProgress(50);
        rg2.setProgress(50);

        rg1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                chosen1.setText(String.valueOf(i));
                responded1 = true;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
//                chosen1.setText(String.valueOf(i));
                responded1 = true;
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
//                chosen1.setText(String.valueOf(i));
                responded1 = true;

            }
        });

        rg2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                chosen2.setText(String.valueOf(i));
                responded2 = true;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
//                chosen2.setText(String.valueOf(i));
                responded2 = true;

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
//                chosen2.setText(String.valueOf(i));
                responded2 = true;

            }
        });

        TextView[] tv = new TextView[4];

        tv[0] = (TextView) findViewById(R.id.textView1_subjective_emotions);
        tv[1] = (TextView) findViewById(R.id.textView2_subjective_emotions);
        tv[2] = (TextView) findViewById(R.id.textView3_subjective_emotions);
        tv[3] = (TextView) findViewById(R.id.textView4_subjective_emotions);

        String[] questions = new String[4];
        if(condition==1){
            questions = getResources().getStringArray(R.array.condition1_subjective);
        }
        else if(condition==2){
            questions = getResources().getStringArray(R.array.condition2_subjective);
        }
        else{
            questions = getResources().getStringArray(R.array.condition3_subjective);
        }

        for(int i=0;i<4;i++){
            tv[i].setText(questions[i]);
//            tv_nums[i].setText(String.valueOf(i+1));
        }

        contSubjective.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fileName = "subjective_"+String.valueOf(condition)+".csv";
                File file = new File(ChooseMode.userfolder.getAbsolutePath(), fileName);

                DecideOrder.addFile(file.getAbsolutePath());
                DecideOrder.saveTextAsFile(getResponses(), file);

                if(condition==1){
                    finish();
                    startActivity(new Intent(Subjective1.this, Instructions5.class));
                }
                else if(condition==2){
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
