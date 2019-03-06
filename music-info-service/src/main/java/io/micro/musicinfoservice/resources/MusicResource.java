package io.micro.musicinfoservice.resources;

import io.micro.musicinfoservice.models.MusicInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/musics")
public class MusicResource {

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping("/{musicId}")
    public MusicInfo getMusicInfo(@PathVariable("musicId") String userId) {
        MusicInfo musicInfo = restTemplate.getForObject("http://mongodb-service/db/musicInfo/find/" + userId, MusicInfo.class);

        return musicInfo;
    }

}
