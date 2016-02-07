package com.seanwin.crunchtime;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Spinner spinner = (Spinner) findViewById(R.id.exercise_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.exercises_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        TableLayout table = (TableLayout)findViewById(R.id.exercise_table);
        String[] exercises = getResources().getStringArray(R.array.exercises_array);
        String[] rates = getResources().getStringArray(R.array.rates_array);
        for (int i = 0; i < exercises.length; i++) {
            TableRow tr = new TableRow(this);
            TextView count = new TextView(this);
            TextView label = new TextView(this);
            TextView rate = new TextView(this);

            count.setText("0");
            label.setText(exercises[i]);
            rate.setText(rates[i]);
            rate.setVisibility(View.INVISIBLE);

            tr.addView(count);
            tr.addView(label);
            tr.addView(rate);
            table.addView(tr);
        }
    }

    public void refreshTable(View view) {
        TableLayout table = (TableLayout)findViewById(R.id.exercise_table);
        String repsString = ((TextView)findViewById(R.id.input_units)).getText().toString();
        float inputReps = Float.parseFloat(repsString);
        String inputExercise = ((Spinner)findViewById(R.id.exercise_spinner)).getSelectedItem().toString();
        float inputRate = -1;

        // find row == inputExercise
        for (int j = 2; j < table.getChildCount(); j++) {
            TableRow row = (TableRow) table.getChildAt(j);
            String exercise = (String) ((TextView) row.getChildAt(1)).getText();
            Log.d("curExercise = ", exercise);
            Log.d("iputExercise = ", inputExercise);
            Log.d("equal?? ", String.valueOf(exercise.equals(inputExercise)));

            if (exercise.equals(inputExercise)) {
                String rateString = (String)((TextView)row.getChildAt(2)).getText();
                inputRate = Float.parseFloat(rateString);
                break;
            }
        }

        // refresh values for each exercise (row)
        for (int i = 2; i < table.getChildCount(); i++) {
            TableRow row = (TableRow) table.getChildAt(i);
            String rateString = (String)((TextView)row.getChildAt(2)).getText();
            float thisRate = Float.parseFloat(rateString);

            TextView outputReps = (TextView) row.getChildAt(0);
            float calc = inputReps / inputRate * thisRate;
            outputReps.setText(String.valueOf(calc));
        }
    }
//    public float getInputRate(String inputExercise) {
//        String[] exercises = getResources().getStringArray(R.array.exercises_array);
//        for (int i = 0; i < exercises.length; i++) {
//            String[] data = exercises[i].split("-");
//            if (data[1] == inputExercise) {
//                return Float.parseFloat(data[0]);
//            }
//        }
//        return -1;
//    }

//    // outputreps = inputReps / map(input-rate) * map(cur-label-rate)
//    public String convert(String inputReps, float inputRate, int outputExerciseIndex) {
//        float reps = Float.parseFloat(inputReps);
//        float inputRate = 0, outputRate = 0;
//
//        String[] exercises = getResources().getStringArray(R.array.exercises_array);
//        for (int i = 0; i < exercises.length; i++) {
//            String[] data = exercises[i].split("-");
//            if (data[1] == inputExercise) {
//                inputRate = Float.parseFloat(data[0]);
//            }
//        }
//        for (int j = 0; j < exercises.length; j++) {
//            if (j == outputExerciseIndex) {
//                String[] data = exercises[j].split("-");
//                outputRate = Float.parseFloat(data[0]);
//            }
//        }
//        return "" + (reps / inputRate * outputRate);
//    }

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)
        Spinner spinner = (Spinner) findViewById(R.id.exercise_spinner);
        spinner.setOnItemSelectedListener(this);
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
