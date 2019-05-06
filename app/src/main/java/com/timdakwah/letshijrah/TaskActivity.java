package com.timdakwah.letshijrah;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class TaskActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        Button Daily_Button = findViewById(R.id.task_daily_button);
        Daily_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TaskActivity.this, DailyActivity.class);
                startActivity(i);
            }
        });

        Button Challenge_Button = findViewById(R.id.task_challange_button);
        Challenge_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TaskActivity.this, ChallengeActivity.class);
                startActivity(i);
            }
        });
    }
}
