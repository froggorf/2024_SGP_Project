package kr.ac.tukorea.spgp2024.minigametycoon.game.Scene;

import android.graphics.RectF;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;

import java.util.ArrayList;

import kr.ac.tukorea.spgp2024.R;
import kr.ac.tukorea.spgp2024.minigametycoon.framework.objects.Sprite;
import kr.ac.tukorea.spgp2024.minigametycoon.framework.res.Sound;
import kr.ac.tukorea.spgp2024.minigametycoon.framework.scene.BaseScene;
import kr.ac.tukorea.spgp2024.minigametycoon.game.CountDownClass;
import kr.ac.tukorea.spgp2024.minigametycoon.game.Object.FoodPrepHouseGame.CutLineObject;
import kr.ac.tukorea.spgp2024.minigametycoon.game.TimerSystem;
import kr.ac.tukorea.spgp2024.minigametycoon.game.UserDisplay;


public class FoodPrepGameScene extends BaseScene {
    private final String TAG = FoodPrepGameScene.class.getSimpleName();
    static public int[] resArray = new int[]{
            R.mipmap.temp_fieldgame_box1,R.mipmap.temp_fieldgame_box2,R.mipmap.temp_fieldgame_box3,R.mipmap.temp_fieldgame_box4,R.mipmap.temp_fieldgame_box5,
            R.mipmap.temp_farmgame_cow,R.mipmap.temp_first_ingredients_pork,R.mipmap.temp_first_ingredients_chicken
    };

    TimerSystem timerSystem;

    boolean bFinishGame = true;

    private CutLineObject cutLineObject;


    public enum Layer{
        BACKGROUND, CUTLINE, TIMER_GAUGE,RESULT,TOUCH, COUNT
    }


    public FoodPrepGameScene() {
        // 레이어 초기화
        initLayers(Layer.COUNT);

        // 배경 리소스 추가
        add(Layer.BACKGROUND, new Sprite(R.mipmap.temp_fieldgame_background,
                UserDisplay.getWidth(0.5f),
                UserDisplay.getHeight(0.5f),
                UserDisplay.getDesiredWidth(1.0f),
                UserDisplay.getDesiredHeight(1.0f)
        ));

        cutLineObject = new CutLineObject();

        add(Layer.CUTLINE,cutLineObject);

        CountDownClass CountDownObject = new CountDownClass(
                new RectF(UserDisplay.getWidth(0.4f), UserDisplay.getHeight(0.05f),
                        UserDisplay.getWidth(0.6f), UserDisplay.getHeight(0.05f) + UserDisplay.getWidth(0.2f)),
                3.0f
        );

        bFinishGame=false;
//        add(Layer.RESULT,CountDownObject);
//        new Handler().postDelayed(new Runnable(){
//            public void run(){
//                remove(Layer.RESULT, CountDownObject);
//
//                // 타이머 시스템 생성
//                timerSystem = new TimerSystem(
//                        new RectF(UserDisplay.getWidth(0.1f), UserDisplay.getHeight(0.05f),
//                                UserDisplay.getWidth(0.9f), UserDisplay.getHeight(0.05f)),
//                        10.0f,
//                        75.0f
//                );
//                add(Layer.TIMER_GAUGE,timerSystem);
//
//                bFinishGame = false;
//            }
//        }, 3500);


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
                cutLineObject.setStartPos(x,y);
                cutLineObject.setEndPos(x,y);
                break;

            case MotionEvent.ACTION_MOVE:
                cutLineObject.setEndPos(x,y);
                break;

            case MotionEvent.ACTION_UP:
                cutLineObject.setStartPos(-1,-1);
                cutLineObject.setEndPos(-1,-1);
                break;

        }

        return super.onTouchEvent(event);
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

        new Handler().postDelayed(new Runnable(){
            public void run(){
                //MiniGameResultScene scene = new MiniGameResultScene(ScoreDataMap);
                //scene.changeScene();

            }
        }, 2000);
    }
}
