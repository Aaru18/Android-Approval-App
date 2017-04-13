package in.ac.lnmiit.android.appointr.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import in.ac.lnmiit.android.appointr.ApiCall.ApiClient;
import in.ac.lnmiit.android.appointr.ApiCall.ApiInterface;
import in.ac.lnmiit.android.appointr.ApiCall.Faculty;
import in.ac.lnmiit.android.appointr.ApiCall.Faculty_request;
import in.ac.lnmiit.android.appointr.ApiCall.Request;
import in.ac.lnmiit.android.appointr.ApiCall.SessionManagement;
import in.ac.lnmiit.android.appointr.ApiCall.Student;
import in.ac.lnmiit.android.appointr.ApiCall.Student_request;
import in.ac.lnmiit.android.appointr.Appoint;
import in.ac.lnmiit.android.appointr.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RequestAdapter extends RecyclerView.Adapter<RequestAdapter.MyViewHolder> {
    private static final String TAG = Appoint.class.getSimpleName();
    Context context;
    String muserName = "";
    private List<Request> requestList;

    public RequestAdapter(List<Request> requestList) {
        this.requestList = requestList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);
        context = parent.getContext();
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final Request request = requestList.get(position);
        final TextView user = holder.username;
        if (request.isUrgent() == 1) {
            holder.urgent.setText("Urgent");
            holder.urgent.setVisibility(View.VISIBLE);

        } else {
            holder.urgent.setText("");
            holder.urgent.setVisibility(View.GONE);
        }
        holder.reason.setText(request.getReason());
        holder.date.setText(request.getRequest_date());
        holder.time.setText(request.getRequest_time());
        SessionManagement session = new SessionManagement(context);
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        Map<String, String> names = new HashMap<String, String>();
        names.put("id", "" + session.getID());
        names.put("user", session.getSession());
        if (session.getSession().equals("faculty")) {
            Call<Student_request> call = apiService.showS();
            call.enqueue(new Callback<Student_request>() {
                @Override
                public void onResponse(Call<Student_request> call, Response<Student_request> response) {
                    if (response.body() != null) {
                        int statusCode = response.code();
                        int success = response.body().getSuccess();
                        String message = response.body().getMessage();
                        if (success == 1) {
                            int counts = response.body().getCount();
                            List<Student> stud = response.body().getStudents();
                            for (int i = 0; i < counts; i++) {
                                if (stud.get(i).getUser_id() == request.getStudent_id()) {
                                    muserName = stud.get(i).getStudent_name();
                                    user.setText(muserName);
                                }
                            }
                        } else {
                            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                        }
                    }


                }

                @Override
                public void onFailure(Call<Student_request> call, Throwable t) {
                    // Log error here since request failed
                    Log.e(TAG, t.toString());
                }
            });
        } else {
            Call<Faculty_request> call = apiService.showF();
            call.enqueue(new Callback<Faculty_request>() {
                @Override
                public void onResponse(Call<Faculty_request> call, Response<Faculty_request> response) {
                    if (response.body() != null) {
                        int statusCode = response.code();
                        int success = response.body().getSuccess();
                        String message = response.body().getMessage();
                        if (success == 1) {
                            int counts = response.body().getCount();
                            List<Faculty> fac = response.body().getFaculties();
                            for (int i = 0; i < counts; i++) {
                                if (fac.get(i).getUser_id() == request.getFaculty_id()) {
                                    muserName = fac.get(i).getFaculty_name();
                                    user.setText(muserName);
                                }
                            }
                        } else {
                            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                        }

                    }

                }

                @Override
                public void onFailure(Call<Faculty_request> call, Throwable t) {
                    // Log error here since request failed
                    Log.e(TAG, t.toString());
                }
            });
        }


    }

    @Override
    public int getItemCount() {
        return requestList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView urgent, reason, date, time, username;

        public MyViewHolder(View view) {
            super(view);
            username = (TextView) view.findViewById(R.id.user_name);
            urgent = (TextView) view.findViewById(R.id.urgency);
            reason = (TextView) view.findViewById(R.id.reson_show);
            date = (TextView) view.findViewById(R.id.date_show);
            time = (TextView) view.findViewById(R.id.time_show);
        }
    }
}















