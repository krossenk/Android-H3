package com.jussila.hi3201270916;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;


public class AActivity extends ActionBarActivity implements View.OnClickListener {

    private Button alarmButton;
    private EditText secondsInput;
    private ProgressBar alarmProgress;
    private Intent startAlarmServiceIntent;
    private Intent startActivityBIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a);

        alarmProgress = (ProgressBar) findViewById(R.id.progressBar);
        startAlarmServiceIntent = new Intent(this, AlarmService.class);
        startActivityBIntent = new Intent(this, BActivity.class);
        alarmButton = (Button) findViewById(R.id.btnAlarm);
        secondsInput = (EditText) findViewById(R.id.etSeconds);

        alarmButton.setOnClickListener(this);

        this.registerReceiver(mBroadcastReceiver, new IntentFilter("alarmDone"));
        this.registerReceiver(progressBroadcastReceiver, new IntentFilter("alarmProgress"));
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btnAlarm){
            int s;
            if(secondsInput.getText().toString().equals("")){ // If input is empty, set alarm for 0 seconds
                s = 0;
            }else{
                s = Integer.parseInt(secondsInput.getText().toString());
            }
            startAlarmServiceIntent.putExtra("amountOfSeconds", s);
            startService(startAlarmServiceIntent);

            alarmProgress.setMax(s);
            alarmProgress.setProgress(s);
        }
    }

    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            startActivity(startActivityBIntent);
        }
    };

    private BroadcastReceiver progressBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            alarmProgress.setProgress(intent.getIntExtra("secondsRemaining", 0));
        }
    };

}
