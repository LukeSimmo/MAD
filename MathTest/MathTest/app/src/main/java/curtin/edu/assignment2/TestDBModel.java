package curtin.edu.assignment2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import curtin.edu.assignment2.DBSchema.testTable;

public class TestDBModel
{
    SQLiteDatabase db;

    public void load(Context context)
    {
        this.db = new DBHelper(context).getWritableDatabase();
    }

    public void addTest(Test test)
    {
        ContentValues cv = new ContentValues();
        cv.put(testTable.Cols.STUDENTID, test.getStudentID());
        cv.put(testTable.Cols.TIMEOFTEST, test.getTimeOfTest());
        cv.put(testTable.Cols.TOTALTIME, test.getTotalTime());
        cv.put(testTable.Cols.MARK, test.getMark());
        db.insert(testTable.NAME, null, cv);
    }

    public void updateTest(Test test)
    {
        ContentValues cv = new ContentValues();
        cv.put(testTable.Cols.STUDENTID, test.getStudentID());
        cv.put(testTable.Cols.TIMEOFTEST, test.getTimeOfTest());
        cv.put(testTable.Cols.TOTALTIME, test.getTotalTime());
        cv.put(testTable.Cols.MARK, test.getMark());

        String[] wherevalue = { String.valueOf(test.getStudentID()) };
        db.update(testTable.NAME, cv, testTable.Cols.STUDENTID + " = ?", wherevalue);
    }

    public void removeTest(Test test)
    {
        String[] whereValue = { String.valueOf(test.getStudentID())};
        db.delete(testTable.NAME, testTable.Cols.STUDENTID + " = ?", whereValue);
    }

    public ArrayList<Test> getAllStudentTest(int id)
    {
        ArrayList<Test> testList = new ArrayList<>();
        Cursor cursor = db.query(testTable.NAME, null, "studentID = '" + id + "'", null, null, null, null);
        DBCursor c = new DBCursor(cursor);

        try
        {
            c.moveToFirst();
            while(!c.isAfterLast())
            {
                testList.add(c.getTest());
                c.moveToNext();
            }
        }
        finally
        {
            cursor.close();
        }

        return testList;
    }

    public ArrayList<Test> getAllTest()
    {
        ArrayList<Test> testList = new ArrayList<>();
        Cursor cursor = db.query(testTable.NAME, null, null, null, null, null, null);
        DBCursor c = new DBCursor(cursor);

        try
        {
            c.moveToFirst();
            while(!c.isAfterLast())
            {
                testList.add(c.getTest());
                c.moveToNext();
            }
        }
        finally
        {
            cursor.close();
        }

        return testList;
    }
}
