
package in.ac.lnmiit.android.appointr.Activities;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.BlockingQueue;

import in.ac.lnmiit.android.appointr.DatabaseConnections.DatabaseCalls;
import in.ac.lnmiit.android.appointr.DatabaseConnections.DatabaseHelper;
import in.ac.lnmiit.android.appointr.Functions.AppStatus;
import in.ac.lnmiit.android.appointr.Functions.SessionManagement;
import in.ac.lnmiit.android.appointr.R;
import in.ac.lnmiit.android.appointr.models.Faculty;
import in.ac.lnmiit.android.appointr.models.General_Query;

public class Appoint extends AppCompatActivity {
    private static final String TAG = Appoint.class.getSimpleName();
    Calendar calendar1 = Calendar.getInstance();
    Calendar calendar = Calendar.getInstance();
    TextView dispDate;
    TextView dispTime;
    SessionManagement session;
    private Toolbar toolbar1;
    private String depart;
    private String facul;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.appoint_page);
        session = new SessionManagement(getApplicationContext());


        final DatabaseHelper help = new DatabaseHelper(getApplicationContext());
        final List<String> categories = help.getDepartments();
        final Spinner spinner = (Spinner) findViewById(R.id.department_spin);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, R.layout.spinner_layout, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                depart = parent.getItemAtPosition(position).toString();
                List<String> faculty  = help.getFacultyFromDepartments(depart);

                final Spinner spinner1 = (Spinner) findViewById(R.id.faculty_spin);
                ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_layout, faculty);
                dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner1.setAdapter(dataAdapter1);
                spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        facul = parent.getItemAtPosition(position).toString();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        ((TextView)spinner1.getSelectedView()).setError("Not Selected");
                    }
                });

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                ((TextView)spinner.getSelectedView()).setError("Not Selected");
            }
        });


        toolbar1 = (Toolbar) findViewById(R.id.appoint_toolbar);
        setSupportActionBar(toolbar1);
        ImageView appoint = (ImageView) findViewById(R.id.back);
        appoint.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
               onBackPressed();
                // Do something in response to button click
            }
        });

        ImageView appoint1 = (ImageView) findViewById(R.id.send);
        appoint1.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                if (AppStatus.getInstance(getApplicationContext()).isOnline()) {

                    int student_id = session.getID();
                    int faculty_id = 0;
                    List<Faculty> faculti = help.getFaculties();
                    for (int i = 0; i < faculti.size(); i++) {
                        if (faculti.get(i).getFaculty_name().equals(facul)) {
                            faculty_id = faculti.get(i).getUser_id();
                        }
                    }
                    TextView datet = (TextView) findViewById(R.id.date_spin);
                    String request_date = datet.getText().toString();
                    TextView timet = (TextView) findViewById(R.id.time_spin);
                    String request_time = timet.getText().toString();
                    TextView res = (TextView) findViewById(R.id.reason);
                    String reason = res.getText().toString();
                    CheckBox ur = (CheckBox) findViewById(R.id.urgent_check);
                    int urgent = ur.isChecked() ? 1 : 0;
                    PrefetchData prefetch = new PrefetchData(student_id,faculty_id,urgent,request_date,request_time,reason);
                    prefetch.execute();



                } else {

                    Toast.makeText(getApplicationContext(),"You are not online!!!!",Toast.LENGTH_LONG).show();
                }




                // Do something in response to button click
            }
        });


        dispDate = (TextView) findViewById(R.id.date_spin);
        dispDate.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                DatePickerDialog dated = new DatePickerDialog(Appoint.this, listener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

                Date d = null;
                try {
                    d = sdf.parse(calendar.get(Calendar.DAY_OF_MONTH) + "/" + (calendar.get(Calendar.MONTH) + 1) + "/" + calendar.get(Calendar.YEAR));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                dated.getDatePicker().setMinDate(d.getTime());
                dated.show();

            }
        });


        dispTime = (TextView) findViewById(R.id.time_spin);
        dispTime.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                new TimePickerDialog(Appoint.this, onTimeSetListener, calendar1.get(Calendar.HOUR_OF_DAY), calendar1.get(Calendar.MINUTE), true).show();
            }
        });
        help.closeDB();
    }

    DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            if (monthOfYear > 10 && dayOfMonth > 10)
                dispDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
            else if (monthOfYear < 10 && dayOfMonth > 10)
                dispDate.setText(dayOfMonth + "/0" + (monthOfYear + 1) + "/" + year);
            else if (monthOfYear > 10 && dayOfMonth < 10)
                dispDate.setText("0" + dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
            else if (monthOfYear < 10 && dayOfMonth < 10)
                dispDate.setText("0" + dayOfMonth + "/0" + (monthOfYear + 1) + "/" + year);
        }
    };
    TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            if (hourOfDay > 10 && minute > 10)
                dispTime.setText(hourOfDay + ":" + minute);
            else if (hourOfDay < 10 && minute > 10)
                dispTime.setText("0" + hourOfDay + ":" + minute);
            else if (hourOfDay > 10 && minute < 10)
                dispTime.setText(hourOfDay + ":0" + minute);
            else if (hourOfDay < 10 && minute < 10)
                dispTime.setText("0" + hourOfDay + ":0" + minute);
        }
    };


    private class PrefetchData extends AsyncTask<Void, Void, Void> {
        int student_id;
        int faculty_id;
        int urgent;
        String date;
        String time;
        String reason;
        General_Query general;
        private PrefetchData(int student_id, int faculty_id, int urgent, String date, String time, String reason) {
            this.student_id = student_id;
            this.faculty_id = faculty_id;
            this.urgent = urgent;
            this.date = date;
            this.time = time;
            this.reason = reason;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();


        }

        @Override
        protected Void doInBackground(Void... arg0) {

            BlockingQueue<General_Query> result = (new DatabaseCalls(getApplicationContext())).createRequest(faculty_id,student_id,date,time,reason,urgent);
            try {
                general = result.take();
            } catch (InterruptedException e) {
                e.printStackTrace();

            }



            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            if(general.getSuccess()==1){

                Toast.makeText(getApplicationContext(), general.getMessage(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Appoint.this, S_Home.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
            else{
                Toast.makeText(getApplicationContext(), general.getMessage(), Toast.LENGTH_SHORT).show();
            }

        }

    }
}