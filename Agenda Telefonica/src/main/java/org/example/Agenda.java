package org.example;

import java.util.HashMap;

public class Agenda {
    private HashMap<String, Contato> contatos;

    public Agenda() {
        contatos = new HashMap<>();
    }

    public void adicionarContato(String nome, String telefone) {
        contatos.put(nome, new Contato(nome, telefone));
        System.out.println("Contato adicionado: " + nome);
    }

    public void buscarContato(String nome) {
        Contato contato = contatos.get(nome);
        if (contato != null) {
            System.out.println("Contato encontrado: " + contato);
        } else {
            System.out.println("Contato não encontrado.");
        }
    }

    public void listarContatos() {
        if (contatos.isEmpty()) {
            System.out.println("Agenda vazia.");
        } else {
            System.out.println("Lista de contatos:");
            for (Contato contato : contatos.values()) {
                System.out.println(contato);
            }
        }
    }

    public void removerContato(String nome) {
        if (contatos.remove(nome) != null) {
            System.out.println("Contato removido: " + nome);
        } else {
            System.out.println("Contato não encontrado.");
        }
    }
}
