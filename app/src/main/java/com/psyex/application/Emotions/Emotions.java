package com.psyex.application.Emotions;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.psyex.application.ChooseMode;
import com.psyex.application.DecideOrder;
import com.psyex.application.R;

import java.io.File;
import java.util.Random;

public class Emotions extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "MAINACTIVITY";
    public static Integer[] mThumbIds = {
            R.drawable.ic_launcher_background,R.drawable.ic_launcher_foreground
    };
    String fileName = "Emotions.csv";
    //    String fileName = "Stroop "+dtf.format(now)+ ".csv";
    File file = new File(ChooseMode.userfolder.getAbsolutePath(), fileName);

    //    public int i=1;
    int imgRes;
    Runnable r;
    ConstraintLayout cl;
    ImageView iv;
    TextView valueChosen;
    SeekBar sb;
    Button nextbtn;
    String currentImage="";
    int count=0;
    final int MAX_COUNT=5;
    Long absTime;
    int queNum=0;
    int imageID=0;
    int c = 0;
    int[][] practiceImages={{R.drawable.n001,R.drawable.n002,R.drawable.n003},{R.drawable.n004,R.drawable.n006,R.drawable.n008}};//,{R.}};//,R.drawable.a005,R.drawable.a006,R.drawable.a007}};
    int[][] mode1Images = {
            {R.drawable.p067,R.drawable.p030,R.drawable.p041,R.drawable.p035,R.drawable.p028,R.drawable.p057,R.drawable.a095,R.drawable.a041,R.drawable.a100,R.drawable.h077,R.drawable.h078,R.drawable.h074},
            {R.drawable.p072,R.drawable.p070,R.drawable.p042,R.drawable.p064,R.drawable.p079,R.drawable.p056,R.drawable.h064,R.drawable.h022,R.drawable.a079,R.drawable.h122,R.drawable.h032,R.drawable.a037},
            {R.drawable.p080,R.drawable.p114,R.drawable.p071,R.drawable.p026,R.drawable.p046,R.drawable.p111,R.drawable.a030,R.drawable.h005,R.drawable.h063,R.drawable.a001,R.drawable.h100,R.drawable.a038}
    };
    int [][] mode2Images = {
                {R.drawable.p007,R.drawable.p128,R.drawable.p110,R.drawable.p092,R.drawable.p050,R.drawable.p121,R.drawable.a075,R.drawable.h038,R.drawable.a018,R.drawable.a071,R.drawable.a007,R.drawable.sp131},
            {R.drawable.p037,R.drawable.p108,R.drawable.p008,R.drawable.p055,R.drawable.p130,R.drawable.p013,R.drawable.h036,R.drawable.a083,R.drawable.a013,R.drawable.a089,R.drawable.a097,R.drawable.h079},
            {R.drawable.p045,R.drawable.p014,R.drawable.p086,R.drawable.p087,R.drawable.p095,R.drawable.p063,R.drawable.h037,R.drawable.a055,R.drawable.h034,R.drawable.sp151,R.drawable.h066,R.drawable.sp132}
    };

    int[] imageLocation;
    TextView mode_tv, question_tv, leftValue, rightValue;
    int size;
    boolean isPractice;
    int condition;
    String s="";

    void init(){
//        int imageId = getResources().getIdentifier("1", "mipmap", getPackageName());
//
//        if (imageId > 0) {
//            image.setImageResource(imageId);
//        }
        s="";
        if(isPractice){
//            size
            imageLocation=practiceImages[ChooseMode.mode-1];
        }
        else if(ChooseMode.mode==1){
            imageLocation=mode1Images[condition-1];
        }
        else{
            imageLocation=mode2Images[condition-1];
        }

        size=imageLocation.length;
    }

    static void shuffleChars(int[] ar)
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

    int getRandomImage(){
        if (c<size) {
            currentImage = getResources().getResourceEntryName(imageLocation[c]);
            return imageLocation[c++];
        }
        else {
            return -1;
        }
    }

    void setLeftRightVals(int queId){
        if(queId == R.string.question1){
            leftValue.setText("Very Negative");
            rightValue.setText("Very Positive");
        }
        else if(queId==R.string.question2){
            leftValue.setText("Calm");
            rightValue.setText("Excited");
        }
        else{
            leftValue.setText("Stimulated");
            rightValue.setText("Relaxed");
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emotions);



        isPractice = getIntent().getBooleanExtra("isPractice",true);
        condition = getIntent().getIntExtra("condition",1);

        init();

        valueChosen = (TextView) findViewById(R.id.textView_value_chosen);
        mode_tv = (TextView) findViewById(R.id.textView_mode_main);
        question_tv = (TextView) findViewById(R.id.textView_question_main);
        leftValue = (TextView) findViewById(R.id.textView_leftVal_main);
        rightValue = (TextView) findViewById(R.id.textView_rightVal_main);

        if(isPractice){
            mode_tv.setText("PRACTICE");
        }
        else{
            mode_tv.setText("CONDITION "+String.valueOf(condition));
        }

        cl = (ConstraintLayout) findViewById(R.id.cl_1);
        iv = (ImageView) findViewById(R.id.imageView);
        sb = (SeekBar) findViewById(R.id.seekBar);
        nextbtn = (Button) findViewById(R.id.button_nextQuestion);

        nextbtn.setOnClickListener(this);
        shuffleChars(imageLocation);

        imgRes = getRandomImage();
        iv.setImageResource(imgRes);
        cl.setVisibility(View.INVISIBLE);

        sb.setProgress(50);
        valueChosen.setText("50");

        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                nextbtn.setClickable(true);
                valueChosen.setText(String.valueOf(seekBar.getProgress()));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                nextbtn.setClickable(true);
                valueChosen.setText(String.valueOf(seekBar.getProgress()));
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                nextbtn.setClickable(true);
                valueChosen.setText(String.valueOf(seekBar.getProgress()));
            }
        });

        s += "Practice/Test, ";;
        s+= "Que Num" + ", " ;
        s+= "image_id" + ", ";
        s += "image_name"+", ";
        s+= "Response" + ", ";
        s+= "Resp Time" + ", ";
        s+= "\n";

        r = new Runnable() {
            public void run () {
                int nextque = getNextQue();
//                if(nextque==-1)
                question_tv.setText(nextque);
                sb.setProgress(50);
                valueChosen.setText("50");
                setLeftRightVals(nextque);
                absTime = System.currentTimeMillis();
                cl.setVisibility(View.VISIBLE);
                iv.setVisibility(View.INVISIBLE);
            }
        };
        iv.postDelayed(r, 3000);
    }

    void addResponse(int queNum,int imageID,int val,Long respTime){
//        s ="";
        s += (isPractice)?"Practice, ":"Condition "+String.valueOf(condition)+", ";
        s+= queNum + ", ";
        s+= imageID + ", ";
        s += currentImage+", ";
        s+= val + ", ";
        s+= respTime + ", " ;
        s+= "\n";
    }

    int getNextQue(){
        switch (queNum){
            case 0:
                queNum++;
                return R.string.question1;
            case 1:
                queNum++;
                return R.string.question2;
            case 2:
                queNum++;
                return R.string.question3;
            default:
                queNum=0;
                return -1;
        }
    }

    @Override
    public void onClick(View v){
        int seekValue = sb.getProgress();
        Long respTime = System.currentTimeMillis() - absTime;
        addResponse(queNum, imgRes, seekValue, respTime);
        absTime = System.currentTimeMillis();
        int nextQue = getNextQue();
        if(nextQue==-1) {
            imgRes = getRandomImage();
//            int seekValue = sb.getProgress();
//            Long respTime = System.currentTimeMillis() - absTime;
//            addResponse(queNum, imgRes, seekValue, respTime);
//            Log.d(TAG, String.valueOf(seekValue));


//        if(c<size) {
//                String mDrawableName = "myappicon";
            Log.d(TAG, "imgRes: " + imgRes);
            if (imgRes == -1) {
                DecideOrder.saveTextAsFile(s,file);
//                        DecideOrder.uploadFile(file.getAbsolutePath(),UserInfo.userId);
                DecideOrder.addFile(file.getAbsolutePath());

                if (isPractice) {
                    Intent in1 = new Intent(Emotions.this, Instructions3.class);
//                    in1.putExtra("isPractice", !isPractice);
                    finish();
                    startActivity(in1);
                }
//                    Log.d(TAG,"finishing Main");
                else if(condition==1) {
//                    Intent int1 = new Intent(Emotions.this, preInstrMain.class);
//                    int1.putExtra("text",getResources().getString(R.string.condition3_end_remarks));
//                    int1.putExtra("isPractice", !isPractice);
//                    finish();
//                    startActivity(int1);

                    finish();
                    Intent intent = new Intent(Emotions.this, Subjective1.class);
//                    intent.putExtra("condition",3);
                    intent.putExtra("condition",1);

//                    int1.putExtra("isPractice", !isPractice);
                    startActivity(intent);

//                    Intent in1 = new Intent(Emotions.this, DecideOrder.class);
//                    finish();
//                    startActivity(in1);
                }
                else if(condition==2){
//                    Intent int1 = new Intent(Emotions.this, preInstrMain.class);
//                    int1.putExtra("text",getResources().getString(R.string.condition1_end_remarks));
//                    startActivity(int1);
                    finish();
//                    startActivity(new Intent(Emotions.this, condition2_instr.class));
                    Intent intent = new Intent(Emotions.this, Subjective1.class);
                    intent.putExtra("condition",2);
                    startActivity(intent);
                }
                else if(condition==3){
//                    Intent int1 = new Intent(Emotions.this, preInstrMain.class);
//                    int1.putExtra("text",getResources().getString(R.string.condition2_end_remarks));
//                    startActivity(int1);
                    finish();
                    Intent intent = new Intent(Emotions.this, Subjective1.class);
                    intent.putExtra("condition",3);
                    startActivity(intent);
                }
            } else {
                iv.setImageResource(imgRes);
                cl.setVisibility(View.INVISIBLE);
                iv.setVisibility(View.VISIBLE);
                nextbtn.setClickable(false);
                if (c <= size)
                    iv.postDelayed(r, 4000);
                else iv.postDelayed(r, 0);

            }
        }
        else{
            question_tv.setText(nextQue);
            setLeftRightVals(nextQue);


            sb.setProgress(50);
            valueChosen.setText("50");
        }



//        else iv.postDelayed(r,0);
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
