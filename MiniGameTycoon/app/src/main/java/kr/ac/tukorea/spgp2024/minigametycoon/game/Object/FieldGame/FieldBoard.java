package kr.ac.tukorea.spgp2024.minigametycoon.game.Object.FieldGame;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;

import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

import kr.ac.tukorea.spgp2024.minigametycoon.framework.interfaces.IGameObject;
import kr.ac.tukorea.spgp2024.minigametycoon.game.Scene.FieldGameScene;
import kr.ac.tukorea.spgp2024.minigametycoon.game.Scene.TownScene;


public class FieldBoard implements IGameObject {
    // DEFINE
    private final int X = 0;
    private final int Y = 1;
    private static final String TAG = FieldBoard.class.getSimpleName(); //TAG
    private static final float BLOCK_MOVE_TIME = 0.5f;  // 보드 내 블럭 이동 시 시간
    private static final int MATCH_COUNT = 3;   //블럭 몇개 이상 맞춰야 꺠지는지 (가로/세로 방향)
    Point boardCount = new Point();     // 가로 세로 배열 크기

    FoodBlock[][]  foodBlocks;          // 보드판 내 배열
    RectF boardRect;                    // 보드판 크기
    Paint fillPaint;
    Paint borderPaint;
    Paint strokePaint;
    Paint textPaint;

    Point CurrentPickBlock = new Point();   // 현재 픽된 블럭의 인덱스에 대한 변수

    Vector<int[]> UpdateBlocks = new Vector<int[]>(0);

    boolean bHasEmptyBlock = false;         // EMPTY food Block 처리에 대한 flag

    public int[] score = new int[FoodTypeEnum.SIZE.ordinal()];

    public boolean bTickEnabled = true;
    public FieldBoard(Point getBoardCount, RectF getBoardRect) {
        bTickEnabled =true;
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

            textPaint = new Paint();
            textPaint.setStyle(Paint.Style.STROKE);
            textPaint.setTextAlign(Paint.Align.CENTER);
            textPaint.setStrokeWidth(10.0f);
            textPaint.setColor(Color.BLACK);
            textPaint.setTextSize(100.0f);
        }

        GameInitialize();

