package kr.ac.tukorea.spgp2024.minigametycoon.game.Object.RestaurantScene;

import android.graphics.Canvas;

import kr.ac.tukorea.spgp2024.minigametycoon.game.Object.RestaurantScene.State.BaseState;
import kr.ac.tukorea.spgp2024.minigametycoon.game.Object.RestaurantScene.State.Customer.RoamState;

public class Customer extends Person{
    static final String tAG = Customer.class.getSimpleName();


    public Customer(float RoadStartY, float RoadEndY){
        State = new RoamState(this, RoadStartY,RoadEndY);
        State.Enter();
    }

    @Override
    public void update() {
        super.update();


    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);


    }
}
