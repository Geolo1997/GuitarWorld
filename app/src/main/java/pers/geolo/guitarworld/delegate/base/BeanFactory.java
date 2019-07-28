package pers.geolo.guitarworld.delegate.base;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author 桀骜(Geolo)
 * @version 1.0
 * @date 2019/7/17
 */
public class BeanFactory {

    private static Map<Class<?>, Object> beans = new ConcurrentHashMap<>();

    @SuppressWarnings("unchecked")
    public static <T> T getBean(Class<T> cls) {
        return (T) beans.get(cls);
    }

    public static void registerBean(Object object) {
        beans.put(object.getClass(), object);
    }
}
