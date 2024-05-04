package kr.ac.tukorea.spgp2024.minigametycoon.game;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.transition.Scene;
import android.util.Log;

import androidx.core.content.res.ResourcesCompat;

import java.time.temporal.ValueRange;

import kr.ac.tukorea.spgp2024.R;
import kr.ac.tukorea.spgp2024.minigametycoon.framework.interfaces.IGameObject;
import kr.ac.tukorea.spgp2024.minigametycoon.framework.scene.BaseScene;
import kr.ac.tukorea.spgp2024.minigametycoon.framework.view.GameView;

public class TimerSystem implements IGameObject {
    private static final String TAG = TimerSystem.class.getSimpleName();
    RectF DrawRect;
    int fgColorResId;
    int bgColorResId;
    private Paint fgPaint = new Paint();
    private Paint bgPaint = new Paint();
    float width = 20.0f;

    float currentTime = 0.0f;
    float maxPlayTime = 60.0f;
    private boolean bTickEnabled;

    public TimerSystem(RectF rectF, float MaxTime, float LineWidth){
        bTickEnabled = true;
        DrawRect = rectF;
        fgColorResId = R.color.TimerGaugeFg;
        bgColorResId = R.color.TimerGaugeBg;
        currentTime = 0.0f;
        maxPlayTime = MaxTime;
        width = LineWidth;

        bgPaint.setStyle(Paint.Style.STROKE);
        bgPaint.setStrokeWidth(width);
        bgPaint.setColor(ResourcesCompat.getColor(GameView.res, bgColorResId, null));
        bgPaint.setStrokeCap(Paint.Cap.ROUND);
        fgPaint.setStyle(Paint.Style.STROKE);
        fgPaint.setStrokeWidth(width / 2);
        fgPaint.setColor(ResourcesCompat.getColor(GameView.res, fgColorResId, null));
        fgPaint.setStrokeCap(Paint.Cap.ROUND);
    }
    @Override
    public void update() {
        //currentTime = Math.min(currentTime + BaseScene.frameTime, maxPlayTime);
        if(bTickEnabled){
            currentTime += BaseScene.frameTime;
            if(currentTime >= maxPlayTime){
                currentTime = maxPlayTime;
                bTickEnabled = false;
                BaseScene.getTopScene().FinishGame();
            }
        }

    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawLine(DrawRect.left,DrawRect.centerY(),DrawRect.right,DrawRect.centerY(),bgPaint);
        float value = currentTime/maxPlayTime;
        canvas.drawLine(DrawRect.left, DrawRect.centerY(),DrawRect.left + DrawRect.width()*value, DrawRect.centerY(),fgPaint);
    }
}
