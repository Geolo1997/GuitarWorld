package pers.geolo.guitarworldserver.crawler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import pers.geolo.guitarworldserver.entity.Music;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 桀骜(Geolo)
 * @version 1.0
 * @date 2019/9/18
 */
public class QQMusicCrawler {

    private static final String hotMusicListUrl = "https://u.y.qq.com/cgi-bin/musicu.fcg?-=" +
            "getUCGI4548984138612817&g_tk=5381&loginUin=0&hostUin=0&format=json&inCharset=utf8" +
            "&outCharset=utf-8&notice=0&platform=yqq.json&needNewCode=0" +
            "&data=%7B%22detail%22%3A%7B%22module%22%3A%22musicToplist.ToplistInfoServer%22%2C%22" +
            "method%22%3A%22GetDetail%22%2C%22param%22%3A%7B%22topId%22%3A4%2C%22offset%22%3A0%2C%22" +
            "num%22%3A20%2C%22period%22%3A%222019-09-18%22%7D%7D%2C%22comm%22%3A%7B%22ct%22%3A24%2C%" +
            "22cv%22%3A0%7D%7D";

    private static final String albumImageUrlTemplate = "https://y.gtimg.cn/music/photo_new/T002R90x90M000%s.jpg?max_age=2592000";

    private static final HttpClient httpClient = HttpClientBuilder.create().build();

    public static List<Music> getHotMusicList() throws IOException {
        HttpGet httpGet = new HttpGet(hotMusicListUrl);
        HttpResponse response = httpClient.execute(httpGet);
        String jsonString = EntityUtils.toString(response.getEntity());
        JSONObject jsonObject = (JSONObject) JSON.parse(jsonString);
        JSONArray songInfoList = jsonObject.getJSONObject("detail").getJSONObject("data")
                .getJSONObject("data").getJSONArray("song");

        List<Music> musicList = new ArrayList<>();
        for (int i = 0; i < songInfoList.size(); i++) {
            JSONObject jsonItem = songInfoList.getJSONObject(i);
            int id = jsonItem.getInteger("songId");
            String name = jsonItem.getString("title");
            String author = jsonItem.getString("singerName");
            String albumMid = jsonItem.getString("albumMid");
            String imageUrl = String.format(albumImageUrlTemplate, albumMid);

            Music music = new Music();
            music.setId(id);
            music.setName(name);
            music.setAuthor(author);
            music.setImageUrl(imageUrl);
            musicList.add(music);
        }
        return musicList;
    }
}
