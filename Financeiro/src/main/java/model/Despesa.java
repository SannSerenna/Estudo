package model;

import java.time.LocalDate;

public class Despesa extends Lancamento {
    private String categoria;

    public Despesa(String descricao, double valor, LocalDate data, String categoria) {
        super(descricao, valor, data);
        this.categoria = categoria;
    }

    @Override
    public String getTipo() {
        return "Despesa";
    }

    public String getCategoria() {
        return categoria;
    }
}
