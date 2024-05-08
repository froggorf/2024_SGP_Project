package kr.ac.tukorea.spgp2024.minigametycoon.game.Object.FieldGame;

import static android.util.Half.EPSILON;

import android.graphics.Canvas;
import android.graphics.RectF;
import android.util.Log;


import java.util.Random;

import kr.ac.tukorea.spgp2024.R;
import kr.ac.tukorea.spgp2024.minigametycoon.framework.objects.Sprite;


public class FoodBlock {
    // DEFINE
    private int X = 0;
    private int Y = 1;
    private static final String TAG = FoodBlock.class.getSimpleName();

    public boolean bIsMovingBlock = false;      // 움직이고 있는 중인지에 대한 변수
    private float[] lastCenterPos = new float[2];
    private float[] targetCenterPos = new float[2];
    private float currentMoveFrameTime;
    private float targetMoveFrameTime;

    int[] FoodBitmap = {
            R.mipmap.fieldgame_board_blank,
            R.mipmap.temp_farmgame_beet,
            R.mipmap.temp_farmgame_carrot,
            R.mipmap.temp_farmgame_lettuce,
            R.mipmap.temp_farmgame_onion,
            R.mipmap.temp_farmgame_potato,
    };
    int[] FoodPickBitmap = {
            0,
            R.mipmap.temp_sliced_ingredient_beet,
            R.mipmap.temp_sliced_ingredient_carrot,
            R.mipmap.temp_sliced_ingredient_lettuce,
            R.mipmap.temp_sliced_ingredient_onion,
            R.mipmap.temp_sliced_ingredient_potato
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

        // 초기 생성이라면
        if(FoodSprite == null){
            FoodSprite = new Sprite(FoodBitmap[FoodType.ordinal()],
                    SpriteRect.centerX(),
                    SpriteRect.centerY(),
                    SpriteRect.width(),
                    SpriteRect.height()
            );
        }
        else
        {
            FoodSprite.setBitmapResource(FoodBitmap[FoodType.ordinal()]);
            FoodSprite.moveTo(SpriteRect.centerX(),SpriteRect.centerY());
        }






    }

    public boolean Update(float frameTime){
        // 움직임 중일 때에만 update 처리
        if(bIsMovingBlock){
            currentMoveFrameTime = Math.min(currentMoveFrameTime+frameTime, targetMoveFrameTime);

            if(Math.abs(targetMoveFrameTime - currentMoveFrameTime) < 0.0001){
                bIsMovingBlock = false;
                FoodSprite.moveTo(targetCenterPos[X],targetCenterPos[Y]);
                lastCenterPos[X] = -1; lastCenterPos[Y] = -1;
                targetCenterPos[X] =-1; targetCenterPos[Y] =-1;
                currentMoveFrameTime = targetMoveFrameTime = -1;
                return false;
            }

            float[] newPos = Lerp(lastCenterPos,targetCenterPos,currentMoveFrameTime/targetMoveFrameTime);
            FoodSprite.moveTo(newPos[X], newPos[Y]);
        }

        return bIsMovingBlock;
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
        lastCenterPos = FoodSprite.GetCenterPosition();
        targetCenterPos[X] = newX;
        targetCenterPos[Y] = newY;
        currentMoveFrameTime = 0.0f;
        targetMoveFrameTime = time;
    }


    // 좌표 보간 계산 을 위해 사용되는 lerp 함수
    float[] Lerp(float[] A, float B[], float Alpha)
    {
        float[] returnValue = new float[2];
        returnValue[0] = A[0]*(1-Alpha) + B[0]*Alpha;
        returnValue[1] = A[1]*(1-Alpha) + B[1]*Alpha;
        return returnValue;
    }

    void ChangeFoodType(FoodTypeEnum newFoodTypeEnum){
        FoodType = newFoodTypeEnum;
        FoodSprite.setBitmapResource(FoodBitmap[FoodType.ordinal()]);
    }
}
