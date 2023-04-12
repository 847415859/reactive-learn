package com.tk.webflux.template.webfluxtemplate.controller;

import com.tk.webflux.template.webfluxtemplate.entity.Song;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.thymeleaf.spring5.context.webflux.ReactiveDataDriverContextVariable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description:
 * @Date : 2023/04/12 19:19
 * @Auther : tiankun
 */
@Controller
public class SongController {

    @RequestMapping("/play-list-view-ftl")
    public Mono<String> getPlaylist(final Model model) {
        List<Song> songs = new ArrayList<>();
        Song song = null;
        for (int i = 0; i < 10; i++) {
            song = new Song("曲目" + i, "张三" + i, "1001" + i, "专辑1" + (i %
                    3));
            songs.add(song);
        }
        final Flux<Song> playlistStream = Flux.fromIterable(songs).delayElements(Duration.ofMillis(500));
        return playlistStream
                .collectList()
                .doOnNext(list -> model.addAttribute("playList", list))
                .then(Mono.just("/freemarker/play-list-view"));
    }


    @RequestMapping("/play-list-view-thy")
    public String view(final Model model) {
        List<Song> songs = new ArrayList<>();
        Song song = null;
        for (int i = 0; i < 100; i++) {
            song = new Song("曲目" + i, "张三" + i, "1001" + i, "专辑1" + (i %
                    3));
            songs.add(song);
        }
        final Flux<Song> playlistStream = Flux.fromIterable(songs).delayElements(Duration.ofMillis(500));
        model.addAttribute(
                "playList",
                new ReactiveDataDriverContextVariable(playlistStream, 1, 1)
        );
        return "thymeleaf/play-list-view";
    }

}
