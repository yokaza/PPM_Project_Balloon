package petra.tugas.ppm_project_balloon;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {
    private MainThread thread;
    private SceneManager manager;
    Context context;

    public GamePanel (Context context) {
        super(context);
        this.context = context;
        getHolder().addCallback(this);
        Constants.CURRENT_CONTEXT = context;

        thread = new MainThread(getHolder(), this);
        manager = new SceneManager(context);

        setFocusable(true);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        thread = new MainThread(getHolder(), this);
        Constants.INIT_TIME = System.currentTimeMillis();
        if(manager.RETRY) {
            thread.setRunning(true);
            thread.start();
        } else {
            thread.setRunning(false);
            ((Activity) context).finish();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        while(retry) {
            try {
                thread.setRunning(false);
                thread.join();
            }catch (Exception e) {
                e.printStackTrace();
            }
            retry = false;
        }
    }

    @Override
    public boolean onTouchEvent (MotionEvent event) {
        manager.recieveTouch(event);
        return true;
        //return super.onTouchEvent(event);
    }

    public MainThread getThread() {
        return thread;
    }

    public void update() {
        manager.update();
        if(!manager.RETRY) {
            System.out.println("brenti woi");
            thread.setRunning(false);
            ((Activity) context).finish();
        }
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        manager.draw(canvas);
    }
    public boolean getEXIT() {
        return manager.RETRY;
    }
}
