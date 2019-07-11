package pers.geolo.guitarworld.model;

import pers.geolo.guitarworld.entity.DataListener;
import pers.geolo.guitarworld.entity.Music;
import pers.geolo.guitarworld.entity.MusicScore;
import pers.geolo.guitarworld.network.HttpClient;
import pers.geolo.guitarworld.network.api.MusicApi;
import pers.geolo.guitarworld.network.callback.BaseCallback;

import java.util.HashMap;
import java.util.List;

public class MusicModel {

    MusicApi musicApi = HttpClient.getService(MusicApi.class);

    public void getMusicScoreList(long musicId, DataListener<List<MusicScore>> listener) {
        musicApi.getMusicScore(musicId).enqueue(new BaseCallback<List<MusicScore>>() {
            @Override
            public void onSuccess(List<MusicScore> musicScores) {
                listener.onReturn(musicScores);
            }

            @Override
            public void onError(int errorCode, String errorMessage) {
                listener.onError(errorMessage);
            }

            @Override
            public void onFailure() {
                listener.onError("网络错误");
            }
        });
    }

    public void getMusic(long musicId, DataListener<Music> listener) {
        HashMap<String, Object> filter = new HashMap<>();
        filter.put("id", musicId);
        musicApi.getMusicList(filter).enqueue(new BaseCallback<List<Music>>() {
            @Override
            public void onSuccess(List<Music> music) {
                listener.onReturn(music.get(0));
            }

            @Override
            public void onError(int errorCode, String errorMessage) {
                listener.onError(errorMessage);
            }

            @Override
            public void onFailure() {
                listener.onError("网络错误");
            }
        });
    }

    public void getMusicList(HashMap<String, Object> filter, DataListener<List<Music>> listener) {
        musicApi.getMusicList(filter).enqueue(new BaseCallback<List<Music>>() {
            @Override
            public void onSuccess(List<Music> music) {
                listener.onReturn(music);
            }

            @Override
            public void onError(int errorCode, String errorMessage) {
                listener.onError(errorMessage);
            }

            @Override
            public void onFailure() {
                listener.onError("网络错误");
            }
        });
    }
}
