import java.util.ArrayList;

public class SistemaCadastro {
    private ArrayList<Aluno> alunos;

    public SistemaCadastro() {
        alunos = new ArrayList<>();
    }

    public void adicionarAluno(Aluno aluno) {
        alunos.add(aluno);
        System.out.println("Aluno adicionado com sucesso.");
    }

    public void listarAlunos() {
        if (alunos.isEmpty()) {
            System.out.println("Nenhum aluno cadastrado.");
        } else {
            System.out.println("\n--- Lista de Alunos ---");
            for (Aluno aluno : alunos) {
                System.out.println(aluno);
            }
        }
    }
}