        // 시작 후 부술 수 있는 애들은 부수기
        new Handler().postDelayed(new Runnable(){
            public void run(){
                CheckAllBlocksCanBreak();
            }
        }, 2000);
    }

    private void GameInitialize() {
        CurrentPickBlock.set(-1,-1);
    }

    // 배열 인덱스로부터 좌표 얻는 함수
    public RectF GetBoardPositions(Point currentBoardCount) {
        // data - left / top / right / bottom
        RectF rect = new RectF();
        rect.left= boardRect.width() / boardCount.x * currentBoardCount.x + boardRect.left;
        rect.top= boardRect.height() / boardCount.y * currentBoardCount.y + boardRect.top;
        rect.right = boardRect.width() / boardCount.x * (currentBoardCount.x+1) + boardRect.left;
        rect.bottom = boardRect.height() / boardCount.y * (currentBoardCount.y+1) + boardRect.top;
        return rect;
    }

    // 좌표로부터 배열 인덱스를 얻는 함수
    public Point GetIndexByPosition(float mousePoint[]){
        Point point = new Point();
        int x = (int) ((mousePoint[X]- boardRect.left) / (boardRect.width()/ boardCount.x));
        int y = (int) ((mousePoint[Y]- boardRect.top) / (boardRect.height()/ boardCount.y));
        point.set(x,y);

        return point;
    }


    @Override
    public void update() {
        if(!bTickEnabled) return;
        // 먼저 불필요한 데이터 지우고
        for(int i = 0; i<UpdateBlocks.size(); ++i){
            int[] data = UpdateBlocks.get(i);
            if(data[X] == -1 && data[Y] == -1){
                UpdateBlocks.remove(i);
                --i;
            }

        }

        // 움직임이 있어야 할 블럭들에 대해 움직임 진행
        for(int[] block : UpdateBlocks){
            foodBlocks[block[X]][block[Y]].Update(FieldGameScene.frameTime);
        }

        // 빈 칸에 대한 플래그 처리
        if(bHasEmptyBlock){
            Log.d(TAG, "update: 공백 처리");
            boolean IsDownAtLeastOne = false;
            Set<Point> emptyFoodBlockSet = new HashSet<>();
            // x축 왼쪽부터
            for(int i =0; i< boardCount.x; ++i){
                // y 축 아래에서부터 위로 쭉 관찰하면서
                for(int j = boardCount.y -1; j >= 0; --j){
                    // EMPTY 칸이라면,
                    if(foodBlocks[i][j].FoodType == FoodTypeEnum.BLANK){
                        if(j==0){   // 맨 윗칸 이라면
                            emptyFoodBlockSet.add(new Point(i,j));
                            continue;
                        }
                        boolean find = false;
                        for(int currentY = j - 1; currentY>= 0; --currentY){
                            // EMPTY 아닌 블럭이랑 교체하고,
                            if(foodBlocks[i][currentY].FoodType != FoodTypeEnum.BLANK){
                                FoodBlockIndexChange(new Point(i,j), new Point(i,currentY));
                                MoveFoodBlockToIndex(new Point(i,j), new Point(i,j));
                                IsDownAtLeastOne = true;
                                find = true;
                                break;
                            }

                            // 위가 전부다 빈 칸 이라면 공백 음식 블럭 셋에 추가해준다.
                            if(!find){
                                emptyFoodBlockSet.add(new Point(i,j));
                            }
                        }
                    }

                }
            }
            bHasEmptyBlock = false;

            // 새로운 블럭 생성
            {
                // emptyFoodBlockSet 안에는 y가
                for(Point index : emptyFoodBlockSet){
                    RectF newBlockPosition = GetBoardPositions(index);
                    newBlockPosition.offset(0, -boardRect.top);
                    foodBlocks[index.x][index.y].Initialize(newBlockPosition);
                    MoveFoodBlockToIndex(new Point(index),new Point(index));
                }
            }

            // 연쇄폭발을 위한 함수
            if(IsDownAtLeastOne){
                new Handler().postDelayed(new Runnable(){
                    public void run(){
                        CheckAllBlocksCanBreak();
                    }
                }, (int)(BLOCK_MOVE_TIME*1000*2));
            }
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

        for(int i=1; i<FoodTypeEnum.SIZE.ordinal(); ++i){
            canvas.drawText(String.format("%d",score[i]),boardRect.left+200*(i-1)+85,boardRect.bottom + 100,textPaint);
        }
    }


    public void onClickEvent(MotionEvent event){
        float[] mousePoint = new float[2];
        mousePoint[X] = event.getX();
        mousePoint[Y] = event.getY();

        Point index = GetIndexByPosition(mousePoint);
        // 선택된 블럭이 공백칸이라면 예외처리한다.
        if(foodBlocks[index.x][index.y].FoodType == FoodTypeEnum.BLANK) return;
        // 선택된 블럭이 움직이고 있는 중인 보드면 예외처리한다.
        if (foodBlocks[index.x][index.y].bIsMovingBlock) return;

        // 픽된 블럭을 선택했다면 픽 제거하기
        if(CurrentPickBlock.equals(index.x,index.y)){
            foodBlocks[index.x][index.y].SetPickBitmap(false);
            CurrentPickBlock.set(-1,-1);
            return;
        }
        
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
    private void FoodBlockIndexChange(Point firstBlockIndex, Point secondBlockIndex){
        FoodBlock tempBlock = foodBlocks[firstBlockIndex.x][firstBlockIndex.y];
        foodBlocks[firstBlockIndex.x][firstBlockIndex.y] = foodBlocks[secondBlockIndex.x][secondBlockIndex.y];
        foodBlocks[secondBlockIndex.x][secondBlockIndex.y] =tempBlock;
    }

    // 두 지정된 블럭의 위치를 서로 옮기는 함수
    private void SwapFoodBlock(Point firstBlockIndex, Point secondBlockIndex) {
        // 첫번째 인덱스 블럭 두번쨰 인덱스 위치로 옮기기
        MoveFoodBlockToIndex(firstBlockIndex,secondBlockIndex);
        // 두번째 인덱스 블럭 첫번쨰 인덱스 위치로 옮기기
        MoveFoodBlockToIndex(secondBlockIndex,firstBlockIndex);

        // 보드 블럭 변경하고서
        FoodBlockIndexChange(firstBlockIndex,secondBlockIndex);

        // 부술 수 있는지 체크하고,
        Set<Point> BreakBlocksVector = new HashSet<>();
        FoodTypeEnum firstFoodType = foodBlocks[firstBlockIndex.x][firstBlockIndex.y].FoodType;
        FindBreakBlocks(firstBlockIndex,firstFoodType, BreakBlocksVector);
        FoodTypeEnum secondFoodType = foodBlocks[secondBlockIndex.x][secondBlockIndex.y].FoodType;
        FindBreakBlocks(secondBlockIndex,secondFoodType, BreakBlocksVector);

        if(!BreakBlocksVector.isEmpty())    // 부술 수 있다면
        {
            new Handler().postDelayed(new Runnable(){
                public void run(){

                    bHasEmptyBlock = true;
                    for(Point breakIndex : BreakBlocksVector){
                        score[foodBlocks[breakIndex.x][breakIndex.y].FoodType.ordinal()] += 1;
                        foodBlocks[breakIndex.x][breakIndex.y].ChangeFoodType(FoodTypeEnum.BLANK);
                    }
                }
            }, (int)(BLOCK_MOVE_TIME*1500));
        }
        else        //못 부순 다면 다시 돌리기
        {
            new Handler().postDelayed(new Runnable(){
                public void run(){
                    // 첫번째 인덱스 블럭 두번쨰 인덱스 위치로 옮기기
                    MoveFoodBlockToIndex(firstBlockIndex,secondBlockIndex);
                    // 두번째 인덱스 블럭 첫번쨰 인덱스 위치로 옮기기
                    MoveFoodBlockToIndex(secondBlockIndex,firstBlockIndex);

                    FoodBlock tempBlock = foodBlocks[firstBlockIndex.x][firstBlockIndex.y];
                    foodBlocks[firstBlockIndex.x][firstBlockIndex.y] = foodBlocks[secondBlockIndex.x][secondBlockIndex.y];
                    foodBlocks[secondBlockIndex.x][secondBlockIndex.y] =tempBlock;
                }
            }, (int)(BLOCK_MOVE_TIME*1500));


        }



    }

    private void FindBreakBlocks(Point blockIndex, FoodTypeEnum foodType, Set<Point> BreakBlocksVector) {
        // 좌우 방향으로 체크
        Set<Point> HorizontalVector = new HashSet<>();
        FindSameFoodTypeBlocks(blockIndex, foodType, new Point(1,0),HorizontalVector);      //우측
        FindSameFoodTypeBlocks(blockIndex, foodType, new Point(-1,0),HorizontalVector);      //좌측

        // 위아래 방향으로 체크
        Set<Point> VerticalVector = new HashSet<>();
        FindSameFoodTypeBlocks(blockIndex, foodType, new Point(0,1),VerticalVector);      //우측
        FindSameFoodTypeBlocks(blockIndex, foodType, new Point(0,-1),VerticalVector);      //좌측

        // BreakBlocksVector에 저장하기
        if(HorizontalVector.size() >= MATCH_COUNT){
            BreakBlocksVector.addAll(HorizontalVector);
        }
        if(VerticalVector.size() >= MATCH_COUNT){
            BreakBlocksVector.addAll(VerticalVector);
        }
    }

    private void FindSameFoodTypeBlocks(Point blockIndex, FoodTypeEnum foodType, Point offset, Set<Point> SameFoodBlockVector) {
        // blockIndex - 현재 블럭에 대한 인덱스 // foodType - 부수는 종류의 foodType // offset - 인덱스 증가되는 방향 // SameFoodBlockVector - 해당 방향에 대한 동일한 FoodType 가진 인덱스에 대한 벡터
        // 인덱스 범위 초과일 경우 재귀함수 중지
        if (offset.x != 0 && (blockIndex.x < 0 || blockIndex.x >= boardCount.x)) return;
        if (offset.y != 0 && (blockIndex.y < 0 || blockIndex.y >= boardCount.y)) return;

        // 현재 블럭이 동일한 블럭이 아니라면 반환
        if(foodBlocks[blockIndex.x][blockIndex.y].FoodType != foodType) return;
        // 해당 블럭 추가
        SameFoodBlockVector.add(blockIndex);

        // 해당 방향으로 계속 탐색
        FindSameFoodTypeBlocks(new Point(blockIndex.x + offset.x, blockIndex.y + offset.y),foodType,offset,SameFoodBlockVector);
    }

    private void MoveFoodBlockToIndex(Point TargetBlockIndex, Point ToBlockIndex) {
        RectF rect = GetBoardPositions(ToBlockIndex);
        foodBlocks[TargetBlockIndex.x][TargetBlockIndex.y].SetSpriteDrawPositionWithTime(
                rect.centerX(),rect.centerY(), BLOCK_MOVE_TIME
        );
        int[] firstData =  new int[2];
        firstData[X] = TargetBlockIndex.x;
        firstData[Y] = TargetBlockIndex.y;
        UpdateBlocks.add(firstData);
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

    private void CheckAllBlocksCanBreak(){
        for(int i =0; i< boardCount.x; ++i){
            for(int j=0; j< boardCount.y; ++j){
                if(foodBlocks[i][j].FoodType == FoodTypeEnum.BLANK) continue;

                Set<Point> BreakBlocksVector = new HashSet<>();
                FindBreakBlocks(new Point(i,j), foodBlocks[i][j].FoodType, BreakBlocksVector);

                if(!BreakBlocksVector.isEmpty()) bHasEmptyBlock = true;

                for(Point breakIndex : BreakBlocksVector){
                    score[foodBlocks[breakIndex.x][breakIndex.y].FoodType.ordinal()] += 1;
                    foodBlocks[breakIndex.x][breakIndex.y].ChangeFoodType(FoodTypeEnum.BLANK);
                }

            }
        }
    }


}
