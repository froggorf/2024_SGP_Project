package kr.ac.tukorea.spgp2024.minigametycoon.game.Scene;

import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;

import kr.ac.tukorea.spgp2024.R;
import kr.ac.tukorea.spgp2024.minigametycoon.framework.objects.Button;
import kr.ac.tukorea.spgp2024.minigametycoon.framework.objects.Sprite;
import kr.ac.tukorea.spgp2024.minigametycoon.framework.res.Sound;
import kr.ac.tukorea.spgp2024.minigametycoon.framework.scene.BaseScene;
import kr.ac.tukorea.spgp2024.minigametycoon.game.UserDisplay;

public class TownScene extends BaseScene {
    private final String TAG = TownScene.class.getSimpleName();
    long startTime;
    Sprite fieldSprite;
    Sprite backgroundSprite;
    static Button FieldButton, FarmButton,FoodPrepButton;

    public enum Layer{
        BACKGROUND, TOUCH ,COUNT
    }
    public TownScene() {
        startTime = System.currentTimeMillis();

        // 레이어 초기화
        initLayers(TitleScene.Layer.COUNT);

        // 배경사진 추가
        backgroundSprite = new Sprite(R.mipmap.town_background,
                UserDisplay.getWidth(0.5f),
                UserDisplay.getHeight(0.5f),
                UserDisplay.getDesiredWidth(0.95f),
                UserDisplay.getDesiredHeight(1.0f));
        add(Layer.BACKGROUND, backgroundSprite);

        // 밭 사진 추가
        //fieldSprite = new Sprite(R.mipmap.temp_town_field,
        //UserDisplay.getWidth(0.3f),
        //UserDisplay.getHeight(0.7f),
        //UserDisplay.getDesiredWidth(0.5f),
        //UserDisplay.getDesiredHeight(0.5f));
        //add(Layer.HOUSE, fieldSprite);
    }

    @Override
    public void update(long elapsedNanos) {
        super.update(elapsedNanos);
    }

    @Override
    protected void onStart() {
        startTime = System.currentTimeMillis();
        CreateButtons();
        // 500ms 뒤 노래 틀기
        new Handler().postDelayed(new Runnable(){
            public void run(){
                Sound.playMusic(R.raw.temp_town_sound);

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
        Sound.playMusic(R.raw.temp_town_sound);
        //Sound.resumeMusic();
    }

    public boolean handleBackKey(){

        return true;
    }

    @Override
    protected int getTouchLayerIndex() {
        return Layer.TOUCH.ordinal();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN){

        }
        return super.onTouchEvent(event);
    }

    void CreateButtons(){
        // 목장 버튼 Press 버튼 추가
        {
            add(Layer.TOUCH, new Button(R.mipmap.fieldgame_board_blank,
                    UserDisplay.getWidth(0.15f),
                    UserDisplay.getHeight(0.75f),
                    UserDisplay.getDesiredWidth(0.5f),
                    UserDisplay.getDesiredHeight(0.5f),
                    new Button.Callback() {
                        @Override
                        public boolean onTouch(Button.Action action) {
                            if(System.currentTimeMillis() - startTime < 1000) return false;
                            if (action == Button.Action.pressed) {
                                new FarmGameInfoScene().changeScene();
                            }
                            return true;
                        }
                    }));
        }

        // 밭 미니게임
        {
            add(Layer.TOUCH, new Button(R.mipmap.fieldgame_board_blank,
                    UserDisplay.getWidth(0.85f),
                    UserDisplay.getHeight(0.53f),
                    UserDisplay.getDesiredWidth(0.5f),
                    UserDisplay.getDesiredHeight(0.25f),
                    new Button.Callback() {
                        @Override
                        public boolean onTouch(Button.Action action) {
                            if(System.currentTimeMillis() - startTime < 1000) return false;
                            if (action == Button.Action.pressed) {
                                new FieldGameInfoScene().changeScene();

                            }
                            return true;
                        }
                    }));
        }

        //재료 가공소
        {
            add(Layer.TOUCH, new Button(R.mipmap.fieldgame_board_blank,
                    UserDisplay.getWidth(0.15f),
                    UserDisplay.getHeight(0.33f),
                    UserDisplay.getDesiredWidth(0.5f),
                    UserDisplay.getDesiredHeight(0.3f),
                    new Button.Callback() {
                        @Override
                        public boolean onTouch(Button.Action action) {
                            if(System.currentTimeMillis() - startTime < 1000) return false;
                            if (action == Button.Action.pressed) {
                                new FoodPrepGameInfoScene().changeScene();

                            }
                            return true;
                        }
                    }));
        }

        // 레스토랑
        {
            add(Layer.TOUCH, new Button(R.mipmap.fieldgame_board_blank,
                    UserDisplay.getWidth(0.75f),
                    UserDisplay.getHeight(0.25f),
                    UserDisplay.getDesiredWidth(0.4f),
                    UserDisplay.getDesiredHeight(0.25f),
                    new Button.Callback() {
                        @Override
                        public boolean onTouch(Button.Action action) {
                            if(System.currentTimeMillis() - startTime < 1000) return false;
                            if (action == Button.Action.pressed) {
                                new RestaurantScene().changeScene();

                            }
                            return true;
                        }
                    }));
        }

    }
}
