package kr.ac.tukorea.spgp2024.minigametycoon.framework;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;

import kr.ac.tukorea.spgp2024.minigametycoon.framework.objects.AnimSprite;

public class PersonAnimSprite extends AnimSprite {
    int SpriteStartX;
    int SpriteStartY;
    public PersonAnimSprite(int bitmapResId) {
        super(bitmapResId, 0,0,0,0,0,0);
    }

    @Override
    public void draw(Canvas canvas) {
        long now = System.currentTimeMillis();
        float time = (now - createdOn) / 1000.0f;
        int frameIndex = Math.round(time * fps) % frameCount;
        srcRect.set(SpriteStartX + frameIndex * frameWidth,
                SpriteStartY,
                SpriteStartX + (frameIndex + 1) * frameWidth,
                SpriteStartY + frameHeight);

        canvas.drawBitmap(bitmap, srcRect, dstRect, null);
    }

    public void SetResourceValue(int startX, int startY, int width, int height, float fps, int frameCount){
        this.SpriteStartX = startX;
        this.SpriteStartY = startY;
        this.frameWidth = width;
        this.height = height;


        this.fps = fps;
        this.frameCount = frameCount;

        this.width = width;
        this.height =height;
    }
    public void SetSpriteFPS(float fps){
        this.fps = fps;
    }

    public Rect GetSrcRect(){ return srcRect;}

}
