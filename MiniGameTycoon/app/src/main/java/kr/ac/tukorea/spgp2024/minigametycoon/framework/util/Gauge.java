package kr.ac.tukorea.spgp2024.minigametycoon.framework.util;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;

import androidx.core.content.res.ResourcesCompat;

import kr.ac.tukorea.spgp2024.minigametycoon.framework.view.GameView;

public class Gauge {
    private Paint fgPaint = new Paint();
    private Paint bgPaint = new Paint();


    private Paint CookFgPaint = new Paint();
    private Paint CookBgPaint = new Paint();
    public Gauge(float width, int fgColorResId, int bgColorResId) {
        bgPaint.setStyle(Paint.Style.STROKE);
        bgPaint.setStrokeWidth(width);
        // Gauge 생성 시점이 GameView.res 가 설정된 이후여야 한다.
        bgPaint.setColor(ResourcesCompat.getColor(GameView.res, bgColorResId, null));
        bgPaint.setStrokeCap(Paint.Cap.ROUND);
        fgPaint.setStyle(Paint.Style.STROKE);
        fgPaint.setStrokeWidth(width / 2);
        fgPaint.setColor(ResourcesCompat.getColor(GameView.res, fgColorResId, null));
        fgPaint.setStrokeCap(Paint.Cap.ROUND);

        CookFgPaint.setStyle(Paint.Style.FILL);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CookFgPaint.setColor(Color.rgb(1.0f,1.0f,0.0f));
        }
        CookBgPaint.setStyle(Paint.Style.FILL);
        CookBgPaint.setColor(Color.BLACK);
    }
    public void draw(Canvas canvas, float value) {
        canvas.drawLine(0, 0, 1.0f, 0.0f, bgPaint);
        if (value > 0) {
            canvas.drawLine(0, 0, value, 0.0f, fgPaint);
        }
    }

    public void DrawLine(Canvas canvas, float startX, float startY, float endX, float endY, float value){
        canvas.drawRect(startX,startY,endX,endY,CookBgPaint);
        if (value > 0) {
            canvas.drawRect(startX,startY,startX + (endX- startX) * value,endY, CookFgPaint);
        }
    }

}
