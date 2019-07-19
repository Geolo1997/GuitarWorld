package pers.geolo.guitarworld.network;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;

/**
 * @author 桀骜(Geolo)
 * @version 1.0
 * @date 2019/7/17
 */
public class ParamBeanHandler {

    public static HashMap<String, Object> handle(Object object) {
        Class<?> cls = object.getClass();
        Method[] methods = cls.getMethods();
        HashMap<String, Object> queryMap = new HashMap<>();
        for (Method method : methods) {
            String methodName = method.getName();
            // 是get方法
            if (isGetter(method)) {
                String fieldName = getFieldName(methodName);
                Object fieldValue = null;
                try {
                    fieldValue = method.invoke(object);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
                queryMap.put(fieldName, fieldValue);
             }
        }
        return queryMap;
    }

    private static boolean isGetter(Method method) {
        String methodName = method.getName();
        return !Modifier.isStatic(method.getModifiers())
                && method.getParameterCount() == 0
                && (methodName.startsWith("get") || methodName.startsWith("is"))
                && methodName.length() > 3
                && !methodName.equals("getClass");
    }

    private static String getFieldName(String methodName) {
        char[] chars = new char[1];
        if (methodName.startsWith("get")) {
            chars = methodName.substring(3).toCharArray();
        } else if (methodName.startsWith("is")) {
            chars = methodName.substring(2).toCharArray();
        }
        chars[0] += 'a' - 'A';
        return String.valueOf(chars);
    }
}
