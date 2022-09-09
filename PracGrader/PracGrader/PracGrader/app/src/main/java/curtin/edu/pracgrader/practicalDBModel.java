package curtin.edu.pracgrader;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import curtin.edu.pracgrader.pracDBSchema.practicalTable;

public class practicalDBModel
{
    SQLiteDatabase db;

    public void load(Context context)
    {
        this.db = new pracDBHelper(context).getWritableDatabase();
    }

    public void addPractical(Practical prac)
    {
        ContentValues cv = new ContentValues();
        cv.put(practicalTable.Cols.PRACTICALNAME, prac.getName());
        cv.put(practicalTable.Cols.DESC, prac.getDesc());
        cv.put(practicalTable.Cols.MARK, prac.getMark());
        db.insert(practicalTable.NAME, null, cv);
    }

    public void updatePractical(Practical prac)
    {
        ContentValues cv = new ContentValues();
        cv.put(practicalTable.Cols.PRACTICALNAME, prac.getName());
        cv.put(practicalTable.Cols.DESC, prac.getDesc());
        cv.put(practicalTable.Cols.MARK, prac.getMark());

        String[] whereValue = { String.valueOf(prac.getName()) };
        db.update(practicalTable.NAME, cv, practicalTable.Cols.PRACTICALNAME + " = ?", whereValue);
    }

    public void removePractical(Practical prac)
    {
        String[] whereValue = { String.valueOf(prac.getName()) };
        db.delete(practicalTable.NAME, practicalTable.Cols.PRACTICALNAME + " = ?", whereValue);
    }

    public Practical getPrac(String name)
    {
        pracDBCursor c = new pracDBCursor(
                db.query(practicalTable.NAME, null, "practicalName = '" + name + "'", null, null, null, null, null));

        c.moveToFirst();
        Practical prac = c.getPractical();

        return prac;
    }

    public double getPracMark(String name)
    {
        pracDBCursor c = new pracDBCursor(
                db.query(practicalTable.NAME, null, "practicalName = '" + name + "'", null, null, null, null, null));

        c.moveToFirst();
        Practical prac = c.getPractical();

        return prac.getMark();
    }

    public boolean hasPractical(String name)
    {
        boolean prac = false;

        pracDBCursor cursor = new pracDBCursor(
                db.query(practicalTable.NAME, null, "practicalName = '" + name + "'", null, null, null, null, null));

        if(cursor.getCount() > 0)
        {
            prac = true;
        }

        return prac;
    }

    public ArrayList<Practical> getAllUser()
    {
        ArrayList<Practical> pracList = new ArrayList<>();
        Cursor cursor = db.query(practicalTable.NAME, null, null, null, null, null, null, null);
        pracDBCursor pracDBCursor = new pracDBCursor(cursor);

        try
        {
            pracDBCursor.moveToFirst();
            while(!pracDBCursor.isAfterLast())
            {
                pracList.add(pracDBCursor.getPractical());
                pracDBCursor.moveToNext();
            }
        }
        finally
        {
            cursor.close();
        }

        return pracList;
    }
}
