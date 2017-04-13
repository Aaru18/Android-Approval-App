package in.ac.lnmiit.android.appointr.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Student_request {
    @SerializedName("success")
    private int success;
    @SerializedName("message")
    private String message;
    @SerializedName("students")
    private List<Student> students;
    @SerializedName("count")
    private int count;

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

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
