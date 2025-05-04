package com.example.tunehub.POJO;

public class Lista {
    private Nodo Testa;

    public Lista() {
        Testa = null;
    }

    private Nodo creaNodo(Song canzone, Nodo link) {
        Nodo nuovoNodo = new Nodo(canzone);
        nuovoNodo.setLink(link);
        return nuovoNodo;
    }

    // Inserimento in testa
    public void inserisciInTesta(Song canzone) {
        Testa = creaNodo(canzone, Testa);
    }

    public void cancInTesta() {
        if (Testa == null) {
            System.out.println("Lista vuota");
            return;
        }
        Testa = Testa.getLink();
    }

    // Inserimento in coda
    public void inserisciInCoda(Song canzone) {
        if (Testa == null) {
            inserisciInTesta(canzone);
            return;
        }

        Nodo pCurr = Testa;
        while (pCurr.getLink() != null) {
            pCurr = pCurr.getLink();
        }
        pCurr.setLink(creaNodo(canzone, null));
    }

    // Verifica se la lista è vuota
    public boolean isEmpty() {
        return Testa == null;
    }

    // Ottieni la lista delle canzoni (senza conversioni in List)
    public Nodo getTesta() {
        return Testa;
    }

    // Restituisci la lista di canzoni
    public String elencoNodi() {
        return visita(Testa);
    }

    private String visita(Nodo currNodo) {
        StringBuilder sb = new StringBuilder();
        while (currNodo != null) {
            sb.append(currNodo.getInfo().getTitle()).append(" -> ");
            currNodo = currNodo.getLink();
        }
        sb.append("null");
        return sb.toString();
    }

    // Metodo per ottenere tutte le canzoni
    public Nodo getCanzoni() {
        return Testa;
    }
}
