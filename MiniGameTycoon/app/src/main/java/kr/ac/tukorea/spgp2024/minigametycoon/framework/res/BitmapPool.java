package kr.ac.tukorea.spgp2024.minigametycoon.framework.res;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.util.HashMap;

import kr.ac.tukorea.spgp2024.minigametycoon.framework.view.GameView;

public class BitmapPool {
    private static final String TAG = BitmapPool.class.getSimpleName();
    private static HashMap<Integer, Bitmap> bitmaps = new HashMap<>();
    private static BitmapFactory.Options opts;

    public static Bitmap get(int mipmapResId) {
        Bitmap bitmap = bitmaps.get(mipmapResId);
        if (bitmap == null) {
            Resources res = GameView.res;
            if (opts == null) {
                opts = new BitmapFactory.Options();
                opts.inScaled = false;
            }
            bitmap = BitmapFactory.decodeResource(res, mipmapResId, opts);

            bitmaps.put(mipmapResId, bitmap);
        }
        return bitmap;
    }
}
