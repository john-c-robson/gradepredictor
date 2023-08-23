package com.example.mobile_dev_assignment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class OverviewActivity extends AppCompatActivity implements View.OnClickListener {

    GlobalClass globalClass;
    ArrayList <ModuleClass> moduleClasses;
    int id;

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);

        listView = findViewById(R.id.listview);

        globalClass = (GlobalClass) this.getApplication();
        id = globalClass.getUserID();
        moduleClasses = getAllModuleInfo(id);

        OverviewAdapter adapter = new OverviewAdapter(this, moduleClasses);
        listView.setAdapter(adapter);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_button:
                Log.i("back button", "back button pressed");
                finish();
                break;
            default:
                break;
        }
    }

    public ArrayList<ModuleClass> getAllModuleInfo (int projectID)
    {
        ProjectDBO projectDBO = new ProjectDBO(OverviewActivity.this);
        ArrayList <ModuleClass> moduleClasses = projectDBO.getAllModules(projectID);
        return moduleClasses;
    }



}