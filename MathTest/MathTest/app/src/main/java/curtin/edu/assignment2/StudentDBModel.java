package curtin.edu.assignment2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import curtin.edu.assignment2.DBSchema.studentTable;

public class StudentDBModel
{
    SQLiteDatabase db;

    public void load(Context context)
    {
        this.db = new DBHelper(context).getWritableDatabase();
    }

    public void addStudent(Student student)
    {
        ContentValues cv = new ContentValues();
        cv.put(studentTable.Cols.ID, student.getId());
        cv.put(studentTable.Cols.FIRSTNAME, student.getFirstname());
        cv.put(studentTable.Cols.LASTNAME, student.getLastname());
        cv.put(studentTable.Cols.EMAIL, student.getEmail());
        cv.put(studentTable.Cols.NUMBER, student.getNumber());
        cv.put(studentTable.Cols.IMAGE, student.getImage());
        db.insert(studentTable.NAME, null, cv);
    }
    
    public void updateStudent(Student student)
    {
        ContentValues cv = new ContentValues();
        cv.put(studentTable.Cols.FIRSTNAME, student.getFirstname());
        cv.put(studentTable.Cols.LASTNAME, student.getLastname());
        cv.put(studentTable.Cols.EMAIL, student.getEmail());
        cv.put(studentTable.Cols.NUMBER, student.getNumber());
        cv.put(studentTable.Cols.IMAGE, student.getImage());
        
        String[] whereValue = { String.valueOf(student.getId()) };
        db.update(studentTable.NAME, cv, studentTable.Cols.ID + " = ?", whereValue);
    }
    
    public void removeStudent(Student student)
    {
        String[] whereValue = { String.valueOf(student.getId()) };
        db.delete(studentTable.NAME, studentTable.Cols.ID + " = ?", whereValue);
    }

    public Student getStudent(String name)
    {
        DBCursor c = new DBCursor(
                db.query(studentTable.NAME, null, "firstname = '" + name + "'", null, null, null,  null));

        c.moveToFirst();
        Student student = c.getStudent();

        return student;
    }

    public Student getStudentById(int id)
    {
        DBCursor c = new DBCursor(
                db.query(studentTable.NAME, null, "id = '" + id + "'", null, null, null, null));

        c.moveToFirst();
        Student student = c.getStudent();

        return student;
    }

    public ArrayList<Student> getAllStudent()
    {
        ArrayList<Student> studentList = new ArrayList<>();
        Cursor cursor = db.query(studentTable.NAME, null, null, null, null, null, null);
        DBCursor c = new DBCursor(cursor);

        try
        {
            c.moveToFirst();
            while(!c.isAfterLast())
            {
                studentList.add(c.getStudent());
                c.moveToNext();
            }
        }
        finally
        {
            cursor.close();
        }

        return studentList;
    }
}
