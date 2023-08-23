package com.example.mobile_dev_assignment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ActivityRegister extends AppCompatActivity implements View.OnClickListener{

    EditText name;
    EditText course;
    GlobalClass globalClass; //Used to get or set the currently used projectID



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        name = (EditText) findViewById(R.id.Profile_Name);
        course = (EditText) findViewById(R.id.Profile_Course);

        //Used to get or set the currently used projectID
        globalClass = (GlobalClass) this.getApplication();
    }

    @Override
    public void onClick(View view) {

        switch (view.getId())
        {
            case R.id.back_button:
                Log.i("Back Button Test", "Back Button Pressed");
                globalClass.setUserID(-1); //None value within database to stop incorrect access
                finish();
                break;
            case R.id.Submit_button:
                Log.i("Submit test", "Submit button Pressed");
                Log.i("Submit test", name.getText().toString());
                Log.i("Submit test", course.getText().toString());

                //sanitation checks for database entries as database requests NULL values
                boolean safe = false;

                if((name.getText().toString() != null) && (!name.getText().toString().isEmpty())) {
                    if((course.getText().toString() != null) && (!course.getText().toString().isEmpty())) {
                        safe = true;
                    }
                    else{
                        Toast.makeText(ActivityRegister.this, "Please input your Name!", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(ActivityRegister.this, "Please input Project Name!", Toast.LENGTH_SHORT).show();
                }
                //if sanitation is safe {
                if (safe) {

                    ProjectClass projectClass;

                    try {
                        projectClass = new ProjectClass(-1, name.getText().toString(), course.getText().toString());
                        Toast.makeText(ActivityRegister.this, projectClass.toString(), Toast.LENGTH_SHORT).show();

                        ProjectDBO projectdbo = new ProjectDBO(ActivityRegister.this);

                        boolean success = projectdbo.addProject(projectClass);

//                        Toast.makeText(ActivityRegister.this, "Success= " + success, Toast.LENGTH_SHORT).show();

                        //direct user to profile success if successful

                        if (success) {
                            globalClass.setUserID(getId(name.getText().toString()));
                            Intent intent = new Intent(this, SuccessActivity.class);
                            startActivity(intent);
                            break;
                        }

                    }
                    catch (Exception e) {
                        Toast.makeText(ActivityRegister.this, "Error Creating Entry", Toast.LENGTH_SHORT).show();
                    }

                }
            default:
                break;
        }

    }

    public int getId (String name)
    {
        Log.i("projectname",name);
        ProjectDBO projectDBO = new ProjectDBO(ActivityRegister.this);
        int id = projectDBO.getProjectID(name);
        Log.i("projectcounter",Integer.toString(id));
        return id;
    }
}