public class Aluno {
    private String nome;
    private String matricula;
    private double[] notas;

    public Aluno(String nome, String matricula, double[] notas) {
        this.nome = nome;
        this.matricula = matricula;
        this.notas = notas;
    }

    public double calcularMedia() {
        double soma = 0;
        for (double nota : notas) {
            soma += nota;
        }
        return soma / notas.length;
    }

    public String getStatus() {
        return calcularMedia() >= 7.0 ? "Aprovado" : "Reprovado";
    }

    public String getNome() {
        return nome;
    }

    public String getMatricula() {
        return matricula;
    }

    @Override
    public String toString() {
        return "Aluno: " + nome +
                " | Matrícula: " + matricula +
                " | Média: " + String.format("%.2f", calcularMedia()) +
                " | Status: " + getStatus();
    }
}
