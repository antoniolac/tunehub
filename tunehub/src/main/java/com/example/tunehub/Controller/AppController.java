package com.example.tunehub.Controller;

import com.example.tunehub.POJO.Playlist;
import com.example.tunehub.POJO.Song;
import com.example.tunehub.SpotifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class AppController {

    private final List<Playlist> playlistList = new ArrayList<>();  // Lista delle playlist
    private final RestTemplate restTemplate = new RestTemplate();

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("playlistForm", new Playlist("")); // Oggetto per il form
        model.addAttribute("playlists", getNomiPlaylist());  // Passa solo i nomi delle playlist
        return "index";  // index.html
    }

    @PostMapping("/playlist")
    public String creaPlaylist(@ModelAttribute("playlistForm") Playlist nuova, Model model) {
        playlistList.add(nuova);  // Aggiungi la nuova playlist alla lista
        return "redirect:/";  // Torna alla home dopo aver creato la playlist
    }

    private List<String> getNomiPlaylist() {
        List<String> nomi = new ArrayList<>();
        for (Playlist p : playlistList) {
            try {
                String encoded = java.net.URLEncoder.encode(p.getNomePlaylist(), java.nio.charset.StandardCharsets.UTF_8.toString());
                nomi.add(encoded);  // Aggiungi la versione codificata
            } catch (Exception e) {
                nomi.add(p.getNomePlaylist());  // In caso di errore, aggiungi il nome normale
            }
        }
        return nomi;
    }

    @GetMapping("/playlist/{nomePlaylist}")
    public String mostraCanzoni(@PathVariable("nomePlaylist") String nomePlaylist, Model model) {
        // Decodifica il nomePlaylist se è stato codificato nell'URL
        try {
            nomePlaylist = java.net.URLDecoder.decode(nomePlaylist, java.nio.charset.StandardCharsets.UTF_8.toString());
        } catch (Exception e) {
            model.addAttribute("message", "Errore nella decodifica del nome della playlist.");
            return "index";  // Torna alla home se c'è un errore
        }

        Playlist playlistSelezionata = null;

        // Cerca la playlist con il nome specificato
        for (Playlist p : playlistList) {
            if (p.getNomePlaylist().equals(nomePlaylist)) {
                playlistSelezionata = p;
                break;
            }
        }

        if (playlistSelezionata == null) {
            model.addAttribute("message", "Playlist non trovata");
            return "index";  // Torna alla home se la playlist non viene trovata
        }

        // Passa l'oggetto playlist alla vista solo se non è null
        model.addAttribute("playlist", playlistSelezionata);
        if (playlistSelezionata.getListaCanzoni().isEmpty()) {
            model.addAttribute("message", "Questa playlist è vuota.");
        } else {
            model.addAttribute("canzoni", playlistSelezionata.getListaCanzoni());
        }

        return "playlistSong";  // Mostra la vista con le canzoni
    }



    @Autowired
    private SpotifyService spotifyService;

    // Endpoint per la ricerca delle tracce
    @PostMapping("/research")
    public String ricercaCanzoni(@RequestParam("searchQuery") String searchQuery, Model model) {
        // Cerca canzoni (questa parte dipende dalla tua implementazione della ricerca)
        List<Map<String, Object>> tracks = spotifyService.searchTracks(searchQuery);  // Usa il servizio per cercare tracce

        // Passa i risultati alla vista
        model.addAttribute("tracks", tracks);
        model.addAttribute("playlists", getNomiPlaylist());  // Passa le playlist esistenti
        return "research";  // La vista che mostra i risultati della ricerca
    }

    // Endpoint per aggiungere una traccia alla playlist
    @PostMapping("/playlist/{nomePlaylist}/add")
    public String aggiungiCanzone(@PathVariable("nomePlaylist") String nomePlaylist,
                                  @RequestParam("trackName") String trackName,
                                  @RequestParam("trackArtist") String trackArtist,
                                  Model model) {
        // Cerca la playlist per nome
        Playlist playlist = playlistList.stream()
                .filter(p -> p.getNomePlaylist().equals(nomePlaylist))
                .findFirst()
                .orElse(null);

        if (playlist == null) {
            model.addAttribute("message", "Playlist non trovata!");
            return "redirect:/";  // Se la playlist non esiste, reindirizza
        }

        // Aggiungi la canzone alla playlist usando solo il nome e l'artista
        playlist.aggiungiCanzone(trackName, trackArtist);  // Aggiungi la canzone alla playlist
        model.addAttribute("playlist", playlist);
        model.addAttribute("message", "Canzone aggiunta alla playlist!");

        return "playlistDetails";  // Dettaglio della playlist aggiornata
    }


}
