package pers.geolo.guitarworld.util;

import android.view.View;

import java.lang.reflect.Constructor;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

public class ReflactionUtils {

    public Class getInstance(Class subClass) {
        Type genericSuperclass = subClass.getGenericSuperclass();// 返回超类的type
        ParameterizedType types = (ParameterizedType) genericSuperclass;// 如果超类是参数化类型，返回的Type对象必须准确地反映源代码中使用的实际类型参数,
        // 也就是 ParameterizedType 类型
        Type[] actualTypeArguments = types.getActualTypeArguments();//返回表示此类型的实际类型参数的Type
        // 对象的数组。请注意，在某些情况下，返回的数组是空的。如果此类型表示嵌套在参数化类型中的非参数化类型，则会发生这种情况。
        Class<?> reponseClass = (Class) actualTypeArguments[1];

        Constructor[] constructors = reponseClass.getDeclaredConstructors();
        for (Constructor constructor : constructors) {
            System.out.println(constructor);
        }

        Constructor constructor = reponseClass.getDeclaredConstructor(context.getClass(), View.class);
        System.out.println(constructor);
        return constructor.newInstance(context, view);
    }
}

/**
 * 运行时获取泛型类型
 */
class GenericUtils {

    /**
     * 获取继承自父类的泛型的实际类型列表
     *
     * @param object
     * @return
     */
    public static List<Class> getActualGenericsExtended(Object object) {
        List<Class> generics = null;
        // 获取包含泛型参数的直接父类
        Type genericSuperclass = object.getClass().getGenericSuperclass();
        if (genericSuperclass instanceof ParameterizedType) {
            ParameterizedType types = (ParameterizedType) genericSuperclass;
            // 返回此类型的实际类型参数的Type对象的数组
            Type[] actualTypeArguments = types.getActualTypeArguments();
            // 强制转换为Class对象列表
            generics = Arrays.asList((Class[]) actualTypeArguments);
        }
        return generics;
    }

    public static Class getActualGenericExtended(Object object, int genericIndex) {
        List<Class> parameterizedTypeList = getActualGenericsExtended(object);
        if (parameterizedTypeList != null && parameterizedTypeList.size() != 0) {
            return parameterizedTypeList.get(genericIndex);
        } else {
            return null;
        }
    }

    public static List<Class> getActualGenericsImplemented(Object object, int interfaceIndex) {
        List<Class> generics = null;
        // 获取包含泛型参数的第interfaceIndex个接口
        Type interfaceType = object.getClass().getGenericInterfaces()[interfaceIndex];
        if (interfaceType instanceof ParameterizedType) {
            ParameterizedType types = (ParameterizedType) interfaceType;
            Type[] actualTypeArguments = types.getActualTypeArguments();
            generics = Arrays.asList((Class[]) actualTypeArguments);
        }
        return generics;
    }

    public static Class getActualGenericImplemented(Object object, int interfaceIndex, int genericIndex) {
        List<Class> parameterizedTypeList = getActualGenericsImplemented(object, interfaceIndex);
        if (parameterizedTypeList != null && parameterizedTypeList.size() != 0) {
            return parameterizedTypeList.get(genericIndex);
        } else {
            return null;
        }
    }

    public static Object getInstanceOfGenericExtended(Object object, int genericIndex) {
       Class genericClass = getActualGenericExtended(object, genericIndex);
       genericClass.getDeclaredConstructor()
    }

    public static Object newInstance(Class clazz, Object... parameters) throws NoSuchMethodException {
        Class[] parameterTypes = new Class[];
        Constructor constructor = clazz.getDeclaredConstructor(parameterTypes);
        constructor.newInstance()
    }

}