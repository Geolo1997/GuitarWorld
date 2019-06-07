package pers.geolo.guitarworld.util;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import java.net.URL;

/**
 * @author 桀骜(Geolo)
 * @date 2019-06-01
 */
public class ImageUtils {

    public static void getBitmap(Activity activity, String url, Callback callback) {
        new Thread(() -> {
            try {
                URL url1 = new URL(url);
                Bitmap bitmap = BitmapFactory.decodeStream(url1.openStream());
                activity.runOnUiThread(() -> {
                    callback.onSuccess(bitmap);
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
//            try {
//                RequestOptions myOptions = new RequestOptions()
//                        .skipMemoryCache(true)//默认为false
//                        .diskCacheStrategy(DiskCacheStrategy.NONE)
//                        .placeholder(R.drawable.place_holder);
//                Bitmap bitmap = Glide.with(context)
//                        .asBitmap()
//                        .apply(myOptions)
//                        .load(url)
//                        .into(100, 100)
//                        .get();
//                context.runOnUiThread(() -> {
//                    callback.success(bitmap);
//                });
//            } catch (ExecutionException e) {
//                e.printStackTrace();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
        }).start();
    }

    public interface Callback {
        void onSuccess(Bitmap bitmap);
    }
}
