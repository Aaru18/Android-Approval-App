package in.ac.lnmiit.android.appointr;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import in.ac.lnmiit.android.appointr.ApiCall.ApiClient;
import in.ac.lnmiit.android.appointr.ApiCall.ApiInterface;
import in.ac.lnmiit.android.appointr.ApiCall.General_Query;
import in.ac.lnmiit.android.appointr.ApiCall.Request;
import in.ac.lnmiit.android.appointr.ApiCall.RequestReq;
import in.ac.lnmiit.android.appointr.ApiCall.Student;
import in.ac.lnmiit.android.appointr.ApiCall.Student_request;
import in.ac.lnmiit.android.appointr.Home.F_Home;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Reply extends AppCompatActivity {

    private static final String TAG = Appoint.class.getSimpleName();
    private Toolbar toolbar1;
    private int value;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.faculty_reply);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            value = extras.getInt("request_id");

        }
        final Request[] ret = new Request[1];
        SharedPreferences sharedPreferences = getSharedPreferences("mypref", Context.MODE_PRIVATE);
        int user_id = sharedPreferences.getInt("id",0);
        final String session_id = sharedPreferences.getString("session",null);
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        Map<String,String> names= new HashMap<String, String>();
        names.put("id",""+user_id);
        names.put("user",session_id);
        Call<RequestReq> call = apiService.showRequests(names);
        call.enqueue(new Callback<RequestReq>() {
            @Override
            public void onResponse(Call<RequestReq>call, Response<RequestReq> response) {
                int statusCode = response.code();
                int success = response.body().getSuccess();
                String message = response.body().getMessage();
                if(success==1){
                    int counts = response.body().getCount();
                    List<Request> requestss = response.body().getRequests();
                   for(int i=0;i<counts;i++){
                       if(requestss.get(i).getRequest_id() == value){
                           ret[0] = requestss.get(i);
                          TextView t= (TextView)findViewById(R.id.date_reply);
                           t.setText(ret[0].getRequest_date());

                           TextView tq= (TextView)findViewById(R.id.time_reply);
                           tq.setText(ret[0].getRequest_time());

                           TextView ts= (TextView)findViewById(R.id.reasonss);
                           ts.setText(ret[0].getReason());
                       }
                   }


                }
                else{
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<RequestReq>call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
            }
        });
        final String[] nme = new String[1];
        Call<Student_request> call1 = apiService.showS();
        call1.enqueue(new Callback<Student_request>() {
            @Override
            public void onResponse(Call<Student_request>call, Response<Student_request> response) {
                int statusCode = response.code();
                int success = response.body().getSuccess();
                String message = response.body().getMessage();
                if(success==1){
                    int counts = response.body().getCount();
                    List<Student> students = response.body().getStudents();
                    for(int i=0;i<counts;i++){
                        if(students.get(i).getUser_id() == ret[0].getStudent_id()){
                           nme[0] = students.get(i).getStudent_name();
                            TextView nm = (TextView)findViewById(R.id.student_field);
                            nm.setText(nme[0]);
                        }
                    }


                }
                else{
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                }

            }


            @Override
            public void onFailure(Call<Student_request>call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
            }
        });

        Button b1 =(Button)findViewById(R.id.accept);
        b1.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                ApiInterface apiService =
                        ApiClient.getClient().create(ApiInterface.class);
                Map<String,String> names= new HashMap<String, String>();
                names.put("id",""+value);
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
                Intent intent = new Intent(Reply.this, F_Home.class);
                startActivity(intent);
                finish();
                // Do something in response to button click
            }
        });
                toolbar1 = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar1);







    }


}
