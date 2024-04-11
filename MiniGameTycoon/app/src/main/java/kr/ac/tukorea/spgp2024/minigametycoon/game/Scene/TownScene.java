package kr.ac.tukorea.spgp2024.minigametycoon.game.Scene;

import android.os.Handler;
import android.view.MotionEvent;

import kr.ac.tukorea.spgp2024.R;
import kr.ac.tukorea.spgp2024.minigametycoon.framework.objects.Sprite;
import kr.ac.tukorea.spgp2024.minigametycoon.framework.res.Sound;
import kr.ac.tukorea.spgp2024.minigametycoon.framework.scene.BaseScene;
import kr.ac.tukorea.spgp2024.minigametycoon.game.UserDisplay;

public class TownScene extends BaseScene {
    private final String TAG = TownScene.class.getSimpleName();
    Sprite fieldSprite;
    Sprite backgroundSprite;

    public enum Layer{
        BACKGROUND, HOUSE, UI ,COUNT
    }
    public TownScene() {
        // 레이어 초기화
        initLayers(TitleScene.Layer.COUNT);

        //TitleSprite = new Sprite(R.mipmap.title,4.5f,8.0f,9.0f,16.0f);
        backgroundSprite = new Sprite(R.mipmap.temp_town_backgroundpng,
                UserDisplay.getWidth(0.5f),
                UserDisplay.getHeight(0.5f),
                UserDisplay.getDesiredWidth(1.0f),
                UserDisplay.getDesiredHeight(1.0f));
        add(Layer.BACKGROUND, backgroundSprite);

        fieldSprite = new Sprite(R.mipmap.temp_town_field,
                UserDisplay.getWidth(0.3f),
                UserDisplay.getHeight(0.7f),
                UserDisplay.getDesiredWidth(0.5f),
                UserDisplay.getDesiredHeight(0.5f));
        add(Layer.HOUSE, fieldSprite);



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
        Sound.resumeMusic();
    }

    public boolean handleBackKey(){

        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN){

        }
        return super.onTouchEvent(event);
    }
}
