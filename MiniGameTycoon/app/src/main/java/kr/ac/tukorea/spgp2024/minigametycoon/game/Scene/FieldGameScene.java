package kr.ac.tukorea.spgp2024.minigametycoon.game.Scene;

import android.graphics.Point;
import android.graphics.RectF;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;

import kr.ac.tukorea.spgp2024.R;
import kr.ac.tukorea.spgp2024.minigametycoon.framework.objects.Sprite;
import kr.ac.tukorea.spgp2024.minigametycoon.framework.res.Sound;
import kr.ac.tukorea.spgp2024.minigametycoon.framework.scene.BaseScene;
import kr.ac.tukorea.spgp2024.minigametycoon.game.Object.FieldGame.FieldBoard;
import kr.ac.tukorea.spgp2024.minigametycoon.game.UserDisplay;

public class FieldGameScene extends BaseScene {
    private final String TAG = FieldGameScene.class.getSimpleName();
    private RectF boardRect;
    Sprite infoSprite;
    Sprite[] boxSprite = new Sprite[5];
    long startTime;



    public enum Layer{
        BACKGROUND, BOX, BOARD ,INPUT, COUNT
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
            boxSprite[i] = new Sprite(R.mipmap.temp_fieldgame_box1 + i,
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
        add(Layer.BOARD,new FieldBoard(new Point(boardCountX,boardCountY),boardRect));


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
        return super.onTouchEvent(event);
    }
}
