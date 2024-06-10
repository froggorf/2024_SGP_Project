package kr.ac.tukorea.spgp2024.minigametycoon.game.Object.RestaurantScene.State.Employee.Chef;

import android.graphics.Canvas;
import android.util.Log;

import kr.ac.tukorea.spgp2024.R;
import kr.ac.tukorea.spgp2024.minigametycoon.framework.objects.AnimSprite;
import kr.ac.tukorea.spgp2024.minigametycoon.framework.scene.BaseScene;
import kr.ac.tukorea.spgp2024.minigametycoon.game.Object.RestaurantScene.OrderData;
import kr.ac.tukorea.spgp2024.minigametycoon.game.Object.RestaurantScene.Person;
import kr.ac.tukorea.spgp2024.minigametycoon.game.Scene.RestaurantScene;
import kr.ac.tukorea.spgp2024.minigametycoon.game.UserDisplay;

public class ChefRestState extends ChefBaseState{
    float[] CurrentLocation;
    public ChefRestState(Person Owner, float[] StartLocation) {
        super(Owner);

        StartTime = System.currentTimeMillis();
        CurrentLocation = StartLocation;

        PersonSprite = new AnimSprite(R.mipmap.temp_chef_idle_right,0,0,54*1.6f,64*1.6f,4,8);
        PersonSprite.Resize(UserDisplay.getWidth(0.1f),UserDisplay.getHeight(0.1f));

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

        if(!((RestaurantScene) (BaseScene.getTopScene())).RestaurantObject.Orders.isEmpty()){
            OrderData Data = ((RestaurantScene) (BaseScene.getTopScene())).RestaurantObject.Orders.get(0);
            ((RestaurantScene) (BaseScene.getTopScene())).RestaurantObject.Orders.remove(0);

            Exit();
            StateOwner.State = new ChefCookState(StateOwner, CurrentLocation, Data);
        }
    }

    @Override
    public void Draw(Canvas canvas) {
        super.Draw(canvas);
        //PersonSprite.draw(canvas);
    }
}
