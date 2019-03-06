package io.micro.mongodbservice.resource;

import io.micro.mongodbservice.document.MusicInfo;
import io.micro.mongodbservice.repository.MusicInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/db/musicInfo")
public class MusicInfoResource {

    @Autowired
    private MusicInfoRepository musicInfoRepository;

    @GetMapping("/all")
    public List<MusicInfo> getAll() {
        return musicInfoRepository.findAll();
    }

    @GetMapping("/find/{id}")
    public Optional<MusicInfo> getById(@PathVariable("id") Integer id) {
        return musicInfoRepository.findById(id);
    }

    @PostMapping(value = "/save")
    public void save(@RequestBody MusicInfo musicInfo) {
        musicInfoRepository.save(musicInfo);
    }
}
