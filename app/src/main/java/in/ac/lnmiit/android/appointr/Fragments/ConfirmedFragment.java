package in.ac.lnmiit.android.appointr.Fragments;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import in.ac.lnmiit.android.appointr.Activities.Appoint;
import in.ac.lnmiit.android.appointr.Activities.F_Home;
import in.ac.lnmiit.android.appointr.Activities.NotificationView;
import in.ac.lnmiit.android.appointr.Activities.S_Home;
import in.ac.lnmiit.android.appointr.Adapters.RequestAdapter;
import in.ac.lnmiit.android.appointr.DatabaseConnections.ApiClient;
import in.ac.lnmiit.android.appointr.DatabaseConnections.ApiInterface;
import in.ac.lnmiit.android.appointr.DatabaseConnections.DatabaseHelper;
import in.ac.lnmiit.android.appointr.Functions.SessionManagement;
import in.ac.lnmiit.android.appointr.R;
import in.ac.lnmiit.android.appointr.models.General_Query;
import in.ac.lnmiit.android.appointr.models.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConfirmedFragment extends Fragment {
    private static final String TAG = Appoint.class.getSimpleName();
    private List<Request> requests = new ArrayList<>();
    SessionManagement session;
    public ConfirmedFragment() {
        // Required empty public constructor
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        View rootView = inflater.inflate(R.layout.list_view, container, false);
        RecyclerView recycle = (RecyclerView) rootView.findViewById(R.id.recycler);
        LinearLayoutManager llm = new LinearLayoutManager(this.getContext());
        TextView emptyView = (TextView) rootView.findViewById(R.id.emptyElement);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recycle.setLayoutManager(llm);
        session = new SessionManagement(rootView.getContext());
        List<Request> storedRequest;
        if(session.getSession().equals("faculty")){
            F_Home activity = (F_Home) getActivity();
            storedRequest = activity.getRequestss();
        }else{
            S_Home activity = (S_Home) getActivity();
            storedRequest = activity.getRequestss();
        }
        DatabaseHelper help = new DatabaseHelper(getContext());
        for(int i = 0; storedRequest!=null && i<storedRequest.size(); i++){
            if(storedRequest.get(i).isStatus()==1 && storedRequest.get(i).isUrgent()==1 && storedRequest.get(i).getIsClose()==0){
                requests.add(storedRequest.get(i));
            }
        }
        for(int i = 0; storedRequest!=null && i<storedRequest.size(); i++){
            if(storedRequest.get(i).isStatus()==1 && storedRequest.get(i).isUrgent()==0 && storedRequest.get(i).getIsClose()==0){
                requests.add(storedRequest.get(i));
            }
        }

        for(int i = 0; storedRequest!=null && i<storedRequest.size(); i++){
          if(storedRequest.get(i).getRequest_date() + 43200000 < System.currentTimeMillis() && storedRequest.get(i).getIsClose()==0){

                ApiInterface apiService =
                        ApiClient.getClient().create(ApiInterface.class);
                Map<String,String> names= new HashMap<String, String>();
                names.put("id",""+storedRequest.get(i).getRequest_id());
                Call<General_Query> call = apiService.deleteAppointment(names);
                call.enqueue(new Callback<General_Query>() {
                    @Override
                    public void onResponse(Call<General_Query>call, Response<General_Query> response) {

                        if(response.body().getSuccess()==1){
                           Log.v("tag",response.body().getMessage());
                        }


                    }

                    @Override
                    public void onFailure(Call<General_Query>call, Throwable t) {
                        // Log error here since request failed
                        Log.e(TAG, t.toString());
                    }
                });
            }
        }


        if(session.getSession().equals("student")) {
            for (int i = 0; storedRequest != null && i < storedRequest.size(); i++) {
                if (storedRequest.get(i).isStatus() == -1  && storedRequest.get(i).getIsClose()==0) {

                    NotificationCompat.Builder builder =
                            (NotificationCompat.Builder) new NotificationCompat.Builder(getContext())
                                    .setSmallIcon(R.mipmap.ic_launcher)
                                    .setContentTitle("Appointment Denied")
                                    .setContentText("Appointment with "+ help.getFaculty(storedRequest.get(i).getFaculty_id()).getFaculty_name() + " is denied.");

                    Intent notificationIntent = new Intent(getContext(), NotificationView.class);
                    notificationIntent.putExtra("request",storedRequest.get(i).getRequest_id());
                    PendingIntent contentIntent = PendingIntent.getActivity(getContext(), 0, notificationIntent,
                            PendingIntent.FLAG_UPDATE_CURRENT);
                    builder.setContentIntent(contentIntent);

                    // Add as notification
                    NotificationManager manager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
                    manager.notify(0, builder.build());

                }
            }
        }
        if (requests.isEmpty()) {
            recycle.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
        }
        else {
            recycle.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
            RequestAdapter adapter = new RequestAdapter(requests, getActivity());
            recycle.setAdapter(adapter);
        }


        help.closeDB();
        return rootView;
    }



}