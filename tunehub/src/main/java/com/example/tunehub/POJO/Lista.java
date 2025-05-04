package com.example.tunehub.POJO;

public class Lista {
    private Nodo testa;

    // Costruttore per inizializzare la lista
    public Lista() {
        testa = null;
    }

    // Crea un nodo che contiene una canzone (oggetto Song)
    private Nodo creaNodo(Song song, Nodo link) {
        Nodo nuovoNodo = new Nodo(song);
        nuovoNodo.setLink(link);
        return nuovoNodo;
    }

    // Aggiungi una canzone in testa alla lista
    public void inserisciInTesta(Song song) {
        testa = creaNodo(song, testa);
    }

    // Aggiungi una canzone in coda alla lista
    public void inserisciInCoda(Song song) {
        if (testa == null) {
            inserisciInTesta(song);  // Se la lista è vuota, aggiunge in testa
            return;
        }

        Nodo corrente = testa;
        while (corrente.getLink() != null) {
            corrente = corrente.getLink();  // Scorre fino all'ultimo nodo
        }
        corrente.setLink(creaNodo(song, null));  // Aggiunge la canzone in coda
    }

    // Rimuovi la canzone in testa
    public void cancInTesta() {
        if (testa == null) {
            System.out.println("Lista vuota");
            return;
        }
        testa = testa.getLink();  // Rimuove il nodo che sta in testa
    }

    // Rimuovi la canzone in coda
    public void cancInCoda() {
        if (testa == null) {
            System.out.println("Lista vuota");
            return;
        }

        if (testa.getLink() == null) {
            testa = null;  // Se c'è solo un nodo, lo rimuove
            return;
        }

        Nodo corrente = testa;
        Nodo precedente = null;

        // Scorre fino all'ultimo nodo
        while (corrente.getLink() != null) {
            precedente = corrente;
            corrente = corrente.getLink();
        }

        precedente.setLink(null);  // Rimuove il nodo in coda
    }

    // Aggiungi una canzone in una posizione specifica
    public void inserisciInPosizione(Song song, int posizione) {
        if (posizione < 0) {
            System.out.println("Posizione non valida");
            return;
        }

        if (posizione == 0) {
            inserisciInTesta(song);  // Se la posizione è 0, inserisce in testa
            return;
        }

        Nodo corrente = testa;
        int contatore = 0;

        // Scorre fino alla posizione specificata
        while (corrente != null && contatore < posizione - 1) {
            corrente = corrente.getLink();
            contatore++;
        }

        // Aggiunge la canzone alla posizione corretta
        if (corrente != null) {
            Nodo nuovoNodo = creaNodo(song, corrente.getLink());
            corrente.setLink(nuovoNodo);
        } else {
            System.out.println("Posizione oltre la lunghezza della lista");
        }
    }

    // Restituisce la rappresentazione della lista di canzoni
    public String elencoNodi() {
        return visita(testa);
    }

    // Metodo privato che scorre i nodi e restituisce l'elenco delle canzoni
    private String visita(Nodo nodoCorrente) {
        StringBuilder sb = new StringBuilder();
        while (nodoCorrente != null) {
            sb.append(nodoCorrente.getInfo().getTitle())  // Usa il metodo getTitle() di Song
                    .append(" -> ");
            nodoCorrente = nodoCorrente.getLink();
        }
        sb.append("null");
        return sb.toString();
    }

    public Nodo getTesta() {
        return testa;
    }
}
