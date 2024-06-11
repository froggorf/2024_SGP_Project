package kr.ac.tukorea.spgp2024.minigametycoon.game.Scene;

import android.graphics.RectF;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;

import java.util.ArrayList;
import java.util.List;

import kr.ac.tukorea.spgp2024.R;
import kr.ac.tukorea.spgp2024.minigametycoon.framework.objects.Button;
import kr.ac.tukorea.spgp2024.minigametycoon.framework.objects.Score;
import kr.ac.tukorea.spgp2024.minigametycoon.framework.objects.Sprite;
import kr.ac.tukorea.spgp2024.minigametycoon.framework.res.Sound;
import kr.ac.tukorea.spgp2024.minigametycoon.framework.scene.BaseScene;
import kr.ac.tukorea.spgp2024.minigametycoon.game.Object.RestaurantScene.Customer;
import kr.ac.tukorea.spgp2024.minigametycoon.game.Object.RestaurantScene.EarnOfflineReward;
import kr.ac.tukorea.spgp2024.minigametycoon.game.Object.RestaurantScene.Restaurant;
import kr.ac.tukorea.spgp2024.minigametycoon.game.OffLineData;
import kr.ac.tukorea.spgp2024.minigametycoon.game.RestaurantData;
import kr.ac.tukorea.spgp2024.minigametycoon.game.UserDisplay;
import kr.ac.tukorea.spgp2024.minigametycoon.game.enums.EFarmAnimalType;

public class RestaurantScene extends BaseScene {
    private final String TAG = RestaurantScene.class.getSimpleName();


    public enum Layer{
        BACKGROUND, ROAD , RESTAURANT, GOLDSCORE , TOUCH, COUNT
    }

    RectF RestaurantRect;
    public Restaurant RestaurantObject;
    Score GoldScoreObject;
    Button AddChefButton;
    Button AddServerButton;
    EarnOfflineReward OfflineReward;
    Button OfflineRewardButton;

