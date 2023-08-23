package com.example.mobile_dev_assignment;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;



public class GradeTrendActivity extends AppCompatActivity implements View.OnClickListener{

    TextView predictiontv;
    TextView average;
    ArrayList<ModuleClass> moduleClasses;
    GlobalClass globalClass = new GlobalClass();

    String query = "";
    int [] x;
    int [] y;

    int [] p;


    GraphView graph;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grade_trend);

        predictiontv = (TextView) findViewById(R.id.prediction_textview);
        average = (TextView) findViewById(R.id.average_textview);
        graph = (GraphView) findViewById(R.id.graph);
        globalClass = (GlobalClass) this.getApplication();
        moduleClasses = getAllModuleInfo(globalClass.getUserID());

        int count = moduleClasses.size();
        x = new int[count];
        y = new int[count];
        int total = 0;

        //parsing grade list into array for graph plotting
        for (int i =0; i < count; i++){
            x [i] = moduleClasses.get(i).getModMark();
            y [i] = i+1;
            //creating a query builder for requesting linear regression from api
            if(i == count-1) query = query + x[i];
            else query = query + x[i] + ",";
            total = total + x[i];
        }

        int avg = total/count;
        average.setText("Your Average Grade is: " + avg);

        //Start background thread to pull API data
        new SyncData(query).execute();

        //Draw the graph with current accessible information
        makeGraph(x,y);
    }

    public ArrayList<ModuleClass> getAllModuleInfo (int projectID)
    {
        ProjectDBO projectDBO = new ProjectDBO(GradeTrendActivity.this);
        ArrayList <ModuleClass> moduleClasses = projectDBO.getAllModules(projectID);
        return moduleClasses;
    }

    //Stop the page from being cached ensuring the API and graph are refreshed with each load
    //If this was going to be a larger project, a check would be done on database changes
    //if had been changed the page would reload, otherwise the cache would be loaded.
    @Override
    public void onBackPressed() {
        finish();
    }


    void makeGraph(int[] x, int[] y) {

        //load in all the x and y points into a series and plot
        DataPoint[] data = new DataPoint[y.length];
        for (int i = 0; i < y.length; i++) {
            data[i] = new DataPoint(y[i], x[i]);
        }
        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(data);
        series.setColor(Color.rgb(226, 91, 34));
        series.setDrawBackground(true);
        series.setBackgroundColor(Color.argb(80, 95, 226, 156));
        series.setDrawDataPoints(true);
        series.setDataPointsRadius(15);
        graph.addSeries(series);

    }

    class SyncData extends AsyncTask<String, String, String> {

        String values;
        SyncData(String values){
            this.values = values;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            //load in the JSON response from webResponse
            try {
                JSONObject obj = new JSONObject(s);
                JSONArray guess = obj.getJSONArray("guess");
                String guesstring = guess.getString(0);

                //removing the square brackets to allow parsing to int
                guesstring = guesstring.replace("[","");
                guesstring = guesstring.replace("]","");

                //ensuring the prediction doesn't go beyond the marks range.
                int newguess = Integer.parseInt(guesstring);
                if (newguess > 100) newguess = 100;
                if (newguess < 0) newguess = 0;

                //parsing the linear regression points into an arraylist for graphing
                JSONArray prediction = obj.getJSONArray("prediction");
                ArrayList <Integer> preds = new ArrayList<Integer>();
                for (int i = 0; i < prediction.length(); i++){

                    String step = prediction.getString(i);
                    //removing the square brackets to allow parsing to int
                    step = step.replace("[","");
                    step = step.replace("]","");
                    preds.add(Integer.valueOf(step));

                }

                //with new arraylist, draw new datapoint to graph
                DataPoint[] data2 = new DataPoint[y.length];
                for (int i = 0; i < y.length; i++) {
                    data2[i] = new DataPoint(y[i], preds.get(i));
                }
                LineGraphSeries<DataPoint> series2 = new LineGraphSeries<DataPoint>(data2);
                series2.setColor(Color.rgb(20, 240, 34));
                series2.setDrawDataPoints(true);
                series2.setDataPointsRadius(15);
                graph.addSeries(series2);

                predictiontv.setText("Your next predicted grade is: " + newguess);
                Log.i("Total Module Count: ","" + preds.get(0));

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected String doInBackground(String... strings) {

            BufferedReader reader = null;
            HttpURLConnection connection = null;
            StringBuilder builder = new StringBuilder();

            //try to connect to API, if successful read in the response
            try {
                URL url = new URL("https://loxton.pythonanywhere.com/example?marks="+values);

                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                while (true) {

                    String readLine = reader.readLine();
                    String data = readLine;

                    if (data == null)
                    {
                        break;
                    }
                    builder.append(data);
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            return builder.toString();
        }
    }
    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.back_button:
                Log.i("Back Button test", "Back Button Pressed");
                finish();
                break;
            default:
                break;
        }

    }
}


