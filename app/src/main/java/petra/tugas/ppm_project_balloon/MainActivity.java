package petra.tugas.ppm_project_balloon;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ListView;
import java.util.ArrayList;

public class MainActivity extends Activity {
    Context context;
    ListView lv;
    public static CustomAdaptor custom;
    private ArrayList<String> _listNama = new ArrayList<String>(5);
    private ArrayList<Integer> _listScore = new ArrayList<Integer>(5);
    SQLiteDatabase sqlitedb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        Constants.SCREEN_WIDTH=dm.widthPixels;
        Constants.SCREEN_HEIGHT=dm.heightPixels;
        final DBHelper DB = new DBHelper(this);
        sqlitedb = DB.getWritableDatabase();
        DB.onCreate(sqlitedb);

        setContentView(R.layout.activity_main);
        for(int i=0;i<5;i++) {
            _listNama.add(i,DB.GetData1(sqlitedb,i));
            _listScore.add(i,DB.GetData2(sqlitedb,i));
        }

        context=this;
        lv=(ListView) findViewById(R.id._sSheet);
        custom = new CustomAdaptor(this, _listNama, _listScore);
        lv.setAdapter(custom);

        lv.setTextFilterEnabled(true);


    }
    public void PlayGame(View v) {
        //setContentView(new GamePanel(this));
        Intent in = new Intent(this, GameActivity.class);
        EditText et1 = (EditText) findViewById(R.id._Name);
        String _UserName = et1.getText().toString();
        Constants.PLAYER_NAME = _UserName;
        startActivity(in);
    }
}
