package curtin.edu.pracgrader;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import curtin.edu.pracgrader.pracDBSchema.studentMarksTable;

public class MarkDBModel
{
    SQLiteDatabase db;

    public void load(Context context)
    {
        this.db = new pracDBHelper(context).getWritableDatabase();
    }

    public void addMark(Mark mark)
    {
        ContentValues cv = new ContentValues();
        cv.put(studentMarksTable.Cols.USERNAME, mark.getUsername());
        cv.put(studentMarksTable.Cols.PRACTICALNAME, mark.getPracName());
        cv.put(studentMarksTable.Cols.MARK, mark.getMark());
        db.insert(studentMarksTable.NAME, null, cv);
    }

    public void updateMark(Mark mark)
    {
        ContentValues cv = new ContentValues();
        cv.put(studentMarksTable.Cols.USERNAME, mark.getUsername());
        cv.put(studentMarksTable.Cols.PRACTICALNAME, mark.getPracName());
        cv.put(studentMarksTable.Cols.MARK, mark.getMark());

        String[] whereValue = { String.valueOf(mark.getUsername()) };
        db.update(studentMarksTable.NAME, cv, studentMarksTable.Cols.USERNAME + " = ?", whereValue);
    }

    public void removeMark(Mark mark)
    {
        String[] whereValue = { String.valueOf(mark.getUsername()) };
        db.delete(studentMarksTable.NAME, studentMarksTable.Cols.USERNAME + " = ?", whereValue);
    }

    public Mark getMark(String username)
    {
        pracDBCursor c = new pracDBCursor(
                db.query(studentMarksTable.NAME, null, "username = '" + username + "'", null, null, null, null));

        c.moveToFirst();
        Mark mark = c.getMark();

        return mark;
    }

    public ArrayList<Mark> getAllMark()
    {
        ArrayList<Mark> marks = new ArrayList<>();
        Cursor cursor = db.query(studentMarksTable.NAME, null, null, null, null, null, null);
        pracDBCursor pracDBCursor = new pracDBCursor(cursor);

        try
        {
            pracDBCursor.moveToFirst();
            while(!pracDBCursor.isAfterLast())
            {
                marks.add(pracDBCursor.getMark());
                pracDBCursor.moveToNext();
            }
        }
        finally
        {
            cursor.close();
        }

        return marks;
    }
}
