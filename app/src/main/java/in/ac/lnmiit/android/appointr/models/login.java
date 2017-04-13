package in.ac.lnmiit.android.appointr.models;

import com.google.gson.annotations.SerializedName;

public class login {

    @SerializedName("success")
    private int success;
    @SerializedName("message")
    private String message;
    @SerializedName("id")
    private int user_id;

    public login(int success, String message, int user_id){
        this.success = success;
        this.message = message;
        this.user_id = user_id;

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

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }


}
