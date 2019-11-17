package pers.geolo.guitarworldserver.controller.param;

/**
 * @author 桀骜(Geolo)
 * @version 1.0
 * @date 2019/7/17
 */
public class MusicParam {

    private Long musicId;
    private String tag;

    public Long getMusicId() {
        return musicId;
    }

    public void setMusicId(Long musicId) {
        this.musicId = musicId;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
