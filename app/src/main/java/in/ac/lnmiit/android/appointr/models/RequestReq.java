package in.ac.lnmiit.android.appointr.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RequestReq {
    @SerializedName("success")
    private int success;
    @SerializedName("requests")
    private List<Request> requests;
    @SerializedName("message")
    private String message;
    @SerializedName("count")
    private int count;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public List<Request> getRequests() {
        return requests;
    }

    public void setRequests(List<Request> requests) {
        this.requests = requests;
    }
}
