package in.ac.lnmiit.android.appointr;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Flogin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.faculty_login);

    TextView change_password = (TextView)findViewById(R.id.change2);
        change_password.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent intent = new Intent(Flogin.this, P_Reset.class);
                startActivity(intent);
                // Do something in response to button click
            }
        });

        Button submit1 = (Button) findViewById(R.id.submit_button);
        submit1.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent intent = new Intent(Flogin.this, F_Home.class);
                startActivity(intent);
                // Do something in response to button click
            }
        });
    }

    public void new_password (View v){
        Intent intent = new Intent(Flogin.this,P_Reset.class);
        startActivity(intent);

    }
}
