package kr.ac.tukorea.spgp2024.minigametycoon.game.Scene;

import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.RectF;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;

import java.util.HashMap;
import java.util.Map;

import kr.ac.tukorea.spgp2024.R;
import kr.ac.tukorea.spgp2024.minigametycoon.framework.objects.Button;
import kr.ac.tukorea.spgp2024.minigametycoon.framework.objects.Sprite;
import kr.ac.tukorea.spgp2024.minigametycoon.framework.res.Sound;
import kr.ac.tukorea.spgp2024.minigametycoon.framework.scene.BaseScene;
import kr.ac.tukorea.spgp2024.minigametycoon.game.CountDownClass;
import kr.ac.tukorea.spgp2024.minigametycoon.game.Object.FieldGame.FieldBoard;
import kr.ac.tukorea.spgp2024.minigametycoon.game.Object.FieldGame.FoodTypeEnum;
import kr.ac.tukorea.spgp2024.minigametycoon.game.TimerSystem;
import kr.ac.tukorea.spgp2024.minigametycoon.game.UserDisplay;
import kr.ac.tukorea.spgp2024.minigametycoon.game.enums.EDataName;

enum EFarmAnimalType{
    COW, PIG,CHICKEN, SIZE
}

public class FarmGameScene extends BaseScene {
    private final String TAG = FarmGameScene.class.getSimpleName();

    int[] AnimalResourceArray = new int[]{
            R.mipmap.temp_farmgame_cow,
            R.mipmap.temp_farmgame_pig,
            R.mipmap.temp_farmgame_chicken,
    };

    //Sprite[] AnimalSprites = new Sprite[EFarmAnimalType.SIZE.ordinal()];

    TimerSystem timerSystem;

    boolean bFinishGame = true;

    public enum Layer{
        BACKGROUND,FENCE,  TIMER_GAUGE,RESULT,INPUT, COUNT
    }
    public FarmGameScene() {
        // 레이어 초기화
        initLayers(Layer.COUNT);

        // 배경 리소스 추가
        add(Layer.BACKGROUND, new Sprite(R.mipmap.temp_farmgame_background,
                UserDisplay.getWidth(0.5f),
                UserDisplay.getHeight(0.5f),
                UserDisplay.getDesiredWidth(1.0f),
                UserDisplay.getDesiredHeight(1.0f)
        ));

        add(Layer.FENCE, new Sprite(R.mipmap.temp_farmgame_fence,
                UserDisplay.getWidth(0.5f),
                UserDisplay.getHeight(0.5f),
                UserDisplay.getDesiredWidth(1.0f),
                UserDisplay.getDesiredHeight(0.2f)));

        add(Layer.INPUT, new Button(R.mipmap.temp_farmgame_button_cow,
                UserDisplay.getWidth(0.1625f),UserDisplay.getHeight(0.9f),UserDisplay.getWidth(0.3f),UserDisplay.getHeight(0.1f),
                CowFeedButton));
        add(Layer.INPUT, new Button(R.mipmap.temp_farmgame_button_pig,
                UserDisplay.getWidth(0.5f),UserDisplay.getHeight(0.95f),UserDisplay.getWidth(0.3f),UserDisplay.getHeight(0.1f),
                PigFeedButton));
        add(Layer.INPUT, new Button(R.mipmap.temp_farmgame_button_chicken,
                UserDisplay.getWidth(0.8375f),UserDisplay.getHeight(0.9f),UserDisplay.getWidth(0.3f),UserDisplay.getHeight(0.1f),
                ChickenFeedButton));


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
                //add(Layer.TIMER_GAUGE,timerSystem);

                bFinishGame = false;
            }
        }, 3500);


    }

    private void FeedAnimal(EFarmAnimalType FeedAnimalType){
        Log.d(TAG, "FeedAnimal: " + FeedAnimalType.name());
    }


    private  Button.Callback CowFeedButton = new Button.Callback() {
        @Override
        public boolean onTouch(Button.Action action) {
            if(action != Button.Action.pressed) return false;
            FeedAnimal(EFarmAnimalType.COW);
            return false;
        }
    };
    private  Button.Callback PigFeedButton = new Button.Callback() {
        @Override
        public boolean onTouch(Button.Action action) {
            if(action != Button.Action.pressed) return false;
            FeedAnimal(EFarmAnimalType.PIG);
            return false;
        }
    };
    private  Button.Callback ChickenFeedButton = new Button.Callback() {
        @Override
        public boolean onTouch(Button.Action action) {
            if(action != Button.Action.pressed) return false;
            FeedAnimal(EFarmAnimalType.CHICKEN);
            return false;
        }
    };

    @Override
    public void draw(Canvas canvas){
        super.draw(canvas);


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
        return Layer.INPUT.ordinal();
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
