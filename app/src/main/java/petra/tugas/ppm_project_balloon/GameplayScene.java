package petra.tugas.ppm_project_balloon;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.MotionEvent;

public class GameplayScene implements Scene {

    private Rect r = new Rect();

    private Player player;
    private Point playerPoint;
    private ObstacleManager obstacleManager;

    private boolean movingPlayer = false;

    private boolean gameOver = false;
    private long gameOverTime;

    private OrientationData orientationData;
    private long frameTime;

    private Boolean doubleClick=false;
    private long doubleClickTime;

    private long sizeDownTime;
    private int sizeDownCount=0;

    public GameplayScene() {
        //player
        //
        player = new Player(new Rect(0,0,Constants.SCREEN_WIDTH/10,Constants.SCREEN_WIDTH/10), Color.rgb(255, 0,0 ));
        playerPoint = new Point(Constants.SCREEN_WIDTH/2,3*Constants.SCREEN_HEIGHT/4);
        player.update(playerPoint);
        //obstacles
        obstacleManager = new ObstacleManager(Constants.SCREEN_WIDTH/3, Constants.SCREEN_HEIGHT/5,Constants.SCREEN_HEIGHT/10, Color.BLACK);

        orientationData = new OrientationData();
        orientationData.register();
        frameTime = System.currentTimeMillis();

    }

    public void reset() {
        playerPoint = new Point(Constants.SCREEN_WIDTH/2,3*Constants.SCREEN_HEIGHT/4);
        player.update(playerPoint);
        obstacleManager = new ObstacleManager(200, 350,75, Color.BLACK);
        movingPlayer = false;
    }

    @Override
    public void update() {
        if(!gameOver) {
            if(frameTime < Constants.INIT_TIME)
                frameTime = Constants.INIT_TIME;
            int elapsedTime = (int)(System.currentTimeMillis() - frameTime);
            frameTime = System.currentTimeMillis();
            if(orientationData.getOrientations() != null && orientationData.getStartOrientation() != null) {
                float pitch = orientationData.getOrientations()[1] - orientationData.getStartOrientation()[1];
                float roll = orientationData.getOrientations()[2] - orientationData.getStartOrientation()[2];

                float xSpeed = 2*roll * Constants.SCREEN_WIDTH/1000f;
                float ySpeed = pitch * Constants.SCREEN_HEIGHT/1000f;

                playerPoint.x += Math.abs(xSpeed * elapsedTime) > 5 ? xSpeed*elapsedTime : 0;
                //playerPoint.y -= Math.abs(ySpeed * elapsedTime) > 5 ? ySpeed*elapsedTime : 0;
            }

            if(playerPoint.x < 0)
                playerPoint.x = 0;
            else if(playerPoint.x > Constants.SCREEN_WIDTH)
                playerPoint.x = Constants.SCREEN_WIDTH;

            if(playerPoint.y < 0)
                playerPoint.y = 0;
            else if(playerPoint.y > Constants.SCREEN_HEIGHT)
                playerPoint.y = Constants.SCREEN_HEIGHT;

            player.update(playerPoint);
            obstacleManager.update();

            if (obstacleManager.playerCollode(player)) {
                gameOver = true;
                gameOverTime = System.currentTimeMillis();
            }
            if(sizeDownCount>0 && System.currentTimeMillis() - sizeDownTime >= 4000) {
                player.sizeDown(playerPoint);
                sizeDownCount--;
            }
        }

    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawColor(Color.WHITE);

        player.draw(canvas);
        obstacleManager.draw(canvas);

        if (gameOver) {
            Paint paint = new Paint();
            paint.setTextSize(100);
            paint.setColor(Color.MAGENTA);
            drawCenterText(canvas, paint, "GAME OVER");

        }
    }

    @Override
    public void terminate() {
        SceneManager.RETRY = false;
    }

    @Override
    public void recieveTouch(MotionEvent event) {
        switch(event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if(doubleClick && System.currentTimeMillis() - doubleClickTime <= 1000 && sizeDownCount<2) {
                    player.sizeUp(playerPoint);
                    sizeDownTime = System.currentTimeMillis();
                    doubleClick=false;
                    sizeDownCount++;
                }
                if(!gameOver && player.getRectangle().contains((int)event.getX(), (int)event.getY())) {
                    movingPlayer = true;
                    doubleClick=true;
                    doubleClickTime = System.currentTimeMillis();
                }
                if(gameOver && System.currentTimeMillis() - gameOverTime >= 2000) {
                    reset();
                    gameOver = false;
                    orientationData.newGame();
                    terminate();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if(movingPlayer && !gameOver)
                    playerPoint.set((int)event.getX(), (int)event.getY());
                break;
            case MotionEvent.ACTION_UP:
                movingPlayer = false;
                break;
        }

    }

    private void updateSize() {

    }

    private void drawCenterText(Canvas canvas, Paint paint, String text) {
        paint.setTextAlign(Paint.Align.LEFT);
        canvas.getClipBounds(r);
        int cHeight = r.height();
        int cWidth = r.width();
        paint.getTextBounds(text, 0, text.length(), r);
        float x = cWidth / 2f - r.width() / 2f - r.left;
        float y = cHeight / 2f + r.height() / 2f - r.bottom;
        canvas.drawText(text, x, y, paint);
    }
}
