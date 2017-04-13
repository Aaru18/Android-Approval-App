package in.ac.lnmiit.android.appointr.models;

import com.google.gson.annotations.SerializedName;

public class Faculty {

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getDepartments() {
        return departments;
    }

    public void setDepartments(String departments) {
        this.departments = departments;
    }

    public String getFaculty_name() {
        return faculty_name;
    }

    public void setFaculty_name(String faculty_name) {
        this.faculty_name = faculty_name;
    }

    @SerializedName("user_id")
    private int user_id;
    @SerializedName("departments")
    private String departments;
    @SerializedName("faculty_name")
    private String faculty_name;
}
