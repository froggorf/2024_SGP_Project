package kr.ac.tukorea.spgp2024.minigametycoon.game.Object.RestaurantScene.State.Employee.Chef;

import android.graphics.Canvas;
import android.util.Log;

import com.google.android.material.transition.MaterialSharedAxis;

import kr.ac.tukorea.spgp2024.R;
import kr.ac.tukorea.spgp2024.minigametycoon.framework.objects.AnimSprite;
import kr.ac.tukorea.spgp2024.minigametycoon.framework.scene.BaseScene;
import kr.ac.tukorea.spgp2024.minigametycoon.framework.util.Gauge;
import kr.ac.tukorea.spgp2024.minigametycoon.game.Object.RestaurantScene.OrderData;
import kr.ac.tukorea.spgp2024.minigametycoon.game.Object.RestaurantScene.Person;
import kr.ac.tukorea.spgp2024.minigametycoon.game.UserDisplay;

public class ChefCookState extends ChefBaseState{
    OrderData OrderData;
    float CookTime = 5.0f;   //5s
    float CurrentCookTime = 0;
    Gauge CookGauge;
    float[] CurrentLocation;
    public ChefCookState(Person Owner, float[] StartLocation, OrderData OrderData) {
        super(Owner);

        StartTime = System.currentTimeMillis();

        PersonSprite = new AnimSprite(R.mipmap.temp_chef_fly_right,0,0,50*1.6f,68*1.6f,4,8);
        PersonSprite.Resize(UserDisplay.getWidth(0.1f),UserDisplay.getHeight(0.1f));

        this.OrderData = OrderData;

        CookGauge = new Gauge(UserDisplay.getWidth(0.01f),R.color.TimerGaugeFg,R.color.TimerGaugeBg);

        CurrentLocation = StartLocation;
        PersonSprite.moveTo(StartLocation[0],StartLocation[1]);
    }

    @Override
    public void Enter() {
        super.Enter();
    }

    @Override
    public void Exit() {
        super.Exit();
    }

    @Override
    public void Update() {
        super.Update();
        CurrentCookTime += BaseScene.frameTime;
        Log.d("TAG", "Update: " + CurrentCookTime);
        if(CurrentCookTime >= CookTime){
            Exit();
            StateOwner.State = new ChefRestState(StateOwner,CurrentLocation);
            StateOwner.State.Enter();
        }
    }

    @Override
    public void Draw(Canvas canvas) {
        super.Draw(canvas);

        // 요리 게이지 그리기
        {
            float GaugeWidth = UserDisplay.getWidth(0.2f);
            float GaugeHeight = UserDisplay.getHeight(0.02f);
            CookGauge.DrawLine(canvas,CurrentLocation[0] - GaugeWidth/2, CurrentLocation[1] - GaugeHeight/2,CurrentLocation[0]+GaugeWidth/2, CurrentLocation[1]+GaugeHeight/2,CurrentCookTime/CookTime);
        }
    }
}
