package kr.ac.tukorea.spgp2024.minigametycoon.game.Object.RestaurantScene.State.Customer;

import android.graphics.Canvas;
import android.util.Log;

import kr.ac.tukorea.spgp2024.R;
import kr.ac.tukorea.spgp2024.minigametycoon.framework.objects.AnimSprite;
import kr.ac.tukorea.spgp2024.minigametycoon.framework.scene.BaseScene;
import kr.ac.tukorea.spgp2024.minigametycoon.game.Object.RestaurantScene.Person;
import kr.ac.tukorea.spgp2024.minigametycoon.game.Object.RestaurantScene.Restaurant;
import kr.ac.tukorea.spgp2024.minigametycoon.game.Object.RestaurantScene.State.BaseState;
import kr.ac.tukorea.spgp2024.minigametycoon.game.Object.RestaurantScene.State.Employee.Server.ServerRestState;
import kr.ac.tukorea.spgp2024.minigametycoon.game.Scene.RestaurantScene;
import kr.ac.tukorea.spgp2024.minigametycoon.game.UserDisplay;

public class CustomerExitState extends BaseState {
    float[] CurrentLocation;
    float[] EndLocation = new float[2];
    float RoadStartY, RoadEndY;
    float[] RoadCenterLocation = new float[2];
    float LerpAlpha = 0.0f;
    boolean bIsArriveRoadCenter = false;
    float MoveTime = 2.0f;
    public CustomerExitState(Person Owner,float[] StartLocation, float RoadStartY, float RoadEndY)
    {
        super(Owner);

        PersonSprite = new AnimSprite(R.mipmap.temp_customer_withdish_walk_right,0,0,54*1.6f,64*1.6f,4,8);
        PersonSprite.Resize(UserDisplay.getWidth(0.1f),UserDisplay.getHeight(0.1f));

        this.RoadStartY =RoadStartY;
        this.RoadEndY = RoadEndY;

        CurrentLocation = StartLocation;

        RoadCenterLocation[1] = (float) (Math.random() * (RoadEndY - RoadStartY)  + RoadStartY);
        RoadCenterLocation[0] = UserDisplay.getWidth(0.5f);


        EndLocation[0] = RoadCenterLocation[0];
        EndLocation[1] = RoadCenterLocation[1];

        if(CurrentLocation[0] > RoadCenterLocation[0])
        { // 왼쪽 애니메이션
            EndLocation[0] = UserDisplay.getWidth(-0.5f);
            PersonSprite.setBitmapResource(R.mipmap.temp_customer_withdish_walk_left);
        }
        else
        {
            EndLocation[0] = UserDisplay.getWidth(1.5f);
        }

        PersonSprite.moveTo(CurrentLocation[0], CurrentLocation[1]);
    }

    @Override
    public void Enter() {
        super.Enter();

        // 돈 내기
        ((RestaurantScene)(BaseScene.getTopScene())).ChangeGoldFromCurrent((int) (Math.random()*50+150));

    }

    @Override
    public void Exit() {
        super.Exit();
    }

    @Override
    public void Draw(Canvas canvas){
        super.Draw(canvas);
    }

    @Override
    public void Update(){
        super.Update();
        LerpAlpha += BaseScene.frameTime/MoveTime;

        if(!bIsArriveRoadCenter){
            if(LerpAlpha >= 1.0f){
                LerpAlpha = 1.0f;
                bIsArriveRoadCenter = true;
            }

            PersonSprite.moveTo(
                    Lerp(CurrentLocation[0], RoadCenterLocation[0], LerpAlpha),
                    Lerp(CurrentLocation[1], RoadCenterLocation[1], LerpAlpha)
            );
            if(bIsArriveRoadCenter){
                LerpAlpha = 0.0f;
                CurrentLocation = RoadCenterLocation;
            }

        }
        else
        {
            if(LerpAlpha >= 1.0f){
                Exit();
                StateOwner.State = new RoamState(StateOwner,RoadStartY,RoadEndY);
                StateOwner.State.Enter();
                return;
            }

            PersonSprite.moveTo(
                    Lerp(CurrentLocation[0], EndLocation[0], LerpAlpha),
                    Lerp(CurrentLocation[1], EndLocation[1], LerpAlpha)
            );
        }
    }
    float Lerp (float a, float b, float f) {
        return a + f * (b - a);
    }
}
