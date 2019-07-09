package pers.geolo.guitarworld.model;

import pers.geolo.guitarworld.entity.DataListener;
import pers.geolo.guitarworld.entity.Music;

import java.util.ArrayList;
import java.util.List;

public class MusicModel {

    public void getMusicScoreList(Long musicId, DataListener<List<Music>> listener) {
        // TODO 获取乐谱列表
        ArrayList<Music> list = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Music music = new Music();
            music.setAuthor("桀骜");
            music.setTitle("乐谱#" + i);
            list.add(music);
        }
        listener.onReturn(list);
    }
}
