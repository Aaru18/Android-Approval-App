package in.ac.lnmiit.android.appointr.ApiCall;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Faculty_request {

    @SerializedName("success")
    private int success;
    @SerializedName("message")
    private String message;
    @SerializedName("faculties")
    private List<Faculty> faculties;
    @SerializedName("count")
    private int count;
    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }



    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Faculty> getFaculties() {
        return faculties;
    }

    public void setFaculties(List<Faculty> faculties) {
        this.faculties = faculties;
    }
}
