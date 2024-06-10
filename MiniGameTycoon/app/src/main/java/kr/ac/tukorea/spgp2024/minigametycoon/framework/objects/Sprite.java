package kr.ac.tukorea.spgp2024.minigametycoon.framework.objects;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.util.Log;

import kr.ac.tukorea.spgp2024.minigametycoon.framework.interfaces.IGameObject;
import kr.ac.tukorea.spgp2024.minigametycoon.framework.res.BitmapPool;

public class Sprite implements IGameObject {
    private static final String TAG = Sprite.class.getSimpleName();
    protected Bitmap bitmap;
    protected RectF dstRect = new RectF();
    protected float x, y, width, height;
    public Sprite() {} // 상속받은 class 에서 자유롭게 생성자를 만들 수 있도록 default 생성자를 추가한다

    // 2019180031 현재의 비트맵 리소스 아이디에 대해서 저장해둔다
    public int BitmapResId;

    public Sprite(int bitmapResId, float cx, float cy, float width, float height) {
        this.x = cx;
        this.y = cy;
        this.width = width;
        this.height = height;

        BitmapResId = bitmapResId;  // 2019180031

        if (bitmapResId != 0) {
            setBitmapResource(bitmapResId);
        }
        fixDstRect();

        Log.v(TAG, "Created " + this.getClass().getSimpleName() + "@" + System.identityHashCode(this));
    }

    public float getWidth() { return width; }
    public float getHeight() { return height; }
    public float getDstWidth() {
        return dstRect.width();
    }

    public float getDstHeight() {
        return dstRect.height();
    }

    //2019180031 setBitmapResource 함수를 public으로 변경 및
    // 2019180031 bitmapResid를 저장하도록 추가
    public void setBitmapResource(int bitmapResId) {
        BitmapResId = bitmapResId;
        bitmap = BitmapPool.get(bitmapResId);
    }
    //2019180031 현재 비트맵 리소스 아이디를 반환하는 함수 추가
    public int getCurrentBitmapResId(){
        return BitmapResId;
    }


    protected void fixDstRect() {
        setSize(width, height);
    }

    public void setSize(float width, float height) {
        float half_width = width / 2;
        float half_height = height / 2;
        dstRect.set(x - half_width, y - half_height, x + half_width, y + half_height);
    }

    @Override
    public void update() {
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap, null, dstRect, null);
    }

    public void moveTo(float x, float y) {
        this.x = x;
        this.y = y;
        fixDstRect();
    }

    public float[] GetCenterPosition(){
        float[] returnValue = new float[2];
        returnValue[0] = x;
        returnValue[1] = y;
        return returnValue;
    }

    public RectF GetDstRect(){
        return dstRect;
    }

    public void MoveOffset(float x, float y){
        this.x += x;
        this.y += y;
        fixDstRect();
    }
    public float[] GetLocation(){
        return new float[]{this.x,this.y };
    }
    public void Resize(float width, float height){
        this.width = width;
        this.height = height;
        fixDstRect();
    }
}
