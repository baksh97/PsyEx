package com.psyex.application.Stroop;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.psyex.application.ChooseMode;
import com.psyex.application.DecideOrder;
import com.psyex.application.R;

import java.io.File;
import java.util.Arrays;
import java.util.Random;

public class Stroop extends AppCompatActivity implements View.OnClickListener {

    String fileName = "Stroop.csv";
    //    String fileName = "Stroop "+dtf.format(now)+ ".csv";
    File file = new File(ChooseMode.userfolder.getAbsolutePath(), fileName);

    Button bt1,bt2,bt3,bt4;
    TextView tv;

    int count=0;
    int colors[] = {R.color.red, R.color.green, R.color.blue, R.color.yellow};
    int black = R.color.black;
    String texts[] = {"red","green","blue","yellow","when","and"};
    String colorNames[] = {"Red", "Green", "Blue", "Yellow"};
    //    String texts[] = {"red","orange","blue","green","when","and"};
    private String TAG="STROOP";
    Handler handler = new Handler();
    Runnable r,r1,r2,r3;
    int text_id=-1, color_id;
    int color;
    int responded=0, response, correct;
    String text, cond;
    Long abstime;
    private TextView mode_tv;
    Long startTime;
    //    final int MAX_COUNT=10;
    String s="";
    int size = 72;
    int[] color_list;
    boolean isPractice;

    int c = 0;
    int t = 0;


    int[] colorPos = {0,0,0,0};
    int[] colorzero,colorone,colortwo,colorthree;


    void init(){
        color_list= new int[size];
        colorzero = new int[size/4];
        colorone = new int[size/4];
        colortwo = new int[size/4];
        colorthree = new int[size/4];
        s="";

        Log.d(TAG, "colozero.size: "+colorone.length+" size: "+size);

    }


