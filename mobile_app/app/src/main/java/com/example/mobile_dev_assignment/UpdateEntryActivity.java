package com.example.mobile_dev_assignment;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.Log;
import android.view.View;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class UpdateEntryActivity extends AppCompatActivity implements View.OnClickListener{

    EditText modCode;
    EditText modName;
    EditText modMark;
    TextView viewDate;
    CalendarView calendarView;
    String modDate;
    GlobalClass globalClass;
    int userID;

    int modID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_entry);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String value = extras.getString("key");
            modID = Integer.parseInt(value);
        }

        modCode = (EditText) findViewById(R.id.editModCode);
        modName = (EditText) findViewById(R.id.editModName);
        modMark = (EditText) findViewById(R.id.editModMark);
        viewDate = (TextView) findViewById(R.id.printdate_textview);

        modMark.setFilters(new InputFilter[]{ new InputFilterMinMax("0", "100")});
        calendarView = (CalendarView) findViewById(R.id.editModDate);


        ModuleClass modClass = getModuleInfo(modID);
        modCode.setText(modClass.getModCode());
        modName.setText(modClass.getModName());
        modMark.setText(Integer.toString(modClass.getModMark()));
        modDate = modClass.getModDate();
        viewDate.setText("Current Select Date: " + modDate);

        globalClass = (GlobalClass) this.getApplication();
        userID = globalClass.getUserID();

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                modDate = i2 + "/" + (i1 + 1) + "/" + i;
                viewDate.setText("Current Select Date: " + modDate);
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_button:
                Log.i("Back Button test", "Back Button Pressed");
                finish();
                break;
            case R.id.update_button:
                Log.i("Update Button test", "Update Button Pressed");

                //tests
                Log.i("modCode test", modCode.getText().toString());
                Log.i("ModName test", modName.getText().toString());
                Log.i("modMark test", modMark.getText().toString());
                if (modDate != null) Log.i("modDate test", modDate);

                //sanitisation checks
                boolean safe = false;

                if((modCode.getText().toString() != null) && (!modCode.getText().toString().isEmpty())) {
                    if((modName.getText().toString() != null) && (!modName.getText().toString().isEmpty())) {
                        if((modMark.getText().toString() != null) && (!modMark.getText().toString().isEmpty())) {
                            if((modDate!= null) && (!modDate.isEmpty())) {
                                safe = true;
                            }
                            else
                            {
                                Toast.makeText(UpdateEntryActivity.this, "Please select a date!", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else{
                            Toast.makeText(UpdateEntryActivity.this, "Please input a Mark!!", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        Toast.makeText(UpdateEntryActivity.this, "Please input a Module Name!", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(UpdateEntryActivity.this, "Please input a Module Code!", Toast.LENGTH_SHORT).show();
                }

                //if data is safe, do database!
                if (safe) {
                    ModuleClass moduleClass = null;
                    try {
                        moduleClass = new ModuleClass(modID, userID, modName.getText().toString(), modCode.getText().toString(), Integer.parseInt(modMark.getText().toString()), modDate);
                        Toast.makeText(UpdateEntryActivity.this, moduleClass.toString(), Toast.LENGTH_SHORT).show();
                        ProjectDBO projectDBO = new ProjectDBO(UpdateEntryActivity.this);

                        boolean success = projectDBO.moduleUpdate(moduleClass, modID);
                        Toast.makeText(UpdateEntryActivity.this, "Success= " + success, Toast.LENGTH_SHORT).show();

                        //if update was successful!
                        if (success) {
                            Intent intent = new Intent(this, SuccessActivity.class);
                            startActivity(intent);
                            break;
                        }
                    } catch (Exception e) {
                        Toast.makeText(UpdateEntryActivity.this, "Error Creating Entry", Toast.LENGTH_SHORT).show();
                    }
                }
            default:
                break;
        }
    }

    public ModuleClass getModuleInfo (int modID)
    {
        ProjectDBO projectDBO = new ProjectDBO(UpdateEntryActivity.this);
        ModuleClass moduleClass = projectDBO.getModuleDetails(modID);
        return moduleClass;
    }


}