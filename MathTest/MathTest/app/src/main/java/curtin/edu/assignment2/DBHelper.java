package curtin.edu.assignment2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import curtin.edu.assignment2.DBSchema.studentTable;
import curtin.edu.assignment2.DBSchema.testTable;

public class DBHelper extends SQLiteOpenHelper
{
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "students.db";

    public DBHelper(Context context)
    {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL("create table " + studentTable.NAME + "("
                + studentTable.Cols.ID + " Text, "
                + studentTable.Cols.FIRSTNAME + " Text, "
                + studentTable.Cols.LASTNAME + " Text, "
                + studentTable.Cols.NUMBER + " Text, "
                + studentTable.Cols.EMAIL + " Text, "
                + studentTable.Cols.IMAGE + " Blob);");

        db.execSQL("create table " + testTable.NAME + "("
                + testTable.Cols.STUDENTID + " Text, "
                + testTable.Cols.TIMEOFTEST + " Text, "
                + testTable.Cols.TOTALTIME + " Text, "
                + testTable.Cols.MARK + " Text);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1)
    {

    }
}
