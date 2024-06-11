package kr.ac.tukorea.spgp2024.minigametycoon.game.Object.RestaurantScene;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.text.TextPaint;
import android.util.Log;

import kr.ac.tukorea.spgp2024.R;
import kr.ac.tukorea.spgp2024.minigametycoon.framework.interfaces.IGameObject;
import kr.ac.tukorea.spgp2024.minigametycoon.game.OffLineData;

public class EarnOfflineReward implements IGameObject {
    Paint TextPaint = new TextPaint();
    Paint RectPaint = new Paint();
    RectF UISize = new RectF();
    long minute;

    public EarnOfflineReward(RectF Size){
        TextPaint.setTextAlign(Paint.Align.CENTER);
        TextPaint.setTextSize(75.0f);

        RectPaint.setColor(Color.rgb(255,255,255));
        RectPaint.setStyle(Paint.Style.FILL);

        UISize.set(Size);

        OffLineData.ReadData();
        long TimeDifference = System.currentTimeMillis() - OffLineData.LastOfflineTime;
        Log.d("ASDASD", "EarnOfflineReward: " + TimeDifference + " , "+OffLineData.LastOfflineTime);
        minute = (TimeDifference / (1000 * 60));

    }

    @Override
    public void update() {

    }

    @SuppressLint("DefaultLocale")
    @Override
    public void draw(Canvas canvas) {
        canvas.drawRect(UISize.left-UISize.right/2, UISize.top-UISize.bottom/2,UISize.left+UISize.right/2,UISize.top+UISize.bottom/2,RectPaint);

        drawString(canvas,String.format("마지막 레스토랑\n입장으로부터\n\n%d 분이\n지났습니다\n\n%d 골드를\n얻습니다.",minute,CalculateGold()),UISize.left,UISize.top - UISize.bottom/4, (android.text.TextPaint) TextPaint);

    }

    public void drawString(Canvas canvas, String text, float x, float y, TextPaint paint) {
        if (text.contains("\n")) {
            String[] texts = text.split("\n");
            for (String txt : texts) {
                canvas.drawText(txt, x, y, paint);
                y += paint.getTextSize();
            }
        } else {
            canvas.drawText(text, x, y, paint);
        }
    }

    public int CalculateGold(){
        return (int) (minute*50);
    }

}
