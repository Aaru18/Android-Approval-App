package in.ac.lnmiit.android.appointr.LoginAc;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import in.ac.lnmiit.android.appointr.ApiCall.ApiClient;
import in.ac.lnmiit.android.appointr.ApiCall.ApiInterface;
import in.ac.lnmiit.android.appointr.ApiCall.SessionManagement;
import in.ac.lnmiit.android.appointr.ApiCall.login;
import in.ac.lnmiit.android.appointr.Home.F_Home;
import in.ac.lnmiit.android.appointr.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Flogin extends AppCompatActivity {
    SessionManagement session;
    private static final String TAG = Flogin.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.faculty_login);

    TextView change_password = (TextView)findViewById(R.id.change2);
        change_password.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Contact Administrator For the Password", Toast.LENGTH_SHORT).show();
                // Do something in response to button click
            }
        });

        Button submit1 = (Button) findViewById(R.id.submit_button);
        submit1.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                EditText email_text = (EditText) findViewById(R.id.input_email);
                String email =  email_text.getText().toString();
                EditText pass_text = (EditText) findViewById(R.id.input_password);
                String pass =  pass_text.getText().toString();
               ApiInterface apiService =
                        ApiClient.getClient().create(ApiInterface.class);
                Map<String,String> names= new HashMap<String, String>();
                names.put("email",email);
                names.put("pass",pass);
                Call<login> call = apiService.checkFLogin(names);
                call.enqueue(new Callback<login>() {
                    @Override
                    public void onResponse(Call<login>call, Response<login> response) {
                        int statusCode = response.code();
                        int success = response.body().getSuccess();
                        String message = response.body().getMessage();
                        if(success==1){
                            int id = response.body().getUser_id();
                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                            session = new SessionManagement(getApplicationContext());
                            session.createLoginSession("faculty",id);

                            Intent intent = new Intent(Flogin.this, F_Home.class);
                            startActivity(intent);
                            finish();
                        }
                        else{
                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                        }
                        Log.e(TAG, "Number request: " + success+" "+message);
                    }

                    @Override
                    public void onFailure(Call<login>call, Throwable t) {
                        // Log error here since request failed
                        Log.e(TAG, t.toString());
                    }
                });


                // Do something in response to button click
            }
        });

    }
}
