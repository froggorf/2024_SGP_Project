package kr.ac.tukorea.spgp2024.minigametycoon.game.Scene;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Vector;


import kr.ac.tukorea.spgp2024.R;
import kr.ac.tukorea.spgp2024.minigametycoon.framework.objects.Sprite;
import kr.ac.tukorea.spgp2024.minigametycoon.framework.res.Sound;
import kr.ac.tukorea.spgp2024.minigametycoon.framework.scene.BaseScene;
import kr.ac.tukorea.spgp2024.minigametycoon.game.CountDownClass;
import kr.ac.tukorea.spgp2024.minigametycoon.game.Object.FoodPrepHouseGame.CutLineObject;
import kr.ac.tukorea.spgp2024.minigametycoon.game.Object.FoodPrepHouseGame.CuttingObject;
import kr.ac.tukorea.spgp2024.minigametycoon.game.Object.FoodPrepHouseGame.SlicedObject;
import kr.ac.tukorea.spgp2024.minigametycoon.game.TimerSystem;
import kr.ac.tukorea.spgp2024.minigametycoon.game.UserDisplay;
import kr.ac.tukorea.spgp2024.minigametycoon.game.enums.EDataName;
import kr.ac.tukorea.spgp2024.minigametycoon.game.enums.EFarmAnimalType;



public class FoodPrepGameScene extends BaseScene {
    private final String TAG = FoodPrepGameScene.class.getSimpleName();

    static public int[] resArray = new int[]{
            R.mipmap.farmgame_beet,R.mipmap.farmgame_carrot,R.mipmap.farmgame_lettuce,R.mipmap.farmgame_onion,R.mipmap.farmgame_potato,
            R.mipmap.temp_farmgame_cow,R.mipmap.temp_farmgame_pig,R.mipmap.temp_farmgame_chicken
    };
    private float UiSize;
    private Paint textPaint;
    CountDownClass CountDownObject;
    TimerSystem timerSystem;

    boolean bFinishGame = true;

    Map<CuttingObject.IngredientType, Integer> ScoreMap = new HashMap<>();

    private CutLineObject cutLineObject;

    static public int MAX_OBJECT_COUNT = 10;
    public CuttingObject[] CuttingObjects = new CuttingObject[MAX_OBJECT_COUNT];

    public enum Layer{
        BACKGROUND, CUT_OBJECT,SLICED_OBJECT,CUTLINE, SCORE_UI,SCORE_TEXT,TIMER_GAUGE,RESULT,TOUCH, COUNT
    }


    public FoodPrepGameScene() {
        // 레이어 초기화
        initLayers(Layer.COUNT);

        for(int i =0; i<CuttingObject.IngredientType.SIZE.ordinal(); ++i){
            ScoreMap.put(CuttingObject.IngredientType.values()[i],0);
        }

        textPaint = new Paint();
        textPaint.setStyle(Paint.Style.STROKE);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setStrokeWidth(10.0f);
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(100.0f);

        // 배경 리소스 추가
        add(Layer.BACKGROUND, new Sprite(R.mipmap.temp_fieldgame_background,
                UserDisplay.getWidth(0.5f),
                UserDisplay.getHeight(0.5f),
                UserDisplay.getDesiredWidth(1.0f),
                UserDisplay.getDesiredHeight(1.0f)
        ));
        cutLineObject = new CutLineObject();


        for(int i = 0; i < MAX_OBJECT_COUNT; ++i){
            CuttingObjects[i] = CuttingObject.CreateRandomCuttingObject(i);
            add(Layer.CUT_OBJECT,CuttingObjects[i]);
        }

        UiSize = UserDisplay.getWidth(0.25f);
        for(int i = 0; i < CuttingObject.IngredientType.SIZE.ordinal(); ++i){
            add(Layer.SCORE_UI,new Sprite(resArray[i],
                    UiSize/2 + UiSize*(i%4),
                    UserDisplay.getHeight(0.95f) - UiSize* (1 - (int)((i)/4)),
                    UiSize,UiSize
            ));
        }


        add(Layer.CUTLINE,cutLineObject);

        CountDownObject = new CountDownClass(
                new RectF(UserDisplay.getWidth(0.4f), UserDisplay.getHeight(0.05f),
                        UserDisplay.getWidth(0.6f), UserDisplay.getHeight(0.05f) + UserDisplay.getWidth(0.2f)),
                3.0f
        );

        add(Layer.RESULT,CountDownObject);



    }

