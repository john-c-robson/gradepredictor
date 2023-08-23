package com.example.mobile_dev_assignment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DeleteEntryActivity extends AppCompatActivity implements View.OnClickListener{

    TextView prompt;
    int modID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_entry);

        prompt = (TextView) findViewById(R.id.check_textview);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String value = extras.getString("key");
            modID = Integer.parseInt(value);
        }

        ModuleClass modClass = getModuleInfo(modID);

        prompt.setText("Are you sure you want to delete module: \n \n" + modClass.getModName());
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
                deleteModule(modID);
                Intent intent = new Intent(this, SuccessActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    public ModuleClass getModuleInfo (int modID)
    {
        ProjectDBO projectDBO = new ProjectDBO(DeleteEntryActivity.this);
        ModuleClass moduleClass = projectDBO.getModuleDetails(modID);
        return moduleClass;
    }

    public void deleteModule (int modID)
    {
        ProjectDBO projectDBO = new ProjectDBO(DeleteEntryActivity.this);
        projectDBO.deleteModule(modID);
    }

}