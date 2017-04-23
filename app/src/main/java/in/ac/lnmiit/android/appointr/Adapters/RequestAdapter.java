package in.ac.lnmiit.android.appointr.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import in.ac.lnmiit.android.appointr.Activities.Appoint;
import in.ac.lnmiit.android.appointr.Activities.Reply;
import in.ac.lnmiit.android.appointr.DatabaseConnections.DatabaseHelper;
import in.ac.lnmiit.android.appointr.Functions.FunctionUsed;
import in.ac.lnmiit.android.appointr.Functions.SessionManagement;
import in.ac.lnmiit.android.appointr.R;
import in.ac.lnmiit.android.appointr.models.Request;

public class RequestAdapter extends RecyclerView.Adapter<RequestAdapter.MyViewHolder> {
    private static final String TAG = Appoint.class.getSimpleName();
    Context context;
    String muserName = "";
    private List<Request> requestList;
    SessionManagement session;
    Activity activity;


    public RequestAdapter(List<Request> requestList, Activity activity) {
        this.requestList = requestList;
        this.activity = activity;
        this.context = activity.getApplicationContext();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);
        session = new SessionManagement(context);
        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final Request request = requestList.get(position);
        final TextView user = holder.username;
        DatabaseHelper helpp = new DatabaseHelper(context);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (session.getSession().equals("faculty")) {
                    Intent intent = new Intent(activity, Reply.class);
                    intent.putExtra("request_id", requestList.get(position).getRequest_id());
                    activity.startActivity(intent);
                    activity.finish();
                }
            }
        });
        if (request.isUrgent() == 1) {
            holder.urgent.setText("Urgent");
            holder.urgent.setVisibility(View.VISIBLE);

        } else {
            holder.urgent.setText("");
            holder.urgent.setVisibility(View.GONE);
        }
        if (session.getSession().equals("faculty")) {
            holder.image.setImageResource(R.drawable.ic_learning);
            holder.username.setText( helpp.getStudent(request.getStudent_id()).getStudent_name());
        } else {
            holder.image.setImageResource(R.drawable.ic_team);
            holder.username.setText( helpp.getFaculty(request.getFaculty_id()).getFaculty_name());
        }

        holder.reason.setText(request.getReason());
        holder.date.setText(FunctionUsed.getStringFromDate(request.getRequest_date()));
        holder.time.setText(FunctionUsed.getTimeFromDate(request.getRequest_date()));


    }

    @Override
    public int getItemCount() {
        return requestList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView urgent, reason, date, time, username;
        public ImageView image;

        public MyViewHolder(View view) {
            super(view);
            image = (ImageView) view.findViewById(R.id.list_image);
            username = (TextView) view.findViewById(R.id.user_name);
            urgent = (TextView) view.findViewById(R.id.urgency);
            reason = (TextView) view.findViewById(R.id.reson_show);
            date = (TextView) view.findViewById(R.id.date_show);
            time = (TextView) view.findViewById(R.id.time_show);
        }
    }

}















