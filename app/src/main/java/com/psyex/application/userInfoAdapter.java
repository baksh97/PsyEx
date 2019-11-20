package com.psyex.application;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

public class userInfoAdapter extends RecyclerView.Adapter<userInfoAdapter.MyViewHolder> {
//    public class question_adapter {


        static private List<String> userfieldnames;
        static private List<TextInputLayout> tils;

//        static private List<String> userfieldvalues;
        static List<String> responses = new ArrayList<>();

        static void getInputs(){
            for(int i=0;i<tils.size();i++){
                if(i==2 || i==7){}else {
                    if (tils.get(i) != null) {
                        responses.set(i, tils.get(i).getEditText().getText().toString());
                    } else responses.set(i, "");
                }
            }
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView userfieldname;
            public TextInputLayout userfieldvalue;
            RadioGroup maritalrg, genderrg;
//            LinearLayout gender_field;
            ConstraintLayout normal_fields,gender_field, marital_field;

            public MyViewHolder(View view) {
                super(view);
                userfieldname = (TextView) view.findViewById(R.id.textView_user_field_name);
                userfieldvalue = (TextInputLayout) view.findViewById(R.id.til_user_field_value);
                gender_field = (ConstraintLayout) view.findViewById(R.id.gender_field);
                marital_field = (ConstraintLayout) view.findViewById(R.id.marital_field);
                normal_fields = (ConstraintLayout) view.findViewById(R.id.normal_fields);

                genderrg = (RadioGroup) view.findViewById(R.id.radioGroup_questionnaire1);
                maritalrg = (RadioGroup) view.findViewById(R.id.radioGroup_marital);




//                rg = (RadioGroup) view.findViewById(R.id.radioGroup);

//                genre = (TextView) view.findViewById(R.id.genre);
//                year = (TextView) view.findViewById(R.id.year);
            }
        }


        public userInfoAdapter(List<String> names) {
            this.userfieldnames = names;
//            responses = new ArrayList<>();
            responses = new ArrayList<>();
            tils = new ArrayList<>();
            for(int i=0;i<names.size();i++)responses.add("");
            for(int i=0;i<names.size();i++)tils.add(null);
//            Toast.makeText(, "", Toast.LENGTH_SHORT).show();
//            Log.d("Question:", "repsponses: "+responses.size());
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//            responses =
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.user_information_field, parent, false);

            return new MyViewHolder(itemView);
        }

        @Override
        public int getItemViewType(int position) {
            return position;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, final int position) {
            holder.normal_fields.setVisibility(View.INVISIBLE);
            holder.gender_field.setVisibility(View.INVISIBLE);
            holder.marital_field.setVisibility(View.INVISIBLE);
            if(position==2){
                holder.gender_field.setVisibility(View.VISIBLE);
                holder.genderrg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
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
            else if(position==7){
                final String mstatus[] = {"Married","Unmarried" ,"Live-in","Widowed","Widower"};
                holder.marital_field.setVisibility(View.VISIBLE);
                holder.maritalrg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
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
                            int temp = Integer.parseInt(checkedRadioButton.getText().toString())-1;
                            responses.set(position,mstatus[temp]);
                        }
                        else responses.set(position,"-1");
                    }
                });
            }
            else {
                holder.normal_fields.setVisibility(View.VISIBLE);
                String questionText = userfieldnames.get(position);
                holder.userfieldname.setText(questionText);
                tils.set(position, holder.userfieldvalue);

                if(position==1){
                    holder.userfieldvalue.getEditText().setInputType(InputType.TYPE_CLASS_NUMBER);
                }
            }
//            holder.userfieldvalue.setText(questionText);
//            holder.question_num.setText(String.valueOf(position+1));


        }



        @Override
        public int getItemCount() {
            return userfieldnames.size();
        }

    }

//}
