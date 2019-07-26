package pers.geolo.guitarworld.model;

import pers.geolo.guitarworld.entity.DataListener;
import pers.geolo.guitarworld.entity.Music;
import pers.geolo.guitarworld.entity.MusicScore;
import pers.geolo.guitarworld.network.HttpClient;
import pers.geolo.guitarworld.network.ParamBeanHandler;
import pers.geolo.guitarworld.network.api.MusicApi;
import pers.geolo.guitarworld.network.callback.BaseCallback;
import pers.geolo.guitarworld.network.callback.DataCallback;
import pers.geolo.guitarworld.network.param.MusicParam;
import pers.geolo.guitarworld.network.param.MusicScoreImageParam;
import pers.geolo.guitarworld.network.param.MusicScoreParam;

import java.util.HashMap;
import java.util.List;

public class MusicModel {

    MusicApi musicApi = HttpClient.getService(MusicApi.class);

    public void getMusic(long musicId, DataListener<Music> listener) {
        MusicParam param = new MusicParam();
        param.setMusicId(musicId);
        musicApi.getMusicList(ParamBeanHandler.handle(param))
                .enqueue(new BaseCallback<List<Music>>() {
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
        musicApi.getMusicList(filter).enqueue(new DataCallback<>(listener));
    }


    public void getMusicScoreList(long musicId, DataListener<List<MusicScore>> listener) {
        MusicScoreParam param = new MusicScoreParam();
        param.setMusicId(musicId);
        musicApi.getMusicScore(ParamBeanHandler.handle(param))
                .enqueue(new DataCallback<>(listener));
    }

    public void getMusicScoreImage(long musicScoreId, DataListener<List<String>> listener) {
        MusicScoreImageParam param = new MusicScoreImageParam();
        param.setMusicScoreId(musicScoreId);
        musicApi.getMusicScoreImages(ParamBeanHandler.handle(param))
                .enqueue(new DataCallback<>(listener));
    }

    public void getMusicScore(long musicScoreId, DataListener<List<MusicScore>> listener) {
        MusicScoreParam param = new MusicScoreParam();
        param.setMusicScoreId(musicScoreId);
        musicApi.getMusicScore(ParamBeanHandler.handle(param))
                .enqueue(new DataCallback<>(listener));
    }
}
