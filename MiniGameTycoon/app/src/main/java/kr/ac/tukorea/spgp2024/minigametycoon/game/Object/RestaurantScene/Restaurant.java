package kr.ac.tukorea.spgp2024.minigametycoon.game.Object.RestaurantScene;

import android.graphics.Canvas;
import android.graphics.RectF;

import kr.ac.tukorea.spgp2024.R;
import kr.ac.tukorea.spgp2024.minigametycoon.framework.interfaces.IGameObject;
import kr.ac.tukorea.spgp2024.minigametycoon.framework.objects.Sprite;

public class Restaurant implements IGameObject {
    static final String TAG = Restaurant.class.getSimpleName();

    RectF RestaurantSize = new RectF();

    Sprite TempSprite;
    public Restaurant(RectF Size) {
        RestaurantSize.set(Size);
        TempSprite = new Sprite(R.mipmap.temp_fieldgame_background, Size.left,Size.top,Size.right,Size.bottom);
    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Canvas canvas) {
        TempSprite.draw(canvas);
    }
}
