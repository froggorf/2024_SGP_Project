package kr.ac.tukorea.spgp2024.minigametycoon.game.Scene;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.text.TextPaint;
import android.util.Log;
import android.view.MotionEvent;

import java.util.HashMap;
import java.util.Map;

import kr.ac.tukorea.spgp2024.R;
import kr.ac.tukorea.spgp2024.minigametycoon.framework.objects.Button;
import kr.ac.tukorea.spgp2024.minigametycoon.framework.objects.Sprite;
import kr.ac.tukorea.spgp2024.minigametycoon.framework.res.BitmapPool;
import kr.ac.tukorea.spgp2024.minigametycoon.framework.res.Sound;
import kr.ac.tukorea.spgp2024.minigametycoon.framework.scene.BaseScene;
import kr.ac.tukorea.spgp2024.minigametycoon.game.CountDownClass;
import kr.ac.tukorea.spgp2024.minigametycoon.game.Object.FarmGame.EatingAnimal;
import kr.ac.tukorea.spgp2024.minigametycoon.game.TimerSystem;
import kr.ac.tukorea.spgp2024.minigametycoon.game.UserDisplay;
import kr.ac.tukorea.spgp2024.minigametycoon.game.enums.EDataName;
import kr.ac.tukorea.spgp2024.minigametycoon.game.enums.EFarmAnimalType;

public class FarmGameScene extends BaseScene {
    private final String TAG = FarmGameScene.class.getSimpleName();
    private final int MAX_ANIMAL_COUNT = 10;

    static public int[] AnimalResourceArray = new int[]{
            R.mipmap.temp_farmgame_cow,
            R.mipmap.temp_farmgame_pig,
            R.mipmap.temp_farmgame_chicken,
    };
    static public int[] FoodBowlResourceArray = new int[]{
            R.mipmap.temp_farmgame_cow_foodbowl,
            R.mipmap.temp_farmgame_pig_foodbowl,
            R.mipmap.temp_farmgame_chicken_foodbowl,
    };
    Bitmap[] AnimalBitmaps = new Bitmap[EFarmAnimalType.SIZE.ordinal()];
    Sprite Background;
    EFarmAnimalType[] Animals = new EFarmAnimalType[MAX_ANIMAL_COUNT];
    int CurFrontAnimalIndex = 0;

    Map<EFarmAnimalType, Integer> Score = new HashMap<>();
    private final int CORRECT_SCORE = 5;
    private final int WRONG_SCORE = -1;
    Paint paint = new Paint();
    TextPaint TextPaint = new TextPaint();

    TimerSystem timerSystem;

    boolean bFinishGame = true;

    public enum Layer{
        BACKGROUND,FENCE,EATING_ANIMAL, FOOD_BOWL,  TIMER_GAUGE,RESULT,INPUT, COUNT
    }



