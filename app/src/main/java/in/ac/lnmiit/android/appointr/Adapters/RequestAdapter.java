package in.ac.lnmiit.android.appointr.Adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import in.ac.lnmiit.android.appointr.ApiCall.ApiClient;
import in.ac.lnmiit.android.appointr.ApiCall.ApiInterface;
import in.ac.lnmiit.android.appointr.ApiCall.Faculty;
import in.ac.lnmiit.android.appointr.ApiCall.Faculty_request;
import in.ac.lnmiit.android.appointr.ApiCall.Request;
import in.ac.lnmiit.android.appointr.ApiCall.Student;
import in.ac.lnmiit.android.appointr.ApiCall.Student_request;
import in.ac.lnmiit.android.appointr.Appoint;
import in.ac.lnmiit.android.appointr.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RequestAdapter extends ArrayAdapter<Request> {
    String muserName;
    String mcon;
    private static final String TAG = Appoint.class.getSimpleName();

        public RequestAdapter(Context context, ArrayList<Request> words, String con) {
            super(context, 0, words);
            mcon =con;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Check if an existing view is being reused, otherwise inflate the view
            View listItemView = convertView;
            if (listItemView == null) {
                listItemView = LayoutInflater.from(getContext()).inflate(
                        R.layout.list_item, parent, false);
            }


            // Get the {@link Word} object located at this position in the list
            final Request currentRequest = getItem(position);
            if(currentRequest.isStatus() ==1 && mcon.equals("Unconfirmed")) {

                listItemView.setVisibility(View.INVISIBLE);
            }
            if(currentRequest.isStatus() ==0 && mcon.equals("Confirmed")){
                listItemView.setVisibility(View.INVISIBLE);
            }
            final TextView name = (TextView) listItemView.findViewById(R.id.user_name);
            SharedPreferences sharedPreferences = getContext().getSharedPreferences("mypref", Context.MODE_PRIVATE);
            final int user_id = sharedPreferences.getInt("id",0);
            String session_id = sharedPreferences.getString("session",null);
            ApiInterface apiService =
                    ApiClient.getClient().create(ApiInterface.class);
            Map<String,String> names= new HashMap<String, String>();
            names.put("id",""+user_id);
            names.put("user",session_id);
            if(session_id.equals("faculty")){
                Call<Student_request> call = apiService.showS();
                call.enqueue(new Callback<Student_request>() {
                    @Override
                    public void onResponse(Call<Student_request>call, Response<Student_request> response) {
                        int statusCode = response.code();
                        int success = response.body().getSuccess();
                        String message = response.body().getMessage();
                        if(success==1){
                            int counts = response.body().getCount();
                            List<Student> stud = response.body().getStudents();
                            for(int i=0;i<counts;i++){
                                if(stud.get(i).getUser_id()== currentRequest.getStudent_id()){
                                    muserName = stud.get(i).getStudent_name();
                                    name.setText(muserName);
                                    Log.e("dd",muserName);
                                }
                            }
                        }
                        else{
                            Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onFailure(Call<Student_request>call, Throwable t) {
                        // Log error here since request failed
                        Log.e(TAG, t.toString());
                    }
                });
            }
            else{
                Call<Faculty_request> call = apiService.showF();
                call.enqueue(new Callback<Faculty_request>() {
                    @Override
                    public void onResponse(Call<Faculty_request>call, Response<Faculty_request> response) {
                        int statusCode = response.code();
                        int success = response.body().getSuccess();
                        String message = response.body().getMessage();
                        if(success==1){
                            int counts = response.body().getCount();
                            List<Faculty> fac = response.body().getFaculties();
                            for(int i=0;i<counts;i++){
                                if(fac.get(i).getUser_id()==currentRequest.getFaculty_id()){
                                    muserName = fac.get(i).getFaculty_name();
                                    name.setText(muserName);
                                    Log.e("dd",muserName);
                                }
                            }
                        }
                        else{
                            Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onFailure(Call<Faculty_request>call, Throwable t) {
                        // Log error here since request failed
                        Log.e(TAG, t.toString());
                    }
                });
            }


            // Find the TextView in the list_item.xml layout with the ID default_text_view.
            TextView urgentu = (TextView) listItemView.findViewById(R.id.urgency);
            Log.e(" ",currentRequest.isUrgent()+" ");
            if(currentRequest.isUrgent() ==1) {
                urgentu.setText("Urgent");
                urgentu.setVisibility(View.VISIBLE);
            }

            TextView reason = (TextView) listItemView.findViewById(R.id.reson_show);
            reason.setText(currentRequest.getReason());
            TextView datett = (TextView) listItemView.findViewById(R.id.date_show);
            datett.setText(currentRequest.getRequest_date());
            TextView timett = (TextView) listItemView.findViewById(R.id.time_show);
            timett.setText(currentRequest.getRequest_time());

            return listItemView;

    }
}
