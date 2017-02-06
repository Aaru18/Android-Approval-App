package in.ac.lnmiit.android.appointr;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button submit = (Button) findViewById(R.id.sub_btn);
        submit.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, S_Home.class);
                startActivity(intent);
                // Do something in response to button click
            }
        });

        TextView changePassword = (TextView) findViewById(R.id.change1);
        changePassword.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, P_Reset.class);
                startActivity(intent);
                // Do something in response to button click
            }
        });


    }

    public void change_request (View v){
        Intent intent = new Intent(MainActivity.this,P_Reset.class);
        startActivity(intent);

    }
}
