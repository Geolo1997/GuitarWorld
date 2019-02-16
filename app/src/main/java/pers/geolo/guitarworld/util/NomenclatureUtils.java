package pers.geolo.guitarworld.util;

/**
 * 命名法转换工具类
 *
 * @author Geolo
 * @version 1.0
 */
public class NomenclatureUtils {

    /**
     * 驼峰命名法转下划线命名法
     *
     * @param underScoreName 驼峰命名法变量名
     * @return
     */
    public static String toUnderScore(String underScoreName) {
        StringBuilder camelCaseName = new StringBuilder();
        for (int i = 0; i < underScoreName.length(); i++) {
            char c = underScoreName.charAt(i);
            if (Character.isUpperCase(c)) {
                camelCaseName.append("_" + Character.toLowerCase(c));
            } else {
                camelCaseName.append(c);
            }
        }
        return camelCaseName.toString();
    }

    /**
     * 下划线命名法转驼峰命名法
     *
     * @param camelCaseName 下划线命名法变量名
     * @return
     */
    public static String toCamelCase(String camelCaseName) {
        StringBuilder underScoreName = new StringBuilder();
        for (int i = 0; i < camelCaseName.length(); i++) {
            char c = camelCaseName.charAt(i);
            if (c == '_') {
                i++;
                c = camelCaseName.charAt(i);
                underScoreName.append(Character.toUpperCase(c));
            } else {
                underScoreName.append(c);
            }
        }
        return underScoreName.toString();
    }
}
