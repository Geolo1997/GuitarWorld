package pers.geolo.guitarworld.ui.icon;

import com.joanzapata.iconify.Icon;

/**
 * @author 桀骜(Geolo)
 * @date 2019-06-09
 */
public enum MyIcon implements Icon {

    dynamic('\ue616'),
    find('\ue746'),
    shop('\ue67a'),
    mine('\ue622'),
    friend('\ue666'),
    article('\ue628'),
    tools('\ue606'),
    exit('\ue607');

    private char character;

    MyIcon(char character) {
        this.character = character;
    }

    @Override
    public String key() {
        return name().replace('_', '-');
    }

    @Override
    public char character() {
        return character;
    }
}
