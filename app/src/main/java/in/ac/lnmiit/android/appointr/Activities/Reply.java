package in.ac.lnmiit.android.appointr.Activities;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import in.ac.lnmiit.android.appointr.DatabaseConnections.ApiClient;
import in.ac.lnmiit.android.appointr.DatabaseConnections.ApiInterface;
import in.ac.lnmiit.android.appointr.DatabaseConnections.DatabaseHelper;
import in.ac.lnmiit.android.appointr.Functions.AppStatus;
import in.ac.lnmiit.android.appointr.Functions.FunctionUsed;
import in.ac.lnmiit.android.appointr.R;
import in.ac.lnmiit.android.appointr.models.General_Query;
import in.ac.lnmiit.android.appointr.models.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Reply extends AppCompatActivity {

    private static final String TAG = Appoint.class.getSimpleName();
    private Toolbar toolbar1;
    private Request value;
    Calendar calendar1 = Calendar.getInstance();
    Calendar calendar = Calendar.getInstance();
    TextView dispDate;
    TextView dispTime;
    private int modify = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.faculty_reply);
        value = (Request) getIntent().getSerializableExtra("Request");
        DatabaseHelper help = new DatabaseHelper(getApplicationContext());


      dispDate= (TextView)findViewById(R.id.date_reply);
       dispDate.setText(FunctionUsed.getStringFromDate(value.getRequest_date()));

     dispTime= (TextView)findViewById(R.id.time_reply);
       dispTime.setText(FunctionUsed.getTimeSringFromDate(value.getRequest_date()));

       TextView ts= (TextView)findViewById(R.id.reasonss);
       ts.setText(value.getReason());

        TextView nm = (TextView)findViewById(R.id.student_field);
        nm.setText(help.getStudent(value.getStudent_id()).getStudent_name());

        toolbar1 = (Toolbar) findViewById(R.id.reply_toolbar);
        setSupportActionBar(toolbar1);
        ImageView appoint = (ImageView) findViewById(R.id.back1);
        appoint.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
               onBackPressed();
                // Do something in response to button click
            }
        });

        final Button b1 =(Button)findViewById(R.id.accept);
        final Button b2 =(Button)findViewById(R.id.reject);
        Button b4 = (Button) findViewById(R.id.mail);
        b1.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                if (AppStatus.getInstance(getApplicationContext()).isOnline()) {

                    b1.setEnabled(false);
                    b2.setEnabled(false);
                    if(FunctionUsed.getLongFromDate(dispDate.getText()+ " " + dispTime.getText()) == value.getRequest_date()){
                        ApiInterface apiService =
                                ApiClient.getClient().create(ApiInterface.class);
                        Map<String,String> names= new HashMap<String, String>();
                        names.put("id",""+value.getRequest_id());
                        names.put("status","1");
                        names.put("modify","0");
                        Call<General_Query> call = apiService.modifyRequest(names);
                        call.enqueue(new Callback<General_Query>() {
                            @Override
                            public void onResponse(Call<General_Query>call, Response<General_Query> response) {
                                int statusCode = response.code();
                                int success = response.body().getSuccess();
                                String message = response.body().getMessage();
                                if(success==1){
                                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(Reply.this, F_Home.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);
                                    finish();
                                }
                                else{
                                    b1.setEnabled(true);
                                    b2.setEnabled(true);
                                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                                }

                            }

                            @Override
                            public void onFailure(Call<General_Query>call, Throwable t) {
                                // Log error here since request failed
                                Log.e(TAG, t.toString());
                            }
                        });

                    } else {
                        ApiInterface apiService =
                                ApiClient.getClient().create(ApiInterface.class);
                        Map<String,String> names= new HashMap<String, String>();
                        names.put("id",""+value.getRequest_id());
                        names.put("status","1");
                        names.put("modify","1");
                        names.put("date",""+FunctionUsed.getLongFromDate(dispDate.getText() + " " + dispTime.getText()));
                        Call<General_Query> call = apiService.modifyRequest(names);
                        call.enqueue(new Callback<General_Query>() {
                            @Override
                            public void onResponse(Call<General_Query>call, Response<General_Query> response) {
                                int statusCode = response.code();
                                int success = response.body().getSuccess();
                                String message = response.body().getMessage();
                                if(success==1){
                                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(Reply.this, F_Home.class);
                                    startActivity(intent);
                                    finish();
                                }
                                else{
                                    b1.setEnabled(true);
                                    b2.setEnabled(true);
                                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                                }

                            }

                            @Override
                            public void onFailure(Call<General_Query>call, Throwable t) {
                                // Log error here since request failed
                                Log.e(TAG, t.toString());
                            }
                        });

                    }

                } else {

                    Toast.makeText(getApplicationContext(),"You are not online!!!!",Toast.LENGTH_LONG).show();
                }

                // Do something in response to button click
            }
        });


        b2.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                if (AppStatus.getInstance(getApplicationContext()).isOnline()) {

                    b1.setEnabled(false);
                    b2.setEnabled(false);
                    ApiInterface apiService =
                            ApiClient.getClient().create(ApiInterface.class);
                    Map<String,String> names= new HashMap<String, String>();
                    names.put("id",""+value.getRequest_id());
                    names.put("status","-1");
                    Log.v("d",names.get("status"));
                    names.put("modify","0");
                    Call<General_Query> call = apiService.modifyRequest(names);
                    call.enqueue(new Callback<General_Query>() {
                        @Override
                        public void onResponse(Call<General_Query>call, Response<General_Query> response) {
                            int statusCode = response.code();
                            int success = response.body().getSuccess();
                            String message = response.body().getMessage();
                            if(success==1){
                                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(Reply.this, F_Home.class);
                                startActivity(intent);
                                finish();
                            }
                            else{
                                b1.setEnabled(true);
                                b2.setEnabled(true);
                                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                            }

                        }

                        @Override
                        public void onFailure(Call<General_Query>call, Throwable t) {
                            // Log error here since request failed
                            Log.e(TAG, t.toString());
                        }
                    });
                } else {

                    Toast.makeText(getApplicationContext(),"You are not online!!!!",Toast.LENGTH_LONG).show();
                }



                // Do something in response to button click
            }
        });
        if(value.isUrgent()== 1){

            b4.setOnClickListener(new View.OnClickListener() {

                public void onClick(View view) {
                    if (AppStatus.getInstance(getApplicationContext()).isOnline()) {

                        DatabaseHelper help = new DatabaseHelper(getApplicationContext());

                        Intent intent = new Intent(Intent.ACTION_SENDTO);
                        intent.setData(Uri.parse("mailto: "+ help.getStudent(value.getStudent_id()).getEmail_id()));
                        intent.putExtra(Intent.EXTRA_SUBJECT, "Appointment Request For "+ value.getReason());
                        intent.putExtra(Intent.EXTRA_TEXT, help.getStudent(value.getStudent_id()).getStudent_name()+", ");
                        startActivity(Intent.createChooser(intent, "Send feedback"));

                        help.closeDB();
                        startActivity(intent);
                    } else {

                        Toast.makeText(getApplicationContext(),"You are not online!!!!",Toast.LENGTH_LONG).show();
                    }



                    // Do something in response to button click
                }
            });

        } else {
            b4.setEnabled(false);
        }


        dispDate.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                DatePickerDialog dated = new DatePickerDialog(Reply.this, listener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

                Date d = null;
                try {
                    d = sdf.parse(calendar.get(Calendar.DAY_OF_MONTH) + "/" + (calendar.get(Calendar.MONTH) + 1) + "/" + calendar.get(Calendar.YEAR));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                dated.getDatePicker().setMinDate(value.getRequest_date());
                dated.show();

            }
        });


        dispTime.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                new TimePickerDialog(Reply.this, onTimeSetListener, calendar1.get(Calendar.HOUR_OF_DAY), calendar1.get(Calendar.MINUTE), true).show();
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

}
