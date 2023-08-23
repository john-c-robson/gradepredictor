package com.example.mobile_dev_assignment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    GlobalClass globalClass;
    ArrayList<ProjectClass> projectClasses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Gather the project entries
        projectClasses = GetProjectDetail();
        LinearLayout ll = (LinearLayout) findViewById(R.id.root_layout);

        //Before learning about listview and recycle view, generated the project intents programmatically
        //but rather than removing this, left it in to display learning process
        for (int i = 0; i < projectClasses.size(); i++) {

            Button myButton = new Button(this);
            myButton.setId(projectClasses.get(i).getId());
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(500, 150);
            layoutParams.setMargins(0, 0, 0, 70);
            myButton.setLayoutParams(layoutParams);

            myButton.setText(projectClasses.get(i).getCourse());
            ll.addView(myButton);
            myButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view)
                {
                    Button b = (Button) view;
                    //as only a single button, set the button ID to be the projectID, later found out buttons
                    //have a "tag" variable for this job, left in to show learning.
                    int id = b.getId();
                    globalClass.setUserID(id);
                    //Toast.makeText(MainActivity.this, Integer.toString(globalClass.getUserID()), Toast.LENGTH_SHORT).show();
                    Log.i("Profile test", "Login Button Pressed");
                    NextIntent();
                }
            });
        }
        globalClass = (GlobalClass) this.getApplication();
    }

    @Override
    public void onClick(View view) {

        switch (view.getId())
        {
            case R.id.NewProfileButton:
                //Due to not using a scrollable view, a limitation is put in to ensure elements are morphed
                //to a unusable state or drawing out of bounds of the view. Left in again to show learning.
                int count = GetProjectCount();
                if (count > 4) {
                    //Prompt the user about the restriction, this could be updated but again, feel its better to
                    //demonstrate my understanding throughout the project.
                    Toast.makeText(MainActivity.this, "Only 5 Projects are currently supported",Toast.LENGTH_SHORT).show();
                    break;
                }
                Log.i("New Profile test", "New Profile Button Pressed");
                Intent intent2 = new Intent(this, ActivityRegister.class);
                startActivity(intent2);
                break;
            default:
                break;
        }
    }

    //disabling backbutton to avoid incorrect project access
    @Override
    public void onBackPressed() {

    }

    //This was the original method to get the button count, but using getClasses method
    //was cleaner and i couldn't resist updating the code for this.

//    public int GetButtonCount () {
//        ProjectDBO projectDBO = new ProjectDBO(MainActivity.this);
//        int count = projectDBO.getProjectCount();
//        Log.i("projectcount",Integer.toString(count));
//        return count;
//    }


    public int GetProjectCount () {
        ProjectDBO projectDBO = new ProjectDBO(MainActivity.this);
        int count = projectDBO.getProjectCount();
        Log.i("projectCount",String.valueOf(count));
        return count;
    }
    public ArrayList<ProjectClass> GetProjectDetail () {
        ProjectDBO projectDBO = new ProjectDBO(MainActivity.this);
        ArrayList<ProjectClass> projectClasses = projectDBO.getAllProjects();
        return projectClasses;
    }

    public void NextIntent () {
        Intent intent = new Intent(this, DashboardActivity.class);
        startActivity(intent);
    }

}