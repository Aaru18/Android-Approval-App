package in.ac.lnmiit.android.appointr.Activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.BlockingQueue;

import in.ac.lnmiit.android.appointr.DatabaseConnections.DatabaseCalls;
import in.ac.lnmiit.android.appointr.DatabaseConnections.DatabaseHelper;
import in.ac.lnmiit.android.appointr.Functions.AppStatus;
import in.ac.lnmiit.android.appointr.R;
import in.ac.lnmiit.android.appointr.models.Faculty_request;
import in.ac.lnmiit.android.appointr.models.Student_request;

public class Slogin extends AppCompatActivity {
    String str;
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

        final Button submit = (Button) findViewById(R.id.sub_btns);
        submit.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                findViewById(R.id.loadingPane9).setVisibility(View.VISIBLE);
                submit.setEnabled(false);
                if (AppStatus.getInstance(getApplicationContext()).isOnline()) {

                    submit.setEnabled(false);
                    EditText email_text = (EditText) findViewById(R.id.input_emails);
                    String email =  email_text.getText().toString();
                    EditText pass_text = (EditText) findViewById(R.id.input_passwords);
                    String pass =  pass_text.getText().toString();
                    PrefetchData prefetch = new PrefetchData(email,pass, submit);
                    prefetch.execute();






                } else {
                    findViewById(R.id.loadingPane9).setVisibility(View.GONE);
                    submit.setEnabled(true);
                    Toast.makeText(getApplicationContext(),"You are not online!!!!",Toast.LENGTH_LONG).show();
                }



                // Do something in response to button click
            }
        });



    }

    private class PrefetchData extends AsyncTask<Void, Void, Void> {
        String email,pass;
        Button submit;
        private PrefetchData(String email, String pass, Button submit){
            this.email  = email;
            this.pass = pass;
            this.submit = submit;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();


        }

        @Override
        protected Void doInBackground(Void... arg0) {

            BlockingQueue<String> result = (new DatabaseCalls(getApplicationContext())).checkLoginS(email, pass);
            try {
                str = result.take();
            } catch (InterruptedException e) {
                e.printStackTrace();

            }
            if(str.equals("Success")){
                BlockingQueue<Faculty_request> faculties = (new DatabaseCalls(getApplicationContext())).getFaculties();
                BlockingQueue<Student_request> students = (new DatabaseCalls(getApplicationContext())).getStudents();
                try {
                    DatabaseHelper helpp = new DatabaseHelper(getApplicationContext());
                    Faculty_request faculty = faculties.take();
                    Student_request student = students.take();
                    for( int i = 0; i< faculty.getFaculties().size(); i++){
                        helpp.createFaculty(faculty.getFaculties().get(i));
                    }
                    for( int i = 0; i< student.getStudents().size(); i++){
                        helpp.createStudent(student.getStudents().get(i));
                    }

                    helpp.closeDB();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
            return null;
        }

            @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

                Log.v(TAG, str);
                if(str.equals("Success")){
                    findViewById(R.id.loadingPane9).setVisibility(View.GONE);
                    Intent intent = new Intent(Slogin.this, S_Home.class);
                    startActivity(intent);
                    finish();
                }else{
                    findViewById(R.id.loadingPane9).setVisibility(View.GONE);
                    submit.setEnabled(true);
                }

        }

    }
}
