package com.example.mobile_dev_assignment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DeleteProjectActivity extends AppCompatActivity implements View.OnClickListener{
    TextView prompt;
    int projectID;
    GlobalClass globalClass = new GlobalClass();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_project);

        projectID = globalClass.getUserID();

        prompt = (TextView) findViewById(R.id.check_textview);

        ProjectClass projectClass = getProjectInfo(projectID);

        prompt.setText("Are you sure you want to delete Project: \n \n" + projectClass.getCourse());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_button:
                Log.i("Back Button test", "Back Button Pressed");
                finish();
                break;
            case R.id.delete_button:
                Log.i("delete Button test", "delete Button Pressed");
                deleteProject(projectID);
                Intent intent = new Intent(this, SuccessActivity.class);
                intent.putExtra("key", 1337);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    public ProjectClass getProjectInfo (int projectID)
    {
        ProjectDBO projectDBO = new ProjectDBO(DeleteProjectActivity.this);
        ProjectClass projectClass = projectDBO.getProjectDetails(projectID);
        return projectClass;
    }

    public void deleteProject (int projectID)
    {
        ProjectDBO projectDBO = new ProjectDBO(DeleteProjectActivity.this);
        projectDBO.deleteProject(projectID);
    }
}