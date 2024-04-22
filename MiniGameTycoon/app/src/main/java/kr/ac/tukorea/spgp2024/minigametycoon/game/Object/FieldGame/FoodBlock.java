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

    int[] FoodBitmap = {
      R.mipmap.temp_fieldgameinfo_infoimage,
      R.mipmap.temp_fieldgame_box1,
            R.mipmap.temp_fieldgame_box2,
            R.mipmap.temp_fieldgame_box3,
            R.mipmap.temp_fieldgame_box4,
            R.mipmap.temp_fieldgame_box5,
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

    public void Draw(Canvas canvas){
        FoodSprite.draw(canvas);
    }

}
