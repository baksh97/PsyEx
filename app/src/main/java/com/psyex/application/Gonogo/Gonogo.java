package com.psyex.application.Gonogo;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.psyex.application.ChooseMode;
import com.psyex.application.DecideOrder;
import com.psyex.application.R;

import java.io.File;
import java.util.Arrays;
import java.util.Random;

public class Gonogo extends AppCompatActivity {

    String fileName = "Gonogo.csv";
    File file = new File(ChooseMode.userfolder.getAbsolutePath(), fileName);

    int count=0;
    ConstraintLayout cl;
    TextView[] tv,x;
    ImageView[] iv;
    int nums[] = new int[2];
    int pos=0;
    Handler handler = new Handler();
    char currChar='P';
    boolean start=false;
    char correctChar='P';
    Runnable r,r1;
    private String TAG="GONOGO";
    boolean showtext=true;
    String c = "";
    int quad;
    int trialNum=0;
    String s="";

    String[] quadzero,quadtwo,quadthree,quadone;
    int[] quadrant_list,quadPos = {0,0,0,0};
    int q=0;
    int size = 8;
    void init(){
        quadrant_list = new int[size];
        quadzero = new String[size/4];
        quadone = new String[size/4];
        quadtwo = new String[size/4];
        quadthree = new String[size/4];
        s="";
    }

    boolean isPractice=false;

    TextView mode_tv;


    String[] Chars = {"R", "P","P","P","P"};//change this if requires //TODO
    //    int[] x = {620, 820, 620, 820};
//    int[] y = {350, 350, 550, 550};
    int responded = 0;
    int present, correct;
    int trial = 1;
    Long starttime =  System.currentTimeMillis();
    Long curtime;
    Long restime = new Long(1500);
//    final int MAX_COUNT = 10;


    String getNext(int quad){
        if(quad==0){
            c = quadzero[quadPos[quad]++];
        }
        else if(quad==1){
            c = quadone[quadPos[quad]++];
        }
        else if(quad==2){
            c = quadtwo[quadPos[quad]++];
        }
        else{
            c = quadthree[quadPos[quad]++];
        }
        if(trialNum==0)return c;
        else{
            if(c.equals("P")){
                return "R";
            }else return "P";
        }
//        return c;
    }

    int getQuad(){
        if (q<size) {
            return quadrant_list[q++];
        }
        else {
            return -1;
        }
    }

    static void shuffleArray(int[] ar)
    {
        Random rnd = new Random();
        for (int i = ar.length - 1; i > 0; i--)
        {
            int index = rnd.nextInt(i + 1);
            int a = ar[index];
            ar[index] = ar[i];
            ar[i] = a;
        }
    }

