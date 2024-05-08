package kr.ac.tukorea.spgp2024.minigametycoon.game.Object.FoodPrepHouseGame;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.PathDashPathEffect;
import android.graphics.PathEffect;
import android.graphics.Rect;
import android.graphics.RectF;
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
    public boolean bIsDrag = false;
    public CutLineObject(){
        paint = new Paint();
        paint.setColor(Color.GREEN);
        paint.setStrokeWidth(10.0f);
        paint.setPathEffect(dashPath);

        startX = startY = endX = endY = -1;

        bIsDrag = false;
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
        bIsDrag = true;
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

    public RectF GetLinePositions(){
        return  new RectF(startX,startY,endX,endY);
    }

    public boolean SliceObject(CuttingObject cuttingObject) {
        if(cuttingObject.bIsCut) return false;
        RectF LineData = GetLinePositions();
        RectF RectData = cuttingObject.ObjectSprite.GetDstRect();
        return LineIntersectRect(LineData,RectData);
    }

    // https://gist.github.com/w8r/eab8882561167c06ea5c7460485b1d17
    public boolean LineIntersectRect(RectF LineData, RectF RectData){
        float t = 0;

        //  If the start or end of the line is inside the rect then we assume
        //  collision, as rects are solid for our use-case.
        if ((LineData.left >= RectData.left && LineData.left <= RectData.right && LineData.top >= RectData.top && LineData.top <= RectData.bottom) ||
                (LineData.right >= RectData.left && LineData.right <= RectData.right && LineData.bottom >= RectData.top && LineData.bottom <= RectData.bottom)) {
            return true;
        }

        if (LineData.left < RectData.left && LineData.right >= RectData.left) { //  Left edge
            t = LineData.top + (LineData.bottom - LineData.top) * (RectData.left - LineData.left) / (LineData.right - LineData.left);
            if (t > RectData.top && t <= RectData.bottom) {
                return true;
            }
        }
        else if (LineData.left > RectData.right && LineData.right <= RectData.right) { //  Right edge
            t = LineData.top + (LineData.bottom - LineData.top) * (RectData.right - LineData.left) / (LineData.right - LineData.left);
            if (t >= RectData.top && t <= RectData.bottom) {
                return true;
            }
        }      
        if (LineData.top < RectData.top && LineData.bottom >= RectData.top) { //  Top edge
            t = LineData.left + (LineData.right - LineData.left) * (RectData.top - LineData.top) / (LineData.bottom - LineData.top);
            if (t >= RectData.left && t <= RectData.right) {
                return true;
            }
        } else if (LineData.top > RectData.bottom && LineData.bottom <= RectData.bottom) {  //  Bottom edge
            t = LineData.left + (LineData.right - LineData.left) * (RectData.bottom - LineData.top) / (LineData.bottom - LineData.top);
            if (t >= RectData.left && t <= RectData.right) {
                return true;
            }
        }
        return false;
    }
  
}
