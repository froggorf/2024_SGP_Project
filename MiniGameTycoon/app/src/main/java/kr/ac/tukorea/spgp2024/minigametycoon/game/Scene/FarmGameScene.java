package kr.ac.tukorea.spgp2024.minigametycoon.game.Scene;

import android.os.Handler;
import android.view.MotionEvent;

import kr.ac.tukorea.spgp2024.R;
import kr.ac.tukorea.spgp2024.minigametycoon.framework.objects.Sprite;
import kr.ac.tukorea.spgp2024.minigametycoon.framework.res.Sound;
import kr.ac.tukorea.spgp2024.minigametycoon.framework.scene.BaseScene;
import kr.ac.tukorea.spgp2024.minigametycoon.game.Object.FoodPrepHouseGame.CutLineObject;


public class FarmGameScene extends BaseScene {
    private final String TAG = FarmGameScene.class.getSimpleName();
    Sprite infoSprite;
    long startTime;

    private CutLineObject cutLineObject;
    public enum Layer{
        INFO, CUTLINE ,  TOUCH, COUNT
    }
    public FarmGameScene() {

        // 레이어 초기화
        initLayers(Layer.COUNT);

        cutLineObject = new CutLineObject();

        add(Layer.CUTLINE,cutLineObject);
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
        return Layer.TOUCH.ordinal();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
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
}
