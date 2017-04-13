package in.ac.lnmiit.android.appointr.Activities;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
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
        NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancelAll();

        if (AppStatus.getInstance(getApplicationContext()).isOnline()){
            ApiInterface apiService =
                    ApiClient.getClient().create(ApiInterface.class);
            Map<String,String> names= new HashMap<String, String>();
            names.put("id",""+id);
            Log.v("tag",""+id);
            Call<General_Query> call = apiService.deleteAppointment(names);
            call.enqueue(new Callback<General_Query>() {
                @Override
                public void onResponse(Call<General_Query>call, Response<General_Query> response) {
                    if(response.body().getSuccess()==1){
                        finish();
                        overridePendingTransition( 0, 0);
                        Intent intent = new Intent(NotificationView.this, login_home.class);
                        startActivity(intent);
                        overridePendingTransition( 0, 0);
                        Log.v("tag",response.body().getMessage());

                    }else{
                        finish();
                        overridePendingTransition( 0, 0);
                        Intent intent = new Intent(NotificationView.this, login_home.class);
                        startActivity(intent);
                        overridePendingTransition( 0, 0);
                    }


                }

                @Override
                public void onFailure(Call<General_Query>call, Throwable t) {
                    // Log error here since request failed
                    Log.e(TAG, "failed");
                    Log.e(TAG, t.toString());
                }
            });
        } else {

            Toast.makeText(getApplicationContext(),"You are not online!!!!",Toast.LENGTH_LONG).show();
        }

    }


}
