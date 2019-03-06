package io.micro.mongodbservice.document;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class MusicInfo {
    @Id
    private Integer musicId;

    private String name;

    public MusicInfo(Integer musicId, String name) {
        this.musicId = musicId;
        this.name = name;
    }

    public Integer getMusicId() {
        return musicId;
    }

    public void setMusicId(Integer musicId) {
        this.musicId = musicId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
