package in.ac.lnmiit.android.appointr.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import in.ac.lnmiit.android.appointr.DatabaseConnections.ApiClient;
import in.ac.lnmiit.android.appointr.DatabaseConnections.ApiInterface;
import in.ac.lnmiit.android.appointr.Functions.AppStatus;
import in.ac.lnmiit.android.appointr.models.General_Query;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class NotificationView extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        int id = getIntent().getIntExtra("request",0);
        if (AppStatus.getInstance(getApplicationContext()).isOnline()) {

        } else {

            Toast.makeText(getApplicationContext(),"You are not online!!!!",Toast.LENGTH_LONG).show();
        }

    }


}
