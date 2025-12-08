package com.musicupload.music.clone.restcontrollers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

@RestController
@RequestMapping("/testing")
@RequiredArgsConstructor
public class TestingRestController {

    private final WebClient webClient;

    @GetMapping(path = "/hello")
    public String getFromTesting(){
        return webClient.get()
                .uri("/base64/SFRUUEJJTiBpcyBhd2Vzb21l")
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

}
