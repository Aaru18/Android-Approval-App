package in.ac.lnmiit.android.appointr.ApiCall;

import com.google.gson.annotations.SerializedName;

public class Student {



    @SerializedName("user_id")
    private int user_id;
    @SerializedName("roll_no")
    private String roll_no;
    @SerializedName("student_name")
    private String student_name;
    @SerializedName("email_id")
    private String email_id;

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getRoll_no() {
        return roll_no;
    }

    public void setRoll_no(String roll_no) {
        this.roll_no = roll_no;
    }

    public String getStudent_name() {
        return student_name;
    }

    public void setStudent_name(String student_name) {
        this.student_name = student_name;
    }

    public String getEmail_id() {
        return email_id;
    }

    public void setEmail_id(String email_id) {
        this.email_id = email_id;
    }
}
