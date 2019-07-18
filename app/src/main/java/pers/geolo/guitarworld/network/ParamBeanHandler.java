package pers.geolo.guitarworld.network;

import java.lang.reflect.Field;
import java.util.HashMap;

/**
 * @author 桀骜(Geolo)
 * @version 1.0
 * @date 2019/7/17
 */
public class ParamBeanHandler {

    public static HashMap<String, Object> handle(Object object) {
        Class<?> cls = object.getClass();
        Field[] fields = cls.getDeclaredFields();
        HashMap<String, Object> queryMap = new HashMap<>();
        for (Field field : fields) {
            try {
                String fieldName = field.getName();
                Object value = field.get(object);
                queryMap.put(fieldName, value);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return queryMap;
    }
}
