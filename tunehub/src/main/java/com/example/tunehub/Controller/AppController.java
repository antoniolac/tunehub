package com.example.tunehub.Controller;

import com.example.tunehub.POJO.Playlist;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class AppController {
    private final List<Playlist> playlistList = new ArrayList<>();  // Lista delle playlist

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
            nomi.add(p.getNomePlaylist());  // Aggiungi il nome della playlist alla lista
        }
        return nomi;
    }

    // Quando clicchi su una playlist, mostra le canzoni aggiunte
    @GetMapping("/playlist/{nomePlaylist}")
    public String mostraCanzoni(@PathVariable("nomePlaylist") String nomePlaylist, Model model) {
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
            return "playlist_error";  // Se la playlist non viene trovata, visualizza la pagina di errore
        }

        // Se la playlist è vuota
        if (playlistSelezionata.getListaCanzoni().isEmpty()) {
            model.addAttribute("message", "Questa playlist è vuota.");
        } else {
            // Se la playlist contiene canzoni, passiamo le canzoni al modello
            model.addAttribute("playlist", playlistSelezionata);
            model.addAttribute("canzoni", playlistSelezionata.getListaCanzoni());  // Lista delle canzoni
        }

        return "playlistSong";
    }
}
