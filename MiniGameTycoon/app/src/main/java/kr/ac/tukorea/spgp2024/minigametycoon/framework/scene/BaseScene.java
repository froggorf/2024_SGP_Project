package kr.ac.tukorea.spgp2024.minigametycoon.framework.scene;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;

import java.util.ArrayList;


//import kr.ac.tukorea.ge.spgp2023.cookierun.BuildConfig;
import kr.ac.tukorea.spgp2024.minigametycoon.framework.interfaces.IBoxCollidable;
import kr.ac.tukorea.spgp2024.minigametycoon.framework.interfaces.IGameObject;
import kr.ac.tukorea.spgp2024.minigametycoon.framework.interfaces.IRecyclable;
import kr.ac.tukorea.spgp2024.minigametycoon.framework.interfaces.ITouchable;
import kr.ac.tukorea.spgp2024.minigametycoon.framework.view.GameView;
import kr.ac.tukorea.spgp2024.minigametycoon.game.Scene.TownScene;

public abstract class BaseScene {
    private static final String TAG = BaseScene.class.getSimpleName();
    private static ArrayList<BaseScene> stack = new ArrayList<>();
    public static float frameTime;
    protected static Handler handler = new Handler();
    private static Paint bboxPaint;
    protected long previousNanos;

    public static BaseScene getTopScene() {
        int top = stack.size() - 1;
        if (top < 0) return null;
        return stack.get(top);
    }

    public static void popAll() {
        while (!stack.isEmpty()) {
            BaseScene scene = getTopScene();
            scene.popScene();
        }
    }

    public int changeScene() {
        BaseScene scene = getTopScene();
        if (scene != null) {
            scene.onEnd();
        }
        int topIndex = stack.size() - 1;
        stack.set(topIndex, this);
        this.onStart();
        return stack.size();
    }

    public int pushScene() {
        BaseScene scene = getTopScene();
        if (scene != null) {
            scene.onPause();
        }
        stack.add(this);
        this.onStart();
        return stack.size();
    }

    public void popScene() {
        this.onEnd();
        stack.remove(this);
        BaseScene scene = getTopScene();
        if (scene != null) {
            scene.resumeScene();
            return;
        }

        finishActivity();
    }

    public void finishActivity() {
        GameView.view.getActivity().finish();
    }

    public void pauseScene() {
        onPause();
    }

    public void resumeScene() {
        previousNanos = 0;
        onResume();
    }

    public boolean isTransparent() {
        return false;
    }

    protected <E extends Enum<E>> void initLayers(E countEnum) {
        int layerCount = countEnum.ordinal();
        layers = new ArrayList<>();
        for (int i = 0; i < layerCount; i++) {
            layers.add(new ArrayList<>());
        }
    }

    public <E extends Enum<E>> void add(E layerEnum, IGameObject gobj, boolean immediate) {
        if (immediate) {
            add(layerEnum, gobj);
            return;
        }
        handler.post(new Runnable() {
            @Override
            public void run() {
                add(layerEnum, gobj);
            }
        });
    }
    public <E extends Enum<E>> void add(E layerEnum, IGameObject gobj) {
        ArrayList<IGameObject> objects = layers.get(layerEnum.ordinal());
        objects.add(gobj);
    }
    public <E extends Enum> void remove(E layerEnum, IGameObject gobj, boolean immediate) {
        if (immediate) {
            remove(layerEnum, gobj);
            return;
        }
        handler.post(new Runnable() {
            @Override
            public void run() {
                remove(layerEnum, gobj);
            }
        });
    }

    public <E extends Enum> void remove(E layerEnum, IGameObject gobj) {
        boolean removed = getObjectsAt(layerEnum).remove(gobj);
        if (removed && gobj instanceof IRecyclable) {
            RecycleBin.collect((IRecyclable) gobj);
        }
    }

    public int count() {
        int count = 0;
        for (ArrayList<IGameObject> objects: layers) {
            count += objects.size();
        }
        return count;
    }
    public void update(long nanos) {
        long prev = previousNanos;
        previousNanos = nanos;
        if (prev == 0) {
            return;
        }
        long elapsedNanos = nanos - prev;
        frameTime = elapsedNanos / 1_000_000_000f;
        for (ArrayList<IGameObject> objects: layers) {
            for (int i = objects.size() - 1; i >= 0; i--) {
                IGameObject gobj = objects.get(i);
                gobj.update();
            }
        }
    }

    public void draw(Canvas canvas) {
        draw(canvas, stack.size() - 1);
    }
    protected void draw(Canvas canvas, int index) {
        BaseScene scene = stack.get(index);
        if (scene.isTransparent() && index > 0) {
            draw(canvas, index - 1);
        }

        ArrayList<ArrayList<IGameObject>> layers = scene.layers;
        for (ArrayList<IGameObject> objects: layers) {
            for (IGameObject gobj : objects) {
                gobj.draw(canvas);
            }
        }

//        if (BuildConfig.DEBUG) {
//            if (bboxPaint == null) {
//                bboxPaint = new Paint();
//                bboxPaint.setStyle(Paint.Style.STROKE);
//                bboxPaint.setColor(Color.RED);
//            }
//            for (ArrayList<IGameObject> objects: layers) {
//                for (IGameObject gobj : objects) {
//                    if (gobj instanceof IBoxCollidable) {
//                        RectF rect = ((IBoxCollidable) gobj).getCollisionRect();
//                        canvas.drawRect(rect, bboxPaint);
//                    }
//                }
//            }
//        }
    }

    protected ArrayList<ArrayList<IGameObject>> layers = new ArrayList<>();
    public <E extends Enum> ArrayList<IGameObject> getObjectsAt(E layerEnum) {
        return layers.get(layerEnum.ordinal());
    }

    public boolean onTouchEvent(MotionEvent event) {
        int touchLayer = getTouchLayerIndex();
        if (touchLayer < 0) return false;
        ArrayList<IGameObject> gameObjects = layers.get(touchLayer);
        for (IGameObject gobj : gameObjects) {
            if (!(gobj instanceof ITouchable)) {
                continue;
            }
            boolean processed = ((ITouchable) gobj).onTouchEvent(event);
            if (processed) return true;
        }
        return false;
    }

    protected int getTouchLayerIndex() {
        return -1;
    }

    public boolean clipsRect() {
        return true;
    }

    protected void onStart() {
    }


    protected void onEnd() {
    }

    protected void onPause() {
    }
    protected void onResume() {
    }

    public boolean handleBackKey() {
        new TownScene().changeScene();
        return true;
    }

    public void StartGame(){

    }

    // 게임이 종료될 시에 대한 처리를 위한 함수
    // TimerSystem 에서 시간이 다 되면 해당 함수를 호출하여 실행..
    public void FinishGame(){

    }

    public void AddTimerSystem(){

    }
}
