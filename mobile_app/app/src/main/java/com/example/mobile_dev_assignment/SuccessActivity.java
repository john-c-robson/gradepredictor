package com.example.mobile_dev_assignment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class SuccessActivity extends AppCompatActivity implements View.OnClickListener{

    int reset = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success);

        //Activity is a success prompt for the user that typically lands on the Dashboard activity,
        //In the case they have landed here from the project deletion activity, a extras check is done,
        //if any value is detected the user to directed to the mainActivity to avoid errors and
        //out of bound access.

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            reset = extras.getInt("key");
        }
    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.Continue_button)
        {
            Log.i("Continue test", "Submit button Pressed");
            if (reset != 0) {
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            }
            else {
                Intent intent = new Intent(this, DashboardActivity.class);
                startActivity(intent);
            }
        }

    }

    @Override
    public void onBackPressed() {

    }

}