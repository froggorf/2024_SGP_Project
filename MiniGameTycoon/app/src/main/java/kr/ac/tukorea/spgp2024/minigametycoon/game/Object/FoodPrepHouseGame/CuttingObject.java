package kr.ac.tukorea.spgp2024.minigametycoon.game.Object.FoodPrepHouseGame;

import android.graphics.Canvas;
import android.view.MotionEvent;

import java.util.Random;

import kr.ac.tukorea.spgp2024.R;
import kr.ac.tukorea.spgp2024.minigametycoon.framework.interfaces.IGameObject;
import kr.ac.tukorea.spgp2024.minigametycoon.framework.interfaces.ITouchable;
import kr.ac.tukorea.spgp2024.minigametycoon.framework.objects.Sprite;
import kr.ac.tukorea.spgp2024.minigametycoon.game.Scene.FoodPrepGameScene;
import kr.ac.tukorea.spgp2024.minigametycoon.game.UserDisplay;


public class CuttingObject implements IGameObject {
    // DEFINE
    static public int X = 0;
    static public int Y = 1;
    static public float ObjectSize;
    static private float GravityScale = 9.8f;
    static private float[] StartVelocityPower = new float[]{
            UserDisplay.getWidth(0.5f), UserDisplay.getHeight(0.5f)
    };

    public enum IngredientType{
        Food_A,Food_B,Food_C,Food_D,Food_E,
        Beef, Pork, Chicken,
        SIZE
    }
    IngredientType ObjectIngredientType;
    Sprite ObjectSprite;
    float[] Velocity = new float[2];
    static Random RandomObject = new Random();
    public CuttingObject(){

    }

    static public CuttingObject CreateRandomCuttingObject(){
        CuttingObject Object = new CuttingObject();

        Object.Initialize();;

        return Object;
    }

    public void Initialize(){
        int TypeIndex = (int) (Math.random()*IngredientType.SIZE.ordinal());
        ObjectIngredientType = IngredientType.values()[TypeIndex];
        ObjectSize = UserDisplay.getWidth(0.3f);
        if(ObjectSprite == null)
        {
            ObjectSprite = new Sprite(FoodPrepGameScene.resArray[ObjectIngredientType.ordinal()],
                    0,0, ObjectSize,ObjectSize);
        }
        else
        {
            ObjectSprite.setBitmapResource(FoodPrepGameScene.resArray[ObjectIngredientType.ordinal()]);
        }

        SetStartPositionAndVelocity();
    }

    public void SetStartPositionAndVelocity(){
        // 시작위치 구하기
        float newX, newY;
        if(RandomObject.nextBoolean())
        {
            newX = 0 - ObjectSize;
        }
        else
        {
            newX = UserDisplay.getWidth(1.0f) + ObjectSize;
            Velocity[X] *= -1;
        }
        newY = UserDisplay.getHeight(0.3f) + RandomObject.nextFloat() * (UserDisplay.getHeight(0.4f));
        ObjectSprite.moveTo(newX, newY);

        // 초기 속력 설정
        Velocity[X] = StartVelocityPower[X];
        Velocity[Y] = newX * StartVelocityPower[Y];
    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Canvas canvas) {
        ObjectSprite.draw(canvas);
    }


}
