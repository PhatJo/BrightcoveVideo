package com.example.test.video;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class MainActivity extends Activity {

    private static final int START_VIDEO_REQUEST = 0;
    private static int VIDEO_ACTIVITY_LAUNCHES = 0;
    private static int MAX_VIDEO_ACTIVITY_LAUNCHES = 30;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = (Button) findViewById(R.id.button);
        final EditText text = (EditText) findViewById(R.id.launch_editor);
        text.setText(Integer.toString(MAX_VIDEO_ACTIVITY_LAUNCHES));

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VIDEO_ACTIVITY_LAUNCHES = 1;
                String runText = text.getText().toString();
                if (runText != null && !TextUtils.isEmpty(runText)) {
                    MAX_VIDEO_ACTIVITY_LAUNCHES = Integer.parseInt(runText);;
                }
                Log.d("MainActivity", "MainActivity max launches: " + MAX_VIDEO_ACTIVITY_LAUNCHES);
                Intent intent = new Intent(getApplicationContext(), VideoActivity.class);
                startActivityForResult(intent, START_VIDEO_REQUEST);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("MainActivity", "destroy. activity launches: " + VIDEO_ACTIVITY_LAUNCHES);
        if (requestCode == START_VIDEO_REQUEST) {
            if (resultCode == RESULT_OK && VIDEO_ACTIVITY_LAUNCHES < MAX_VIDEO_ACTIVITY_LAUNCHES) {
                VIDEO_ACTIVITY_LAUNCHES++;
                Intent intent = new Intent(getApplicationContext(), VideoActivity.class);
                startActivityForResult(intent, START_VIDEO_REQUEST);
            }
        }
    }
}
