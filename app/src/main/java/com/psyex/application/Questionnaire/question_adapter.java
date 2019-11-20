package com.psyex.application.Questionnaire;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.psyex.application.R;

import java.util.ArrayList;
import java.util.List;

public class question_adapter extends RecyclerView.Adapter<question_adapter.MyViewHolder> {


        static private List<String> questionList;

        static public List<String> responses;
        int questionnaire_num;
//        static List

//        static List<Integer> getInputs(){
//            List<Integer> inputs = new ArrayList<>();
//            for(int i=0;i<questionList.size();i++){
//
//            }
//        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView questionText,question_num;
            RadioGroup rg;

            public MyViewHolder(View view) {
                super(view);
                questionText = (TextView) view.findViewById(R.id.textView_question_questionnaire);
                question_num = (TextView) view.findViewById(R.id.textView_question_num_questionnaire);
                rg = (RadioGroup) view.findViewById(R.id.radioGroup_questionnaire);

//                genre = (TextView) view.findViewById(R.id.genre);
//                year = (TextView) view.findViewById(R.id.year);
            }
        }


        public question_adapter(List<String> questionList, int questionnaire_num) {
            this.questionnaire_num = questionnaire_num;
            this.questionList = questionList;
            responses = new ArrayList<>();
            for(int i=0;i<questionList.size();i++)responses.add("-1");
//            Toast.makeText(, "", Toast.LENGTH_SHORT).show();
            Log.d("Question:", "repsponses: "+responses.size());
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//            responses =
            View itemView;
            if(questionnaire_num==4){
                 itemView= LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.activity_questionnaire4, parent, false);
            }else {
                 itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.activity_questionnaire1, parent, false);
            }

            return new MyViewHolder(itemView);
        }

        @Override
        public int getItemViewType(int position) {
            return position;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, final int position) {
            String questionText = questionList.get(position);
            holder.questionText.setText(questionText);
            holder.question_num.setText(String.valueOf(position+1));

            holder.rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
            {
                public void onCheckedChanged(RadioGroup group, int checkedId)
                {
                    // This will get the radiobutton that has changed in its check state
                    RadioButton checkedRadioButton = (RadioButton)group.findViewById(checkedId);
                    // This puts the value (true/false) into the variable
                    boolean isChecked = checkedRadioButton.isChecked();
                    // If the radiobutton that has changed in check state is now checked...
                    if (isChecked)
                    {
                        // Changes the textview's text to "Checked: example radiobutton text"
                        responses.set(position,checkedRadioButton.getText().toString());
                    }
                    else responses.set(position,"-1");
                }
            });

        }



        @Override
        public int getItemCount() {
            return questionList.size();
        }

}
