package io.micro.musicinfoservice.models;

public class MusicInfo {

    private String musicId;

    private String name;

    public MusicInfo() {
    }

    public MusicInfo(String musicId, String name) {
        this.musicId = musicId;
        this.name = name;
    }

    public String getMusicId() {
        return musicId;
    }

    public void setMusicId(String musicId) {
        this.musicId = musicId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
