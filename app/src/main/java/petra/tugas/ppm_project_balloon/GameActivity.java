package petra.tugas.ppm_project_balloon;

import android.app.Activity;
import android.os.Bundle;

public class GameActivity extends Activity {
    GamePanel gameView;
    MainThread gThread;

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
