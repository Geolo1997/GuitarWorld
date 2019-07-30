package pers.geolo.guitarworld.entity.event;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 桀骜(Geolo)
 * @version 1.0
 * @date 2019/7/28
 */
public class Event {

    private Map<String, Object> param;

    public Event() {
        param = new HashMap<>();
    }

    public Event(String key, Object value) {
        this();
        param.put(key, value);
    }

    public void put(Map<String, Object> param) {
        param.putAll(param);
    }

    public void put(String key, Object value) {
        param.put(key, value);
    }

    public Object get(String key) {
        return param.get(key);
    }

    public enum Const {
        CREATE_DELEGATE_FINISH,
        GET_COMMENT_SUCCESS
    }
}

