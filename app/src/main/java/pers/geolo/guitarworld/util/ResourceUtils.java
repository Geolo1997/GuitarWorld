package pers.geolo.guitarworld.util;

import pers.geolo.guitarworld.R;

import java.lang.reflect.Field;

public class ResourceUtils {

    public static final Class idClass = R.id.class;
    public static final Class layoutClass = R.layout.class;

    public static int getIdValue(String idName) {
        Field field = null;
        int value = -1;
        try {
            field = idClass.getDeclaredField(idName);
            value = field.getInt(idClass);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return value;
    }
}
