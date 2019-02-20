package io.micro.musicinfoservice.resources;

import io.micro.musicinfoservice.models.MusicInfo;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/musics")
public class MusicResource {

    @RequestMapping("/{musicId}")
    public MusicInfo getMusicInfo(@PathVariable("musicId") String userId) {
        return new MusicInfo(userId, "String in a Strange Land");
    }

}
