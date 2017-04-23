package in.ac.lnmiit.android.appointr.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import in.ac.lnmiit.android.appointr.Activities.Appoint;
import in.ac.lnmiit.android.appointr.Activities.F_Home;
import in.ac.lnmiit.android.appointr.Activities.S_Home;
import in.ac.lnmiit.android.appointr.Adapters.RequestAdapter;
import in.ac.lnmiit.android.appointr.Functions.SessionManagement;
import in.ac.lnmiit.android.appointr.R;
import in.ac.lnmiit.android.appointr.models.Request;

public class UnconfirmedFragment extends Fragment {
    private static final String TAG = Appoint.class.getSimpleName();
    private List<Request> requests = new ArrayList<>();
    SessionManagement session;
    public UnconfirmedFragment() {
        // Required empty public constructor
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.list_view, container, false);
        RecyclerView recycle = (RecyclerView) rootView.findViewById(R.id.recycler);
        TextView emptyView = (TextView) rootView.findViewById(R.id.emptyElement);
        LinearLayoutManager llm = new LinearLayoutManager(this.getContext());
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


                        for(int i = 0; storedRequest!=null && i<storedRequest.size(); i++){
                            if(storedRequest.get(i).isStatus()==0 && storedRequest.get(i).isUrgent()==1 && storedRequest.get(i).getIsClose()==0){
                                requests.add(storedRequest.get(i));
                            }
                        }
                        for(int i = 0; storedRequest!=null && i<storedRequest.size(); i++){
                            if(storedRequest.get(i).isStatus()==0 && storedRequest.get(i).isUrgent()==0 && storedRequest.get(i).getIsClose()==0){
                                requests.add(storedRequest.get(i));
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





        return rootView;
    }


}