    @Override
    public void draw(Canvas canvas){
        super.draw(canvas);

        for(int i = 0; i<CuttingObject.IngredientType.SIZE.ordinal(); ++i){
            Integer Score = ScoreMap.get(CuttingObject.IngredientType.values()[i]);

            canvas.drawText(String.format("%d",Score),
                    UiSize/2 + UiSize*(i%4),
                    UserDisplay.getHeight(0.95f) - UiSize* (1 - (int)((i)/4)),
                    textPaint);



        }
    }

    @Override
    public void update(long elapsedNanos) {
        super.update(elapsedNanos);
    }

    @Override
    protected void onStart() {
        // 500ms 뒤 노래 틀기
//        new Handler().postDelayed(new Runnable(){
//            public void run(){
//                Sound.playMusic(R.raw.temp_fieldgameinfo_sound);
//
//            }
//        }, 500);

    }

    @Override
    protected void onEnd() {
        Sound.stopMusic();
    }

    @Override
    protected void onPause() {
        Sound.pauseMusic();
    }

    @Override
    protected void onResume() {
        Sound.resumeMusic();
    }

    public boolean handleBackKey(){
        return true;
    }

    @Override
    protected int getTouchLayerIndex() {
        return Layer.CUTLINE.ordinal();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(bFinishGame) return super.onTouchEvent(event);

        float x = event.getX();
        float y = event.getY();
        switch(event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                cutLineObject.bIsDrag = true;
                cutLineObject.setStartPos(x,y);
                cutLineObject.setEndPos(x,y);
                break;

            case MotionEvent.ACTION_MOVE:
                if(cutLineObject.bIsDrag)
                    cutLineObject.setEndPos(x,y);
                break;
            case MotionEvent.ACTION_UP:
                SliceCuttingObject();
                cutLineObject.setStartPos(-1,-1);
                cutLineObject.setEndPos(-1,-1);
                break;
        }


        //return super.onTouchEvent(event);
        return true;
    }

    private void SliceCuttingObject() {
        for(int i = 0; i<MAX_OBJECT_COUNT; ++i){
            if(cutLineObject.SliceObject(CuttingObjects[i])){
                CuttingObject.IngredientType Type = CuttingObjects[i].ObjectIngredientType;
                Integer CurrentScore = ScoreMap.get(Type);
                CurrentScore += 10;
                ScoreMap.replace(Type,CurrentScore);

                CuttingObjects[i].bIsCut= true;
                add(Layer.SLICED_OBJECT, new SlicedObject(CuttingObjects[i]));
            }
        }
    }

    public void ResetCuttingObject(int Index){
        CuttingObjects[Index].Initialize();
    }
    @Override
    public void StartGame(){
        remove(Layer.RESULT, CountDownObject);
        AddTimerSystem();

        for(int i =0; i<MAX_OBJECT_COUNT; ++i){
            CuttingObjects[i].bTickEnabled = true;
        }

        bFinishGame = false;
    }
    @Override
    public void AddTimerSystem(){
        // 타이머 시스템 생성
        timerSystem = new TimerSystem(
                new RectF(UserDisplay.getWidth(0.1f), UserDisplay.getHeight(0.05f),
                        UserDisplay.getWidth(0.9f), UserDisplay.getHeight(0.05f)),
                10.0f,
                75.0f
        );
        add(Layer.TIMER_GAUGE,timerSystem);
    }
    @Override
    public void FinishGame(){
        super.FinishGame();
        bFinishGame = true;

        // 게임 끝에 대한 이미지 추가
        add(Layer.RESULT, new Sprite(R.mipmap.temp_finishgame,
                UserDisplay.getWidth(0.5f),
                UserDisplay.getHeight(0.5f),
                UserDisplay.getDesiredWidth(0.75f),
                UserDisplay.getDesiredWidth(0.75f)
                ));


        Map<EDataName, Integer> ScoreDataMap = new HashMap<>();
        for(int i =0; i<CuttingObject.IngredientType.SIZE.ordinal(); ++i){
            CuttingObject.IngredientType Type = CuttingObject.IngredientType.values()[i];
            Integer Score = ScoreMap.get(Type);

            ScoreDataMap.put(EDataName.values()[EDataName.EDN_First_Ingredients_Beet.ordinal() + i], -Score/10);
            ScoreDataMap.put(EDataName.values()[EDataName.EDN_Sliced_Ingredient_Beet.ordinal() + i], Score);
        }

        new Handler().postDelayed(new Runnable(){
            public void run(){
                MiniGameResultScene scene = new MiniGameResultScene(ScoreDataMap);
                scene.changeScene();
            }
        }, 2000);
    }
}
