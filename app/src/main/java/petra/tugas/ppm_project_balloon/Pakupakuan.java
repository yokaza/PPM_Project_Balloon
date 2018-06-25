package petra.tugas.ppm_project_balloon;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public class Pakupakuan implements GameObject {
    private Rect rectangle;
    private Rect rectangle2;
    private int color;
    private int startX;
    private int playerGap;

    private Animation nails;
    private AnimationManager animationManager;

    public Rect getRectangle() {
        return rectangle;
    }

    public void incrementY(float y) {
        rectangle.top += y;
        rectangle.bottom += y;
        rectangle2.top += y;
        rectangle2.bottom += y;
    }

    public Pakupakuan(int rectHeight, int color, int startX, int startY , int playerGap) {
        this.color = color;
        //left top right bot

        rectangle = new Rect(startX,startY,startX+rectHeight/8,startY + rectHeight);
        rectangle2 = new Rect(startX + playerGap + rectHeight/8, startY, startX + playerGap + rectHeight/4, startY + rectHeight);

        BitmapFactory bf = new BitmapFactory();
        Bitmap img_nails = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.nails);

        nails = new Animation(new Bitmap[]{img_nails},2);

        animationManager = new AnimationManager(new Animation[]{nails});
    }

    public boolean playerCollide(Player player) {
        /*
        //an ineffective way
        if( rectangle.contains(player.getRectangle().right, player.getRectangle().top)
         || rectangle.contains(player.getRectangle().left, player.getRectangle().top)
         || rectangle.contains(player.getRectangle().right, player.getRectangle().bottom)
         || rectangle.contains(player.getRectangle().left, player.getRectangle().bottom) )
            return true;
        return false;
        */
        return Rect.intersects(rectangle, player.getRectangle()) || Rect.intersects(rectangle2, player.getRectangle());

    }


    @Override
    public void draw(Canvas canvas) {
        /*
        Paint paint = new Paint();
        paint.setColor(color);
        canvas.drawRect(rectangle,paint);
        canvas.drawRect(rectangle2,paint);
        */
        animationManager.playAnim(0);
        animationManager.draw(canvas,rectangle);
        animationManager.draw(canvas,rectangle2);
    }

    @Override
    public void update() {
        animationManager.playAnim(0);
        animationManager.update();
    }
}
