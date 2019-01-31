package pers.geolo.guitarworld.util;

import java.util.HashMap;
import java.util.Map;

/**
 * 单例对象持有者
 */
public class SingletonHolder {


    private volatile static Map<Class, Object> instanceHolder = new HashMap<>();

    /**
     * 单例模式
     *
     * @return
     */
    public static <T> T getInstance(Class<T> clazz) {
        T instance = (T) instanceHolder.get(clazz);
        if (instance == null) {
            synchronized (clazz) {
                if (instance == null) {
                    try {
                        instance = clazz.newInstance();
                        instanceHolder.put(clazz, instance);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InstantiationException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return instance;
    }
}
