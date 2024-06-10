package kr.ac.tukorea.spgp2024.minigametycoon.game.Scene;

import android.graphics.RectF;
import android.os.Handler;
import android.view.MotionEvent;

import kr.ac.tukorea.spgp2024.R;
import kr.ac.tukorea.spgp2024.minigametycoon.framework.objects.Sprite;
import kr.ac.tukorea.spgp2024.minigametycoon.framework.res.Sound;
import kr.ac.tukorea.spgp2024.minigametycoon.framework.scene.BaseScene;
import kr.ac.tukorea.spgp2024.minigametycoon.game.Object.RestaurantScene.Restaurant;
import kr.ac.tukorea.spgp2024.minigametycoon.game.UserDisplay;

public class RestaurantScene extends BaseScene {
    private final String TAG = RestaurantScene.class.getSimpleName();

    public enum Layer{
        BACKGROUND, ROAD , RESTAURANT,TOUCH, COUNT
    }

    RectF RestaurantRect;
    Restaurant RestaurantObject;



    public RestaurantScene() {
        // 레이어 초기화
        initLayers(Layer.COUNT);

        // 백그라운드(풀) 추가
        add(Layer.BACKGROUND,new Sprite(R.mipmap.temp_restaurant_background,
                UserDisplay.getWidth(0.5f),
                UserDisplay.getHeight(0.5f),
                UserDisplay.getDesiredHeight(1.0f),
                UserDisplay.getDesiredHeight(1.0f)
                ));

        // 레스토랑 객체 추가
        RestaurantRect = new RectF(
                UserDisplay.getWidth(0.1f),
                UserDisplay.getHeight(0.35f),
                UserDisplay.getWidth(0.9f),
                UserDisplay.getHeight(0.95f)
        );
        RestaurantObject = new Restaurant(RestaurantRect);
        add(Layer.RESTAURANT, RestaurantObject);


        // 길 스프라이트 추가
        float RoadSize = UserDisplay.getHeight(0.1f);
        add(Layer.ROAD, new Sprite(R.mipmap.temp_restaurant_road,
                UserDisplay.getWidth(0.5f),
                RestaurantRect.top - RoadSize,
                UserDisplay.getWidth(1.0f),
                RoadSize*2));
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
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            //new FarmGameScene().changeScene();
        }
        return super.onTouchEvent(event);
    }
}
