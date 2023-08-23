package com.example.mobile_dev_assignment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DashboardActivity extends AppCompatActivity implements View.OnClickListener{

    TextView welcome;
    GlobalClass globalClass; //Used to get or set the currently used projectID
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        welcome = (TextView) findViewById(R.id.Welcome_Textview);
        TextView prompt = (TextView) findViewById(R.id.empty_textview);
        globalClass = (GlobalClass) this.getApplication();
        Button overview = (Button) findViewById(R.id.Overview_button);
        Button gradetrend = (Button) findViewById(R.id.GradeTrend_button);
        Button edit = (Button) findViewById(R.id.EditEntry_button);
        id = globalClass.getUserID();


        welcome.setText("Welcome " + GetProjectName(id) + "!");

        int totalModules = GetModuleCount(id);
        if (totalModules == 0){
            overview.setVisibility(View.GONE);
            gradetrend.setVisibility(View.GONE);
            edit.setVisibility(View.GONE);
            prompt.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.back_button:
                Log.i("Logout Button test", "Logout Button Pressed");
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.NewEntry_button:
                Log.i("New Entry test", "New Entry Button Pressed");
                Intent intent1 = new Intent(this, NewEntryActivity.class);
                startActivity(intent1);
                break;
            case R.id.EditEntry_button:
                Log.i("Edit Entry test", "Edit Entry Button Pressed");
                Intent intent2 = new Intent(this, SelectEntryActivity.class);
                startActivity(intent2);
                break;
            case R.id.Overview_button:
                Log.i("Overview test", "Overview Button Pressed");
                Intent intent3 = new Intent(this, OverviewActivity.class);
                startActivity(intent3);
                break;
            case R.id.GradeTrend_button:
                Log.i("Grade Trend test", "Grade Trend Button Pressed");
                Intent intent4 = new Intent(this, GradeTrendActivity.class);
                startActivity(intent4);
                break;
            case R.id.delete_button:
                Log.i("Delete test", "Delete Button Pressed");
                Intent intent5 = new Intent(this, DeleteProjectActivity.class);
                intent5.putExtra("key", 1);
                startActivity(intent5);
                break;
            default:
                break;
        }
    }

    public String GetProjectName (int i) {
        Log.i("projectcounter",Integer.toString(i));
        ProjectDBO projectDBO = new ProjectDBO(DashboardActivity.this);
        ProjectClass projectClass = projectDBO.getProjectDetails(i);
        Log.i("projectname",projectClass.getName());
        return projectClass.getName();
    }
    public int GetModuleCount (int i) {
        Log.i("Project ID: ",Integer.toString(i));
        ProjectDBO projectDBO = new ProjectDBO(DashboardActivity.this);
        int total = projectDBO.getModuleCount(i);
        Log.i("Total Module Count: ",String.valueOf(total));
        return total;
    }

}