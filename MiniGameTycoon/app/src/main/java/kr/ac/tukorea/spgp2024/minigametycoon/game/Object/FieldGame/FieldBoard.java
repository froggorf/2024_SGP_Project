package kr.ac.tukorea.spgp2024.minigametycoon.game.Object.FieldGame;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;

import kr.ac.tukorea.spgp2024.R;
import kr.ac.tukorea.spgp2024.minigametycoon.framework.interfaces.IGameObject;
import kr.ac.tukorea.spgp2024.minigametycoon.framework.objects.Sprite;
import kr.ac.tukorea.spgp2024.minigametycoon.game.Scene.FieldGameScene;

public class FieldBoard implements IGameObject {
    Point boardCount = new Point();

    int[][]  board;
    RectF boardRect;
    Paint fillPaint;
    Paint borderPaint;
    Paint strokePaint;

    public FieldBoard(Point getBoardCount, RectF getBoardRect) {
        boardCount = getBoardCount;
        board = new int[boardCount.x][boardCount.y];

        boardRect = getBoardRect;

        fillPaint = new Paint();
        fillPaint.setStyle(Paint.Style.FILL);
        fillPaint.setColor(Color.rgb(155,155,155));

        strokePaint = new Paint();
        strokePaint.setStyle(Paint.Style.STROKE);
        strokePaint.setColor(Color.BLACK);
        strokePaint.setStrokeWidth(5.0f);

    }

    RectF GetBoardPositions(Point currentBoardCount) {
        // data - left / top / right / bottom
        RectF rect = new RectF();
        rect.left= boardRect.width() / boardCount.x * currentBoardCount.x + boardRect.left;
        rect.top= boardRect.height() / boardCount.y * currentBoardCount.y + boardRect.top;
        rect.right = boardRect.width() / boardCount.x * (currentBoardCount.x+1) + boardRect.left;
        rect.bottom = boardRect.height() / boardCount.y * (currentBoardCount.y+1) + boardRect.top;
        return rect;
    }




    @Override
    public void update() {

    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawRect(boardRect.left,boardRect.top,boardRect.right,boardRect.bottom,fillPaint);

        for(int i = 0; i<boardCount.x; ++i){
            for(int j=0; j<boardCount.y; ++j){
                RectF rect = GetBoardPositions(new Point(i,j));
                canvas.drawRect(rect.left,rect.top,rect.right,rect.bottom,strokePaint);

                }
            }
    }
}
