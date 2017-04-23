package in.ac.lnmiit.android.appointr.DatabaseConnections;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import in.ac.lnmiit.android.appointr.models.Faculty;
import in.ac.lnmiit.android.appointr.models.Student;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String LOG = "DatabaseHelper";

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "appointr";

    // Table Names
    private static final String STUDENTS = "student";
    private static final String FACULTIES = "faculty";

    // Common column names
    private static final String KEY_ID = "user_id";


    // STUDENTS Table - column nmaes
    private static final String KEY_STUDENTS_NAME = "student_name";
    private static final String KEY_ROLLNO = "roll_no";

    // FACULTIES Table - column names
    private static final String KEY_FACULTY_NAME = "faculty_name";
    private static final String KEY_DEPARTMENTS = "departments";

    // Table Create Statements
    // Student table create statement
    private static final String CREATE_STUDENT = "CREATE TABLE "
            + STUDENTS + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_STUDENTS_NAME
            + " TEXT," + KEY_ROLLNO + " TEXT" + ")";

    // Faculty table create statement
    private static final String CREATE_FACULTY = "CREATE TABLE " + FACULTIES
            + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_FACULTY_NAME + " TEXT,"
            + KEY_DEPARTMENTS + " TEXT"  + ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // creating required tables
        db.execSQL(CREATE_STUDENT);
        db.execSQL(CREATE_FACULTY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + CREATE_FACULTY);
        db.execSQL("DROP TABLE IF EXISTS " + CREATE_STUDENT);

        onCreate(db);
    }

    public void createStudent(Student student) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID, student.getUser_id());
        values.put(KEY_STUDENTS_NAME, student.getStudent_name());
        values.put(KEY_ROLLNO, student.getRoll_no());

        db.insert(STUDENTS, null, values);
    }

    public void createFaculty(Faculty faculty) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID, faculty.getUser_id());
        values.put(KEY_FACULTY_NAME, faculty.getFaculty_name());
        values.put(KEY_DEPARTMENTS, faculty.getDepartments());

        db.insert(FACULTIES, null, values);
    }

    /**
     * get single todo
     */
    public Student getStudent(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + STUDENTS + " WHERE "
                + KEY_ID + " = " + userId;

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        Student td = new Student();
        td.setUser_id(c.getInt(c.getColumnIndex(KEY_ID)));
        td.setStudent_name((c.getString(c.getColumnIndex(KEY_STUDENTS_NAME))));
        td.setRoll_no((c.getString(c.getColumnIndex(KEY_ROLLNO))));

        return td;
    }
    public Faculty getFaculty(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + FACULTIES + " WHERE "
                + KEY_ID + " = " + userId;

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        Faculty td = new Faculty();
        td.setUser_id(c.getInt(c.getColumnIndex(KEY_ID)));
        td.setFaculty_name((c.getString(c.getColumnIndex(KEY_FACULTY_NAME))));
        td.setDepartments((c.getString(c.getColumnIndex(KEY_DEPARTMENTS))));

        return td;
    }

    public List<Student> getStudents() {
        List<Student> todos = new ArrayList<Student>();
        String selectQuery = "SELECT  * FROM " + STUDENTS;

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Student td = new Student();
                td.setUser_id(c.getInt(c.getColumnIndex(KEY_ID)));
                td.setStudent_name((c.getString(c.getColumnIndex(KEY_STUDENTS_NAME))));
                td.setRoll_no((c.getString(c.getColumnIndex(KEY_ROLLNO))));

                // adding to todo list
                todos.add(td);
            } while (c.moveToNext());
        }

        return todos;
    }
    public List<Faculty> getFaculties() {
        List<Faculty> todos = new ArrayList<Faculty>();
        String selectQuery = "SELECT  * FROM " + FACULTIES;

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Faculty td = new Faculty();
                td.setUser_id(c.getInt(c.getColumnIndex(KEY_ID)));
                td.setFaculty_name((c.getString(c.getColumnIndex(KEY_FACULTY_NAME))));
                td.setDepartments((c.getString(c.getColumnIndex(KEY_DEPARTMENTS))));

                // adding to todo list
                todos.add(td);
            } while (c.moveToNext());
        }

        return todos;
    }

    public List<String> getDepartments(){
        List<String> depart = new ArrayList<String>();
        String selectQuery = "SELECT DISTINCT " + KEY_DEPARTMENTS + " FROM " + FACULTIES;

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
            do {

                depart.add(c.getString(c.getColumnIndex(KEY_DEPARTMENTS)));
            } while (c.moveToNext());
        }

        return depart;
    }

    public List<String> getFacultyFromDepartments(String department){
        List<String> faculty = new ArrayList<String>();
        String selectQuery = "SELECT " + KEY_FACULTY_NAME + " FROM " + FACULTIES + " WHERE " + KEY_DEPARTMENTS + " = '" + department + "'";

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
            do {

                faculty.add(c.getString(c.getColumnIndex(KEY_FACULTY_NAME)));
            } while (c.moveToNext());
        }

        return faculty;
    }


    // closing database
    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }

    public void deleteDB(Context context){
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
        context.deleteDatabase(DATABASE_NAME);
    }

}