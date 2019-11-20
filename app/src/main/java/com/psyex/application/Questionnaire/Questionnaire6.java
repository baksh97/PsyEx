package com.psyex.application.Questionnaire;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

public class Questionnaire6 extends AppCompatActivity {

    private static final String TAG = "assessment";
    RecyclerView recyclerView;
    assess_adapter mAdapter;
    Button cbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.questionnaire);
        TextView instr_ques = (TextView) findViewById(R.id.textView_instructions_questionnaire);

        instr_ques.setVisibility(View.INVISIBLE);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView_questionnaire);
        List<String> questions = Arrays.asList(getResources().getStringArray(R.array.assessment));
        cbtn = (Button) findViewById(R.id.button_continue_question);

        mAdapter = new assess_adapter(questions);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
//        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        cbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mAdapter.getRepsonse();

                File file= new File(ChooseMode.userfolder, "questionnaire6.csv");
//        Log.d("questionarrie, ","resp size: "+resp.size());
                Log.d(TAG, "Saving at: "+file.getAbsolutePath());
//        for(String i:resp){
//            if(i.equals("-1"))return false;
//        }

                DecideOrder.addFile(file.getAbsolutePath());

                String content="";
                for(String i:mAdapter.responses)content+=i+'\n';
                DecideOrder.saveTextAsFile(content,file);
                DecideOrder.addFile(file.getAbsolutePath());


                finish();
                startActivity(new Intent(Questionnaire6.this, DecideOrder.returnNext()));
            }
        });


    }
}
