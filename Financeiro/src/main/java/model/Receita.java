package model;

import java.time.LocalDate;

public class Receita extends Lancamento {
    public Receita(String descricao, double valor, LocalDate data) {
        super(descricao, valor, data);
    }

    @Override
    public String getTipo() {
        return "Receita";
    }
}
