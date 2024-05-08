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

public class FieldGameInfoScene extends BaseScene {
    private final String TAG = TownScene.class.getSimpleName();
    Sprite infoSprite;
    long startTime;

    public enum Layer{
        INFO, COUNT
    }
    public FieldGameInfoScene() {
        startTime = System.currentTimeMillis();

        // 레이어 초기화
        initLayers(TitleScene.Layer.COUNT);

        infoSprite = new Sprite(R.mipmap.temp_fieldgameinfo_infoimage,
                UserDisplay.getWidth(0.5f),
                UserDisplay.getHeight(0.5f),
                UserDisplay.getDesiredWidth(1.0f),
                UserDisplay.getDesiredHeight(1.0f));
        add(TownScene.Layer.BACKGROUND, infoSprite);


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
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            new FieldGameScene().changeScene();
        }
        return super.onTouchEvent(event);
    }
}
