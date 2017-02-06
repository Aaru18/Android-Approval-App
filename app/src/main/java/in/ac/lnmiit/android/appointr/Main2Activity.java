package in.ac.lnmiit.android.appointr;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


        Button button_faculty = (Button) findViewById(R.id.faculty);
        button_faculty.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent intent = new Intent(Main2Activity.this, Flogin.class);
                startActivity(intent);
                // Do something in response to button click
            }
        });

        Button button_student = (Button) findViewById(R.id.student);
        button_student.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Main2Activity.this, MainActivity.class);
                startActivity(intent);
                // Do something in response to button click
            }
        });
    }

}
