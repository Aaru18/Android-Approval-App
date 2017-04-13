package in.ac.lnmiit.android.appointr.Home;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import in.ac.lnmiit.android.appointr.Adapters.RequestAdapter;
import in.ac.lnmiit.android.appointr.ApiCall.ApiClient;
import in.ac.lnmiit.android.appointr.ApiCall.ApiInterface;
import in.ac.lnmiit.android.appointr.ApiCall.Request;
import in.ac.lnmiit.android.appointr.ApiCall.RequestReq;
import in.ac.lnmiit.android.appointr.ApiCall.SessionManagement;
import in.ac.lnmiit.android.appointr.Appoint;
import in.ac.lnmiit.android.appointr.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConfirmedFragment extends Fragment {
    private static final String TAG = Appoint.class.getSimpleName();
    private List<Request> requests = new ArrayList<>();
    public ConfirmedFragment() {
        // Required empty public constructor
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.list_view, container, false);
        final RecyclerView recycle = (RecyclerView) rootView.findViewById(R.id.recycler);
        LinearLayoutManager llm = new LinearLayoutManager(this.getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recycle.setLayoutManager(llm);
        SessionManagement session = new SessionManagement(this.getContext());
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        Map<String,String> names= new HashMap<String, String>();
        names.put("id",""+session.getID());
        names.put("user",session.getSession());
        Call<RequestReq> call = apiService.showRequests(names);
        call.enqueue(new Callback<RequestReq>() {
            @Override
            public void onResponse(Call<RequestReq>call, Response<RequestReq> response) {
                if(response.body()!=null){
                    int statusCode = response.code();
                    int success = response.body().getSuccess();
                    String message = response.body().getMessage();
                    if(success==1){


                        for(int i = 0; i<response.body().getRequests().size(); i++){
                            if(response.body().getRequests().get(i).isStatus()==1 && response.body().getRequests().get(i).isUrgent()==1){
                                requests.add(response.body().getRequests().get(i));
                            }
                        }
                        for(int i = 0; i<response.body().getRequests().size(); i++){
                            if(response.body().getRequests().get(i).isStatus()==1 && response.body().getRequests().get(i).isUrgent()==0){
                                requests.add(response.body().getRequests().get(i));
                            }
                        }

                        RequestAdapter adapter = new RequestAdapter(requests);


                        recycle.setAdapter(adapter);


                    }
                    else{
                        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                    }
                }


            }

            @Override
            public void onFailure(Call<RequestReq> call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
            }
        });


        return rootView;
    }

}
