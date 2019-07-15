package pers.geolo.guitarworld.model;

import pers.geolo.guitarworld.entity.DataListener;
import pers.geolo.guitarworld.entity.Music;
import pers.geolo.guitarworld.entity.MusicScore;
import pers.geolo.guitarworld.network.HttpClient;
import pers.geolo.guitarworld.network.api.MusicApi;
import pers.geolo.guitarworld.network.callback.BaseCallback;
import pers.geolo.guitarworld.network.queryparam.MusicScoreImageParam;
import pers.geolo.guitarworld.network.queryparam.MusicScoreParam;

import java.util.HashMap;
import java.util.List;

public class MusicModel {

    MusicApi musicApi = HttpClient.getService(MusicApi.class);

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


    public void getMusicScoreList(long musicId, DataListener<List<MusicScore>> listener) {
        MusicScoreParam param = new MusicScoreParam();
        param.setMusicId(musicId);
        musicApi.getMusicScore(param).enqueue(new BaseCallback<List<MusicScore>>() {
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

    public void getMusicScoreImage(long musicScoreId, DataListener<List<String>> listener) {
        MusicScoreImageParam param = new MusicScoreImageParam();
        param.setMusicScoreId(musicScoreId);
        musicApi.getMusicScoreImages(param).enqueue(new BaseCallback<List<String>>() {
            @Override
            public void onSuccess(List<String> strings) {
                listener.onReturn(strings);
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
