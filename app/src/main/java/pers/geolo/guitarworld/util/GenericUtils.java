package pers.geolo.guitarworld.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * 运行时获取泛型类型
 */
public class GenericUtils {

    /**
     * 获取继承自父类的泛型的实际类型列表
     *
     * @param object
     * @return
     */
    public static Class getActualGenericExtended(Object object, int genericIndex) {
        // 获取包含泛型参数的直接父类
        Type genericSuperclass = object.getClass().getGenericSuperclass();
        if (genericSuperclass instanceof ParameterizedType) {
            ParameterizedType types = (ParameterizedType) genericSuperclass;
            // 返回此类型的实际类型参数的Type对象的数组
            Type[] actualTypeArguments = types.getActualTypeArguments();
            // 强制转换为Class对象
            return (Class) actualTypeArguments[genericIndex];
        } else {
            return null;
        }
    }

    public static Class getActualGenericImplemented(Object object, int interfaceIndex, int genericIndex) {
        // 获取包含泛型参数的第interfaceIndex个接口
        Type interfaceType = object.getClass().getGenericInterfaces()[interfaceIndex];
        if (interfaceType instanceof ParameterizedType) {
            ParameterizedType types = (ParameterizedType) interfaceType;
            Type[] actualTypeArguments = types.getActualTypeArguments();
            return (Class) actualTypeArguments[genericIndex];
        }
        return null;
    }

    public static <T> T newInstance(Class<T> clazz, Class[] parameterTypes, Object[] parameters) throws NoSuchMethodException,
            IllegalAccessException, InvocationTargetException, InstantiationException {
        Constructor<T> constructor = clazz.getDeclaredConstructor(parameterTypes);
        return constructor.newInstance(parameters);
    }

}