package kr.ac.tukorea.spgp2024.minigametycoon.game.Object.FieldGame;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.transition.Scene;
import android.util.Log;
import android.view.MotionEvent;

import androidx.constraintlayout.widget.ConstraintSet;

import java.util.Vector;

import kr.ac.tukorea.spgp2024.R;
import kr.ac.tukorea.spgp2024.minigametycoon.framework.interfaces.IGameObject;
import kr.ac.tukorea.spgp2024.minigametycoon.framework.objects.Sprite;
import kr.ac.tukorea.spgp2024.minigametycoon.framework.scene.BaseScene;
import kr.ac.tukorea.spgp2024.minigametycoon.game.Scene.FieldGameScene;
import kr.ac.tukorea.spgp2024.minigametycoon.game.UserDisplay;


public class FieldBoard implements IGameObject {
    // DEFINE
    private final int X = 0;
    private final int Y = 1;
    private static final String TAG = FieldBoard.class.getSimpleName();
    Point boardCount = new Point();     // 가로 세로 배열 크기

    FoodBlock[][]  foodBlocks;          // 보드판 내 배열
    RectF boardRect;                    // 보드판 크기
    Paint fillPaint;
    Paint borderPaint;
    Paint strokePaint;

    Point CurrentPickBlock = new Point();   // 현재 픽된 블럭의 인덱스에 대한 변수

    Vector<int[]> UpdateBlocks = new Vector<int[]>(0);

    public FieldBoard(Point getBoardCount, RectF getBoardRect) {
        // 보드판 크기 설정
        boardCount.set(getBoardCount.x,getBoardCount.y);

        // 보드판 영역 설정
        boardRect = getBoardRect;

        // 보드판 만들기
        foodBlocks = new FoodBlock[boardCount.x][boardCount.y];

        for(int i = 0; i<boardCount.x; ++i){
            for(int j = 0; j<boardCount.y;++j){
                foodBlocks[i][j] = new FoodBlock(GetBoardPositions(new Point(i,j)));
            }
        }


        { // Paint 생성
            fillPaint = new Paint();
            fillPaint.setStyle(Paint.Style.FILL);
            fillPaint.setColor(Color.rgb(155,155,155));

            strokePaint = new Paint();
            strokePaint.setStyle(Paint.Style.STROKE);
            strokePaint.setColor(Color.BLACK);
            strokePaint.setStrokeWidth(5.0f);
        }

        GameInitialize();
    }

    private void GameInitialize() {
        CurrentPickBlock.set(-1,-1);
    }

    // 배열 인덱스로부터 좌표 얻는 함수
    RectF GetBoardPositions(Point currentBoardCount) {
        // data - left / top / right / bottom
        RectF rect = new RectF();
        rect.left= boardRect.width() / boardCount.x * currentBoardCount.x + boardRect.left;
        rect.top= boardRect.height() / boardCount.y * currentBoardCount.y + boardRect.top;
        rect.right = boardRect.width() / boardCount.x * (currentBoardCount.x+1) + boardRect.left;
        rect.bottom = boardRect.height() / boardCount.y * (currentBoardCount.y+1) + boardRect.top;
        return rect;
    }

    // 좌표로부터 배열 인덱스를 얻는 함수
    Point GetIndexByPosition(float mousePoint[]){
        Point point = new Point();
        int x = (int) ((mousePoint[X]- boardRect.left) / (boardRect.width()/ boardCount.x));
        int y = (int) ((mousePoint[Y]- boardRect.top) / (boardRect.height()/ boardCount.y));
        point.set(x,y);

        return point;
    }


