package petra.tugas.ppm_project_balloon;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Vibrator;

public class GameActivity extends Activity {
    GamePanel gameView;
    MainThread gThread;
    SQLiteDatabase sqlitedb;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        final DBHelper DB = new DBHelper(this);
        sqlitedb = DB.getWritableDatabase();
        DB.onCreate(sqlitedb);
        DB.InsertData(sqlitedb, Constants.PLAYER_NAME,Constants.PLAYER_SCORE);
    }

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gameView = new GamePanel(this);
        setContentView(gameView);

        gThread = gameView.getThread();
        gThread.run();
    }
}
