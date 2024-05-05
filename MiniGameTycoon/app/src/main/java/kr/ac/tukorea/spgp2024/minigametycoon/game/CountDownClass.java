package kr.ac.tukorea.spgp2024.minigametycoon.game;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;

import com.google.android.material.transition.MaterialSharedAxis;

import kr.ac.tukorea.spgp2024.minigametycoon.framework.interfaces.IGameObject;
import kr.ac.tukorea.spgp2024.minigametycoon.framework.scene.BaseScene;
import kr.ac.tukorea.spgp2024.minigametycoon.game.Scene.FieldGameScene;

public class CountDownClass implements IGameObject {
    float CurrentTime = 0.0f;
    RectF DrawRect;

    Paint FillPaint = new Paint();
    Paint TextPaint = new Paint();

    public CountDownClass(RectF Rect, float CountDownTime){
        CurrentTime = CountDownTime;
        DrawRect = Rect;

        FillPaint.setStyle(Paint.Style.FILL);
        FillPaint.setColor(Color.rgb(255,255,255));

        TextPaint.setTextAlign(Paint.Align.CENTER);
        TextPaint.setTextSize(100.0f);
        TextPaint.setColor(Color.rgb(0,0,0));
    }

    @Override
    public void update() {
        CurrentTime = Math.max(0, CurrentTime - BaseScene.frameTime);
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawRect(DrawRect,FillPaint);

        canvas.drawText(String.valueOf((int)CurrentTime),DrawRect.centerX(),DrawRect.centerY(),TextPaint);
    }
}
