package petra.tugas.ppm_project_balloon;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

public class Player implements GameObject{

    private Rect rectangle;
    private int color;

    private Animation idle;
    private Animation blowRight;
    private Animation blowLeft;
    private AnimationManager animationManager;


    public Rect getRectangle() {
        return rectangle;
    }

    public Player(Rect rectangle, int color) {
        this.rectangle = rectangle;
        this.color = color;

        BitmapFactory bf = new BitmapFactory();
        Bitmap img_idle = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.idle);
        Bitmap img_blowLeft = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.blowleft);
        Bitmap img_blowRight = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.blowright);

        idle = new Animation(new Bitmap[]{img_idle, img_idle},2);
        blowLeft = new Animation(new Bitmap[]{img_blowLeft, img_blowLeft},2);
        blowRight = new Animation(new Bitmap[]{img_blowRight, img_blowRight},2);

        animationManager = new AnimationManager(new Animation[]{idle, blowRight, blowLeft});

    }

    @Override
    public void draw(Canvas canvas) {
        /*
        Paint paint = new Paint();
        paint.setColor(color);
        canvas.drawRect(rectangle,paint);
        */
        animationManager.draw(canvas,rectangle);
    }

    @Override
    public void update() {
        animationManager.update();

    }

    public void update(Point point) {
        float oldLeft = rectangle.left;

        //l,t,r,b
        rectangle.set(point.x - rectangle.width()/2, point.y - rectangle.height()/2, point.x + rectangle.width()/2, point.y + rectangle.height()/2);

        int state = 0;
        if(rectangle.left - oldLeft > 5)
            state = 1;
        else if(rectangle.left - oldLeft < -5)
            state = 2;
        animationManager.playAnim(state);
        animationManager.update();
    }
}
