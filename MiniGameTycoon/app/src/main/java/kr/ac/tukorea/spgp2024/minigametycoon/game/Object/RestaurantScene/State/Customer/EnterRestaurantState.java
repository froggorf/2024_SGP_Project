package kr.ac.tukorea.spgp2024.minigametycoon.game.Object.RestaurantScene.State.Customer;

import android.graphics.Canvas;

import java.util.Random;

import kr.ac.tukorea.spgp2024.R;
import kr.ac.tukorea.spgp2024.minigametycoon.framework.objects.AnimSprite;
import kr.ac.tukorea.spgp2024.minigametycoon.framework.scene.BaseScene;
import kr.ac.tukorea.spgp2024.minigametycoon.game.Object.RestaurantScene.Person;
import kr.ac.tukorea.spgp2024.minigametycoon.game.Scene.RestaurantScene;
import kr.ac.tukorea.spgp2024.minigametycoon.game.UserDisplay;

public class EnterRestaurantState extends CustomerBaseState{

    public EnterRestaurantState(Person Owner) {
        super(Owner);
    }

    static int X = 0; static int Y = 1;

    float[] StartLocation = new float[2];
    float Speed;
    boolean bFaceRight;
    boolean bTry = false;
    float RoadStartY, RoadEndY;
    public RoamState(Person Owner, float RoadStartY, float RoadEndY){
        super(Owner);
        this.RoadStartY =RoadStartY;
        this.RoadEndY = RoadEndY;

        PersonSprite = new AnimSprite(R.mipmap.temp_customer_walk_right,0,0,52*1.6f,62*1.6f,4,8);
        PersonSprite.Resize(UserDisplay.getWidth(0.1f),UserDisplay.getHeight(0.1f));

        StartLocation[Y] = (float) (Math.random() * (RoadEndY - RoadStartY)  + RoadStartY);
        Speed = (float) (Math.random()* UserDisplay.getWidth(0.1f) + UserDisplay.getWidth(0.1f));
        if(Math.random()*2 > 1)
        {
            StartLocation[X] = UserDisplay.getWidth(-0.5f);
            bFaceRight= true;
        }
        else
        {
            PersonSprite.setBitmapResource(R.mipmap.temp_customer_walk_left);
            StartLocation[X] = UserDisplay.getWidth(1.5f);
            Speed *= -1;
            bFaceRight= false;
        }

        PersonSprite.moveTo(StartLocation[X], StartLocation[Y]);
        //PersonSprite.moveTo(UserDisplay.getWidth(0.3f),UserDisplay.getHeight(0.3f));
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

        PersonSprite.MoveOffset(BaseScene.frameTime*Speed,0);



        if(bFaceRight)
        {

        }
        else
        {
            if(!bTry)
            {
                if(PersonSprite.GetLocation()[X] <= UserDisplay.getWidth(0.6f)){
                    if(CanEnterRestaurant()){
                        Exit();
                        StateOwner.State = null;
                    }
                }
            }
            if(PersonSprite.GetLocation()[X] <= UserDisplay.getWidth(-0.5f)){
                Exit();
                StateOwner.State = new RoamState(StateOwner,RoadStartY,RoadEndY);
            }
        }
    }

    @Override
    public void Draw(Canvas canvas){
        super.Draw(canvas);
    }


}
