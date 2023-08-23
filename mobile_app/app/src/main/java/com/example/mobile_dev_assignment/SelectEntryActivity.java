package com.example.mobile_dev_assignment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SelectEntryActivity extends AppCompatActivity implements View.OnClickListener, SelectListener{

    GlobalClass globalClass = new GlobalClass();
    ArrayList<ModuleClass> moduleClasses;

    SelectListener listener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_entry);

        //layout control for buttons generated
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.mRecView);
        moduleClasses = getAllModuleInfo(globalClass.getUserID());

        M_RecyclerViewAdapter adapter = new M_RecyclerViewAdapter(this, moduleClasses, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        globalClass = (GlobalClass) this.getApplication();
    }

    @Override
    public void onClick(View view) {

        switch (view.getId())
        {
            case R.id.back_button:
                Log.i("back button","back button pressed");
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    public void onItemClicked(int id, int value) {

        //used to pass the buttonID and ModuleID to a intent path [M_RecyclerViewAdapter, Line 94],
        // as Recycler is out of scope of the view so intent creation is impossible.

        switch (id) {
            case R.id.edit_Button:
                editIntent(String.valueOf(value));
                break;
            case R.id.delete_Button:
                deleteIntent(String.valueOf(value));
                break;
            default:
                break;
        }
    }

    public ArrayList<ModuleClass> getAllModuleInfo (int projectID)
    {
        ProjectDBO projectDBO = new ProjectDBO(SelectEntryActivity.this);
        ArrayList <ModuleClass> moduleClasses = projectDBO.getAllModules(projectID);
        return moduleClasses;
    }

    public void editIntent (String id)
    {
        Intent intent = new Intent(this, UpdateEntryActivity.class);
        Log.i("test",String.valueOf(id));
        intent.putExtra("key", id);
        startActivity(intent);
    }

    public void deleteIntent (String id)
    {
        Intent intent = new Intent(this, DeleteEntryActivity.class);
        Log.i("test",String.valueOf(id));
        intent.putExtra("key", id);
        startActivity(intent);
    }
}