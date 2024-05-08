package kr.ac.tukorea.spgp2024.minigametycoon.game.Scene;

import android.graphics.RectF;
import android.os.Handler;
import android.view.MotionEvent;

import kr.ac.tukorea.spgp2024.R;
import kr.ac.tukorea.spgp2024.minigametycoon.framework.objects.Sprite;
import kr.ac.tukorea.spgp2024.minigametycoon.framework.res.Sound;
import kr.ac.tukorea.spgp2024.minigametycoon.framework.scene.BaseScene;
import kr.ac.tukorea.spgp2024.minigametycoon.game.CountDownClass;
import kr.ac.tukorea.spgp2024.minigametycoon.game.TimerSystem;
import kr.ac.tukorea.spgp2024.minigametycoon.game.UserDisplay;


public class BaseMiniGameFrame extends BaseScene {
    private final String TAG = BaseMiniGameFrame.class.getSimpleName();
    int[] resArray = new int[]{
            0
    };

    TimerSystem timerSystem;

    boolean bFinishGame = true;

    public enum Layer{
        BACKGROUND, TIMER_GAUGE,RESULT,INPUT, COUNT
    }
    public BaseMiniGameFrame() {
        // 레이어 초기화
        initLayers(Layer.COUNT);

        // 배경 리소스 추가
        add(Layer.BACKGROUND, new Sprite(R.mipmap.temp_fieldgame_background,
                UserDisplay.getWidth(0.5f),
                UserDisplay.getHeight(0.5f),
                UserDisplay.getDesiredWidth(1.0f),
                UserDisplay.getDesiredHeight(1.0f)
        ));

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
