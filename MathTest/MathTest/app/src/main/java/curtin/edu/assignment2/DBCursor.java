package curtin.edu.assignment2;

import android.database.Cursor;
import android.database.CursorWrapper;
import android.graphics.Bitmap;
import android.widget.ImageView;

import curtin.edu.assignment2.DBSchema.studentTable;
import curtin.edu.assignment2.DBSchema.testTable;

public class DBCursor extends CursorWrapper
{
    public DBCursor(Cursor cursor)
    {
        super(cursor);
    }

    public Student getStudent()
    {
        int id = getInt(getColumnIndex(studentTable.Cols.ID));
        String firstname = getString(getColumnIndex(studentTable.Cols.FIRSTNAME));
        String lastname = getString(getColumnIndex(studentTable.Cols.LASTNAME));
        String number = getString(getColumnIndex(studentTable.Cols.NUMBER));
        String email = getString(getColumnIndex(studentTable.Cols.EMAIL));
        byte[] image = getBlob(getColumnIndex(studentTable.Cols.IMAGE));
        return new Student(id, firstname, lastname, number, email, image);
    }

    public Test getTest()
    {
        int studentID = getInt(getColumnIndex(testTable.Cols.STUDENTID));
        String timeOfTest = getString(getColumnIndex(testTable.Cols.TIMEOFTEST));
        int totalTime = getInt(getColumnIndex(testTable.Cols.TOTALTIME));
        double mark = getDouble(getColumnIndex(testTable.Cols.MARK));
        return new Test(studentID, timeOfTest, totalTime, mark);
    }
}
