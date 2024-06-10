package kr.ac.tukorea.spgp2024.minigametycoon.game.Object.RestaurantScene;

import android.graphics.Canvas;

import kr.ac.tukorea.spgp2024.minigametycoon.game.Object.RestaurantScene.State.Employee.Chef.ChefRestState;

public class Chef extends Employee{
    public Chef(float[] StartLocation)
    {
        State = new ChefRestState(this,StartLocation);
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
