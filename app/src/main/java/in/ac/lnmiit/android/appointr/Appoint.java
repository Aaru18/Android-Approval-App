
package in.ac.lnmiit.android.appointr;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import in.ac.lnmiit.android.appointr.ApiCall.ApiClient;
import in.ac.lnmiit.android.appointr.ApiCall.ApiInterface;
import in.ac.lnmiit.android.appointr.ApiCall.Faculty;
import in.ac.lnmiit.android.appointr.ApiCall.Faculty_request;
import in.ac.lnmiit.android.appointr.ApiCall.General_Query;
import in.ac.lnmiit.android.appointr.ApiCall.SessionManagement;
import in.ac.lnmiit.android.appointr.Home.S_Home;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Appoint extends AppCompatActivity  {
    private static final String TAG = Appoint.class.getSimpleName();
    Calendar calendar1 = Calendar.getInstance();
    Calendar calendar = Calendar.getInstance();
    TextView dispDate;
    TextView dispTime;
    DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener(){
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth){
            if(monthOfYear>10 && dayOfMonth >10)
                dispDate.setText(dayOfMonth + "/" + (monthOfYear+1) + "/" + year);
            else if(monthOfYear<10 && dayOfMonth >10)
                dispDate.setText(dayOfMonth + "/0" +(monthOfYear+1) + "/" + year);
            else if(monthOfYear>10 && dayOfMonth <10)
                dispDate.setText("0"+dayOfMonth + "/" + (monthOfYear+1) + "/" + year);
            else if(monthOfYear<10 && dayOfMonth <10)
                dispDate.setText("0"+dayOfMonth + "/0" + (monthOfYear+1) + "/" + year);

        }
    };
    TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener(){
        public void onTimeSet(TimePicker view, int hourOfDay, int minute ){
            if(hourOfDay>10 && minute >10)
                dispTime.setText(hourOfDay + ":" + minute);
            else if(hourOfDay<10 && minute >10)
                dispTime.setText("0"+hourOfDay + ":" + minute);
            else if(hourOfDay>10 && minute <10)
                dispTime.setText(hourOfDay + ":0" + minute);
            else if(hourOfDay<10 && minute <10)
                dispTime.setText("0"+hourOfDay + ":0" + minute);
        }
    };
    private Toolbar toolbar1;
    private String depart;
    private String facul;
    private List<Faculty> faculty;
    private int counts;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.appoint_page);



        final List<String> categories1 = new ArrayList<String>();
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        Call<Faculty_request> call = apiService.showF();
        call.enqueue(new Callback<Faculty_request>() {
            @Override
            public void onResponse(Call<Faculty_request>call, Response<Faculty_request> response) {
                int statusCode = response.code();
                int success = response.body().getSuccess();
                String message = response.body().getMessage();
                if(success==1){
                    counts = response.body().getCount();
                   faculty = response.body().getFaculties();

                }
                else{
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                }
                Log.e(TAG, "Number request: " + success+" "+message+" "+counts);
            }

            @Override
            public void onFailure(Call<Faculty_request>call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
            }
        });


        final List<String> categories = new ArrayList<String>();
        categories.add("CSE");
        categories.add("ECE");
        categories.add("MME");
        categories.add("MTH");
        categories.add("PHY");
        categories.add("HSS");
        categories.add("OTHERS");
        Spinner spinner = (Spinner) findViewById(R.id.department_spin);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, R.layout.spinner_layout, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
        spinner.setSelection(0);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                depart = parent.getItemAtPosition(position).toString();
                categories1.clear();
                for(int i=0;i< counts;i++){
                    if(faculty.get(i).getDepartments().equals(depart)) {
                        categories1.add(faculty.get(i).getFaculty_name());
                    }

                }
                Spinner spinner1 = (Spinner) findViewById(R.id.faculty_spin);
                ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_layout, categories1);
                dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner1.setAdapter(dataAdapter1);
                spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        facul = parent.getItemAtPosition(position).toString();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        toolbar1 = (Toolbar) findViewById(R.id.appoint_toolbar);
        setSupportActionBar(toolbar1);
        ImageView appoint = (ImageView) findViewById(R.id.back);
        appoint.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent intent = new Intent(Appoint.this, S_Home.class);
                startActivity(intent);
                finish();
                // Do something in response to button click
            }
        });

        ImageView appoint1 = (ImageView) findViewById(R.id.send);
        appoint1.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                SessionManagement session = new SessionManagement(getApplicationContext());
                int student_id = session.getID();
                int faculty_id=0;
                for(int i=0;i<counts;i++){
                    if(faculty.get(i).getFaculty_name().equals(facul)){
                        faculty_id = faculty.get(i).getUser_id();
                    }
                }
                TextView datet = (TextView)findViewById(R.id.date_spin);
                String request_date = datet.getText().toString();
                TextView timet = (TextView)findViewById(R.id.time_spin);
                String request_time = timet.getText().toString();
                TextView res = (TextView)findViewById(R.id.reason);
                String reason = res.getText().toString();
                CheckBox ur = (CheckBox) findViewById(R.id.urgent_check);
                int urgent = ur.isChecked()?1:0;
                ApiInterface apiService =
                        ApiClient.getClient().create(ApiInterface.class);
                Map<String,String> names= new HashMap<String, String>();
                names.put("faculty_id",""+faculty_id);
                names.put("student_id",""+student_id);
                names.put("date",request_date);
                names.put("time",request_time);
                names.put("reason",reason);
                names.put("urgent",""+urgent);
                Call<General_Query> call = apiService.createRRequest(names);
                call.enqueue(new Callback<General_Query>() {
                    @Override
                    public void onResponse(Call<General_Query>call, Response<General_Query> response) {
                        int statusCode = response.code();
                        int success = response.body().getSuccess();
                        String message = response.body().getMessage();
                        if(success==1){

                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Appoint.this, S_Home.class);
                            startActivity(intent);
                            finish();
                        }
                        else{
                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onFailure(Call<General_Query>call, Throwable t) {
                        // Log error here since request failed
                        Log.e(TAG, t.toString());
                    }
                });





                // Do something in response to button click
            }
        });



        dispDate = (TextView)findViewById(R.id.date_spin);
        dispDate.setOnClickListener( new View.OnClickListener(){

            public void onClick(View v){

                DatePickerDialog dated = new DatePickerDialog(Appoint.this,listener,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

                Date d=null;
                try {
                    d = sdf.parse(calendar.get(Calendar.DAY_OF_MONTH)+"/"+(calendar.get(Calendar.MONTH)+1)+"/"+calendar.get(Calendar.YEAR));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                dated.getDatePicker().setMinDate(d.getTime());
                dated.show();

            }
        });


        dispTime = (TextView)findViewById(R.id.time_spin);
        dispTime.setOnClickListener(new View.OnClickListener(){

            public void onClick (View view){
                new TimePickerDialog(Appoint.this,onTimeSetListener,calendar1.get(Calendar.HOUR_OF_DAY),calendar1.get(Calendar.MINUTE),true).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Appoint.this, S_Home.class);
        startActivity(intent);
        finish();
    }
}