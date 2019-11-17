package pers.geolo.guitarworld.microview;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 桀骜(Geolo)
 * @version 1.0
 * @date 2019/9/2
 */
public class ViewParam implements Serializable {

    private Map<String, Object> parameters = new HashMap<>();

    public void set(String key, Object value) {
        parameters.put(key, value);
    }

    public Object get(String key) {
        return parameters.get(key);
    }
}