    public RestaurantScene() {
        // 저장된 데이터 다시 로드
        RestaurantData.ReadData();

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
        {
            RestaurantRect = new RectF(
                    UserDisplay.getWidth(0.1f),
                    UserDisplay.getHeight(0.35f),
                    UserDisplay.getWidth(0.9f),
                    UserDisplay.getHeight(0.95f)
            );
            RestaurantObject = new Restaurant(RestaurantRect);
            add(Layer.RESTAURANT, RestaurantObject);
        }

        // 길 스프라이트 추가
        {
            float RoadSize = UserDisplay.getHeight(0.1f);
            add(Layer.ROAD, new Sprite(R.mipmap.temp_restaurant_road,
                    UserDisplay.getWidth(0.5f),
                    RestaurantRect.top - RoadSize,
                    UserDisplay.getWidth(1.0f),
                    RoadSize*2));
        }

        // 골드 출력 UI 추가
        {
            GoldScoreObject = new Score(R.mipmap.temp_number,
                    UserDisplay.getWidth(0.95f), UserDisplay.getHeight(0.01f),UserDisplay.getWidth(0.1f)
            );
            GoldScoreObject.setScore(RestaurantData.CurGold);

            add(Layer.GOLDSCORE,GoldScoreObject);
        }

        // 직원 추가 버튼 만들기
        {
            AddChefButton = new Button(R.mipmap.temp_add_chef_enable,
                    UserDisplay.getWidth(0.25f),UserDisplay.getHeight(0.9f),
                    UserDisplay.getWidth(0.4f),UserDisplay.getHeight(0.1f),AddChefButtonFunc
            );
            if(RestaurantData.MaxChefCount <= RestaurantData.CurChefCount) AddChefButton.setBitmapResource(R.mipmap.temp_add_chef_disable);
            add(Layer.TOUCH,AddChefButton);

            AddServerButton = new Button(R.mipmap.temp_add_server_enable,
                    UserDisplay.getWidth(0.75f),UserDisplay.getHeight(0.9f),
                    UserDisplay.getWidth(0.4f),UserDisplay.getHeight(0.1f),AddServerButtonFunc
            );
            if(RestaurantData.MaxServerCount <= RestaurantData.CurServerCount) AddServerButton.setBitmapResource(R.mipmap.temp_add_server_disable);
            add(Layer.TOUCH,AddServerButton);
        }

        // 로비로 돌아가는 버튼 누르기
        {
            add(Layer.TOUCH,new Button(
                    R.mipmap.temp_back_icon,
                    UserDisplay.getWidth(0.1f),
                    UserDisplay.getWidth(0.1f),       // 같은 크기로 설정
                    UserDisplay.getWidth(0.15f),
                    UserDisplay.getWidth(0.15f),
                    GoToTownScene
            ));
        }

        // 오프라인 보상 추가
        {
            OfflineReward = new EarnOfflineReward(
                    new RectF(
                            UserDisplay.getWidth(0.5f),
                            UserDisplay.getHeight(0.5f),
                            UserDisplay.getWidth(0.75f),
                            UserDisplay.getHeight(0.75f)
                    )
            );
            add(Layer.TOUCH,OfflineReward);

            new Handler().postDelayed(new Runnable(){
                public void run(){
                    OfflineRewardButton =new Button(R.mipmap.fieldgame_board_blank,UserDisplay.getWidth(0.5f),
                            UserDisplay.getHeight(0.5f),
                            UserDisplay.getWidth(0.75f),
                            UserDisplay.getHeight(0.75f),
                            RemoveEarnOffLineReward
                    );
                    add(Layer.TOUCH, OfflineRewardButton);
                }
            }, 1000);

        }
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

    public float[] IsRestaurantEmpty(int[] TableTileNum){
        return RestaurantObject.EnterRestaurant(TableTileNum);
    }

    public void ChangeGoldFromCurrent(int ChangeValue){
        int NewValue = Math.max (0,GoldScoreObject.getScore() + ChangeValue);
        GoldScoreObject.setScore(NewValue);

        RestaurantData.SetCurGoldAndSaveData(GoldScoreObject.getScore());
    }

    private  Button.Callback AddChefButtonFunc = new Button.Callback() {
        @Override
        public boolean onTouch(Button.Action action) {
            if(AddChefButton.BitmapResId == R.mipmap.temp_add_chef_enable){
                if(RestaurantData.MaxChefCount > RestaurantData.CurChefCount)
                {
                    RestaurantObject.AddSecondChef();
                    RestaurantData.AddOneCurChefCountAndSaveData();
                    return true;
                }
                AddChefButton.setBitmapResource(R.mipmap.temp_add_chef_disable);
            }
            return false;
        }
    };

    private  Button.Callback AddServerButtonFunc = new Button.Callback() {
        @Override
        public boolean onTouch(Button.Action action) {
            if(AddServerButton.BitmapResId == R.mipmap.temp_add_server_enable){
                if(RestaurantData.MaxServerCount > RestaurantData.CurServerCount)
                {
                    RestaurantObject.AddSecondServer();
                    RestaurantData.AddOneCurServerCountAndSaveData();
                    return true;
                }
                AddServerButton.setBitmapResource(R.mipmap.temp_add_server_disable);
            }
            return false;
        }
    };

    private  Button.Callback GoToTownScene = new Button.Callback() {
        @Override
        public boolean onTouch(Button.Action action) {
            RestaurantData.SaveData();
            TownScene Scene = new TownScene();
            Scene.changeScene();
            return true;
        }
    };

    private Button.Callback RemoveEarnOffLineReward = new Button.Callback() {
        @Override
        public boolean onTouch(Button.Action action) {
            ChangeGoldFromCurrent(OfflineReward.CalculateGold());
            OffLineData.SaveData();

            remove(Layer.TOUCH,OfflineReward);
            remove(Layer.TOUCH,OfflineRewardButton);

            return true;
        }
    };
}
