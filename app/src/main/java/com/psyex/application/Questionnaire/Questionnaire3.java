package com.psyex.application.Questionnaire;

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
import android.widget.TextView;

import com.psyex.application.ChooseMode;
import com.psyex.application.DecideOrder;
import com.psyex.application.R;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class Questionnaire3 extends AppCompatActivity {
    private static final String TAG = "questionnaire";
    int queNum=0;
    Button continuebtn;
    RecyclerView recyclerView;
    question_adapter mAdapter;
    TextView questionnaire_text;
//    static int qnum=0;
//    int questionnaire_num=0;

//    void getInputs(){
//
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.questionnaire);

//        qnum++;

        questionnaire_text = (TextView) findViewById(R.id.textView_questionnaire_num);

//        Toast.makeText(this, "New questionnare here!", Toast.LENGTH_SHORT).show();

//        finish();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView_questionnaire);
        TextView instr_ques = (TextView) findViewById(R.id.textView_instructions_questionnaire);

//        questionnaire_num = getIntent().getIntExtra("number",1);

        questionnaire_text.setText("QUESTIONNAIRE "+String.valueOf(DecideOrder.currQue));

        String packageName = getPackageName();

        Log.d("questionnaire","question num: "+DecideOrder.currQue);

//        int resId = getResources().getIdentifier("questions"+String.valueOf(questionnaire_num), "array", packageName);

//        String instr = getString(getResources().getIdentifier("instr_questionnaire"+String.valueOf(questionnaire_num),"string",packageName));
        String instr = getString(R.string.instr_questionnaire3);
        instr_ques.setText(instr);
        List<String> questions = Arrays.asList(getResources().getStringArray(R.array.questions3));

        mAdapter = new question_adapter(questions, 3);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
//        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        continuebtn = (Button) findViewById(R.id.button_continue_question);

        continuebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                File file= new File(ChooseMode.userfolder, "questionnaire3.csv");
//        Log.d("questionarrie, ","resp size: "+resp.size());
                Log.d(TAG, "Saving at: "+file.getAbsolutePath());
//        for(String i:resp){
//            if(i.equals("-1"))return false;
//        }



                String content="";
                for(String i:mAdapter.responses)content+=i+'\n';
//                getInputs();
                DecideOrder.saveTextAsFile(content,file);
                DecideOrder.addFile(file.getAbsolutePath());
                finish();
                startActivity(new Intent(Questionnaire3.this, DecideOrder.returnNext()));
//                }
//                else{
//                    Toast.makeText(Questionnaire3.this, "Please fill in all the responses!", Toast.LENGTH_SHORT).show();
//                }

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
