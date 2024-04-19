package kr.ac.tukorea.spgp2024.minigametycoon.game.Object.FieldGame;

import android.graphics.Canvas;
import android.graphics.RectF;

import java.util.Random;

import kr.ac.tukorea.spgp2024.R;
import kr.ac.tukorea.spgp2024.minigametycoon.framework.objects.Sprite;

enum FoodTypeEnum{
    BLANK, FOOD_1,FOOD_2,FOOD_3,FOOD_4,FOOD_5, SIZE
}
public class FoodBlock {
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
        FoodSprite = new Sprite(R.mipmap.ic_launcher,
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
