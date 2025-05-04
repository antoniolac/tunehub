package com.example.tunehub.Restcontroller;

import com.example.tunehub.SpotifyService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/music")
public class SpotifyController {

    private final SpotifyService spotifyService;

    public SpotifyController(SpotifyService spotifyService) {
        this.spotifyService = spotifyService;
    }

    // Endpoint per cercare le tracce per titolo
    @GetMapping("/search")
    public List<Map<String, Object>> searchTracks(@RequestParam("title") String title) {
        // Usa il SpotifyService per cercare le tracce
        return spotifyService.searchTracks(title); // Restituisce la lista delle tracce in formato JSON
    }
}
