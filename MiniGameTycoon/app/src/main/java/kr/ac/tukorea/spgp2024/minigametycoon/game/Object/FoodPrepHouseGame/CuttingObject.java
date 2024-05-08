package kr.ac.tukorea.spgp2024.minigametycoon.game.Object.FoodPrepHouseGame;

import android.graphics.Canvas;
import android.util.Log;
import android.view.MotionEvent;

import java.util.Random;
import java.util.Vector;

import kr.ac.tukorea.spgp2024.R;
import kr.ac.tukorea.spgp2024.minigametycoon.framework.interfaces.IGameObject;
import kr.ac.tukorea.spgp2024.minigametycoon.framework.interfaces.ITouchable;
import kr.ac.tukorea.spgp2024.minigametycoon.framework.objects.Sprite;
import kr.ac.tukorea.spgp2024.minigametycoon.framework.scene.BaseScene;
import kr.ac.tukorea.spgp2024.minigametycoon.game.Scene.FoodPrepGameScene;
import kr.ac.tukorea.spgp2024.minigametycoon.game.UserDisplay;


public class CuttingObject implements IGameObject {
    private static final String TAG = CuttingObject.class.getSimpleName();
    // DEFINE
    static public int X = 0;
    static public int Y = 1;
    static public float ObjectSize;
    static private float GravityScale = 9.8f * UserDisplay.getHeight(0.05f);
    static private float[] StartVelocityPower = new float[]{
            UserDisplay.getWidth(0.5f), -UserDisplay.getHeight(0.5f)
    };
    static public float KillY = UserDisplay.getHeight(2.0f);
    public enum IngredientType{
        Beet,Carrot,Lettuce,Onion,Potato,
        Beef, Pork, Chicken,
        SIZE
    }
    public IngredientType ObjectIngredientType;
    public Sprite ObjectSprite;
    float[] Velocity = new float[2];
    static Random RandomObject = new Random();
    float ObjectWeight;
    int ObjectIndex;
    public boolean bIsCut;

    public CuttingObject(){
    }

    static public CuttingObject CreateRandomCuttingObject(int Index){
        CuttingObject Object = new CuttingObject();
        Object.Initialize();
        // 초기 한번만 본인의 번호 적기
        Object.ObjectIndex = Index;

        return Object;
    }

    public void Initialize(){
        bIsCut = false;
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
        ObjectWeight = (float) (0.7 + Math.random()* 0.6);
        SetStartPositionAndVelocity();

    }

    public void SetStartPositionAndVelocity(){
        // 시작위치 구하기
        float newX, newY;
        Velocity[X] = StartVelocityPower[X] * ObjectWeight;
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

        Velocity[Y] = StartVelocityPower[Y];
    }

    @Override
    public void update() {
        //Velocity[Y] -= GravityScale * BaseScene.frameTime*2;
        Velocity[Y] += GravityScale*BaseScene.frameTime * ObjectWeight;
        float[] CurPos = ObjectSprite.GetCenterPosition();
        CurPos[X] += BaseScene.frameTime * Velocity[X];
        CurPos[Y] += BaseScene.frameTime * Velocity[Y];
        if(CurPos[Y] >= KillY) Initialize();
        else ObjectSprite.moveTo(CurPos[X],CurPos[Y]);

    }

    @Override
    public void draw(Canvas canvas) {
        if(!bIsCut)
            ObjectSprite.draw(canvas);

    }


}
