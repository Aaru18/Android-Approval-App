package in.ac.lnmiit.android.appointr.LoginAc;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import in.ac.lnmiit.android.appointr.Home.F_Home;
import in.ac.lnmiit.android.appointr.Home.S_Home;
import in.ac.lnmiit.android.appointr.R;

public class login_home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_home);
        SharedPreferences sharedPreference = getSharedPreferences("mypref", Context.MODE_PRIVATE);
        if (sharedPreference.contains("id")) {
            if(sharedPreference.contains("session")){

                String s = sharedPreference.getString("session",null);
                if(s == "student"){
                    Intent intent = new Intent(login_home.this, S_Home.class);
                    startActivity(intent);
                    finish();
                }
                else if(s == "faculty") {
                    Intent intent = new Intent(login_home.this, F_Home.class);
                    startActivity(intent);
                    finish();
                }
            }


        }

        Button button_faculty = (Button) findViewById(R.id.faculty);
        button_faculty.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent intent = new Intent(login_home.this, Flogin.class);
                startActivity(intent);
                finish();
                // Do something in response to button click
            }
        });

        Button button_student = (Button) findViewById(R.id.student);
        button_student.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(login_home.this, Slogin.class);
                startActivity(intent);
                finish();// Do something in response to button click
            }
        });
    }

}
