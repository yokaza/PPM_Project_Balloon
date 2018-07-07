package petra.tugas.ppm_project_balloon;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    private static final int DBVersion = 1;
    private static final String DBName = "HIGHSCORES";

    private static final String TableName = "SCORES";

    private static final String id = "NRP";
    private static final String Nama = "Nama";
    private static final String score = "SCORE";


    public DBHelper(Context context) {
        super(context, DBName, null, DBVersion);
    }

    public void InsertData(SQLiteDatabase db, String isiNama, int isiScore) {
        ContentValues myvalues = new ContentValues();
        myvalues.put(Nama, isiNama);
        myvalues.put(score, isiScore);
        db.insert(TableName, null, myvalues);

    }

    public String GetData1(SQLiteDatabase db, int pos) {
        db = this.getReadableDatabase();
        Cursor cursor = db.query(TableName, new String[] {id, Nama, score}, null, null, null, null, "score DESC");

        String nm;
        cursor.moveToPosition(pos);
        if (!cursor.isAfterLast()) {
            cursor.moveToPosition(pos);
            nm = cursor.getString(1);
        } else
            nm = "No One";
        return nm;
    }
    public int GetData2(SQLiteDatabase db, int pos) {
        db = this.getReadableDatabase();
        Cursor cursor = db.query(TableName, new String[] {id, Nama, score}, null, null, null, null, "score DESC");

        int nm;
        cursor.moveToPosition(pos);
        if (!cursor.isAfterLast()) {
            cursor.moveToPosition(pos);
            nm = cursor.getInt(2);
        } else
            nm = 0;
        return nm;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CreateTable = "CREATE TABLE if not EXISTS " + TableName + "(" +
                id + " INTEGER PRIMARY KEY AUTOINCREMENT,"+
                Nama + " TEXT," +
                score + " INTEGER" + ")";
        db.execSQL(CreateTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP Table if EXISTS " + TableName);
        onCreate(db);
    }
}