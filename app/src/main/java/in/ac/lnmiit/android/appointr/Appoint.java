
package in.ac.lnmiit.android.appointr;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.Time;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import static android.os.Build.VERSION_CODES.M;

public class Appoint extends AppCompatActivity  {
    private Toolbar toolbar1;
    Button date ;
    Calendar calendar1 = Calendar.getInstance();
    Calendar calendar = Calendar.getInstance();
    TextView dispDate;
    Button time;
    TextView dispTime;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.appoint_page);

        toolbar1 = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar1);
        getSupportActionBar().setTitle("TAKE APPOINTMENT");

        Button appoint = (Button) findViewById(R.id.appoint_button);
        appoint.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent intent = new Intent(Appoint.this, S_Home.class);
                startActivity(intent);
                // Do something in response to button click
            }
        });

        if(getSupportActionBar()!=null){

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        dispDate = (TextView)findViewById(R.id.showDate);
        date = (Button)findViewById(R.id.set_date);
        date.setOnClickListener( new View.OnClickListener(){

            public void onClick(View v){

              new DatePickerDialog(Appoint.this,listener,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();

            }
        });


        dispTime = (TextView)findViewById(R.id.timeDisplay);
        time = (Button)findViewById(R.id.pickTime);
        time.setOnClickListener(new View.OnClickListener(){

            public void onClick (View view){
                new TimePickerDialog(Appoint.this,onTimeSetListener,calendar1.get(Calendar.HOUR_OF_DAY),calendar1.get(Calendar.MINUTE),true).show();
            }
        });
    }
    DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener(){
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth){
            dispDate.setText(dayOfMonth + "/" + (monthOfYear+1) + "/" + year);
        }
    };

   TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener(){
        public void onTimeSet(TimePicker view, int hourOfDay, int minute ){

             dispTime.setText(hourOfDay + ":" + minute);
        }
   };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }
}