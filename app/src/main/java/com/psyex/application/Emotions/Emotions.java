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
    int count=0;
    final int MAX_COUNT=5;
    Long absTime;
    int queNum=0;
    int imageID=0;
    int c = 0;
    int[] practiceImages={R.drawable.a001,R.drawable.a002,R.drawable.a004};//,R.drawable.a005,R.drawable.a006,R.drawable.a007};
    int[][] mode1Images = {{R.drawable.a008,R.drawable.a009,R.drawable.a010},{R.drawable.a008,R.drawable.a009,R.drawable.a010},{R.drawable.a008,R.drawable.a009,R.drawable.a010}};//,R.drawable.a011,R.drawable.a013,R.drawable.a014};
    int [][] mode2Images = {{R.drawable.a015,R.drawable.a016,R.drawable.a017},{R.drawable.a015,R.drawable.a016,R.drawable.a017},{R.drawable.a015,R.drawable.a016,R.drawable.a017}};//,R.drawable.a018,R.drawable.a019,R.drawable.a020};
    //    String imageLocation[] = {"ic_launcher_background","ic_star_black_24dp"};
//    int size = imageLocation.length;
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
            imageLocation=practiceImages;
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
                    Intent intent = new Intent(Emotions.this, Instructions5.class);
//                    intent.putExtra("condition",3);
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
