package model;

import java.time.LocalDate;

public abstract class Lancamento {
    protected String descricao;
    protected double valor;
    protected LocalDate data;

    public Lancamento(String descricao, double valor, LocalDate data) {
        this.descricao = descricao;
        this.valor = valor;
        this.data = data;
    }

    public abstract String getTipo(); // "Receita" ou "Despesa"

    public String getDescricao() {
        return descricao;
    }

    public double getValor() {
        return valor;
    }

    public LocalDate getData() {
        return data;
    }
}
