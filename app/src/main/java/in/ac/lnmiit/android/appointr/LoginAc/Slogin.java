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
import in.ac.lnmiit.android.appointr.Home.S_Home;
import in.ac.lnmiit.android.appointr.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Slogin extends AppCompatActivity {
    SessionManagement session;
    private static final String TAG = Slogin.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_login);

        TextView changePassword = (TextView) findViewById(R.id.change1);
        changePassword.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Contact Administrator For the Password", Toast.LENGTH_SHORT).show();
                // Do something in response to button click
            }
        });

        Button submit = (Button) findViewById(R.id.sub_btns);
        submit.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                EditText email_text = (EditText) findViewById(R.id.input_emails);
                String email =  email_text.getText().toString();
                EditText pass_text = (EditText) findViewById(R.id.input_passwords);
                String pass =  pass_text.getText().toString();
                ApiInterface apiService =
                        ApiClient.getClient().create(ApiInterface.class);
                Map<String,String> names= new HashMap<String, String>();
                names.put("email",email);
                names.put("pass",pass);
                Call<login> call = apiService.checkSLogin(names);
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
                            session.createLoginSession("student",id);
                            Log.e(TAG, "Number request: " + success+" "+message);
                            Intent intent = new Intent(Slogin.this, S_Home.class);
                            startActivity(intent);
                            finish();
                        }
                        else{
                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                        }

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
