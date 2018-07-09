package petra.tugas.ppm_project_balloon;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;

import java.util.ArrayList;

public class SceneManager{
    private ArrayList<Scene> scenes = new ArrayList<>();
    public static int ACTIVE_SCENE;
    public static boolean RETRY=true;
    Context context;

    public SceneManager(Context context) {
        ACTIVE_SCENE = 0;
        if(ACTIVE_SCENE==0) {
            scenes.add(new GameplayScene(context));
            RETRY=true;
        }
    }

    public void recieveTouch(MotionEvent event) {
        scenes.get(ACTIVE_SCENE).recieveTouch(event);
    }

    public void update() {
        scenes.get(ACTIVE_SCENE).update();
    }
    public void draw(Canvas canvas) {
        scenes.get(ACTIVE_SCENE).draw(canvas);
    }
}
