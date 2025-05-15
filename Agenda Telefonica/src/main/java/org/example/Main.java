package org.example;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Agenda agenda = new Agenda();
        Scanner scanner = new Scanner(System.in);
        int opcao;

        do {
            System.out.println("\n--- AGENDA TELEFÔNICA ---");
            System.out.println("1. Adicionar contato");
            System.out.println("2. Buscar contato");
            System.out.println("3. Listar contatos");
            System.out.println("4. Remover contato");
            System.out.println("5. Sair");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine(); // consumir o \n

            switch (opcao) {
                case 1:
                    System.out.print("Nome: ");
                    String nomeAdd = scanner.nextLine();
                    System.out.print("Telefone: ");
                    String telefone = scanner.nextLine();
                    agenda.adicionarContato(nomeAdd, telefone);
                    break;
                case 2:
                    System.out.print("Nome para buscar: ");
                    String nomeBusca = scanner.nextLine();
                    agenda.buscarContato(nomeBusca);
                    break;
                case 3:
                    agenda.listarContatos();
                    break;
                case 4:
                    System.out.print("Nome para remover: ");
                    String nomeRemover = scanner.nextLine();
                    agenda.removerContato(nomeRemover);
                    break;
                case 5:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida.");
            }

        } while (opcao != 5);

        scanner.close();
    }
}
