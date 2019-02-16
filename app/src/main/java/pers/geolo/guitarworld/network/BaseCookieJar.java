package pers.geolo.guitarworld.network;

import android.util.Log;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BaseCookieJar implements CookieJar {

    private final String TAG = getClass().getSimpleName();

    private final Map<String, List<Cookie>> COOKIE_HOLDER;

    public BaseCookieJar() {
        COOKIE_HOLDER = new HashMap<>();
    }

    @Override
    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
        COOKIE_HOLDER.put(url.host(), cookies);
    }

    @Override
    public List<Cookie> loadForRequest(HttpUrl url) {
        List<Cookie> cookies = COOKIE_HOLDER.get(url.host());
        return cookies != null ? cookies : new ArrayList<Cookie>();
    }

    private String toString(List<Cookie> cookies) {
        StringBuilder cookiesStr = new StringBuilder();
        for (int i = 0; i < cookies.size(); i++) {
            Cookie cookie = cookies.get(i);
            cookiesStr.append(cookie.name() + ": " + cookie.value());
        }
        return cookiesStr.toString();
    }
}
