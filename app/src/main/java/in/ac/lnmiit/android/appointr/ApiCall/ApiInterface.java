package in.ac.lnmiit.android.appointr.ApiCall;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiInterface {

    @FormUrlEncoded
    @POST("login_request_faculty.php")
    Call<login> checkFLogin(@FieldMap Map<String, String> names);

    @FormUrlEncoded
    @POST("login_request_student.php")
    Call<login> checkSLogin(@FieldMap Map<String, String> names);

    @FormUrlEncoded
    @POST("Create_Request.php")
    Call<General_Query> createRRequest(@FieldMap Map<String, String> names);

    @FormUrlEncoded
    @POST("modify_request.php")
    Call<General_Query> modifyRequest(@FieldMap Map<String, String> names);

    @FormUrlEncoded
    @POST("password_change.php")
    Call<General_Query> passChange(@FieldMap Map<String, String> names);

    @POST("show_faculty.php")
    Call<Faculty_request> showF();

    @POST("show_student.php")
    Call<Student_request> showS();

    @FormUrlEncoded
    @POST("show_request.php")
    Call<RequestReq> showRequests(@FieldMap Map<String, String> names);


}
