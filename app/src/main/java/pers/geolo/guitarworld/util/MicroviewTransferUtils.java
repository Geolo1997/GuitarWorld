package pers.geolo.guitarworld.util;

import android.os.Bundle;
import android.view.ViewGroup;
import me.yokeyword.fragmentation.ISupportFragment;
import me.yokeyword.fragmentation.SupportFragment;
import pers.geolo.guitarworld.delegate.base.BaseDelegate;
import pers.geolo.guitarworld.microview.ViewController;
import pers.geolo.guitarworld.microview.ViewManager;
import pers.geolo.guitarworld.microview.ViewParam;

import java.util.Iterator;
import java.util.Set;

/**
 * @author 桀骜(Geolo)
 * @version 1.0
 * @date 2019/9/6
 */
public class MicroviewTransferUtils {

    public static void start(BaseDelegate toFragment) {
//        if (toFragment instanceof ViewController) {
            Bundle arguments = ((BaseDelegate) toFragment).getArguments();
            ViewParam param = toViewParam(arguments);
            ViewManager.start((ViewController) toFragment, param);
//        }
    }

    public static void load(ViewGroup container, BaseDelegate toFragment) {
//        if (toFragment instanceof ViewController) {
            Bundle arguments = ((BaseDelegate) toFragment).getArguments();
            ViewParam param = toViewParam(arguments);
            ViewManager.load(container, (ViewController) toFragment, param);
//        }
    }

    private static ViewParam toViewParam(Bundle arguments) {
        ViewParam param = new ViewParam();
        if (arguments != null) {
            Set<String> keySet = arguments.keySet();
            Iterator<String> iterator = keySet.iterator();
            while (iterator.hasNext()) {
                String key = iterator.next();
                param.set(key, arguments.get(key));
            }
        }
        return param;
    }
}
