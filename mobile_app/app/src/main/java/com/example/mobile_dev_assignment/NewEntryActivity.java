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



public class NewEntryActivity extends AppCompatActivity implements View.OnClickListener{

    EditText modCode;
    EditText modName;
    EditText modMark;
    TextView viewDate;
    CalendarView calendarView;
    String modDate;
    GlobalClass globalClass;
    int userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_entry);

        modCode = (EditText) findViewById(R.id.NewModCode);
        modName = (EditText) findViewById(R.id.NewModName);
        modMark = (EditText) findViewById(R.id.NewModMark);
        viewDate = (TextView) findViewById(R.id.printdate_textview);

        modMark.setFilters(new InputFilter[]{ new InputFilterMinMax("0", "100")});
        calendarView = (CalendarView) findViewById(R.id.NewModDate);

        globalClass = (GlobalClass) this.getApplication();
        userID = globalClass.getUserID();

        viewDate.setText("Current Select Date: " + "Unselected");


        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                //i2=day, i1=month but starts at 0?, i=year
                modDate = i2 + "/" + (i1 + 1) + "/" + i;
                viewDate.setText("Current Select Date: " + modDate);
            }
        });

    }

    @Override
    public void onClick (View view)
    {
        switch (view.getId())
        {
            case R.id.back_button:
                Log.i("Back Button test", "Back Button Pressed");
                finish();
                break;
            case R.id.Create_button:
                Log.i("Create Button test", "Create Button Pressed");

                //tests
                Log.i("modCode test", modCode.getText().toString());
                Log.i("ModName test", modName.getText().toString());
                Log.i("modMark test", modMark.getText().toString());
                if (modDate != null) Log.i("modDate test", modDate);

                //sanitation checks for database entries as database requests NULL values
                boolean safe = false;

                if((modCode.getText().toString() != null) && (!modCode.getText().toString().isEmpty())) {
                    if((modName.getText().toString() != null) && (!modName.getText().toString().isEmpty())) {
                        if((modMark.getText().toString() != null) && (!modMark.getText().toString().isEmpty())) {
                            if((modDate!= null) && (!modDate.isEmpty())) {
                                safe = true;
                            }
                            else
                            {
                                Toast.makeText(NewEntryActivity.this, "Please select a date!", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else{
                            Toast.makeText(NewEntryActivity.this, "Please input a Mark!!", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        Toast.makeText(NewEntryActivity.this, "Please input a Module Name!", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(NewEntryActivity.this, "Please input a Module Code!", Toast.LENGTH_SHORT).show();
                }
                //if sanitation is safe {

                if (safe) {
                    ModuleClass moduleClass = null;

                    try {
                        moduleClass = new ModuleClass(-1, userID, modName.getText().toString(), modCode.getText().toString(), Integer.parseInt(modMark.getText().toString()), modDate);
                        Toast.makeText(NewEntryActivity.this, moduleClass.toString(), Toast.LENGTH_SHORT).show();

                        ProjectDBO projectDBO = new ProjectDBO(NewEntryActivity.this);
                        boolean success = projectDBO.addModule(moduleClass);

                        //removing testing toasts for presenting
                        //Toast.makeText(NewEntryActivity.this, "Success= " + success, Toast.LENGTH_SHORT).show();

                        //direct user to profile success if successful
                        if (success) {
                            Intent intent = new Intent(this, SuccessActivity.class);
                            startActivity(intent);
                            break;
                        }

                    } catch (Exception e) {
                        Toast.makeText(NewEntryActivity.this, "Error Creating Entry", Toast.LENGTH_SHORT).show();
                    }

                }
            default:
                break;
        }
    }
}