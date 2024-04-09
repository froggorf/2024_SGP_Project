package kr.ac.tukorea.spgp2024.minigametycoon.game.Scene;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import kr.ac.tukorea.spgp2024.R;
import kr.ac.tukorea.spgp2024.minigametycoon.framework.objects.Sprite;
import kr.ac.tukorea.spgp2024.minigametycoon.framework.res.Sound;
import kr.ac.tukorea.spgp2024.minigametycoon.framework.scene.BaseScene;
import kr.ac.tukorea.spgp2024.minigametycoon.framework.view.Metrics;

public class TitleScene extends BaseScene {
    private final String TAG = LogoScene.class.getSimpleName();
    Sprite LogoSprite;
    public enum Layer{
        TITLE,  COUNT
    }
    public TitleScene() {
        // 레이어 초기화
        initLayers(Layer.COUNT);

        LogoSprite = new Sprite(R.mipmap.title,4.5f,8.0f,9.0f,16.0f);
        add(Layer.TITLE,LogoSprite);

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
                Sound.playMusic(R.raw.title_sound);

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
}
