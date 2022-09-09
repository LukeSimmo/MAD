package curtin.edu.pracgrader;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import curtin.edu.pracgrader.pracDBSchema.userTable;
import curtin.edu.pracgrader.pracDBSchema.practicalTable;
import curtin.edu.pracgrader.pracDBSchema.studentMarksTable;

public class pracDBHelper extends SQLiteOpenHelper
{
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "users.db";

    public pracDBHelper(Context context)
    {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase)
    {
        sqLiteDatabase.execSQL("create table " + userTable.NAME+ "("
                + userTable.Cols.USERNAME + " Text, "
                + userTable.Cols.NAME + " Text, "
                + userTable.Cols.EMAIL + " Text, "
                + userTable.Cols.PIN + " Text, "
                + userTable.Cols.COUNTRY + " Text, "
                + userTable.Cols.TYPE + " Text, "
                + userTable.Cols.ADDEDBY + " Text);");

        sqLiteDatabase.execSQL("create table " + practicalTable.NAME+ "("
                + practicalTable.Cols.PRACTICALNAME + " Text, "
                + practicalTable.Cols.DESC + " Text, "
                + practicalTable.Cols.MARK + " Text);");

        sqLiteDatabase.execSQL("create table " + studentMarksTable.NAME+ "("
                + studentMarksTable.Cols.USERNAME + " Text, "
                + studentMarksTable.Cols.PRACTICALNAME + " Text, "
                + studentMarksTable.Cols.MARK + " Text);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1)
    {

    }
}
