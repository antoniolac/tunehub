package com.example.tunehub.Restcontroller;

import com.example.tunehub.SpotifyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/music")
public class SpotifyController {

    private final SpotifyService spotifyService;

    public SpotifyController(SpotifyService spotifyService) {
        this.spotifyService = spotifyService;
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchTracks(@RequestParam("title") String title) {
        if (title == null || title.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("La query di ricerca non può essere vuota");
        }
        try {
            return ResponseEntity.ok(spotifyService.searchTracks(title));
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Errore durante la ricerca");
        }
    }
}