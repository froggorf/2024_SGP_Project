package kr.ac.tukorea.spgp2024.minigametycoon.game.Object.RestaurantScene;

import android.graphics.Canvas;
import android.util.Log;

import kr.ac.tukorea.spgp2024.minigametycoon.game.Object.RestaurantScene.State.Employee.Chef.ChefRestState;
import kr.ac.tukorea.spgp2024.minigametycoon.game.Object.RestaurantScene.State.Employee.Server.ServerRestState;

public class Server extends Employee{
    public Server(float[] StartLocation)
    {
        State = new ServerRestState(this,StartLocation);
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
