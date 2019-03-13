package io.micro.musiccatalogservice.resources;

import io.micro.musiccatalogservice.models.MusicCatalogItem;
import io.micro.musiccatalogservice.models.MusicInfo;
import io.micro.musiccatalogservice.models.UserRating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catalog")
public class MusicCatalogResource {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private WebClient.Builder webClientBuilder;

    @RequestMapping("/{userId}")
    public List<MusicCatalogItem> getMusicCatalog(@PathVariable("userId") Integer userId) {

        UserRating userRating = restTemplate.getForObject("http://music-rating-service/ratings/users/" + userId, UserRating.class);

        return userRating.getRatings().stream().map(rating -> {
            MusicInfo musicInfo = restTemplate.getForObject("http://music-info-service/musics/" + rating.getMusicId(), MusicInfo.class);
/*            MusicInfo musicInfo = webClientBuilder.build()
                    .get()
                    .uri("http://localhost:8082/musics/" + rating.getMovieId())
                    .retrieve()
                    .bodyToMono(MusicInfo.class)
                    .block();*/

            return new MusicCatalogItem(musicInfo.getName(), "desc", rating.getRating());
        }).collect(Collectors.toList());
    }
}
