package kr.ac.tukorea.spgp2024.minigametycoon.game.Object.FieldGame;

import android.graphics.Canvas;
import android.graphics.RectF;
import android.util.Log;

import java.util.Random;

import kr.ac.tukorea.spgp2024.R;
import kr.ac.tukorea.spgp2024.minigametycoon.framework.objects.Sprite;

enum FoodTypeEnum{
    BLANK, FOOD_1,FOOD_2,FOOD_3,FOOD_4,FOOD_5, SIZE
}
public class FoodBlock {
    private static final String TAG = FoodBlock.class.getSimpleName();

    public boolean bIsMovingBlock = false;
    private float lastX;
    private float lastY;
    private float targetX;
    private float targetY;
    private float currentMoveFrameTime;
    private float targetMoveFrameTime;

    int[] FoodBitmap = {
      0,
      R.mipmap.temp_fieldgame_box1,
            R.mipmap.temp_fieldgame_box2,
            R.mipmap.temp_fieldgame_box3,
            R.mipmap.temp_fieldgame_box4,
            R.mipmap.temp_fieldgame_box5,
    };
    int[] FoodPickBitmap = {
            0,
            R.mipmap.temp_fieldgame_box1_pick,
            R.mipmap.temp_fieldgame_box2_pick,
            R.mipmap.temp_fieldgame_box3_pick,
            R.mipmap.temp_fieldgame_box4_pick,
            R.mipmap.temp_fieldgame_box5_pick
    };

    public FoodTypeEnum FoodType;
    private Sprite FoodSprite;
    FoodBlock(){
    }
    FoodBlock(RectF SpriteRect) {
      Initialize(SpriteRect);
    }

    void Initialize(RectF SpriteRect){
        //FoodSprite = new Sprite(R.mipmap)
        //FoodType = Type;

        FoodType = FoodTypeEnum.values()[(int) (Math.random()*5+1)];
        Log.d(TAG, "Initialize: "+SpriteRect.width() +"//"+SpriteRect.height());

        FoodSprite = new Sprite(FoodBitmap[FoodType.ordinal()],
                        SpriteRect.centerX(),
                        SpriteRect.centerY(),
                        SpriteRect.width(),
                        SpriteRect.height()
        );





    }

    public void Update(float frameTime){

    }
    public void Draw(Canvas canvas){
        FoodSprite.draw(canvas);
    }

    // 픽된 블럭 비트맵으로 이미지 변경 / 픽안된 비트맵으로 변경
    public void SetPickBitmap(boolean newSetPick){
        if(newSetPick){
            FoodSprite.setBitmapResource(FoodPickBitmap[FoodType.ordinal()]);
        }
        else{
            FoodSprite.setBitmapResource(FoodBitmap[FoodType.ordinal()]);
        }
    }

    public void SetSpriteDrawPosition(float newX, float newY){
        FoodSprite.moveTo(newX,newY);
    }
    public void SetSpriteDrawPositionWithTime(float newX, float newY, float time){
        bIsMovingBlock= true;
        targetX= newX;
        targetY = newY;
        targetMoveFrameTime = time;
    }
}