    static void shuffleChars(String[] ar)
    {
        Random rnd = new Random();
        for (int i = ar.length - 1; i > 0; i--)
        {
            int index = rnd.nextInt(i + 1);
            String a = ar[index];
            ar[index] = ar[i];
            ar[i] = a;
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_gonogo);
//        int trialNum=0;
        s="";

        Intent thisIntent = getIntent();
        trialNum = thisIntent.getIntExtra("trialNum",0);
        isPractice = thisIntent.getBooleanExtra(getString(R.string.practice_extra),true);

        Log.d(TAG,"got trialNum: "+trialNum);

        if(trialNum==1)correctChar='R';
        else correctChar='P';


        if(isPractice){
            size=4;
        }
        else size=4;

        mode_tv = (TextView) findViewById(R.id.textView_mode_gonogo);
        if(isPractice){
            mode_tv.setText("PRACTICE");
        }
//        else if(ChooseMode.mode==1){
        else{
            mode_tv.setText("Touch for "+correctChar);
        }

        init();

        tv = new TextView[5];
        x = new TextView[5];
        iv = new ImageView[5];

        cl = (ConstraintLayout) findViewById(R.id.cl_gonogo);
        tv[1] = (TextView) findViewById(R.id.textView1);
        tv[2] = (TextView) findViewById(R.id.textView2);
        tv[3]= (TextView) findViewById(R.id.textView3);
        tv[4] = (TextView) findViewById(R.id.textView4);

        iv[1] = (ImageView) findViewById(R.id.imageView1);
        iv[2] = (ImageView) findViewById(R.id.imageView2);
        iv[3] = (ImageView) findViewById(R.id.imageView3);
        iv[4] = (ImageView) findViewById(R.id.imageView4);

        x[1] = (TextView) findViewById(R.id.textView11);
        x[2] = (TextView) findViewById(R.id.textView21);
        x[3] = (TextView) findViewById(R.id.textView31);
        x[4] = (TextView) findViewById(R.id.textView41);

        x[1].setVisibility(View.INVISIBLE);
        x[2].setVisibility(View.INVISIBLE);
        x[3].setVisibility(View.INVISIBLE);
        x[4].setVisibility(View.INVISIBLE);

        tv[1].setVisibility(View.INVISIBLE);
        tv[2].setVisibility(View.INVISIBLE);
        tv[3].setVisibility(View.INVISIBLE);
        tv[4].setVisibility(View.INVISIBLE);

        for(int i=0;i<size;i++){
            quadrant_list[i] = i%4;
        }
        shuffleArray(quadrant_list);
        Log.d("backend", "quadrant list: "+ Arrays.toString(quadrant_list));

        for(int i=0;i<size/4;i++){
            quadzero[i] = Chars[i%Chars.length];
        }
        for(int i=0;i<size/4;i++){
            quadone[i] = Chars[i%Chars.length];
        }
        for(int i=0;i<size/4;i++){
            quadtwo[i] = Chars[i%Chars.length];
        }
        for(int i=0;i<size/4;i++){
            quadthree[i] = Chars[i%Chars.length];
        }

        shuffleChars(quadzero);
        Log.d("backend", "quadrant zero characters: "+Arrays.toString(quadzero));
        shuffleChars(quadone);
        Log.d("backend", "quadrant one characters: "+Arrays.toString(quadone));
        shuffleChars(quadtwo);
        Log.d("backend", "quadrant two characters: "+Arrays.toString(quadtwo));
        shuffleChars(quadthree);
        Log.d("backend", "quadrant three characters: "+Arrays.toString(quadthree));


        cl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "clicked with currentChar: "+currChar);
                try {
                    if (responded == 0) {
                        responded = 1;
                        curtime = System.currentTimeMillis();
                        restime = curtime - starttime;
                        iv[quad + 1].setVisibility(View.VISIBLE);
                        tv[quad + 1].setVisibility(View.INVISIBLE);
                        if(isPractice && correctChar!=currChar) {
                            x[quad + 1].setVisibility(View.VISIBLE);
//                            Thread.sleep(500);
                        }
                    }
                }
                catch (Exception e){}
            }
        });

        s += "Practice/Test, ";
        s+= "correctResponse" + ", " ;
        s+= "trial" + ", " ;
        s+= "choice" + ", " ;
//        t+= "x" + ", " ;
//        t+= "y" + ", " ;
        s+= "stim" + ", " ;
        s+= "present" + ", " ;
        s+= "responded" + ", " ;
        s+= "correct" + ", " ;
        s+= "starttime" + ", " ;
        s+= "rt" + ", " ;
        s+= "\n";
        Log.e(TAG, s);
