package com.psyex.application;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.psyex.application.Emotions.Emotions;
import com.psyex.application.Emotions.Instructions1;
import com.psyex.application.Gonogo.Gonogo;
import com.psyex.application.Questionnaire.Questionnaire1;
import com.psyex.application.Questionnaire.Questionnaire2;
import com.psyex.application.Questionnaire.Questionnaire3;
import com.psyex.application.Questionnaire.Questionnaire4;
import com.psyex.application.Questionnaire.Questionnaire5;
import com.psyex.application.Questionnaire.Questionnaire6;
import com.psyex.application.Stroop.Stroop;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

public class DecideOrder extends AppCompatActivity {

    private static final String TAG = "DecideOrder";
    static ArrayList<Class> tasksOrder;
     static int currAct;
     static ArrayList<Class> questionnaireOrder;
     public static int currQue;
     static Set<String> s;

     static void init(){
         tasksOrder = new ArrayList<>();
         questionnaireOrder = new ArrayList<>();
         s = new LinkedHashSet<>();
         currAct=0;
         currQue=0;
     }

    static public void saveTextAsFile(String content, File file){
        Log.d(TAG,"saving at: "+file.getAbsolutePath());
        try{
            FileOutputStream fos = new FileOutputStream(file,true);
            fos.write(content.getBytes());
            fos.close();
//            Toast.makeText(this,"Saved!",Toast.LENGTH_SHORT).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }

//            Toast.makeText(this,"File not found!",Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
//            Toast.makeText(this,"Error saving!",Toast.LENGTH_SHORT).show();
        }
    }

    public static Class returnNext(){
        if(currAct==tasksOrder.size()){
            if(currQue==questionnaireOrder.size()){
                return FinalAct.class;
            }
            else{
                return questionnaireOrder.get(currQue++);
            }
        }
        else{
            return tasksOrder.get(currAct++);
        }
    }

    public static void addFile(String file){
        s.add(file);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decide_order);

//        currAct=0;
//        tasksOrder = new ArrayList<>();
//        currQue=0;
//        questionnaireOrder = new ArrayList<>();
        init();

                Class[] tasks = {Instructions1.class, com.psyex.application.Stroop.Instructions1.class, com.psyex.application.Gonogo.Instructions1.class};
//        Class[] tasks = {Instructions1.class,Instructions1.class,Instructions1.class};
//        Class[] tasks = {com.psyex.application.Gonogo.Instructions1.class,com.psyex.application.Gonogo.Instructions1.class,com.psyex.application.Gonogo.Instructions1.class};
//        Class[] tasks = { com.psyex.application.Stroop.Instructions1.class,com.psyex.application.Stroop.Instructions1.class,com.psyex.application.Stroop.Instructions1.class};
        double r = Math.random();

//        int[] orders = new int[3];

        if(r<0.5){
            tasksOrder.add(tasks[0]);
            r = Math.random();
            if(r<0.5){
                tasksOrder.add(tasks[1]);
                tasksOrder.add(tasks[2]);
            }else{
                tasksOrder.add(tasks[2]);
                tasksOrder.add(tasks[1]);
            }
        }
        else{
            r = Math.random();
            if(r<0.75){
                tasksOrder.add(tasks[1]);
                r = Math.random();
                if(r<0.5) {
                    tasksOrder.add(tasks[2]);
                    tasksOrder.add(tasks[0]);
                }
                else{
                    tasksOrder.add(tasks[0]);
                    tasksOrder.add(tasks[2]);
                }
            }else{
                tasksOrder.add(tasks[2]);
                r = Math.random();
                if(r<0.5) {
                    tasksOrder.add(tasks[1]);
                    tasksOrder.add(tasks[0]);
                }
                else{
                    tasksOrder.add(tasks[0]);
                    tasksOrder.add(tasks[1]);
                }
            }
        }

        questionnaireOrder.add(Questionnaire1.class);
        questionnaireOrder.add(Questionnaire2.class);
        questionnaireOrder.add(Questionnaire3.class);
        questionnaireOrder.add(Questionnaire4.class);
        questionnaireOrder.add(Questionnaire5.class);

        Collections.shuffle(questionnaireOrder);

        if(ChooseMode.mode==2){
            questionnaireOrder.add(Questionnaire6.class);
        }

        File file= new File(ChooseMode.mainFolder.getAbsolutePath(), "userInfo"+".csv");
//        Log.d("user info, ","resp size: "+resp.size());
        Log.d(TAG,"saving at: "+file.getAbsolutePath());
//        for(String i:resp){
//            if(i.equals("-1"))return false;
//        }

//        Log.d(TAG, "file done: "+file.getAbsolutePath());


        String content="";
        for(int i=0;i<UserInfo.userInfos.size();i++)content+=UserInfo.userfields.get(i)+", "+UserInfo.userInfos.get(i)+'\n';
        DecideOrder.addFile(file.getAbsolutePath());
        Log.d(TAG, "content done: "+content);
        saveTextAsFile(content,file);

        file= new File(ChooseMode.mainFolder.getAbsolutePath(), "order"+".csv");
        String s1 = "Activites order\n";
        int i=0;
        for(Class c:tasksOrder){
            if(c== Instructions1.class){
                s1 += "Emotions\n";
            }
            else if(c== com.psyex.application.Stroop.Instructions1.class){
                s1 += "Stroop\n";
            }
            else if(c == com.psyex.application.Gonogo.Instructions1.class){
                s1 += "Gonogo\n";
            }
        }

        s1 += "Questionnaires\n";
        for(Class c:questionnaireOrder){
            if(c== Questionnaire1.class){
                s1 += "Questionnaire1\n";
            }
            else if(c== Questionnaire2.class){
                s1 += "Questionnaire2\n";
            }
            else if(c== Questionnaire3.class){
                s1 += "Questionnaire3\n";
            }
            else if(c== Questionnaire4.class){
                s1 += "Questionnaire4\n";
            }
            else if(c== Questionnaire5.class){
                s1 += "Questionnaire5\n";
            }
        }

        saveTextAsFile(s1,file);
        addFile(file.getAbsolutePath());

        finish();
        startActivity(new Intent(this, returnNext()));
    }


}
