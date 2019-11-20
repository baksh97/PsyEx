package com.psyex.application.Questionnaire;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputLayout;
import com.psyex.application.R;

import java.util.ArrayList;
import java.util.List;

public class assess_adapter extends RecyclerView.Adapter<assess_adapter.MyViewHolder> {

//    public class question_adapter{

        static private List<TextInputLayout> tils;

        static private List<String> questionList;

        static public List<String> responses;
//        static List

//        static List<Integer> getInputs(){
//            List<Integer> inputs = new ArrayList<>();
//            for(int i=0;i<questionList.size();i++){
//
//            }

        static void getRepsonse(){
            for(TextInputLayout til:tils){
                responses.add(til.getEditText().getText().toString());
            }
        }
//        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView questionText,question_num;
//            RadioGroup rg;
            TextInputLayout til;
            public MyViewHolder(View view) {
                super(view);
                questionText = (TextView) view.findViewById(R.id.textView_questionnaire6);
                question_num = (TextView) view.findViewById(R.id.textView_quenum_questionnaire6);
//                rg = (RadioGroup) view.findViewById(R.id.radioGroup_gender);
                til = (TextInputLayout) view.findViewById(R.id.til_questionnaire6);
//                genre = (TextView) view.findViewById(R.id.genre);
//                year = (TextView) view.findViewById(R.id.year);
            }
        }


        public assess_adapter(List<String> questionList) {
            this.questionList = questionList;
            responses = new ArrayList<>();
            tils = new ArrayList<>();
//            for(int i=0;i<questionList.size();i++)responses.add("");
//            Toast.makeText(, "", Toast.LENGTH_SHORT).show();
//            Log.d("Question:", "repsponses: "+responses.size());
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//            responses =
            View itemView;
//            if(questionnaire.questionnaire_num==4){
//                itemView= LayoutInflater.from(parent.getContext())
//                        .inflate(R.layout.question2, parent, false);
//            }else {
                itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.activity_questionnaire6, parent, false);
//            }

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
            tils.add(holder.til);
//            holder.til
        }



        @Override
        public int getItemCount() {
            return questionList.size();
        }

    }


//}
