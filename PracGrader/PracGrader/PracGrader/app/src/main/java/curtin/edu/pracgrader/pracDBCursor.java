package curtin.edu.pracgrader;

import android.database.Cursor;
import android.database.CursorWrapper;

import curtin.edu.pracgrader.pracDBSchema.userTable;
import curtin.edu.pracgrader.pracDBSchema.practicalTable;
import curtin.edu.pracgrader.pracDBSchema.studentMarksTable;

public class pracDBCursor extends CursorWrapper
{
    public pracDBCursor(Cursor cursor)
    {
        super(cursor);
    }

    public User getUser()
    {
        String username = getString(getColumnIndex(userTable.Cols.USERNAME));
        String name = getString(getColumnIndex(userTable.Cols.NAME));
        String email = getString(getColumnIndex(userTable.Cols.EMAIL));
        String pin = getString(getColumnIndex(userTable.Cols.PIN));
        String country = getString(getColumnIndex(userTable.Cols.COUNTRY));
        String type = getString(getColumnIndex(userTable.Cols.TYPE));
        String addedBy = getString(getColumnIndex(userTable.Cols.ADDEDBY));
        return new User(username, name, email, pin, country, type, addedBy);
    }

     public Practical getPractical()
     {
         String pracName = getString(getColumnIndex(practicalTable.Cols.PRACTICALNAME));
         String desc = getString(getColumnIndex(practicalTable.Cols.DESC));
         double mark = getDouble(getColumnIndex(practicalTable.Cols.MARK));
         return new Practical(pracName, desc, mark);
     }

     public Mark getMark()
     {
         String username = getString(getColumnIndex(studentMarksTable.Cols.USERNAME));
         String pracName = getString(getColumnIndex(studentMarksTable.Cols.PRACTICALNAME));
         double mark = getDouble(getColumnIndex(studentMarksTable.Cols.MARK));
         return new Mark(username, pracName, mark);
     }
}
