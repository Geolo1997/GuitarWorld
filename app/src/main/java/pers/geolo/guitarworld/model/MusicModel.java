package pers.geolo.guitarworld.model;

import pers.geolo.guitarworld.entity.DataListener;
import pers.geolo.guitarworld.entity.MusicScore;

import java.util.ArrayList;
import java.util.List;

public class MusicModel {

    public void getMusicScoreList(Long musicId, DataListener<List<MusicScore>> listener) {
        // TODO 获取乐谱列表
        ArrayList<MusicScore> list = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            MusicScore musicScore = new MusicScore();
            musicScore.setId((long) i);
            musicScore.setAuthor("桀骜");
            musicScore.setName("乐谱#" + i);
            list.add(musicScore);
        }
        listener.onReturn(list);
    }
}
