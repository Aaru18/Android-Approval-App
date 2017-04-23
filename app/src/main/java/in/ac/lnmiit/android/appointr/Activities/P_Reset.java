package in.ac.lnmiit.android.appointr.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import in.ac.lnmiit.android.appointr.DatabaseConnections.ApiClient;
import in.ac.lnmiit.android.appointr.DatabaseConnections.ApiInterface;
import in.ac.lnmiit.android.appointr.DatabaseConnections.DatabaseHelper;
import in.ac.lnmiit.android.appointr.Functions.SessionManagement;
import in.ac.lnmiit.android.appointr.R;
import in.ac.lnmiit.android.appointr.models.General_Query;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class P_Reset extends AppCompatActivity {
        Toolbar toolbar1;
    Intent intent;
    private static final String TAG = Flogin.class.getSimpleName();
    String activity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.password_reset);

        toolbar1 = (Toolbar) findViewById(R.id.appoint_toolbar);
        setSupportActionBar(toolbar1);
        intent = getIntent();
        activity = intent.getStringExtra("activity");
        ImageView appoint = (ImageView) findViewById(R.id.back_pass);
        appoint.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                if(activity.equals("faculty")){
                    Intent intent = new Intent(P_Reset.this, F_Home.class);
                    startActivity(intent);
                    finish();
                }else {
                    Intent intent = new Intent(P_Reset.this, S_Home.class);
                    startActivity(intent);
                    finish();
                }

                // Do something in response to button click
            }
        });

        Button reset = (Button) findViewById(R.id.reset_button);

        reset.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                EditText oldPass = (EditText) findViewById(R.id.old_pd);
                EditText newPass = (EditText) findViewById(R.id.new_pd);
                String password_to_change = oldPass.getText().toString();
                String new_password = newPass.getText().toString();
                final SessionManagement session = new SessionManagement(getApplicationContext());
                String user = session.getSession();
                int id =session.getID();
                ApiInterface apiService =
                        ApiClient.getClient().create(ApiInterface.class);
                Map<String,String> names= new HashMap<String, String>();
                names.put("id",""+id);
                names.put("pass",password_to_change);
                names.put("new_pass",new_password);
                names.put("user",user);
                Call<General_Query> call = apiService.passChange(names);
                call.enqueue(new Callback<General_Query>() {
                    @Override
                    public void onResponse(Call<General_Query>call, Response<General_Query> response) {
                        int statusCode = response.code();
                        int success = response.body().getSuccess();
                        String message = response.body().getMessage();
                        if(success==1){
                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                            session.logoutUser();
                            new DatabaseHelper(getApplicationContext()).deleteDB(getApplicationContext());
                            Intent intent = new Intent(P_Reset.this, login_home.class);
                            startActivity(intent);
                            finish();
                        }
                        else{

                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                           onBackPressed();
                        }
                        Log.e(TAG, "Number request: " + success+" "+message);
                    }

                    @Override
                    public void onFailure(Call<General_Query>call, Throwable t) {
                        // Log error here since request failed
                        Log.e(TAG, t.toString());
                    }
                });

            }
        });

    }
    @Override
    public void onBackPressed() {
        if(activity.equals("faculty")){
            Intent intent = new Intent(P_Reset.this, F_Home.class);
            startActivity(intent);
            finish();
        }else {
            Intent intent = new Intent(P_Reset.this, S_Home.class);
            startActivity(intent);
            finish();
        }
    }


}

