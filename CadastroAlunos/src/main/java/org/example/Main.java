import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        SistemaCadastro sistema = new SistemaCadastro();
        int opcao;

        do {
            System.out.println("\n--- MENU ---");
            System.out.println("1. Cadastrar aluno");
            System.out.println("2. Listar alunos");
            System.out.println("3. Sair");
            System.out.print("Opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine(); // consumir quebra de linha

            switch (opcao) {
                case 1:
                    System.out.print("Nome: ");
                    String nome = scanner.nextLine();

                    System.out.print("Matrícula: ");
                    String matricula = scanner.nextLine();

                    double[] notas = new double[3];
                    for (int i = 0; i < 3; i++) {
                        System.out.print("Nota " + (i + 1) + ": ");
                        notas[i] = scanner.nextDouble();
                    }
                    scanner.nextLine(); // consumir \n

                    Aluno aluno = new Aluno(nome, matricula, notas);
                    sistema.adicionarAluno(aluno);
                    break;

                case 2:
                    sistema.listarAlunos();
                    break;

                case 3:
                    System.out.println("Encerrando o sistema...");
                    break;

                default:
                    System.out.println("Opção inválida.");
            }
        } while (opcao != 3);

        scanner.close();
    }
}
