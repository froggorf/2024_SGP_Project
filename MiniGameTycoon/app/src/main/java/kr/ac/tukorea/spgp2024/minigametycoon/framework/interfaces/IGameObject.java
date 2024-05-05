package kr.ac.tukorea.spgp2024.minigametycoon.framework.interfaces;

import android.graphics.Canvas;

public interface IGameObject {
    public boolean bTickEnabled = true;
    public void update();
    public void draw(Canvas canvas);
}
