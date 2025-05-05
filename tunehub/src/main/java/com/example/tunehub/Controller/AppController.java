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
    public String search(@ModelAttribute("searchQuery") String query, Model model) {
        // Verifica che la query non sia vuota
        if (query == null || query.trim().isEmpty()) {
            model.addAttribute("message", "Inserisci una query per la ricerca.");
            return "research"; // Mostra il messaggio e torna alla stessa pagina
        }

        // Chiamata all'API REST per la ricerca delle tracce
        String url = "http://localhost:8080/api/music/search?title=" + query;

        // Esegui la chiamata GET per ottenere i risultati
        try {
            List<Map<String, Object>> tracks = restTemplate.getForObject(url, List.class);
            if (tracks != null && !tracks.isEmpty()) {
                model.addAttribute("tracks", tracks); // Passa i risultati delle tracce alla vista
            } else {
                model.addAttribute("message", "Nessuna canzone trovata.");
            }
        } catch (Exception e) {
            model.addAttribute("message", "Errore nella ricerca delle tracce.");
        }

        return "research"; // Ritorna alla stessa pagina (research.html)
    }

}
