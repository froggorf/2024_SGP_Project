package kr.ac.tukorea.spgp2024.minigametycoon.game.Object.RestaurantScene.State.Customer;

import android.graphics.Canvas;

import kr.ac.tukorea.spgp2024.R;
import kr.ac.tukorea.spgp2024.minigametycoon.framework.PersonAnimSprite;
import kr.ac.tukorea.spgp2024.minigametycoon.framework.objects.AnimSprite;
import kr.ac.tukorea.spgp2024.minigametycoon.game.Object.RestaurantScene.Person;
import kr.ac.tukorea.spgp2024.minigametycoon.game.Object.RestaurantScene.State.BaseState;

public class CustomerBaseState extends BaseState {
    public CustomerBaseState(Person Owner) {
        super(Owner);
    }

    @Override
    public void Draw(Canvas canvas){
        if(PersonSprite != null) PersonSprite.draw(canvas);
    }
}
