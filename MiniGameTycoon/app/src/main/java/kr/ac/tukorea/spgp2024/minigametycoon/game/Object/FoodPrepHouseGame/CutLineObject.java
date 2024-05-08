package kr.ac.tukorea.spgp2024.minigametycoon.game.Object.FoodPrepHouseGame;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.PathDashPathEffect;
import android.graphics.PathEffect;
import android.graphics.Typeface;
import android.graphics.drawable.shapes.Shape;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import kr.ac.tukorea.spgp2024.minigametycoon.framework.interfaces.IGameObject;
import kr.ac.tukorea.spgp2024.minigametycoon.framework.interfaces.ITouchable;
import kr.ac.tukorea.spgp2024.minigametycoon.framework.view.GameView;

// 재료 준비소에서 1차 재료를 자를 때 사용할 선 오브젝트
public class CutLineObject implements IGameObject, ITouchable {
    private static final String TAG = CutLineObject.class.getSimpleName();
    private float startX;
    private float startY;

    private float endX;
    private float endY;
    private Paint paint;
    static DashPathEffect dashPath = new DashPathEffect(new float[]{10,10}, 5);
    public CutLineObject(){
        paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(5.0f);
        paint.setPathEffect(dashPath);

        startX = startY = endX = endY = -1;
    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawLine(startX,startY,endX,endY, paint);
    }

    public void setStartPos(float x, float y)
    {
        startX = x;
        startY = y;
    }

    public void setEndPos(float x, float y)
    {
        endX = x;
        endY = y;
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        //Log.d(TAG, "onTouchEvent: "+e.getAction());
        return false;
    }
}