    public FarmGameScene() {
        // 레이어 초기화
        initLayers(Layer.COUNT);

        Background = new Sprite(R.mipmap.temp_farmgame_background,
                UserDisplay.getWidth(0.5f),
                UserDisplay.getHeight(0.5f),
                UserDisplay.getDesiredWidth(1.0f),
                UserDisplay.getDesiredHeight(1.0f));


        // 울타리 배치
        add(Layer.FENCE, new Sprite(R.mipmap.temp_farmgame_fence,
                UserDisplay.getWidth(0.5f),
                UserDisplay.getHeight(0.55f),
                UserDisplay.getDesiredWidth(1.0f),
                UserDisplay.getDesiredHeight(0.2f)));

        // 버튼 배치
        add(Layer.INPUT, new Button(R.mipmap.temp_farmgame_button_cow,
                UserDisplay.getWidth(0.1625f),UserDisplay.getHeight(0.9f),UserDisplay.getWidth(0.3f),UserDisplay.getHeight(0.1f),
                CowFeedButton));
        add(Layer.INPUT, new Button(R.mipmap.temp_farmgame_button_pig,
                UserDisplay.getWidth(0.5f),UserDisplay.getHeight(0.95f),UserDisplay.getWidth(0.3f),UserDisplay.getHeight(0.1f),
                PigFeedButton));
        add(Layer.INPUT, new Button(R.mipmap.temp_farmgame_button_chicken,
                UserDisplay.getWidth(0.8375f),UserDisplay.getHeight(0.9f),UserDisplay.getWidth(0.3f),UserDisplay.getHeight(0.1f),
                ChickenFeedButton));

        // 사료통 배치

        for(int i =0; i<EFarmAnimalType.SIZE.ordinal(); ++i){
            RectF BowlRect = GetBowlRect(i);
            add(Layer.FOOD_BOWL, new Sprite(FoodBowlResourceArray[i],
                    BowlRect.left,BowlRect.top,BowlRect.right,BowlRect.bottom
            ));
        }

        // 게임 초기화
        InitializeGame();

        CountDownClass CountDownObject = new CountDownClass(
                new RectF(UserDisplay.getWidth(0.4f), UserDisplay.getHeight(0.05f),
                        UserDisplay.getWidth(0.6f), UserDisplay.getHeight(0.05f) + UserDisplay.getWidth(0.2f)),
                3.0f
        );

        bFinishGame = true;

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

    static public RectF GetBowlRect(int i) {
        RectF ReturnRectF = new RectF();
        ReturnRectF.left = UserDisplay.getWidth(0.2f) + UserDisplay.getWidth(0.3f) * i;
        ReturnRectF.top =  UserDisplay.getHeight(0.8f);
        ReturnRectF.right = UserDisplay.getWidth(0.25f);
        ReturnRectF.bottom = UserDisplay.getHeight(0.08f);

        return ReturnRectF;
    }

    private void InitializeGame() {
        TextPaint.setTextAlign(Paint.Align.CENTER);
        TextPaint.setTextSize(100.0f);

        for(int i= 0; i<MAX_ANIMAL_COUNT; ++i){
            Animals[i] = EFarmAnimalType.values()[(int) (Math.random()*EFarmAnimalType.SIZE.ordinal())];

        }
        CurFrontAnimalIndex = 0;

        for(int i =0; i<EFarmAnimalType.SIZE.ordinal(); ++i){
            AnimalBitmaps[EFarmAnimalType.values()[i].ordinal()] = BitmapPool.get(AnimalResourceArray[i]);
        }

        if(Score.isEmpty()){
            for(int i =0; i<EFarmAnimalType.SIZE.ordinal(); ++i){
                Score.put(EFarmAnimalType.values()[i],0);
            }
        }
    }

    private void FeedAnimal(EFarmAnimalType FeedAnimalType){
        Log.d(TAG, "FeedAnimal: " + FeedAnimalType.name());
        EFarmAnimalType CurrentAnimalType = Animals[CurFrontAnimalIndex];
        boolean bCorrect = false;
        if(FeedAnimalType == CurrentAnimalType){
            AddScore(CurrentAnimalType, CORRECT_SCORE);
            bCorrect = true;
        }
        else{
            AddScore(CurrentAnimalType, WRONG_SCORE);
            bCorrect= false;
        }

        Animals[CurFrontAnimalIndex] = EFarmAnimalType.values()[(int) (Math.random()*EFarmAnimalType.SIZE.ordinal())];

        // 사료장에 음식 먹는 동물 만들기
        {
            add(Layer.EATING_ANIMAL, new EatingAnimal(CurrentAnimalType,bCorrect));
        }

        CurFrontAnimalIndex = (CurFrontAnimalIndex+1)%MAX_ANIMAL_COUNT;
    }

    private void AddScore(EFarmAnimalType AnimalType, int AddValue) {
        Integer CurrentScore = Score.get(AnimalType);
        CurrentScore = Math.max( CurrentScore + AddValue, 0 );
        Score.replace(AnimalType, CurrentScore);
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
        Background.draw(canvas);
        DrawAnimals(canvas);

        super.draw(canvas);

        for(int i =0; i<EFarmAnimalType.SIZE.ordinal(); ++i){
            canvas.drawText(String.valueOf(Score.get(EFarmAnimalType.values()[i])),
                    UserDisplay.getWidth(0.2f) + UserDisplay.getWidth(0.33f) * i,
                    UserDisplay.getHeight(0.8f), TextPaint);
        }
    }

    private void DrawAnimals(Canvas canvas) {
        float AnimalSize = UserDisplay.getWidth(0.2f);
        float AnimalOffsetY = UserDisplay.getHeight(0.1f);



        for(int i = MAX_ANIMAL_COUNT-1; i>=0; --i){
            // CurFrontAnimalIndex 가 0이 되게 하도록 조정
            int index = (i + CurFrontAnimalIndex) % MAX_ANIMAL_COUNT;

            RectF FirstAnimalRect = new RectF(
                    UserDisplay.getWidth(0.5f) - AnimalSize,
                    UserDisplay.getHeight(0.5f)- AnimalSize - i * AnimalOffsetY,
                    UserDisplay.getWidth(0.5f) + AnimalSize,
                    UserDisplay.getHeight(0.5f)+ AnimalSize - i * AnimalOffsetY
            );

            EFarmAnimalType AnimalType = Animals[index];
            canvas.drawBitmap(AnimalBitmaps[AnimalType.ordinal()], null,FirstAnimalRect, paint);
        }
    }

    @Override
    public void update(long elapsedNanos) {
        if(bFinishGame) return;
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
        if(bFinishGame) return false;
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


        Map<EDataName, Integer> ScoreDataMap = new HashMap<>();
        for(int i =0; i<EFarmAnimalType.SIZE.ordinal(); ++i){
            EDataName DataName = EDataName.values()[EDataName.EDN_First_Ingredients_Beef.ordinal() + i];
            Integer GetTypeScore = Score.get(EFarmAnimalType.values()[i]);
            ScoreDataMap.put(DataName,GetTypeScore);
        }

        // 게임 끝에 대한 이미지 추가
        add(Layer.RESULT, new Sprite(R.mipmap.temp_finishgame,
                UserDisplay.getWidth(0.5f),
                UserDisplay.getHeight(0.5f),
                UserDisplay.getDesiredWidth(0.75f),
                UserDisplay.getDesiredWidth(0.75f)
                ));

        new Handler().postDelayed(new Runnable(){
            public void run(){
                MiniGameResultScene scene = new MiniGameResultScene(ScoreDataMap);
                scene.changeScene();

            }
        }, 2000);
    }
}
