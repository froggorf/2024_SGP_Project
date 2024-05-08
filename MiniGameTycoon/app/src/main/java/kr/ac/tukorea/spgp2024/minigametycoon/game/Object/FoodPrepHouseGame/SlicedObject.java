package kr.ac.tukorea.spgp2024.minigametycoon.game.Object.FoodPrepHouseGame;



import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.health.connect.datatypes.units.Velocity;

import kr.ac.tukorea.spgp2024.R;
import kr.ac.tukorea.spgp2024.minigametycoon.framework.interfaces.IGameObject;
import kr.ac.tukorea.spgp2024.minigametycoon.framework.objects.Sprite;
import kr.ac.tukorea.spgp2024.minigametycoon.framework.res.BitmapPool;
import kr.ac.tukorea.spgp2024.minigametycoon.framework.scene.BaseScene;
import kr.ac.tukorea.spgp2024.minigametycoon.game.Scene.FarmGameScene;
import kr.ac.tukorea.spgp2024.minigametycoon.game.Scene.FoodPrepGameScene;
import kr.ac.tukorea.spgp2024.minigametycoon.game.UserDisplay;

public class SlicedObject implements IGameObject {
    static private final int FIRST = 0;
    static private final int SECOND = 1;
    static private final int X = 0;
    static private final int Y = 1;
    int[] ResArray = new int[]{
            R.mipmap.temp_fieldgame_box1_pick,
            R.mipmap.temp_fieldgame_box2_pick,
            R.mipmap.temp_fieldgame_box3_pick,
            R.mipmap.temp_fieldgame_box4_pick,
            R.mipmap.temp_fieldgame_box5_pick,
            R.mipmap.temp_first_ingredients_beef,
            R.mipmap.temp_first_ingredients_pork    ,
            R.mipmap.temp_first_ingredients_chicken,
    };

    RectF[] SlicedObjectRect = new RectF[2];
    Bitmap SliceBitmap;
    static Paint paint = new Paint();

    float[] ObjectWeight = new float[2];
    float[][] Velocity = new float[2][2];

    static private float GravityScale = 9.8f * UserDisplay.getHeight(0.05f);
    static private float[] StartVelocityPower = new float[]{
            UserDisplay.getWidth(0.5f), -UserDisplay.getHeight(0.1f)
    };

    boolean[] bIsKilled = new boolean[2];
    float[] RotationSpeed = new float[2];
    float[] RotationDegree = new float[]{0.0f,0.0f};

    public SlicedObject(CuttingObject Object)
    {
        // 위치 정보 얻기
        RectF ObjectRect = Object.ObjectSprite.GetDstRect();
        SlicedObjectRect[FIRST] = new RectF(ObjectRect.left,ObjectRect.top,ObjectRect.centerX(),ObjectRect.bottom);
        SlicedObjectRect[SECOND] = new RectF(ObjectRect.centerX(),ObjectRect.top,ObjectRect.right,ObjectRect.bottom);

        // 비트맵 로드
        SliceBitmap = BitmapPool.get(ResArray[Object.ObjectIngredientType.ordinal()]);


        for(int i=0; i<2; ++i){
            RotationSpeed[i] = (float) (50 + Math.random()*150);

            ObjectWeight[i] = (float) (0.5 + Math.random()*3.0);
            Velocity[i][X] = (float) (StartVelocityPower[X] * (0.3 + Math.random()*1.2));
            Velocity[i][Y] = StartVelocityPower[Y];
        }
        Velocity[FIRST][X] *= -1;
        RotationSpeed[FIRST] *= -1;




        bIsKilled[FIRST] = false;
        bIsKilled[SECOND] = false;
    }

    @Override
    public void update() {

        for(int i=0; i<2; ++i){
            if(bIsKilled[i]) continue;
            RotationDegree[i] += BaseScene.frameTime * RotationSpeed[i];

            Velocity[i][Y] += GravityScale* BaseScene.frameTime* ObjectWeight[i];

            SlicedObjectRect[i].offset(
                    BaseScene.frameTime * Velocity[i][X],
                    BaseScene.frameTime * Velocity[i][Y]);
            if(SlicedObjectRect[i].centerY() >= CuttingObject.KillY)
                bIsKilled[i] = true;
        }

        if(bIsKilled[FIRST] && bIsKilled[SECOND]) BaseScene.getTopScene().remove(FoodPrepGameScene.Layer.SLICED_OBJECT,this);
    }

    @Override
    public void draw(Canvas canvas) {
        for(int i =0; i<2; ++i){
            if(!bIsKilled[i]){
                canvas.save();
                canvas.rotate(RotationDegree[i],SlicedObjectRect[i].centerX(),SlicedObjectRect[i].centerY());
                Rect SourceRect = new Rect(0, 0,SliceBitmap.getWidth()/2,SliceBitmap.getHeight());
                SourceRect.offset(SliceBitmap.getWidth()/2 * i,0);

                canvas.drawBitmap(SliceBitmap,
                        SourceRect,
                        SlicedObjectRect[i],
                        paint);
                canvas.restore();
            }
        }
    }
}
