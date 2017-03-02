package in.ac.lnmiit.android.appointr.ApiCall;

import com.google.gson.annotations.SerializedName;

public class General_Query {
    @SerializedName("success")
    private int success;
    @SerializedName("message")
    private String message;


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


}