//        saveTextAsFile(s);

        final int finalTrialNum = trialNum;

        r1 = new Runnable() {
            @Override
            public void run() {
                if(correctChar==currChar && responded==0 && isPractice){
                    x[quad+1].setVisibility(View.VISIBLE);
//                    showtext=true;
                    handler.postDelayed(r,500);
                }else
                    handler.postDelayed(r,0);
            }
        };

        r = new Runnable() {
            public void run () {


                start=true;
                starttime= System.currentTimeMillis();

                if(showtext) {

                    count++;

//                    if(count==MAX_COUNT){
//                        saveTextAsFile(s);
////                        DecideOrder.uploadFile(file.getAbsolutePath(),UserInfo.userId);
//                        DecideOrder.fileNames[1]=file.getAbsolutePath();
//                        finish();
//                        startActivity(new Intent(Gonogo.this, DecideOrder.class));
//                    }

                    x[quad+1].setVisibility(View.INVISIBLE);
//                    s ="";\
                    if(count>1) {
//                        Log.d(TAG, "currchar: "+currChar+" correctChar: "+correctChar+" responded: "+responded);
//                        if(currChar==correctChar && responded==0 && isPractice){
//                            Log.d(TAG, "entered hrer");
//                            x[quad+1].setVisibility(View.VISIBLE);
//                            try {
//                                Thread.sleep(500);
//                            } catch (InterruptedException e) {
//                                e.printStackTrace();
//                            }
//                            x[quad+1].setVisibility(View.INVISIBLE);
//                        }
                        s += (isPractice)?"Practice, ":"Test for "+correctChar+", ";
                        s += correctChar + ", ";
                        s += trial + ", ";
                        trial += 1;
                        s += quad + ", ";
//                    s+= x[quad] + ", " ;
//                    s+= y[quad] + ", " ;
                        s += c + ", ";
                        if (correctChar == currChar) {
                            present = 1;
                        } else {
                            present = 0;
                        }
                        s += present + ", ";

                        s += responded + ", ";
                        if (responded == present) {
                            correct = 1;
                        } else {
                            correct = 0;
                        }
                        s += correct + ", ";
                        responded = 0;

                        s += starttime % 10000000 + ", ";
                        s += restime + ", ";
                        restime = new Long(1500);
                        s += "\n";
                    }
//                    Log.e(TAG, s);
//                    saveTextAsFile(s);

                    quad = getQuad();
                    if(quad==-1){
                        DecideOrder.saveTextAsFile(s,file);
                        DecideOrder.addFile(file.getAbsolutePath());

                        if(isPractice && trialNum==0){
                            Log.d(TAG, "here1");
                            Intent intent1 = new Intent(Gonogo.this, Instructions2.class);
//                            intent1.putExtra("isPractice",false);
                            finish();
                            startActivity(intent1);
                        }
                        else if(isPractice && trialNum==1){
                            Log.d(TAG, "here2");
                            Intent intent1 = new Intent(Gonogo.this, Instructions4.class);
//                            intent1.putExtra("isPractice",false);
                            finish();
                            startActivity(intent1);
                        }
                        else if(!isPractice && trialNum==0){
                            Log.d(TAG, "here3");
                            Intent intent1 = new Intent(Gonogo.this, Instructions3.class);
//                            intent1.putExtra("isPractice",false);
                            finish();
                            startActivity(intent1);
                        }
                        else{
                            Log.d(TAG, "here4");
                            Intent intent1 = new Intent(Gonogo.this, DecideOrder.returnNext());
//                            intent1.putExtra("isPractice",false);
                            finish();
                            startActivity(intent1);
                        }

//                        if(isPractice && finalTrialNum==0){
//                            Intent intent1 = new Intent(Gonogo.this, DecideOrder.class);
//                            intent1.putExtra("isPractice",false);
////                            intent1.putExtra("trial", finalTrialNum);
//                            startActivity(intent1);
//                        }
//                        else if(isPractice && finalTrialNum==1){
//                            Intent intent1 = new Intent(Gonogo.this, Gonogo.class);
//                            intent1.putExtra("isPractice",false);
//                            intent1.putExtra("trial", finalTrialNum);
//                            startActivity(intent1);
//                        }
//                        else if(!isPractice && finalTrialNum==0){
//                            saveTextAsFile(s);
////                        DecideOrder.uploadFile(file.getAbsolutePath(),UserInfo.userId);
////                            DecideOrder.fileNames[1] = file.getAbsolutePath();
//                            DecideOrder.fileNames.add(file.getAbsolutePath());
////                            if(correctChar=='P'){
//                            Intent intent1 = new Intent(Gonogo.this, gonogoChange.class);
////                                intent1.putExtra("isPractice",true);
////                                intent1.put
//                            finish();
//                            startActivity(intent1);
////                            }else {
//
////                                Log.d(TAG, "finishing Gonogo");
//
////                            }
//                        }
//                        else{
//                            saveTextAsFile(s);
////                        DecideOrder.uploadFile(file.getAbsolutePath(),UserInfo.userId);
////                            DecideOrder.fileNames[1] = file.getAbsolutePath();
//                            finish();
//                            Intent intent1 = new Intent(Gonogo.this, DecideOrder.class);
//                            intent1.putExtra("isPractice",true);
//                            startActivity(intent1);
//                        }
////                        if(!isPractice) {
////                            saveTextAsFile(s);
//////                        DecideOrder.uploadFile(file.getAbsolutePath(),UserInfo.userId);
////                            DecideOrder.fileNames[1] = file.getAbsolutePath();
////                        }

                    }
                    else {
                        c = (getNext(quad));
                        currChar = c.charAt(0);

                        iv[quad + 1].setVisibility(View.INVISIBLE);
                        tv[quad + 1].setVisibility(View.VISIBLE);
                        tv[quad + 1].setText(c);

                        showtext = false;
                        handler.postDelayed(r, 500);
                    }

                }
                else {
                    iv[quad+1].setVisibility(View.VISIBLE);
                    tv[quad+1].setVisibility(View.INVISIBLE);
//
                    showtext=true;
                    handler.postDelayed(r1,1000);
                }

            }
        };
        handler.postDelayed(r,2000);

    }
    /*****************************************/


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
