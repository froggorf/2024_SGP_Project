package kr.ac.tukorea.spgp2024.minigametycoon.game.Scene;

import android.graphics.Point;
import android.graphics.RectF;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;

import com.google.android.material.color.utilities.Score;

import java.util.HashMap;
import java.util.Map;

import kr.ac.tukorea.spgp2024.R;
import kr.ac.tukorea.spgp2024.minigametycoon.framework.objects.Sprite;
import kr.ac.tukorea.spgp2024.minigametycoon.framework.res.Sound;
import kr.ac.tukorea.spgp2024.minigametycoon.framework.scene.BaseScene;
import kr.ac.tukorea.spgp2024.minigametycoon.framework.util.Gauge;
import kr.ac.tukorea.spgp2024.minigametycoon.game.CountDownClass;
import kr.ac.tukorea.spgp2024.minigametycoon.game.Object.FieldGame.FieldBoard;
import kr.ac.tukorea.spgp2024.minigametycoon.game.Object.FieldGame.FoodTypeEnum;
import kr.ac.tukorea.spgp2024.minigametycoon.game.TimerSystem;
import kr.ac.tukorea.spgp2024.minigametycoon.game.UserDisplay;
import kr.ac.tukorea.spgp2024.minigametycoon.game.enums.EDataName;


public class FieldGameScene extends BaseScene {
    private final String TAG = FieldGameScene.class.getSimpleName();
    private RectF boardRect;
    int[] resArray = new int[]{
            R.mipmap.temp_farmgame_beet,
            R.mipmap.temp_farmgame_carrot,
            R.mipmap.temp_farmgame_lettuce,
            R.mipmap.temp_farmgame_onion,
            R.mipmap.temp_farmgame_potato
    };
    Sprite infoSprite;
    Sprite[] boxSprite = new Sprite[5];
    long startTime;

    FieldBoard fieldGameBoard;

    TimerSystem timerSystem;

    boolean bFinishGame = false;


    public enum Layer{
        BACKGROUND, BOX, BOARD , TIMER_GAUGE,RESULT,INPUT, COUNT
    }
    public FieldGameScene() {
        startTime = System.currentTimeMillis();

        // 레이어 초기화
        initLayers(Layer.COUNT);

        // 배경 리소스 추가
        add(Layer.BACKGROUND, new Sprite(R.mipmap.temp_fieldgame_background,
                UserDisplay.getWidth(0.5f),
                UserDisplay.getHeight(0.5f),
                UserDisplay.getDesiredWidth(1.0f),
                UserDisplay.getDesiredHeight(1.0f)
        ));

        // 재료함 추가
        for( int i = 0;i<5; ++i)
        {
            boxSprite[i] = new Sprite(resArray[i],
                    UserDisplay.getWidth(0.2f * i + 0.1f),
                    UserDisplay.getHeight(0.9f),
                    UserDisplay.getWidth(0.2f),
                    UserDisplay.getHeight(0.1f)
            );

            add(Layer.BOX, boxSprite[i] );
        }

        // 게임 보드판 크기
        int boardCountX = 7, boardCountY = 9;
        float boardX = UserDisplay.getWidth(0.95f);
        float boardY = boardX/boardCountX*boardCountY;
        //UserDisplay.getWidth(0.05f),
        boardRect = new RectF(
                UserDisplay.getWidth(0.5f) - boardX/2,
                UserDisplay.getHeight(0.45f) -boardY/2,
                UserDisplay.getWidth(0.5f) + boardX/2,
                UserDisplay.getHeight(0.45f) +boardY/2
        );


        //게임 보드판 추가
        fieldGameBoard =new FieldBoard(new Point(boardCountX,boardCountY),boardRect);
        add(Layer.BOARD,fieldGameBoard);



        bFinishGame = true;



        CountDownClass CountDownObject = new CountDownClass(
                new RectF(UserDisplay.getWidth(0.4f), UserDisplay.getHeight(0.05f),
                        UserDisplay.getWidth(0.6f), UserDisplay.getHeight(0.05f) + UserDisplay.getWidth(0.2f)),
                3.0f
        );

        add(Layer.RESULT,CountDownObject);
        new Handler().postDelayed(new Runnable(){
            public void run(){
                remove(Layer.RESULT, CountDownObject);

                // 타이머 시스템 생성
                timerSystem = new TimerSystem(
                        new RectF(UserDisplay.getWidth(0.1f), UserDisplay.getHeight(0.05f),
                                UserDisplay.getWidth(0.9f), UserDisplay.getHeight(0.05f)),
                        10.0f,
                        75.0f
                );
                add(Layer.TIMER_GAUGE,timerSystem);

                bFinishGame = false;
            }
        }, 3500);


    }



    @Override
    public void update(long elapsedNanos) {
        super.update(elapsedNanos);
    }

    @Override
    protected void onStart() {
        // 500ms 뒤 노래 틀기
        new Handler().postDelayed(new Runnable(){
            public void run(){
                Sound.playMusic(R.raw.temp_fieldgameinfo_sound);

            }
        }, 500);

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
        return TownScene.Layer.TOUCH.ordinal();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(bFinishGame) return super.onTouchEvent(event);
        switch (event.getAction()){

            case MotionEvent.ACTION_DOWN:
                // 보드판 범위 안에 있을 때에만 건네주기
                if(boardRect.contains(event.getX(),event.getY())){
                    fieldGameBoard.onClickEvent(event);
                    return true;
                }
                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void FinishGame(){
        super.FinishGame();

        bFinishGame = true;

        fieldGameBoard.bTickEnabled = false;

        Map<EDataName, Integer> ScoreDataMap = new HashMap<>();
        ScoreDataMap.put(EDataName.EDN_First_Ingredients_Beet, fieldGameBoard.score[FoodTypeEnum.FOOD_1.ordinal()]);
        ScoreDataMap.put(EDataName.EDN_First_Ingredients_Carrot, fieldGameBoard.score[FoodTypeEnum.FOOD_2.ordinal()]);
        ScoreDataMap.put(EDataName.EDN_First_Ingredients_Lettuce, fieldGameBoard.score[FoodTypeEnum.FOOD_3.ordinal()]);
        ScoreDataMap.put(EDataName.EDN_First_Ingredients_Onion, fieldGameBoard.score[FoodTypeEnum.FOOD_4.ordinal()]);
        ScoreDataMap.put(EDataName.EDN_First_Ingredients_Potato, fieldGameBoard.score[FoodTypeEnum.FOOD_5.ordinal()]);

        // 게임 끝에 대한 이미지 추가
        add(Layer.RESULT, new Sprite(R.mipmap.temp_finishgame,
                UserDisplay.getWidth(0.5f),
                UserDisplay.getHeight(0.5f),
                UserDisplay.getDesiredWidth(0.75f),
                UserDisplay.getDesiredWidth(0.75f)
                ));

        new Handler().postDelayed(new Runnable(){
            public void run(){
                MiniGameResultScene scene = new MiniGameResultScene(ScoreDataMap);
                scene.changeScene();

            }
        }, 2000);
    }
}
