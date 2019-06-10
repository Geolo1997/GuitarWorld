package pers.geolo.guitarworld.ui.icon;

import com.joanzapata.iconify.Icon;
import com.joanzapata.iconify.IconFontDescriptor;

/**
 * @author 桀骜(Geolo)
 * @date 2019-06-09
 */
public class FontModule implements IconFontDescriptor {
    @Override
    public String ttfFileName() {
        return "iconfont.ttf";
    }

    @Override
    public Icon[] characters() {
        return MyIcon.values();
    }
}
