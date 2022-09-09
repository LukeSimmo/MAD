package curtin.edu.pracgrader;

import curtin.edu.pracgrader.pracDBSchema.userTable;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class UserDBModel
{
    SQLiteDatabase db;

    public void load(Context context)
    {
        this.db = new pracDBHelper(context).getWritableDatabase();
    }

    public void addUser(User user)
    {
        ContentValues cv = new ContentValues();
        cv.put(userTable.Cols.USERNAME, user.getUsername());
        cv.put(userTable.Cols.NAME, user.getName());
        cv.put(userTable.Cols.EMAIL, user.getEmail());
        cv.put(userTable.Cols.PIN, user.getPin());
        cv.put(userTable.Cols.COUNTRY, user.getCountry());
        cv.put(userTable.Cols.TYPE, user.getType());
        cv.put(userTable.Cols.ADDEDBY, user.getAddedBy());
        db.insert(userTable.NAME, null, cv);
    }

    public void updateUser(User user)
    {
        ContentValues cv = new ContentValues();
        cv.put(userTable.Cols.USERNAME, user.getUsername());
        cv.put(userTable.Cols.NAME, user.getName());
        cv.put(userTable.Cols.EMAIL, user.getEmail());
        cv.put(userTable.Cols.PIN, user.getPin());
        cv.put(userTable.Cols.COUNTRY, user.getCountry());
        cv.put(userTable.Cols.TYPE, user.getType());
        cv.put(userTable.Cols.ADDEDBY, user.getAddedBy());

        String[] whereValue = { String.valueOf(user.getUsername()) };
        db.update(userTable.NAME, cv, userTable.Cols.USERNAME + " = ?", whereValue);
    }

    public void removeUser(User user)
    {
        String[] whereValue = { String.valueOf(user.getUsername()) };

        db.delete(userTable.NAME, userTable.Cols.USERNAME + " = ?", whereValue);
    }

    public boolean hasUsername(String username)
    {
        boolean usr = false;

        pracDBCursor cursor = new pracDBCursor(
                db.query(userTable.NAME, null , "username = " + "'" + username + "'", null, null, null, null, null));

        if(cursor.getCount() > 0)
        {
            usr = true;
        }

        return usr;
    }

    public boolean hasAdmin()
    {
        boolean admin = false;

        pracDBCursor cursor = new pracDBCursor(
                db.query(userTable.NAME, null, "type = " + "'admin'", null, null, null, null));

        if(cursor.getCount() > 0)
        {
            admin = true;
        }

        return admin;
    }

    public User getUser(String username)
    {
        pracDBCursor c = new pracDBCursor(
                db.query(userTable.NAME, null, "username = " + "'" + username + "'", null, null, null, null));

        c.moveToFirst();
        User user = c.getUser();


        return user;
    }

    public ArrayList<User> getAllUser()
    {
        ArrayList<User> userList = new ArrayList<>();
        Cursor cursor = db.query(userTable.NAME, null, null, null, null, null, null, null);
        pracDBCursor pracDBCursor = new pracDBCursor(cursor);

        try
        {
            pracDBCursor.moveToFirst();
            while(!pracDBCursor.isAfterLast())
            {
                userList.add(pracDBCursor.getUser());
                pracDBCursor.moveToNext();
            }
        }
        finally
        {
            cursor.close();
        }
        return userList;
    }

    public ArrayList<User> getAllInstructor()
    {
        ArrayList<User> instructorList = new ArrayList<>();
        Cursor cursor = db.query(userTable.NAME, null, "type = 'Instructor'", null, null, null, null, null);
        pracDBCursor pracDBCursor = new pracDBCursor(cursor);

        try
        {
            pracDBCursor.moveToFirst();
            while(!pracDBCursor.isAfterLast())
            {
                instructorList.add(pracDBCursor.getUser());
                pracDBCursor.moveToNext();
            }
        }
        finally
        {
            cursor.close();
        }

        return instructorList;
    }

    public ArrayList<User> getAllStudent()
    {
        ArrayList<User> studentList = new ArrayList<>();
        Cursor cursor = db.query(userTable.NAME, null, "type = 'Student'", null, null, null, null, null);
        pracDBCursor pracDBCursor = new pracDBCursor(cursor);

        try
        {
            pracDBCursor.moveToFirst();
            while(!pracDBCursor.isAfterLast())
            {
                studentList.add(pracDBCursor.getUser());
                pracDBCursor.moveToNext();
            }
        }
        finally
        {
            cursor.close();
        }

        return studentList;
    }
}
