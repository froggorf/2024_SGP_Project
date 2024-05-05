package kr.ac.tukorea.spgp2024.minigametycoon.game;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.util.Log;

import com.google.android.material.transition.MaterialSharedAxis;

import kr.ac.tukorea.spgp2024.minigametycoon.framework.interfaces.IGameObject;
import kr.ac.tukorea.spgp2024.minigametycoon.framework.scene.BaseScene;
import kr.ac.tukorea.spgp2024.minigametycoon.game.Scene.FieldGameScene;

public class CountDownClass implements IGameObject {
    private static final String TAG = CountDownClass.class.getSimpleName();
    float CountdownTime = 0.0f;
    long StartTime;
    RectF DrawRect;

    Paint FillPaint = new Paint();
    Paint TextPaint = new Paint();

    public CountDownClass(RectF Rect, float CountDownTime){
        StartTime = System.currentTimeMillis();;
        CountdownTime = CountDownTime;
        DrawRect = Rect;

        FillPaint.setStyle(Paint.Style.FILL);
        FillPaint.setColor(Color.rgb(255,255,255));

        TextPaint.setTextAlign(Paint.Align.CENTER);
        TextPaint.setTextSize(100.0f);
        TextPaint.setColor(Color.rgb(0,0,0));
    }

    @Override
    public void update() {    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawRect(DrawRect,FillPaint);

        long now = System.currentTimeMillis();
        float time = (now - StartTime) / 1000.0f;

        int DrawTime = (int)(CountdownTime - time);
        Log.d(TAG, "draw: "+ now+" "+time+" "+DrawTime);
        if(DrawTime>=0)
            canvas.drawText (String.valueOf(DrawTime),DrawRect.centerX(),DrawRect.centerY(),TextPaint);
    }
}
