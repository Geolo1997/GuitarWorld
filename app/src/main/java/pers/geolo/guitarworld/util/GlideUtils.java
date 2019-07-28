package pers.geolo.guitarworld.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.FutureTarget;
import pers.geolo.guitarworld.entity.DataListener;

import java.io.File;
import java.util.concurrent.ExecutionException;

/**
 * @author 桀骜(Geolo)
 * @version 1.0
 * @date 2019/7/16
 */
public class GlideUtils {

    public static void load(Context context, String url, ImageView imageView) {
        Glide.with(context).load(url).into(imageView);
    }

    public static void load(Context context, File file, ImageView imageView) {
        Glide.with(context).load(file).into(imageView);
    }

    public static void getBitmap(Context context, String url, DataListener<Bitmap> listener) {
        FutureTarget<Bitmap> bitmapFutureTarget = Glide.with(context).asBitmap().load(url).submit();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Bitmap bitmap = bitmapFutureTarget.get();
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            listener.onReturn(bitmap);
                        }
                    });
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
