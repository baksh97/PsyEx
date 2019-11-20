package com.psyex.application.Emotions;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.psyex.application.R;
import com.psyex.application.Timer;

public class Instructions8 extends AppCompatActivity {

    Button continueBtn;
//    TextView instruc

    void initViews(){
        continueBtn= (Button) findViewById(R.id.button_continue_instruction_emotions8);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructions_emotions8);

        initViews();

        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Instructions8.this, Timer.class);
                intent.putExtra(getString(R.string.practice_extra), false);
                intent.putExtra("condition",3);
                Timer.nextAct = Emotions.class;
                finish();
//                intent.putExtra("correctChar","P");
                startActivity(intent);
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
