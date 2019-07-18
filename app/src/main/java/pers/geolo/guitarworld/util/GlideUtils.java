package pers.geolo.guitarworld.util;

import android.content.Context;
import android.widget.ImageView;
import com.bumptech.glide.Glide;

/**
 * @author 桀骜(Geolo)
 * @version 1.0
 * @date 2019/7/16
 */
public class GlideUtils {

    public static void load(Context context, String url, ImageView imageView) {
        Glide.with(context).load(url).into(imageView);
    }
}