    int getRandomColor(){
        if (c<size) {
            return color_list[c++];
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

//    int getRandomString(){
//        int min=0;
//        int max = texts.length-1;
//        int x = (int)(Math.random()*((max-min)+1))+min;
//        return x;
//    }

    int getTxt(int color, int cond) {
        int x=0;
        if (cond==0){
            x = color;
        }
        else if(cond==1){
            int min=0;
            int max = 3;
            while (x==color){
                x = (int)(Math.random()*((max-min)+1))+min;
            }
        }
        else{
            int min=4;
            int max = texts.length-1;
            x = (int)(Math.random()*((max-min)+1))+min;
        }
        return x;
    }

    int getRandomString(int c){
        if(c==0){
            t = getTxt(c, colorzero[colorPos[c]++]);
        }
        else if(c==1){
            t = getTxt(c, colorone[colorPos[c]++]);
        }
        else if(c==2){
            t = getTxt(c, colortwo[colorPos[c]++]);
        }
        else{
            t = getTxt(c, colorthree[colorPos[c]++]);
        }
        return t;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        Intent intent = new Intent(stroop.this, instructions.class);
//        intent.putExtra("activity",R.string.stroop_instrt);
//        startActivity(intent);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stroop);

//        s = "";

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
//            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1000);
//        }

        isPractice = getIntent().getBooleanExtra(getString(R.string.practice_extra),true);

        if(isPractice)size=16;
        init();

        mode_tv = (TextView) findViewById(R.id.textView_mode_stroop);
//        getIncorrect_stroop = (TextView) findViewById(R.id.textView_incorrect_stroop);
        if(isPractice){
            mode_tv.setText("PRACTICE");
        }
        else{
            mode_tv.setText("TEST "+String.valueOf(ChooseMode.mode));
        }

        bt1 = (Button) findViewById(R.id.stroop_button1);
        bt2 = (Button) findViewById(R.id.stroop_button2);
        bt3 = (Button) findViewById(R.id.stroop_button3);
        bt4 = (Button) findViewById(R.id.stroop_button4);

        tv = (TextView) findViewById(R.id.stroop_textview);
//        incorrect_stroop = (TextView) findViewById(R.id.textView_incorrect_stroop);
//        incorrect_stroop.setVisibility(View.INVISIBLE);

        bt1.setOnClickListener(this);
        bt2.setOnClickListener(this);
        bt3.setOnClickListener(this);
        bt4.setOnClickListener(this);


        for(int i=0;i<size;i++){
            color_list[i] = i%4;
        }
        Log.d("backend", "color list: "+ Arrays.toString(color_list));
        shuffleArray(color_list);
        Log.d("backend", "shuffled color list: "+ Arrays.toString(color_list));


        for(int i=0;i<size/4;i++){
            colorzero[i] = i%3;
        }
        for(int i=0;i<size/4;i++){
            colorone[i] = i%3;
        }
        for(int i=0;i<size/4;i++){
            colortwo[i] = i%3;
        }
        for(int i=0;i<size/4;i++){
            colorthree[i] = i%3;
        }

        Log.d("backend", "color zero: "+ Arrays.toString(colorzero));
        shuffleArray(colorzero);
        Log.d("backend", "shuffled color zero: "+Arrays.toString(colorzero));
        Log.d("backend", "color one: "+Arrays.toString(colorone));
        shuffleArray(colorone);
        Log.d("backend", "shuffled color one: "+Arrays.toString(colorone));
        Log.d("backend", "color two: "+Arrays.toString(colortwo));
        shuffleArray(colortwo);
        Log.d("backend", "shuffled color two: "+Arrays.toString(colortwo));
        Log.d("backend", "color three: "+Arrays.toString(colorthree));
        shuffleArray(colorthree);
        Log.d("backend", "shuffled color three: "+Arrays.toString(colorthree));




//        String t ="";
        s += "Practice/Test, ";
        s+= "text" + ", " ;
        s+= "color_id" + ", ";
        s+= "color" + ", ";
        s+= "cond" + ", ";
        s+= "responded" + ", ";
        s+= "response" + ", ";
        s+= "correct" + ", ";
        s+= "respTime" + ", ";
        s+= "\n";
        Log.e(TAG, s);
//        saveTextAsFile(t);

        r2= new Runnable() {
            @Override
            public void run() {
                bt1.setClickable(true);
                bt2.setClickable(true);
                bt3.setClickable(true);
                bt4.setClickable(true);
                generateNew();
            }
        };

        r3 = new Runnable() {
            @Override
            public void run() {
                tv.setText("+");
                tv.setTextColor(ContextCompat.getColor(Stroop.this, black));
                tv.setVisibility(View.VISIBLE);
                handler.postDelayed(r2,1000);
            }
        };


        r1 = new Runnable() {
            @Override
            public void run() {
//                incorrect_stroop.setVisibility(View.INVISIBLE);
//                tv.setVisibility(View.VISIBLE);
                tv.setVisibility(View.INVISIBLE);
                handler.postDelayed(r3,500);
//                generateNew();

            }
        };

        r = new Runnable() {
            @Override
            public void run() {
//                generateNew();
                bt1.setClickable(false);
                bt2.setClickable(false);
                bt3.setClickable(false);
                bt4.setClickable(false);


                if(responded==0){
//                    incorrect_stroop.setVisibility(View.VISIBLE);
                    tv.setText("Too Slow");
                    tv.setTextColor(ContextCompat.getColor(Stroop.this, black));
//                    tv.setVisibility(View.INVISIBLE);
                    handler.postDelayed(r1,500);
                }
                else{
                    if(isPractice){
//                        bt1.setClickable(false);
//                        bt2.setClickable(false);
//                        bt3.setClickable(false);
//                        bt4.setClickable(false);
//                        incorrect_stroop.setVisibility(View.VISIBLE);
//                        tv.setVisibility(View.INVISIBLE);
                        if(response==color_id+1){
                            tv.setText("Correct");
                            tv.setTextColor(ContextCompat.getColor(Stroop.this, black));
                        }else{
                            tv.setText("Incorrect");
                            tv.setTextColor(ContextCompat.getColor(Stroop.this, black));
                        }
                        handler.postDelayed(r1,500);
                    }
                    else handler.postDelayed(r1,0);
                }
            }
        };

        generateNew();
//

    }

    void generateNew(){

//        handler.postDelayed(r,2000);
//        handler.
//        if(count==MAX_COUNT){

//        }

        if(count>0) {
//            String s = "";
            s += (isPractice)?"Practice, ":"Test, ";
            s += text + ", ";
            s += color_id + ", ";
            s += colorNames[color_id] + ", ";

            if (text_id < 4) {
                if (color_id == text_id) {
                    cond = "C";
                } else {
                    cond = "I";
                }
            } else {
                cond = "N";
            }
            s += cond + ", ";

            s += responded + ", ";
            responded = 0;

            s += response + ", ";

            if (response == color_id+1) {
                correct = 1;
            } else {
                correct = 0;
//                if(isPractice) {
//                    incorrect_stroop.setVisibility(View.VISIBLE);
//                    handler.postDelayed(new Runnable() {
//                        public void run() {
//                            incorrect_stroop.setVisibility(View.INVISIBLE);
//                        }
//                    }, 500);
//                }

            }
            s += correct + ", ";

            abstime = System.currentTimeMillis();
            s += abstime-startTime + ", ";
//            s +=

            s += "\n";
//            Log.e(TAG, s);
//            saveTextAsFile(s);
        }
        startTime = System.currentTimeMillis();

        count++;
        color_id = getRandomColor();
        if(color_id==-1){
            bt1.setClickable(false);
            bt2.setClickable(false);
            bt3.setClickable(false);
            bt4.setClickable(false);
//            if(!isPractice) {
            DecideOrder.saveTextAsFile(s,file);
            DecideOrder.addFile(file.getAbsolutePath());
//            }
            Log.d(TAG,"finishing stroop");
            if(isPractice){
                finish();
                startActivity(new Intent(Stroop.this, Instructions2.class));
            }
            else{
                finish();
                startActivity(new Intent(Stroop.this, DecideOrder.returnNext()));
            }
        }
        else {
            text_id = getRandomString(color_id);
            text = texts[text_id];
            color = colors[color_id];
//        int teid = getRandomString();
//        color_id = getRandomColor();
//        while(text_id == teid) teid = getRandomString();
//        text_id=teid;
//        text = texts[text_id];
//        color = colors[color_id];
            tv.setText(text);
            tv.setTextColor(ContextCompat.getColor(Stroop.this, color));


            handler.postDelayed(r, 3000);
        }
    }

    @Override
    public void onClick(View v){
        Button b = (Button) v;
        responded = 1;
        String buttonpressed = b.getText().toString();
        if("1".equals(buttonpressed)){
            Log.e(TAG,"Button  "+b.getText()+" pressed");
            response =  Integer.parseInt(buttonpressed);
        }

        if("2".equals(buttonpressed)){
            Log.e(TAG,"Button  "+b.getText()+" pressed");
            response =  Integer.parseInt(buttonpressed);
        }

        if("3".equals(buttonpressed)){
            Log.e(TAG,"Button  "+b.getText()+" pressed");
            response =  Integer.parseInt(buttonpressed);
        }

        if("4".equals(buttonpressed)){
            Log.e(TAG,"Button  "+b.getText()+" pressed");
            response =  Integer.parseInt(buttonpressed);
        }

        handler.removeCallbacks(r);
        handler.postDelayed(r,0);
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