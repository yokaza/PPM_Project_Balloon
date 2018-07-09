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
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MainActivity extends Activity {
    Context context;
    ListView lv;
    //public static CustomAdaptor custom;

    SQLiteDatabase sqlitedb;
    //
    TextView _lnama;
    TextView _lscore;
    TextView _lnama2;
    TextView _lscore2;
    TextView _lnama3;
    TextView _lscore3;
    TextView _lnama4;
    TextView _lscore4;
    TextView _lnama5;
    TextView _lscore5;
    //
    ArrayList<String> _listNama;
    ArrayList<Integer> _listScore;
    DBHelper DB;
    //
    ArrayAdapter<ArrayList> adap_nama;
    ArrayAdapter<ArrayList> adap_score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        Constants.SCREEN_WIDTH=dm.widthPixels;
        Constants.SCREEN_HEIGHT=dm.heightPixels;
        DB = new DBHelper(this);
        sqlitedb = DB.getWritableDatabase();
        DB.onCreate(sqlitedb);
        _listNama = new ArrayList<String>();
        _listScore = new ArrayList<Integer>();


        for(int i=0;i<5;i++) {
            _listNama.add(i,DB.GetData1(sqlitedb,i));
            _listScore.add(i,DB.GetData2(sqlitedb,i));
            System.out.println(_listNama.get(i).toString());
        }
        _lnama = (TextView) findViewById(R.id.tv1);
        _lscore = (TextView) findViewById(R.id.tv2);
        _lnama2 = (TextView) findViewById(R.id.tv3);
        _lscore2 = (TextView) findViewById(R.id.tv4);
        _lnama3 = (TextView) findViewById(R.id.tv5);
        _lscore3 = (TextView) findViewById(R.id.tv6);
        _lnama4 = (TextView) findViewById(R.id.tv7);
        _lscore4 = (TextView) findViewById(R.id.tv8);
        _lnama5 = (TextView) findViewById(R.id.tv9);
        _lscore5 = (TextView) findViewById(R.id.tv10);

        _lnama.setText(_listNama.get(0));
        _lscore.setText(_listScore.get(0).toString());
        _lnama2.setText(_listNama.get(1));
        _lscore2.setText(_listScore.get(1).toString());
        _lnama3.setText(_listNama.get(2));
        _lscore3.setText(_listScore.get(2).toString());
        _lnama4.setText(_listNama.get(3));
        _lscore4.setText(_listScore.get(3).toString());
        _lnama5.setText(_listNama.get(4));
        _lscore5.setText(_listScore.get(4).toString());
    }
    @Override
    protected void onRestart() {
        super.onRestart();
        System.out.print("Restart");

        for(int i=0;i<5;i++) {
            _listNama.add(i,DB.GetData1(sqlitedb,i));
            _listScore.add(i,DB.GetData2(sqlitedb,i));
            System.out.println(_listNama.get(i).toString());
        }
        _lnama = (TextView) findViewById(R.id.tv1);
        _lscore = (TextView) findViewById(R.id.tv2);
        _lnama2 = (TextView) findViewById(R.id.tv3);
        _lscore2 = (TextView) findViewById(R.id.tv4);
        _lnama3 = (TextView) findViewById(R.id.tv5);
        _lscore3 = (TextView) findViewById(R.id.tv6);
        _lnama4 = (TextView) findViewById(R.id.tv7);
        _lscore4 = (TextView) findViewById(R.id.tv8);
        _lnama5 = (TextView) findViewById(R.id.tv9);
        _lscore5 = (TextView) findViewById(R.id.tv10);

        _lnama.setText(_listNama.get(0));
        _lscore.setText(_listScore.get(0).toString());
        _lnama2.setText(_listNama.get(1));
        _lscore2.setText(_listScore.get(1).toString());
        _lnama3.setText(_listNama.get(2));
        _lscore3.setText(_listScore.get(2).toString());
        _lnama4.setText(_listNama.get(3));
        _lscore4.setText(_listScore.get(3).toString());
        _lnama5.setText(_listNama.get(4));
        _lscore5.setText(_listScore.get(4).toString());
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
