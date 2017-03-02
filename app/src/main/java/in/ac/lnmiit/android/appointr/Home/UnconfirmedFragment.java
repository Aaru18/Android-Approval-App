package in.ac.lnmiit.android.appointr.Home;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
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
import in.ac.lnmiit.android.appointr.Appoint;
import in.ac.lnmiit.android.appointr.R;
import in.ac.lnmiit.android.appointr.Reply;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UnconfirmedFragment extends Fragment {
    private static final String TAG = Appoint.class.getSimpleName();
    public ArrayList<Request> words = new ArrayList<Request>();
    public UnconfirmedFragment() {
        // Required empty public constructor
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final android.view.View rootView = inflater.inflate(R.layout.list_view, container, false);
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("mypref", Context.MODE_PRIVATE);
        int user_id = sharedPreferences.getInt("id",0);
        final String session_id = sharedPreferences.getString("session",null);
        final ArrayList<Request> requests = new ArrayList<Request>();
        final ListView listView = (ListView) rootView.findViewById(R.id.list);

        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        Map<String,String> names= new HashMap<String, String>();
        names.put("id",""+user_id);
        names.put("user",session_id);
        Call<RequestReq> call = apiService.showRequests(names);
        call.enqueue(new Callback<RequestReq>() {
            @Override
            public void onResponse(Call<RequestReq>call, Response<RequestReq> response) {
                int statusCode = response.code();
                int success = response.body().getSuccess();
                String message = response.body().getMessage();
                if(success==1){
                    int counts = response.body().getCount();
                    List<Request> requestss = response.body().getRequests();
                    words = (ArrayList<Request>) requestss;
                    RequestAdapter adapter = new RequestAdapter(getActivity(), (ArrayList<Request>) requestss, "Unconfirmed");


                    listView.setAdapter(adapter);


                }
                else{
                    Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<RequestReq>call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
            }
        });




        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                if(session_id.equals("faculty")) {
                    Request word = words.get(position);
                    int idd = word.getRequest_id();
                    Intent intent = new Intent(getActivity(), Reply.class);
                    intent.putExtra("request_id", idd);
                    startActivity(intent);
                }
                           }

        });

        return rootView;
    }


}
