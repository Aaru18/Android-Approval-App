package in.ac.lnmiit.android.appointr.DatabaseConnections;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import in.ac.lnmiit.android.appointr.Functions.FunctionUsed;
import in.ac.lnmiit.android.appointr.Functions.SessionManagement;
import in.ac.lnmiit.android.appointr.models.Faculty_request;
import in.ac.lnmiit.android.appointr.models.General_Query;
import in.ac.lnmiit.android.appointr.models.RequestReq;
import in.ac.lnmiit.android.appointr.models.Student_request;
import in.ac.lnmiit.android.appointr.models.login;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class DatabaseCalls {
    SessionManagement session;
    Context context;
    ApiInterface apiService;

    public DatabaseCalls(Context context) {
        session = new SessionManagement(context);
        this.context = context;
        apiService =
                ApiClient.getClient().create(ApiInterface.class);
    }

    public BlockingQueue<RequestReq> showRequests() {

        final BlockingQueue<RequestReq> blockingQueue = new ArrayBlockingQueue<>(1);
        Map<String, String> names = new HashMap<String, String>();
        names.put("id", "" + session.getID());
        names.put("user", session.getSession());
        Call<RequestReq> call = apiService.showRequests(names);
        call.enqueue(new Callback<RequestReq>() {
            @Override
            public void onResponse(Call<RequestReq> call, Response<RequestReq> response) {
                if (response.body() != null) {
                    int statusCode = response.code();
                    int success = response.body().getSuccess();
                    String message = response.body().getMessage();

                    RequestReq requests = response.body();
                    blockingQueue.add(requests);

                }


            }

            @Override
            public void onFailure(Call<RequestReq> call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
            }
        });

        return blockingQueue;
    }

    public BlockingQueue<Student_request> getStudents() {

        final BlockingQueue<Student_request> blockingQueue = new ArrayBlockingQueue<>(1);
        Call<Student_request> call = apiService.showS();
        call.enqueue(new Callback<Student_request>() {
            @Override
            public void onResponse(Call<Student_request> call, Response<Student_request> response) {
                if (response.body() != null) {
                    Student_request requests = response.body();
                    blockingQueue.add(requests);
                }
            }

            @Override
            public void onFailure(Call<Student_request> call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
            }
        });

        return blockingQueue;
    }

    public BlockingQueue<Faculty_request> getFaculties() {

        final BlockingQueue<Faculty_request> blockingQueue = new ArrayBlockingQueue<>(1);
        Call<Faculty_request> call = apiService.showF();
        call.enqueue(new Callback<Faculty_request>() {
            @Override
            public void onResponse(Call<Faculty_request> call, Response<Faculty_request> response) {
                if (response.body() != null) {
                    Faculty_request requests = response.body();
                    blockingQueue.add(requests);
                }
            }

            @Override
            public void onFailure(Call<Faculty_request> call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
            }
        });

        return blockingQueue;
    }

    public BlockingQueue<String> checkLoginS(String email, String pass) {

        final BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(1);

        Map<String,String> names= new HashMap<String, String>();
        names.put("email",email);
        names.put("pass",pass);
        Call<login> call = apiService.checkSLogin(names);
        call.enqueue(new Callback<login>() {
            @Override
            public void onResponse(Call<login>call, Response<login> response) {
                int success = response.body().getSuccess();
                String message = response.body().getMessage();
                if(success==1){
                    int id = response.body().getUser_id();
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                    session = new SessionManagement(context);
                    session.createLoginSession("student",id);
                    String res = "Success";
                    blockingQueue.add(res);
                }
                else{
                    String res = "Fail";
                    blockingQueue.add(res);
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<login>call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
            }
        });
        return blockingQueue;
    }
    public BlockingQueue<String> checkLoginF(String email, String pass) {

        final BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(1);

        Map<String,String> names= new HashMap<String, String>();
        names.put("email",email);
        names.put("pass",pass);
        Call<login> call = apiService.checkFLogin(names);
        call.enqueue(new Callback<login>() {
            @Override
            public void onResponse(Call<login>call, Response<login> response) {
                int success = response.body().getSuccess();
                String message = response.body().getMessage();
                if(success==1){
                    int id = response.body().getUser_id();
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                    session = new SessionManagement(context);
                    session.createLoginSession("faculty",id);
                    String res = "Success";
                    blockingQueue.add(res);
                }
                else{
                    String res = "Fail";
                    blockingQueue.add(res);
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<login>call, Throwable t) {

                Log.e(TAG, t.toString());
            }
        });
        return blockingQueue;
    }

    public BlockingQueue<General_Query> createRequest(int faculty_id, int student_id, String request_date, String request_time, String reason, int urgent) {

        final BlockingQueue<General_Query> blockingQueue = new ArrayBlockingQueue<>(1);

        Map<String, String> names = new HashMap<String, String>();
        names.put("faculty_id", "" + faculty_id);
        names.put("student_id", "" + student_id);
        names.put("date", "" + FunctionUsed.getLongFromDate(request_date + " " + request_time));
        System.out.println(FunctionUsed.getLongFromDate(request_date + " " + request_time));
        names.put("reason", reason);
        names.put("urgent", "" + urgent);
        Call<General_Query> call = apiService.createRRequest(names);
        call.enqueue(new Callback<General_Query>() {
            @Override
            public void onResponse(Call<General_Query>call, Response<General_Query> response) {

                General_Query general = response.body();
                blockingQueue.add(general);

            }

            @Override
            public void onFailure(Call<General_Query>call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
            }
        });

        return blockingQueue;
    }



}