    @Override
    public void update() {
        // 먼저 불필요한 데이터 지우고
        for(int i = 0; i<UpdateBlocks.size(); ++i){
            int[] data = UpdateBlocks.get(i);
            if(data[X] == -1 && data[Y] == -1){
                UpdateBlocks.remove(i);
                --i;
            }

        }

        for(int[] block : UpdateBlocks){
            foodBlocks[block[X]][block[Y]].Update(FieldGameScene.frameTime);
        }
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawRect(boardRect.left,boardRect.top,boardRect.right,boardRect.bottom,fillPaint);

        for(int i = 0; i<boardCount.x; ++i){
            for(int j=0; j<boardCount.y; ++j){
                // 보드판 틀 그리기
                RectF rect = GetBoardPositions(new Point(i,j));
                canvas.drawRect(rect.left,rect.top,rect.right,rect.bottom,strokePaint);

                // Food Block 그리기
                foodBlocks[i][j].Draw(canvas);
                }
            }
    }


    public void onClickEvent(MotionEvent event){
        float[] mousePoint = new float[2];
        mousePoint[X] = event.getX();
        mousePoint[Y] = event.getY();

        Point index = GetIndexByPosition(mousePoint);

        // 선택된 블럭이 움직이고 있는 중인 보드면 예외처리한다.
        if (foodBlocks[index.x][index.y].bIsMovingBlock) return;

        // 현재 선택된 것이 없으면 픽된 블럭으로 바꾸고 종료
        if(CurrentPickBlock.x == -1){
            SetPickBlock(index);
            return;
        }
        
        
        // 근접 보드를 클릭하면 두 블럭 서로 교체
        if(IsNearBoardWithPick(index)) {
            Point firstBlockIndex = new Point(CurrentPickBlock);
            Point secondBlockIndex = new Point(index);
            CurrentPickBlock.set(-1, -1);

            foodBlocks[firstBlockIndex.x][firstBlockIndex.y].SetPickBitmap(false);

            SwapFoodBlock(firstBlockIndex,secondBlockIndex);
        }
        
        
    }

    // 두 지정된 블럭의 위치를 서로 옮기는 함수
    private void SwapFoodBlock(Point firstBlockIndex, Point secondBlockIndex) {
        // 보드 블럭 변경하기
        FoodBlock tempBlock = foodBlocks[firstBlockIndex.x][firstBlockIndex.y];
        foodBlocks[firstBlockIndex.x][firstBlockIndex.y] = foodBlocks[secondBlockIndex.x][secondBlockIndex.y];
        foodBlocks[secondBlockIndex.x][secondBlockIndex.y] =tempBlock;

        // 아래 내용 함수화 시키기
        // 첫번째 인덱스 블럭 두번쨰 인덱스 위치로 옮기기
        RectF rect = GetBoardPositions(firstBlockIndex);
        foodBlocks[firstBlockIndex.x][firstBlockIndex.y].SetSpriteDrawPositionWithTime(
                rect.centerX(),rect.centerY(), 0.3f
        );
        int[] firstData =  new int[2];
        firstData[X] = firstBlockIndex.x; firstData[Y] = firstBlockIndex.y;
        UpdateBlocks.add(firstData);


        // 두번째 인덱스 블럭 첫번쨰 인덱스 위치로 옮기기
        rect = GetBoardPositions(secondBlockIndex);
        foodBlocks[secondBlockIndex.x][secondBlockIndex.y].SetSpriteDrawPositionWithTime(
                rect.centerX(),rect.centerY(),0.3f
        );
        int[] SecondData =  new int[2];
        SecondData[X] = secondBlockIndex.x; SecondData[Y] = secondBlockIndex.y;
        UpdateBlocks.add(SecondData);
    }

    private boolean IsNearBoardWithPick(Point index) {
        int xAxis = Math.abs(CurrentPickBlock.x- index.x);
        int yAxis = Math.abs(CurrentPickBlock.y- index.y);

        if(xAxis == 1 && yAxis == 0) return true;
        if(xAxis == 0 && yAxis == 1) return true;

        return false;
    }

    // 블록 선택하기
    private void SetPickBlock(Point index) {
        CurrentPickBlock.set(index.x,index.y);
        foodBlocks[index.x][index.y].SetPickBitmap(true);
    }
}
