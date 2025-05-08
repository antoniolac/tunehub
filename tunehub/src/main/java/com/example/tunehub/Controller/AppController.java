package com.example.tunehub.Controller;

import com.example.tunehub.POJO.Playlist;
import com.example.tunehub.POJO.PlaylistManager;
import com.example.tunehub.SpotifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class AppController {

    @Autowired
    private SpotifyService spotifyService;

    private List<Playlist> getPlaylistsFromManager() {
        try {
            return PlaylistManager.caricaPlaylist();
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @GetMapping("/")
    public String index(Model model) {
        // Carica le playlist dal file CSV
        List<Playlist> playlists = getPlaylistsFromManager();

        // Passa i nomi delle playlist esistenti
        model.addAttribute("playlists", playlists.stream()
                .map(Playlist::getNomePlaylist)
                .collect(Collectors.toList()));
        // Assicurati che il modello includa l'oggetto playlistForm
        model.addAttribute("playlistForm", new Playlist());
        return "index";
    }

    @PostMapping("/playlist")
    public String createPlaylist(@ModelAttribute("playlistForm") Playlist playlistForm, Model model) {
        try {
            // Carica le playlist esistenti
            List<Playlist> playlists = getPlaylistsFromManager();

            String nomePlaylist = playlistForm.getNomePlaylist();

            // Verifica se la playlist esiste già
            boolean exists = playlists.stream()
                    .anyMatch(p -> p.getNomePlaylist().equals(nomePlaylist));

            if (!exists) {
                // Crea nuova playlist vuota
                Playlist newPlaylist = new Playlist(nomePlaylist);
                playlists.add(newPlaylist);
                // Salva la lista aggiornata
                PlaylistManager.salvaPlaylist(playlists);
            }

        } catch (IOException e) {
            e.printStackTrace();
            model.addAttribute("error", "Errore nella creazione della playlist: " + e.getMessage());
            return "index";
        }

        return "redirect:/";
    }

    @GetMapping("/playlist/{nomePlaylist}")
    public String viewPlaylist(@PathVariable String nomePlaylist, Model model) {
        // Carica le playlist esistenti
        List<Playlist> playlists = getPlaylistsFromManager();

        // Trova la playlist richiesta
        Playlist playlist = playlists.stream()
                .filter(p -> p.getNomePlaylist().equals(nomePlaylist))
                .findFirst()
                .orElse(new Playlist(nomePlaylist));

        model.addAttribute("playlist", playlist);
        return "playlistSong";
    }

    @PostMapping("/research")
    public String searchSongs(@RequestParam String searchQuery, Model model) {
        try {
            // Effettua la ricerca tramite SpotifyService
            List<Map<String, Object>> tracks = spotifyService.searchTracks(searchQuery);
            model.addAttribute("tracks", tracks);

            // Passa anche i nomi delle playlist disponibili per il dropdown
            List<Playlist> playlists = getPlaylistsFromManager();
            model.addAttribute("playlists", playlists.stream()
                    .map(Playlist::getNomePlaylist)
                    .collect(Collectors.toList()));

        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Errore durante la ricerca: " + e.getMessage());
        }
        return "research";
    }

    @PostMapping("/addSongToPlaylist")
    public String addSongToPlaylist(
            @RequestParam String trackName,
            @RequestParam String trackArtist,
            @RequestParam String nomePlaylist,
            Model model) {

        try {
            // Aggiungi la canzone alla playlist tramite PlaylistManager
            PlaylistManager.aggiungiCanzoneAllaPlaylist(nomePlaylist, trackName, trackArtist);

            // Carica la playlist aggiornata per mostrarla
            List<Playlist> playlists = getPlaylistsFromManager();
            Playlist updatedPlaylist = playlists.stream()
                    .filter(p -> p.getNomePlaylist().equals(nomePlaylist))
                    .findFirst()
                    .orElseThrow(() -> new IOException("Playlist non trovata"));

            model.addAttribute("playlist", updatedPlaylist);
            model.addAttribute("message", "Canzone aggiunta alla playlist con successo!");

        } catch (IOException e) {
            e.printStackTrace();
            model.addAttribute("error", "Errore nell'aggiunta della canzone: " + e.getMessage());

            // Crea un oggetto playlist vuoto per evitare errori nel template
            Playlist emptyPlaylist = new Playlist(nomePlaylist);
            model.addAttribute("playlist", emptyPlaylist);
        }

        return "playlistSong";
    }

    @PostMapping("/removeSongFromPlaylist")
    public String removeSongFromPlaylist(
            @RequestParam String nomePlaylist,
            @RequestParam String trackName,
            Model model) {

        try {
            // Rimuovi la canzone dalla playlist tramite PlaylistManager
            PlaylistManager.rimuoviCanzoneDallaPlaylist(nomePlaylist, trackName);

            // Carica la playlist aggiornata per mostrarla
            List<Playlist> playlists = getPlaylistsFromManager();
            Playlist updatedPlaylist = playlists.stream()
                    .filter(p -> p.getNomePlaylist().equals(nomePlaylist))
                    .findFirst()
                    .orElseThrow(() -> new IOException("Playlist non trovata"));

            model.addAttribute("playlist", updatedPlaylist);
            model.addAttribute("message", "Canzone rimossa con successo!");

        } catch (IOException e) {
            e.printStackTrace();
            model.addAttribute("error", "Errore nella rimozione della canzone: " + e.getMessage());

            // Crea un oggetto playlist vuoto per evitare errori nel template
            Playlist emptyPlaylist = new Playlist(nomePlaylist);
            model.addAttribute("playlist", emptyPlaylist);
        }

        return "playlistSong";
    }

    @PostMapping("/deletePlaylist")
    public String deletePlaylist(@RequestParam String nomePlaylist, Model model) {
        try {
            // Elimina la playlist tramite PlaylistManager
            PlaylistManager.eliminaPlaylist(nomePlaylist);
            return "redirect:/";
        } catch (IOException e) {
            e.printStackTrace();

            // In caso di errore, torna alla pagina della playlist con un messaggio di errore
            List<Playlist> playlists = getPlaylistsFromManager();
            Playlist playlist = playlists.stream()
                    .filter(p -> p.getNomePlaylist().equals(nomePlaylist))
                    .findFirst()
                    .orElse(new Playlist(nomePlaylist));

            model.addAttribute("playlist", playlist);
            model.addAttribute("error", "Errore nell'eliminazione della playlist: " + e.getMessage());
            return "playlistSong";
        }
    }
}